package com.joe.jsf.job;

import com.joe.jsf.service.AirTicketService;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Jobs {
  private static final long ONE_Minute = 60 * 1000;
  private static final SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmssSSS");

  @Autowired private AirTicketService airTicketService;

  @Scheduled(fixedDelay = ONE_Minute)
  public void fixedDelayJob() {
    //    AirTicketRequest request = new AirTicketRequest();
    //    request.setDepCity("北京");
    //    request.setDepDate("2018-12-15");
    //    request.setArrCity("上海");
    //    request.setArrDate("2018-12-15");
    //    String resp = airTicketService.search(request);
    //    System.out.println(resp);
    System.out.println(f.format(new Date()) + " >>fixedDelay执行....");
  }

  @Scheduled(fixedRate = ONE_Minute)
  public void fixedRateJob() {
    System.out.println(f.format(new Date()) + " >>fixedRate执行....");
  }

  @Scheduled(cron = "0 0 3 * * ?")
  public void cronJob() {
    System.out.println(f.format(new Date()) + " >>cron执行....");
  }
}
