package com.joe.itinerary.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joe.itinerary.request.AirTicketRequest;
import com.joe.itinerary.response.JDAirFlightResponse;
import com.joe.itinerary.response.JDAirTicketResponse;
import com.joe.itinerary.response.ResponseWrapper;
import com.joe.itinerary.service.AirTicketService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value = "session")
@Component(value = "JDAirTicketController")
@ELBeanName(value = "JDAirTicketController")
@Join(path = "/air-ticket", to = "/air-ticket.itinerary")
@Slf4j
public class JDAirTicketController {

  @Autowired private AirTicketService airTicketService;

  private AirTicketRequest jdAirTicketRequest = AirTicketRequest.builder().build();
  private List<JDAirFlightResponse> jdAirFlightResponses = new ArrayList<JDAirFlightResponse>();

  public AirTicketRequest getJdAirTicketRequest() {
    return jdAirTicketRequest;
  }

  public List<JDAirFlightResponse> getJdAirFlightResponses() {
    return jdAirFlightResponses;
  }

  public void setJdAirFlightResponses(List<JDAirFlightResponse> responses) {
    if (responses == null) {
      return;
    }

    this.jdAirFlightResponses = responses;
  }

  public void query() {
    String resp = airTicketService.search(jdAirTicketRequest);
    Gson gson = new Gson();
    ResponseWrapper<JDAirTicketResponse> responseWrapper =
        gson.fromJson(resp, new TypeToken<ResponseWrapper<JDAirTicketResponse>>() {}.getType());
    this.setJdAirFlightResponses(responseWrapper.getData().getFlights());
  }
}
