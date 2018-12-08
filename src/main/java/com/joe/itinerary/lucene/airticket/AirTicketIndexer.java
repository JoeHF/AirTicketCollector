package com.joe.itinerary.lucene.airticket;

import com.joe.itinerary.response.JDAirFlightResponse;
import com.joe.itinerary.utils.Util;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class AirTicketIndexer {
  public static final String AIRWAY = "airway";
  public static final String ARR_CITY = "arrCity";
  public static final String DEP_CITY = "depCity";
  public static final String DEP_DATE = "depDate";
  public static final String REQUEST_DATE = "requestDate";
  public static final String PRICE = "price";

  private IndexWriter writer;

  public AirTicketIndexer(String indexDirectoryPath) throws IOException {
    this(indexDirectoryPath, false);
  }

  public AirTicketIndexer(String indexDirectoryPath, boolean deleteAll) throws IOException {
    // this directory will contain the indexes
    Directory indexDirectory = FSDirectory.open(new File(indexDirectoryPath).toPath());

    // create the indexer
    writer = new IndexWriter(indexDirectory, new IndexWriterConfig(new StandardAnalyzer()));
    //    if (deleteAll) {
    //      writer.deleteAll();
    //    }
  }

  public void close() throws CorruptIndexException, IOException {
    writer.close();
  }

  public void indexAirTicket(JDAirFlightResponse jdAirFlightResponse) throws IOException {
    Document document = getDocument(jdAirFlightResponse);
    writer.addDocument(document);
  }

  private Document getDocument(JDAirFlightResponse jdAirFlightResponse) {
    Document document = new Document();

    Field airwayField =
        new StringField(AIRWAY, jdAirFlightResponse.getAirwaysCn(), Field.Store.YES);
    Field arrCityField =
        new StringField(ARR_CITY, jdAirFlightResponse.getArrCity(), Field.Store.YES);
    Field depCityField =
        new StringField(DEP_CITY, jdAirFlightResponse.getDepCity(), Field.Store.YES);
    Field depDateFied =
        new StringField(DEP_DATE, jdAirFlightResponse.getDepDate(), Field.Store.YES);
    String requestDate = Util.dateFormat.format(new Date());
    Field requestDateFied = new StringField(REQUEST_DATE, requestDate, Field.Store.YES);

    try {
      Long price = Long.valueOf(jdAirFlightResponse.getEconomicPrice().getAdultPrice());
      Field priceField = new SortedNumericDocValuesField(PRICE, price);
      document.add(priceField);
    } catch (Exception e) {
      System.out.println("air_ticket_indexer parse price fail:" + jdAirFlightResponse);
    }

    document.add(airwayField);
    document.add(arrCityField);
    document.add(depCityField);
    document.add(depDateFied);
    document.add(requestDateFied);

    return document;
  }
}
