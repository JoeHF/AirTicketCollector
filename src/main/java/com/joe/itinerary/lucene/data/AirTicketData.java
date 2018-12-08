package com.joe.itinerary.lucene.data;

import com.joe.itinerary.request.AirTicketRequest;
import com.joe.itinerary.response.JDAirFlightResponse;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AirTicketData {
  AirTicketRequest request;
  JDAirFlightResponse response;
}
