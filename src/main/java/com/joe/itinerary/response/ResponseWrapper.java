package com.joe.itinerary.response;

import lombok.Getter;

@Getter
public class ResponseWrapper<T> {
  int code;
  T data;
}
