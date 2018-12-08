package com.joe.itinerary.job;

import com.joe.itinerary.Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class JobsTest {
  //  private static String testIndexDir = "/Users/houfang/Lucene/AirTicket/Test/Index";
  //  private String dateNow;
  //
  //  @Autowired private Jobs jobs;
  //
  //  @Before
  //  public void setup() throws IOException {
  //    dateNow = Util.dateFormat.format(new Date());
  //    this.jobs.resetIndexPath(testIndexDir);
  //  }
  //
  //  @Test
  //  public void testCron() throws IOException, ParseException {
  //    jobs.cronJob();
  //  }
}
