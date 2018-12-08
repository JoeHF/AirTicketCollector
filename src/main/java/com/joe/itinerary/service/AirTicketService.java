package com.joe.itinerary.service;

import com.joe.itinerary.request.AirTicketRequest;
import com.joe.itinerary.response.JDAirFlightResponse;
import java.io.IOException;
import java.util.List;
import org.apache.lucene.queryparser.classic.ParseException;

public interface AirTicketService {
  List<JDAirFlightResponse> search(AirTicketRequest request) throws IOException, ParseException;
}
