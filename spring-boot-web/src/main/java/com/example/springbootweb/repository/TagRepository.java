package com.example.springbootweb.repository;

import com.example.springbootweb.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
  Tag findByName(String name);
}