package com.joe.jsf.response;

import java.util.List;
import lombok.Getter;

@Getter
public class JDAirTicketResponse {
  String desc;
  List<JDAirFlightResponse> flights;
}
