package com.joe.itinerary.service;

import com.joe.itinerary.request.AirTicketRequest;
import java.io.IOException;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.TopDocs;

public interface AirTicketSearcherService {
  public TopDocs search(AirTicketRequest airTicketRequest) throws IOException, ParseException;

  public Document doc(int doc) throws IOException;
}
