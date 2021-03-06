package com.joe.itinerary.lucene.airticket;

import java.io.IOException;
import javax.annotation.PreDestroy;
import org.apache.lucene.document.*;
import org.springframework.stereotype.Service;

@Service
public class AirTicketIndexer extends BaseAirTicketIndexer {
  private static final String indexDir = "/Users/houfang/Lucene/AirTicket/Index";

  public AirTicketIndexer() throws IOException {
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
    return false;
  }
}
