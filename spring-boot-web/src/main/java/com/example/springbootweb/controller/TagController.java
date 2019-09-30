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

import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.*;

import com.example.springbootweb.model.*;
import com.example.springbootweb.repository.*;

@RestController
public class TagController {
  @Autowired
  private TagRepository tagRepository;

  @RequestMapping("/tags/{name}")
  public Tag getTag(@PathVariable("name") String name) {
    Tag tag = tagRepository.findByName(name);
    System.out.println(tag);
    return tag;
  }

  @RequestMapping("/tags")
  public List<Tag> getTags() {
    List<Tag> tags = tagRepository.findAll();
    return tags;
  }

  @Transactional
  @RequestMapping(value = "/tags", method = RequestMethod.POST)
  public Tag addTag(@RequestBody JSONObject obj) {
    try {
      System.out.println(obj.toJSONString());
      String name = obj.getString("name");
      Tag tag = new Tag(name);
      System.out.println(tag);
      tagRepository.save(tag);
      return tag;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @Transactional
  @RequestMapping(value = "/tags/{tagId}", method = RequestMethod.PUT)
  public Tag editTag(@PathVariable Long tagId, @RequestBody JSONObject obj) {
    try {
      Optional<Tag> optionalTag = tagRepository.findById(tagId);
      Tag tag = null;
      if (optionalTag.isPresent()) {
        tag = optionalTag.get();
        String name = obj.getString("name");
        tag.setName(name);
        tagRepository.save(tag);
      }
      return tag;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @Transactional
  @RequestMapping(value = "/tags/{name}", method = RequestMethod.DELETE)
  public Tag editTag(@PathVariable String name) {
    try {
      Tag tag = tagRepository.findByName(name);
      if (tag != null) {
        tagRepository.deleteById(tag.getId());
      }
      return tag;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}