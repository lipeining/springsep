package com.example.springbootweb.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.*;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Article implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue
  private Long id;
  @Column(nullable = false)
  private String name;
  @Column(nullable = true)
  private Long createTime;
  @Column(nullable = true)
  private Long updateTime;
  @Column(nullable = true)
  private Long deleteTime;

  @ManyToOne()
  @JoinColumn(name = "authorId")
  private User author;

  // @ManyToMany(mappedBy = "tags")
  @ManyToMany()
  @JoinTable(name = "tag_article", joinColumns = {
      @JoinColumn(name = "articleId", referencedColumnName = "id") }, inverseJoinColumns = {
          @JoinColumn(name = "tagId", referencedColumnName = "id") })
  private Set<Tag> tags;

  public Article() {
    super();
  }

  public Article(String name) {
    super();
    this.name = name;
  }

  public Article(String name, Long createTime, Long updateTime, Long deleteTime) {
    super();
    this.name = name;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.deleteTime = deleteTime;
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

  public User getAuthor() {
    return author;
  }

  // @JsonBackReference
  public void setAuthor(User author) {
    this.author = author;
  }

  public Set<Tag> getTags() {
    return tags;
  }

  // @JsonBackReference
  public void setTags(Set<Tag> tags) {
    this.tags = tags;
  }

  public Long getCreateTime() {
    return this.createTime;
  }

  public Long getUpdateTime() {
    return this.updateTime;
  }

  public Long getDeleteTime() {
    return this.deleteTime;
  }

  public void setCreateTime(Long createTime) {
    this.createTime = createTime;
  }

  public void setUpdateTime(Long updateTime) {
    this.updateTime = updateTime;
  }

  public void setDeleteTime(Long deleteTime) {
    this.deleteTime = deleteTime;
  }
}