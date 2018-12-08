package com.joe.itinerary.job;

import com.joe.itinerary.Application;
import com.joe.itinerary.lucene.airticket.AirTicketIndexer;
import com.joe.itinerary.lucene.airticket.AirTicketSearcher;
import com.joe.itinerary.utils.Util;
import java.io.IOException;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class JobsTest {
  private static String testIndexDir = "/Users/houfang/Lucene/AirTicket/Index";
  private String dateNow;
  private AirTicketIndexer airTicketIndexer;
  private AirTicketSearcher airTicketSearcher;

  @Autowired private Jobs jobs;

  @Before
  public void setup() throws IOException {
    dateNow = Util.dateFormat.format(new Date());
  }

  @Test
  public void testCron() throws IOException {
    jobs.cronJob();
  }
}
