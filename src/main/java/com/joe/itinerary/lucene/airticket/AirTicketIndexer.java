package com.joe.itinerary.lucene.airticket;

import com.joe.itinerary.lucene.data.AirTicketData;
import com.joe.itinerary.request.AirTicketRequest;
import com.joe.itinerary.response.JDAirFlightResponse;
import com.joe.itinerary.utils.Util;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import javax.annotation.PreDestroy;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Service;

@Service
public class AirTicketIndexer extends BaseAirTicketIndexer {
  public static final String AIRWAY = "airway";
  public static final String ARR_CITY = "arrCity";
  public static final String DEP_CITY = "depCity";
  public static final String ARR_CITY_RESP = "arrCityResp";
  public static final String DEP_CITY_RESP = "depCityResp";
  public static final String DEP_DATE = "depDate";
  public static final String REQUEST_DATE = "requestDate";
  public static final String PRICE = "price";
  private static String indexDir = "/Users/houfang/Lucene/AirTicket/Index";
  private IndexWriter writer;

  public AirTicketIndexer() throws IOException {
    // this directory will contain the indexes
    Directory indexDirectory = FSDirectory.open(new File(indexDir).toPath());

    // create the indexer
    writer = new IndexWriter(indexDirectory, new IndexWriterConfig(new StandardAnalyzer()));
  }
  //
  //  public AirTicketIndexer(String indexDirectoryPath, boolean deleteAll) throws IOException {
  //
  //    if (deleteAll) {
  //      writer.deleteAll();
  //    }
  //  }

  public void indexAirTicket(AirTicketData airTicketData) throws IOException {
    Document document = getDocument(airTicketData);
    writer.addDocument(document);
  }

  private Document getDocument(AirTicketData airTicketData) {
    JDAirFlightResponse jdAirFlightResponse = airTicketData.getResponse();
    AirTicketRequest airTicketRequest = airTicketData.getRequest();
    Document document = new Document();

    Field airwayField =
        new StringField(AIRWAY, jdAirFlightResponse.getAirwaysCn(), Field.Store.YES);
    Field arrCityField = new StringField(ARR_CITY, airTicketRequest.getArrCity(), Field.Store.YES);
    Field depCityField = new StringField(DEP_CITY, airTicketRequest.getDepCity(), Field.Store.YES);
    Field arrCityRespField =
        new StringField(ARR_CITY_RESP, jdAirFlightResponse.getArrCity(), Field.Store.YES);
    Field depCityRespField =
        new StringField(DEP_CITY_RESP, jdAirFlightResponse.getDepCity(), Field.Store.YES);
    Field depDateFied =
        new StringField(DEP_DATE, jdAirFlightResponse.getDepDate(), Field.Store.YES);
    String requestDate = Util.dateFormat.format(new Date());
    Field requestDateFied = new StringField(REQUEST_DATE, requestDate, Field.Store.YES);

    try {
      Long price = Long.valueOf(jdAirFlightResponse.getEconomicPrice().getAdultPrice());
      LongPoint priceField = new LongPoint(PRICE, price);
      StoredField priceStoredField = new StoredField(PRICE, price);
      document.add(priceField);
      document.add(priceStoredField);
    } catch (Exception e) {
      System.out.println("air_ticket_indexer parse price fail:" + jdAirFlightResponse);
    }

    document.add(airwayField);
    document.add(arrCityField);
    document.add(depCityField);
    document.add(arrCityRespField);
    document.add(depCityRespField);
    document.add(depDateFied);
    document.add(requestDateFied);
    return document;
  }

  @PreDestroy
  public void close() throws IOException {
    this.writer.close();
  }
}
