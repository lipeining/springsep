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
import java.text.DateFormat;
import java.util.Date;

import com.example.springbootweb.repository.*;
// import com.example.springbootweb.model.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleRepositoryTests {

  @Autowired
  private ArticleRepository articleRepository;

  @Autowired
  private UserRepository userRepository;

  @Before
  public void init() {
    Date date = new Date();
    DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
    String formattedDate = dateFormat.format(date);
    Article article1 = new Article("spring");
    Article article2 = new Article("mvc");
    Article article3 = new Article("mybatis");
    User author = new User("zhonghua", "aa@126.com", "aa", "aa123456", formattedDate);
    Set<Article> set = new HashSet<Article>();
    set.add(article1);
    set.add(article2);
    set.add(article3);
    author.setArticles(set);
    userRepository.save(author);
  }

  @After
  public void clear() {
    articleRepository.deleteAll();
  }

  @Test
  public void findArticle() {
    Article article = articleRepository.findByName("mvc");
    System.out.println(article);
  }

  @Test
  public void findUser() {
    User author = userRepository.findByUserName("zhonghua");
    System.out.println(author);
  }
}