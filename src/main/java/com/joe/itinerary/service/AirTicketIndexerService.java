package com.joe.itinerary.service;

import com.joe.itinerary.lucene.data.AirTicketData;
import java.io.IOException;

public interface AirTicketIndexerService {
  public void indexAirTicket(AirTicketData airTicketData) throws IOException;
}
