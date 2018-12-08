package com.joe.itinerary.job;

import static com.joe.itinerary.lucene.airticket.AirTicketIndexer.DEP_CITY;
import static com.joe.itinerary.lucene.airticket.AirTicketIndexer.PRICE;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joe.itinerary.dao.ProductMapper;
import com.joe.itinerary.lucene.airticket.AirTicketIndexer;
import com.joe.itinerary.lucene.airticket.AirTicketSearcher;
import com.joe.itinerary.model.Product;
import com.joe.itinerary.request.AirTicketRequest;
import com.joe.itinerary.response.JDAirTicketResponse;
import com.joe.itinerary.response.ResponseWrapper;
import com.joe.itinerary.service.AirTicketService;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PreDestroy;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Jobs {
  private static final long ONE_Minute = 60 * 1000;
  private static final SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmssSSS");

  private static String indexDir = "/Users/houfang/Lucene/AirTicket/Index";

  private AirTicketService airTicketService;
  private ProductMapper productMapper;
  private AirTicketIndexer airTicketIndexer;
  private AirTicketSearcher airTicketSearcher;

  @Autowired
  public Jobs(AirTicketService airTicketService, ProductMapper productMapper) throws IOException {
    this.airTicketService = airTicketService;
    this.productMapper = productMapper;
    this.airTicketIndexer = new AirTicketIndexer(indexDir);
    this.airTicketSearcher = new AirTicketSearcher(indexDir);
  }

  @Scheduled(fixedDelay = ONE_Minute)
  public void fixedDelayJob() {
    List<Product> products = productMapper.selectAll();
    java.util.Date nowDate = new java.util.Date();
    products
        .stream()
        .forEach(
            product -> {
              if (product.getDepDate().getTime() < nowDate.getTime()) {
                productMapper.removeProduct(product.getId());
                System.out.println(
                    new Date()
                        + " "
                        + f.format(new Date())
                        + " remove product id:"
                        + product.getId());
              }
            });
  }

  @Scheduled(fixedRate = ONE_Minute)
  public void fixedRateJob() throws IOException, ParseException {
    List<Product> products = productMapper.selectAll();
    if (products.size() == 0) {
      return;
    }

    Product product = products.get(0);
    TopDocs topDocs =
        airTicketSearcher.search(
            AirTicketRequest.builder()
                .depCity(product.getDepCity())
                .arrCity(product.getArrCity())
                .depDate(product.getDepDate())
                .build());

    for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
      Document doc = airTicketSearcher.doc(scoreDoc.doc);
      System.out.println(doc.get(DEP_CITY) + ":" + doc.get(PRICE));
    }
  }

  @Scheduled(cron = "0 0 3 * * ?")
  public void cronJob() throws IOException {
    List<Product> products = productMapper.selectAll();
    for (Product product : products) {
      AirTicketRequest request =
          AirTicketRequest.builder()
              .arrCity(product.getArrCity())
              .depCity(product.getDepCity())
              .arrDate(product.getDepDate())
              .depDate(product.getDepDate())
              .build();

      String resp = airTicketService.search(request);
      Gson gson = new Gson();
      ResponseWrapper<JDAirTicketResponse> responseWrapper =
          gson.fromJson(resp, new TypeToken<ResponseWrapper<JDAirTicketResponse>>() {}.getType());
      JDAirTicketResponse jdAirTicketResponse = responseWrapper.getData();
      if (jdAirTicketResponse.getFlights() == null) {
        return;
      }
      jdAirTicketResponse
          .getFlights()
          .stream()
          .forEach(
              jdAirFlightResponse -> {
                try {
                  airTicketIndexer.indexAirTicket(jdAirFlightResponse);
                } catch (IOException e) {
                  e.printStackTrace();
                }
              });
    }
    System.out.println(f.format(new Date()) + " >>cron执行....");
  }

  @PreDestroy
  public void close() throws IOException {
    this.airTicketIndexer.close();
  }
}
