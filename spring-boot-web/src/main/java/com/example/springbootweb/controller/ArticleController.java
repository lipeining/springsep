package com.example.springbootweb.controller;

import java.util.List;
import java.util.Date;
import java.text.DateFormat;
import java.util.UUID;
import java.util.HashSet;
import java.util.*;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.*;

import com.example.springbootweb.model.*;
import com.example.springbootweb.repository.*;

import org.springframework.data.domain.*;

@RestController
public class ArticleController {
  @Autowired
  private ArticleRepository articleRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TagRepository tagRepository;

  @RequestMapping("/articles/{name}")
  public Article getArticle(@PathVariable("name") String name) {
    Article article = articleRepository.findByName(name);
    System.out.println(article);
    return article;
  }

  @RequestMapping("/articles")
  public List<Article> getArticles(@RequestParam("page") int page, @RequestParam("size") int size) {
    // Sort sort = new Sort(Sort.Direction.DESC, "id");
    // Pageable pageable = new PageRequest(page, size, sort);
    // Page<Article> particles = articleRepository.findAll((root, query, cb) -> {
    //   List<Predicate> predicates = new ArrayList<Predicate>();
    //   // //equal 示例
    //   // if (!StringUtils.isNullOrEmpty(detailParam.getIntroduction())){
    //   // predicates.add(cb.equal(root.get("introduction"),detailParam.getIntroduction()));
    //   // }
    //   // //like 示例
    //   // if (!StringUtils.isNullOrEmpty(detailParam.getRealName())){
    //   // predicates.add(cb.like(root.get("realName"),"%"+detailParam.getRealName()+"%"));
    //   // }
    //   // //between 示例
    //   // if (detailParam.getMinAge()!=null && detailParam.getMaxAge()!=null) {
    //   // Predicate agePredicate = cb.between(root.get("age"), detailParam.getMinAge(),
    //   // detailParam.getMaxAge());
    //   // predicates.add(agePredicate);
    //   // }
    //   // //greaterThan 大于等于示例
    //   // if (detailParam.getMinAge()!=null){
    //   // predicates.add(cb.greaterThan(root.get("age"),detailParam.getMinAge()));
    //   // }
    //   predicates.add(cb.like(root.get("name"), "%" + "spring" + "%"));
    //   predicates.add(cb.greaterThan(root.get("createTime"), 0));
    //   return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
    // }, pageable);
    List<Article> articles = articleRepository.findAll();
    return articles;
  }

  @Transactional
  @RequestMapping(value = "/articles", method = RequestMethod.POST)
  public Article addArticle(@RequestBody JSONObject obj) {
    try {
      System.out.println(obj.toJSONString());
      String name = obj.getString("name");
      Article article = new Article(name);
      Long authorId = Long.valueOf(obj.getString("authorId"));
      Optional<User> user = userRepository.findById(authorId);
      if (user.isPresent()) {
        article.setAuthor(user.get());
      }
      System.out.println(user);
      System.out.println(article);
      articleRepository.save(article);
      return article;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @Transactional
  @RequestMapping(value = "/articles/{articleId}", method = RequestMethod.PUT)
  public Article editArticle(@PathVariable Long articleId, @RequestBody JSONObject obj) {
    try {
      Optional<Article> optionalArticle = articleRepository.findById(articleId);
      if (optionalArticle.isPresent()) {
        Article article = optionalArticle.get();
        if (obj.containsKey("name")) {
          String name = obj.getString("name");
          article.setName(name);
        }
        if (obj.containsKey("authorId")) {
          Long authorId = Long.valueOf(obj.getString("authorId"));
          Optional<User> user = userRepository.findById(authorId);
          if (user.isPresent()) {
            article.setAuthor(user.get());
          }
          System.out.println(user);
        }
        if (obj.containsKey("tagId")) {
          JSONArray inputTagId = obj.getJSONArray("tagId");
          Set<Tag> tags = new HashSet<Tag>();
          for (int i = 0; i < inputTagId.size(); i++) {
            Long tagId = inputTagId.getLong(i);
            Optional<Tag> tag = tagRepository.findById(tagId);
            if (tag.isPresent()) {
              tags.add(tag.get());
            }
          }
          System.out.println(tags);
          article.setTags(tags);
        }
        articleRepository.save(article);
        return article;
      }
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @Transactional
  @RequestMapping(value = "/articles/{name}", method = RequestMethod.DELETE)
  public Article editArticle(@PathVariable String name) {
    try {
      Article article = articleRepository.findByName(name);
      if (article != null) {
        articleRepository.deleteById(article.getId());
      }
      return article;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}