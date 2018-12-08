package com.joe.itinerary.controller;

import com.joe.itinerary.dao.ProductMapper;
import com.joe.itinerary.model.Product;
import java.util.List;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value = "session")
@Component(value = "productList")
@ELBeanName(value = "productList")
@Join(path = "/", to = "/product-list.itinerary")
public class ProductListController {
  @Autowired private ProductMapper productMapper;
  private List<Product> products;

  @Deferred
  @RequestAction
  @IgnorePostback
  public void loadData() {
    products = productMapper.selectAll();
  }

  public List<Product> getProducts() {
    return products;
  }
}
