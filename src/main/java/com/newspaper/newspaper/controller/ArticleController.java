package com.newspaper.newspaper.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newspaper.newspaper.model.Article;
import com.newspaper.newspaper.service.ArticleService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/article")
public class ArticleController {

    ArticleService articleService;

    public ArticleController(ArticleService articleService){
        this.articleService = articleService;
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Article> createArticle(@Valid @RequestBody Article article, @PathVariable int userId) {
        return new ResponseEntity<>(articleService.createArticle(article, userId), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable int id) {
        return new ResponseEntity<>(articleService.getArticleById(id), HttpStatus.OK);
    }
    
    @GetMapping("/all/user/{userId}")
    public ResponseEntity<List<Article>> getArticlesByUserId(@PathVariable int userId) {
        return new ResponseEntity<>(articleService.getArticlesByUserId(userId), HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public String deleteArticle(@PathVariable int id){
        return articleService.deleteArticle(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable int id, @Valid @RequestBody Article article) {
        return new ResponseEntity<>(articleService.updateArticle(id,article), HttpStatus.OK);
    }

}
