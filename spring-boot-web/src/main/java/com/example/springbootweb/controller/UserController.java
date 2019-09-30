package com.example.springbootweb.controller;

import java.util.List;
import java.util.Date;
import java.text.DateFormat;
import java.util.UUID;
import java.util.HashSet;
import java.util.Set;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.*;

import com.example.springbootweb.model.*;
import com.example.springbootweb.repository.UserRepository;

import org.springframework.data.jpa.domain.*;
import org.springframework.data.domain.*;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @RequestMapping("/users/{name}")
  public User getUser(@PathVariable("name") String name) {
    // User user = userRepository.findByUserName(name);
    User user = new User();
    user.setUserName("admin");
    Example<User> example = Example.of(user);
    ExampleMatcher matcher = ExampleMatcher.matching()
        .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.startsWith())// 模糊查询匹配开头，即{username}%
        .withMatcher("address", ExampleMatcher.GenericPropertyMatchers.contains())// 全部模糊查询，即%{address}%
        .withIgnorePaths("password");// 忽略字段，即不管password是什么值都不加入查询条件
    Example<User> example2 = Example.of(user, matcher);
    Optional<User> useri = userRepository.findOne(example);
    if (useri.isPresent()) {
      return useri.get();
    }
    return null;
  }

  @RequestMapping("/users")
  public Page<User> getUsers(@RequestParam("page") int page, @RequestParam("size") int size) {
    Sort sort = new Sort(Sort.Direction.DESC, "id");
    Pageable pageable = new PageRequest(page, size, sort);
    Page<User> users = userRepository.findAll(pageable);
    return users;
  }

  @Transactional
  @RequestMapping(value = "/users", method = RequestMethod.POST)
  public User addUser(@RequestBody JSONObject obj) {
    try {
      Date date = new Date();
      DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
      String formattedDate = dateFormat.format(date);
      System.out.println(obj.toJSONString());
      String name = obj.getString("name");
      String nickName = name;
      String email = obj.getString("email");
      String password = email + "pass";
      User user = new User(nickName, email, name, password, formattedDate);
      Article article1 = new Article("spring");
      Article article2 = new Article("mvc");
      Article article3 = new Article("mybatis");
      Set<Article> set = new HashSet<Article>();
      set.add(article1);
      set.add(article2);
      set.add(article3);
      user.setArticles(set);
      userRepository.save(user);
      return user;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @Transactional
  @RequestMapping(value = "/users/{name}", method = RequestMethod.PUT)
  public User editUser(@PathVariable String name, @RequestBody JSONObject obj) {
    try {
      User user = userRepository.findByUserName(name);
      if (user != null) {
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String formattedDate = dateFormat.format(date);
        System.out.println(obj.toJSONString());
        String nickName = obj.getString("nickName");
        user.setNickName(nickName);
        String email = obj.getString("email");
        user.setEmail(email);
        user.setRegTime(formattedDate);
        userRepository.save(user);
      }
      return user;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @Transactional
  @RequestMapping(value = "/users/{name}", method = RequestMethod.DELETE)
  public User editUser(@PathVariable String name) {
    try {
      User user = userRepository.findByUserName(name);
      if (user != null) {
        userRepository.deleteById(user.getId());
      }
      return user;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}