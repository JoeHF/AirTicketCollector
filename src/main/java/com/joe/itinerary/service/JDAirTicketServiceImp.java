package com.joe.itinerary.service;

import com.github.kevinsawicki.http.HttpRequest;
import com.joe.itinerary.request.AirTicketRequest;
import com.joe.itinerary.utils.Util;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class JDAirTicketServiceImp implements AirTicketService {
  private static final String baseUrl = "https://jipiao.jd.com/search/queryFlight.action";
  private static Map<String, String> requestParams;

  static {
    requestParams = new HashMap<String, String>();
    requestParams.put("queryModule", "1");
    requestParams.put("lineType", "OW");
    requestParams.put("queryType", "listquery");
  }

  @Override
  public String search(AirTicketRequest request) {
    String depDate = Util.dateFormat.format(request.getDepDate());
    requestParams.put("depCity", request.getDepCity());
    requestParams.put("depDate", depDate);
    requestParams.put("arrCity", request.getArrCity());
    requestParams.put("arrDate", depDate);
    String url = buildRequestUrl(requestParams);
    String resp = HttpRequest.get(url).body();
    return resp;
  }

  private String buildRequestUrl(Map<String, String> requestParams) {
    String params =
        requestParams
            .entrySet()
            .stream()
            .map(entry -> entry.getKey() + "=" + entry.getValue())
            .collect(Collectors.joining("&"));
    return baseUrl + "?" + params;
  }
}
