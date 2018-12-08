package com.joe.itinerary.request;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AirTicketRequest {
  private String depCity;
  private String arrCity;
  private Date depDate;
  private Date arrDate;
}
