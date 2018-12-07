package com.joe.jsf.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class Product implements Serializable {
  private Long id;
  private String depCity;
  private String arrCity;
  private String depDate;
}
