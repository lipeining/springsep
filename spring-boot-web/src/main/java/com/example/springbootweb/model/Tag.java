package com.example.springbootweb.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Tag implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue
  private Long id;
  @Column(nullable = false)
  private String name;

  // @ManyToMany(mappedBy = "articles")
  @ManyToMany()
  @JoinTable(name = "tag_article", joinColumns = {
      @JoinColumn(name = "tagId", referencedColumnName = "id") }, inverseJoinColumns = {
          @JoinColumn(name = "articleId", referencedColumnName = "id") })
  @JsonIgnore
  private Set<Article> articles;

  public Tag() {
    super();
  }

  public Tag(String name) {
    super();
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Article> getArticles() {
    return articles;
  }

  // @JsonBackReference
  public void setArticles(Set<Article> articles) {
    this.articles = articles;
  }
}