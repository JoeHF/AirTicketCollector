package com.joe.itinerary.response;

import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JDAirFlightResponse {
  public static final String economicCode = "1";

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

  public FilterPriceResponse getEconomicPrice() {
    return economicPrice = filterPriceMap.get(economicCode);
  }
}
