package com.example.springbootweb.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import com.example.springbootweb.repository.*;
// import com.example.springbootweb.model.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TagRepositoryTests {

  @Autowired
  private TagRepository tagRepository;

  @Autowired
  private ArticleRepository articleRepository;

  @Before
  public void init() {
    Tag tag1 = new Tag("java");
    Tag tag2 = new Tag("database");
    Tag tag3 = new Tag("language");
    Set<Tag> set = new HashSet<Tag>();
    // set.add(tag1);
    // set.add(tag2);
    // set.add(tag3);
    set.add(tag1);
    Article article = new Article("java srping boot learning");
    article.setTags(set);
    articleRepository.save(article);
  }

  @After
  public void clear() {
    // articleRepository.deleteAll();
  }

  @Test
  public void findTag() {
    Tag tag = tagRepository.findByName("java");
    System.out.println(tag);
  }

  @Test
  public void findArticle() {
    Article article = articleRepository.findByName("java srping boot learning");
    System.out.println(article);
  }
}