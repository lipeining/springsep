package com.example.springbootweb.web;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.springbootweb.util.NeoProperties;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProPertiesTest {

  @Autowired
  private NeoProperties neoProperties;

  @Test
  public void getHello() throws Exception {
    System.out.println(neoProperties.getTitle());
    Assert.assertEquals(neoProperties.getTitle(), "mytitle");
    Assert.assertEquals(neoProperties.getDescription(), "mydescription");
  }

  @Test
  public void testMap() throws Exception {
    Map<String, Integer> orderMinTime = new HashMap<String, Integer>();
    System.out.println(orderMinTime.containsKey("123"));
    Integer l123 = 123;
    orderMinTime.put("123", l123);
    System.out.println(orderMinTime.containsKey("123"));
  }

}