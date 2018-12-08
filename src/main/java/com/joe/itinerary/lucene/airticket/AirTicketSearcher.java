package com.joe.itinerary.lucene.airticket;

import static com.joe.itinerary.lucene.airticket.AirTicketIndexer.*;

import java.io.IOException;
import org.apache.lucene.search.*;
import org.springframework.stereotype.Service;

@Service
public class AirTicketSearcher extends BaseAirTicketSearcher {
  //  @Getter
  private static final String indexDir = "/Users/houfang/Lucene/AirTicket/Index";

  public AirTicketSearcher() throws IOException {
    super();
  }

  public String getIndexDir() {
    return indexDir;
  }
}
