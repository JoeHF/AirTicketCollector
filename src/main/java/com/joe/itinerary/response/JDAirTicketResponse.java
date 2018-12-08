package com.joe.itinerary.response;

import java.util.List;
import lombok.Getter;

@Getter
public class JDAirTicketResponse {
  String desc;
  List<JDAirFlightResponse> flights;
}
