package com.joe.itinerary.lucene.airticket;

import static com.joe.itinerary.lucene.airticket.AirTicketIndexer.*;
import static com.joe.itinerary.response.JDAirFlightResponse.ECONOMIC_CODE;
import static org.junit.Assert.assertEquals;

import com.google.common.collect.ImmutableMap;
import com.joe.itinerary.Application;
import com.joe.itinerary.lucene.data.AirTicketData;
import com.joe.itinerary.request.AirTicketRequest;
import com.joe.itinerary.response.FilterPriceResponse;
import com.joe.itinerary.response.JDAirFlightResponse;
import com.joe.itinerary.service.AirTicketIndexerService;
import com.joe.itinerary.service.AirTicketSearcherService;
import com.joe.itinerary.utils.Util;
import java.io.IOException;
import java.util.Date;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AirticketTest {
  private Date dateNow;
  private String dateNowStr;

  @Autowired
  @Qualifier("airTicketTestIndexer")
  private AirTicketIndexerService airTicketIndexerService;

  @Autowired
  @Qualifier("airTicketTestSearcher")
  private AirTicketSearcherService airTicketSearcherService;

  @Before
  public void setup() {
    dateNow = new Date();
    dateNowStr = Util.dateFormat.format(dateNow);
  }

  @Test
  public void testIndexAndSearch() throws IOException {
    String depCity = "北京";
    String arrCity = "上海";
    AirTicketRequest airTicketRequest =
        AirTicketRequest.builder().depCity(depCity).arrCity(arrCity).depDate(dateNow).build();
    String depCityResp = "PEK";
    String arrCityResp = "SHA";
    JDAirFlightResponse jdAirFlightResponse =
        JDAirFlightResponse.builder()
            .airwaysCn("国航")
            .depCity(depCityResp)
            .arrCity(arrCityResp)
            .depDate(dateNowStr)
            .filterPriceMap(
                ImmutableMap.of(
                    ECONOMIC_CODE, FilterPriceResponse.builder().adultPrice("300").build()))
            .build();

    airTicketIndexerService.indexAirTicket(
        AirTicketData.builder().request(airTicketRequest).response(jdAirFlightResponse).build());

    AirTicketRequest request =
        AirTicketRequest.builder().depCity(depCity).arrCity(arrCity).depDate(new Date()).build();
    try {
      TopDocs topDocs = airTicketSearcherService.search(request);
      assertEquals(1, topDocs.totalHits);
      ScoreDoc scoreDoc = topDocs.scoreDocs[0];
      Document doc = airTicketSearcherService.doc(scoreDoc.doc);
      assertEquals(depCity, doc.get(DEP_CITY));
      assertEquals(arrCity, doc.get(ARR_CITY));
      assertEquals(depCityResp, doc.get(DEP_CITY_RESP));
      assertEquals(arrCityResp, doc.get(ARR_CITY_RESP));
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}
