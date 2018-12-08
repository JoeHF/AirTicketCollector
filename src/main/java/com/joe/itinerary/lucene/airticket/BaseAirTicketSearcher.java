package com.joe.itinerary.lucene.airticket;

import static com.joe.itinerary.lucene.airticket.BaseAirTicketIndexer.*;

import com.joe.itinerary.request.AirTicketRequest;
import com.joe.itinerary.service.AirTicketSearcherService;
import com.joe.itinerary.utils.Util;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

abstract class BaseAirTicketSearcher implements AirTicketSearcherService {
  IndexSearcher indexSearcher;

  abstract String getIndexDir();

  BaseAirTicketSearcher() throws IOException {
    String path = getIndexDir();
    Directory indexDirectory = FSDirectory.open(new File(path).toPath());
    IndexReader ir = DirectoryReader.open(indexDirectory);
    indexSearcher = new IndexSearcher(ir);
  }

  public TopDocs search(AirTicketRequest airTicketRequest) throws IOException, ParseException {
    String depDateString = Util.dateFormat.format(airTicketRequest.getDepDate());
    String requestDateString = Util.dateFormat.format(new Date());
    BooleanQuery boolQuery =
        new BooleanQuery.Builder()
            .add(
                new TermQuery(new Term(DEP_CITY, airTicketRequest.getDepCity())),
                BooleanClause.Occur.MUST)
            .add(
                new TermQuery(new Term(ARR_CITY, airTicketRequest.getArrCity())),
                BooleanClause.Occur.MUST)
            .add(new TermQuery(new Term(DEP_DATE, depDateString)), BooleanClause.Occur.MUST)
            .add(new TermQuery(new Term(REQUEST_DATE, requestDateString)), BooleanClause.Occur.MUST)
            .build();

    TopDocs topDocs = indexSearcher.search(boolQuery, 10);
    System.out.println("总共搜索到" + topDocs.totalHits + "个结果");
    return topDocs;
  }

  public Document doc(int doc) throws IOException {
    return indexSearcher.doc(doc);
  }
}
