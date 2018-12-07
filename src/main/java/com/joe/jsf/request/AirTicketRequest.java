package com.joe.jsf.request;

import lombok.Data;

@Data
public class AirTicketRequest {
  private String depCity;
  private String arrCity;
  private String depDate;
  private String arrDate;
}
