package com.joe.itinerary;

import com.joe.itinerary.dao.ProductMapper;
import java.util.EnumSet;
import javax.annotation.PostConstruct;
import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;
import org.ocpsoft.rewrite.servlet.RewriteFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAutoConfiguration
@ComponentScan({"com.joe.itinerary"})
@EnableScheduling
public class Application extends SpringBootServletInitializer {
  @Autowired private ProductMapper productMapper;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @PostConstruct
  private void setup() {
    // setup stuff goes here.
    productMapper.createTable();
    // include any other mappers you have...
  }

  @Bean
  public ServletRegistrationBean servletRegistrationBean() {
    FacesServlet servlet = new FacesServlet();
    return new ServletRegistrationBean(servlet, "*.itinerary");
  }

  @Bean
  public FilterRegistrationBean rewriteFilter() {
    FilterRegistrationBean rwFilter = new FilterRegistrationBean(new RewriteFilter());
    rwFilter.setDispatcherTypes(
        EnumSet.of(
            DispatcherType.FORWARD,
            DispatcherType.REQUEST,
            DispatcherType.ASYNC,
            DispatcherType.ERROR));
    rwFilter.addUrlPatterns("/*");
    return rwFilter;
  }
}
