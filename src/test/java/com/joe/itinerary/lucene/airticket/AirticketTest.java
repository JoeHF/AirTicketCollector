package com.joe.itinerary.lucene.airticket;

import static com.joe.itinerary.lucene.airticket.AirTicketIndexer.DEP_CITY;
import static com.joe.itinerary.response.JDAirFlightResponse.economicCode;

import com.google.common.collect.ImmutableMap;
import com.joe.itinerary.request.AirTicketRequest;
import com.joe.itinerary.response.FilterPriceResponse;
import com.joe.itinerary.response.JDAirFlightResponse;
import com.joe.itinerary.utils.Util;
import java.io.IOException;
import java.util.Date;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.junit.Before;
import org.junit.Test;

public class AirticketTest {
  //    private static String testIndexDir = "/Users/houfang/Lucene/AirTicket/Test/Index";
  private static String testIndexDir = "/Users/houfang/Lucene/AirTicket/Index";
  private String dateNow;
  private AirTicketIndexer airTicketIndexer;
  private AirTicketSearcher airTicketSearcher;

  @Before
  public void setup() throws IOException {
    dateNow = Util.dateFormat.format(new Date());
    airTicketIndexer = new AirTicketIndexer(testIndexDir, false);
  }

  @Test
  public void testIndexAndSearch() throws IOException {
    String depCity = "北京";
    String arrCity = "上海";
    JDAirFlightResponse jdAirFlightResponse =
        JDAirFlightResponse.builder()
            .airwaysCn("国航")
            .depCity("北京")
            .arrCity("上海")
            .depDate(dateNow)
            .filterPriceMap(
                ImmutableMap.of(
                    economicCode, FilterPriceResponse.builder().adultPrice("300").build()))
            .build();

    try {
      airTicketIndexer.indexAirTicket(jdAirFlightResponse);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      airTicketIndexer.close();
    }

    airTicketSearcher = new AirTicketSearcher(testIndexDir);
    AirTicketRequest request =
        AirTicketRequest.builder().depCity(depCity).arrCity(arrCity).depDate(new Date()).build();
    try {
      TopDocs topDocs = airTicketSearcher.search(request);
      for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
        Document doc = airTicketSearcher.doc(scoreDoc.doc);
        System.out.println(doc.get(DEP_CITY));
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}
