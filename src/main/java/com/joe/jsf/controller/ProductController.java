package com.joe.jsf.controller;

import com.joe.jsf.dao.ProductMapper;
import com.joe.jsf.model.Product;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value = "session")
@Component(value = "productController")
@ELBeanName(value = "productController")
@Join(path = "/product", to = "/product-form.jsf")
public class ProductController {
  @Autowired private ProductMapper productMapper;

  private Product product = new Product();

  public String save() {
    productMapper.insert(product);
    return "/product-list.xhtml?faces-redirect=true";
  }

  public Product getProduct() {
    return product;
  }
}
