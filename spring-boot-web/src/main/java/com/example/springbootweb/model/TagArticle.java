package com.example.springbootweb.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "tag_article")
public class TagArticle implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue
  private Long id;
  @Column(nullable = false)
  private Long tagId;
  @Column(nullable = false)
  private Long articleId;

  public TagArticle() {
    super();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getTagId() {
    return tagId;
  }

  public void setTagId(Long tagId) {
    this.tagId = tagId;
  }

  public Long getArticleId() {
    return articleId;
  }

  public void setArticleId(Long articleId) {
    this.articleId = articleId;
  }

}