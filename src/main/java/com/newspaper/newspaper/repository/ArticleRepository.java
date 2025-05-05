package com.newspaper.newspaper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newspaper.newspaper.model.Article;
import com.newspaper.newspaper.model.User;

public interface ArticleRepository extends JpaRepository<Article, Integer>{
    List<Article> findByAuthor(User author);
    List<Article> findByAuthorName(String name);
   
}
