package com.example.springbootweb.repository;

import com.example.springbootweb.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
  Article findByName(String name);
}