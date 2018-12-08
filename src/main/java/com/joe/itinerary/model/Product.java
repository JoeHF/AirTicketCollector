package com.joe.itinerary.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class Product implements Serializable {
  private Long id;
  private String depCity;
  private String arrCity;
  private Date depDate;
}
