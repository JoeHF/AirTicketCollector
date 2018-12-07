package com.joe.jsf.response;

import java.util.Map;
import lombok.Data;

@Data
public class JDAirFlightResponse {
  private static final String economicCode = "1";

  String airwaysCn;
  String depDate;
  String depCity;
  String depTime;
  String arrDate;
  String arrCity;
  String arrTime;
  Map<String, FilterPriceResponse> filterPriceMap;

  // derived data
  FilterPriceResponse economicPrice;
  FilterPriceResponse businessPrice;
  FilterPriceResponse firstClassPrice;

  public void generateDerivedData() {
    economicPrice = filterPriceMap.get(economicCode);
  }
}
