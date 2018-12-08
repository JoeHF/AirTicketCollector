package com.joe.itinerary.lucene.airticket;

import java.io.IOException;
import javax.annotation.PreDestroy;
import org.springframework.stereotype.Service;

@Service
public class AirTicketTestIndexer extends BaseAirTicketIndexer {
  private static final String indexDir = "/Users/houfang/Lucene/AirTicket/Test/Index";

  public AirTicketTestIndexer() throws IOException {
    super();
  }

  @PreDestroy
  public void close() throws IOException {
    this.writer.close();
  }

  @Override
  String getIndexDir() {
    return indexDir;
  }

  @Override
  boolean deleteAll() {
    return true;
  }
}
