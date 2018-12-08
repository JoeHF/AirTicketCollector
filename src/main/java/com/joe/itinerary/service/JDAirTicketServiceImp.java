package com.joe.itinerary.service;

import static com.joe.itinerary.lucene.airticket.AirTicketIndexer.*;
import static com.joe.itinerary.response.JDAirFlightResponse.ECONOMIC_CODE;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joe.itinerary.request.AirTicketRequest;
import com.joe.itinerary.response.FilterPriceResponse;
import com.joe.itinerary.response.JDAirFlightResponse;
import com.joe.itinerary.response.JDAirTicketResponse;
import com.joe.itinerary.response.ResponseWrapper;
import com.joe.itinerary.utils.Util;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.TopDocs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JDAirTicketServiceImp implements AirTicketService {
  private static final String baseUrl = "https://jipiao.jd.com/search/queryFlight.action";
  private static Map<String, String> requestParams;
  private static String indexDir = "/Users/houfang/Lucene/AirTicket/Index";

  @Autowired private AirTicketSearcherService airTicketSearcherService;

  static {
    requestParams = new HashMap<String, String>();
    requestParams.put("queryModule", "1");
    requestParams.put("lineType", "OW");
    requestParams.put("queryType", "listquery");
  }

  @Override
  public List<JDAirFlightResponse> search(AirTicketRequest request)
      throws IOException, ParseException {
    TopDocs topDocs = airTicketSearcherService.search(request);
    if (topDocs.totalHits > 0) {
      return Arrays.stream(topDocs.scoreDocs)
          .map(
              scoreDoc -> {
                Document doc;
                try {
                  doc = airTicketSearcherService.doc(scoreDoc.doc);
                } catch (IOException e) {
                  return null;
                }
                return JDAirFlightResponse.builder()
                    .airwaysCn(doc.get(AIRWAY))
                    .depCity(doc.get(DEP_CITY))
                    .arrCity(doc.get(ARR_CITY))
                    .depDate(doc.get(DEP_DATE))
                    .filterPriceMap(
                        ImmutableMap.of(
                            ECONOMIC_CODE,
                            FilterPriceResponse.builder().adultPrice(doc.get(PRICE)).build()))
                    .build();
              })
          .filter(Objects::nonNull)
          .collect(Collectors.toList());
    }

    String depDate = Util.dateFormat.format(request.getDepDate());
    requestParams.put("depCity", request.getDepCity());
    requestParams.put("depDate", depDate);
    requestParams.put("arrCity", request.getArrCity());
    requestParams.put("arrDate", depDate);
    String url = buildRequestUrl(requestParams);
    String resp = HttpRequest.get(url).body();
    Gson gson = new Gson();
    ResponseWrapper<JDAirTicketResponse> responseWrapper =
        gson.fromJson(resp, new TypeToken<ResponseWrapper<JDAirTicketResponse>>() {}.getType());
    JDAirTicketResponse jdAirTicketResponse = responseWrapper.getData();
    return jdAirTicketResponse.getFlights();
  }

  private String buildRequestUrl(Map<String, String> requestParams) {
    String params =
        requestParams
            .entrySet()
            .stream()
            .map(entry -> entry.getKey() + "=" + entry.getValue())
            .collect(Collectors.joining("&"));
    return baseUrl + "?" + params;
  }
}
