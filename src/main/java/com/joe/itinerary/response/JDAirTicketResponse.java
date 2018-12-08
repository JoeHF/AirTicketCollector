package com.joe.itinerary.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JDAirTicketResponse {
  String desc;
  List<JDAirFlightResponse> flights;
}
