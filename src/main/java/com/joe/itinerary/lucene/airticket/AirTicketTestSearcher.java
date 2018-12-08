package com.joe.itinerary.lucene.airticket;

import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public class AirTicketTestSearcher extends BaseAirTicketSearcher {
  private static final String indexDir = "/Users/houfang/Lucene/AirTicket/Test/Index";

  @Override
  String getIndexDir() {
    return indexDir;
  }

  public AirTicketTestSearcher() throws IOException {
    super();
  }
}
