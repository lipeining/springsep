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

import org.springframework.cache.annotation.Cacheable;
import javax.servlet.http.HttpSession;
import java.util.UUID;
@RestController
public class UserController {

  @Autowired
  private UserRepository userRepository;


  @RequestMapping("/uid")
  String uid(HttpSession session) {
    UUID uid = (UUID) session.getAttribute("uid");
    if (uid == null) {
      uid = UUID.randomUUID();
    }
    session.setAttribute("uid", uid);
    return session.getId();
  }

  @Cacheable(value="user-key")
  @RequestMapping("/users/{name}")
  public User getUser(HttpSession session, @PathVariable("name") String name) {
    // User user = userRepository.findByUserName(name);
    User user = new User();
    user.setUserName(name);
    Example<User> example = Example.of(user);
    ExampleMatcher matcher = ExampleMatcher.matching()
        .withMatcher("userName", ExampleMatcher.GenericPropertyMatchers.startsWith())// 模糊查询匹配开头，即{username}%
        .withMatcher("address", ExampleMatcher.GenericPropertyMatchers.contains())// 全部模糊查询，即%{address}%
        .withIgnorePaths("password");// 忽略字段，即不管password是什么值都不加入查询条件
    Example<User> example2 = Example.of(user, matcher);
    Optional<User> useri = userRepository.findOne(example);
    User sessionUser = (User) session.getAttribute("user");
    User currentUser = useri.orElse(null);
    System.out.println("old session user");
    System.out.println(sessionUser);
    session.setAttribute("user", currentUser);
    return currentUser ;
  }

  @Cacheable(value="users-key")
  @RequestMapping("/users")
  public List<User> getUsers(@RequestParam(name = "page", required = false, defaultValue = "1" ) int page,
                             @RequestParam(name = "size", required = false, defaultValue = "10" ) int size) {
    Sort sort = new Sort(Sort.Direction.DESC, "id");
    Pageable pageable = new PageRequest(page, size, sort);
    System.out.println(page);
    System.out.println(size);
    Page<User> pageUser = userRepository.findAll(pageable);
    List<User> users = pageUser.getContent();
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