package com.joe.itinerary.lucene.airticket;

import static com.joe.itinerary.lucene.airticket.AirTicketIndexer.*;

public class AirticketTest {
  //  private static String testIndexDir = "/Users/houfang/Lucene/AirTicket/Test/Index";
  //  private Date dateNow;
  //  private String dateNowStr;
  //  private AirTicketIndexer airTicketIndexer;
  //  private AirTicketSearcher airTicketSearcher;
  //
  //  @Before
  //  public void setup() throws IOException {
  //    dateNow = new Date();
  //    dateNowStr = Util.dateFormat.format(dateNow);
  //    airTicketIndexer = new AirTicketIndexer(testIndexDir, true);
  //  }
  //
  //  @Test
  //  public void testIndexAndSearch() throws IOException {
  //    String depCity = "北京";
  //    String arrCity = "上海";
  //    AirTicketRequest airTicketRequest =
  //        AirTicketRequest.builder().depCity(depCity).arrCity(arrCity).depDate(dateNow).build();
  //    String depCityResp = "PEK";
  //    String arrCityResp = "SHA";
  //    JDAirFlightResponse jdAirFlightResponse =
  //        JDAirFlightResponse.builder()
  //            .airwaysCn("国航")
  //            .depCity(depCityResp)
  //            .arrCity(arrCityResp)
  //            .depDate(dateNowStr)
  //            .filterPriceMap(
  //                ImmutableMap.of(
  //                    ECONOMIC_CODE, FilterPriceResponse.builder().adultPrice("300").build()))
  //            .build();
  //
  //    try {
  //      airTicketIndexer.indexAirTicket(
  //
  // AirTicketData.builder().request(airTicketRequest).response(jdAirFlightResponse).build());
  //    } catch (IOException e) {
  //      e.printStackTrace();
  //    } finally {
  //      airTicketIndexer.close();
  //    }
  //
  //    airTicketSearcher = new AirTicketSearcher(testIndexDir);
  //    AirTicketRequest request =
  //        AirTicketRequest.builder().depCity(depCity).arrCity(arrCity).depDate(new
  // Date()).build();
  //    try {
  //      TopDocs topDocs = airTicketSearcher.search(request);
  //      assertEquals(1, topDocs.totalHits);
  //      ScoreDoc scoreDoc = topDocs.scoreDocs[0];
  //      Document doc = airTicketSearcher.doc(scoreDoc.doc);
  //      assertEquals(depCity, doc.get(DEP_CITY));
  //      assertEquals(arrCity, doc.get(ARR_CITY));
  //      assertEquals(depCityResp, doc.get(DEP_CITY_RESP));
  //      assertEquals(arrCityResp, doc.get(ARR_CITY_RESP));
  //    } catch (IOException e) {
  //      e.printStackTrace();
  //    } catch (ParseException e) {
  //      e.printStackTrace();
  //    }
  //  }
}
