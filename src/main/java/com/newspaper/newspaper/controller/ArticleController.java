package com.newspaper.newspaper.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.newspaper.newspaper.model.Article;
import com.newspaper.newspaper.service.ArticleService;
import com.newspaper.newspaper.service.ImageService;

import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/article")
public class ArticleController {

    ArticleService articleService;
    ImageService imageService;

    public ArticleController(ArticleService articleService, ImageService imageService){
        this.articleService = articleService;
        this.imageService = imageService;
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Article> createArticle(@Valid @ModelAttribute Article article, @RequestParam MultipartFile file, @PathVariable int userId) throws IOException {
        return new ResponseEntity<>(articleService.createArticle(article, file, userId), HttpStatus.CREATED);
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
    public ResponseEntity<Article> updateArticle(@PathVariable int id, @Valid @ModelAttribute Article article, @RequestParam MultipartFile file) {
        return new ResponseEntity<>(articleService.updateArticle(id,article, file), HttpStatus.OK);
    }

    @GetMapping("/all/username/{name}")
    public ResponseEntity<List<Article>> getArticlesByUserName(@PathVariable String name) {
        return new ResponseEntity<>(articleService.getArticlesByAuthorName(name), HttpStatus.OK);
    }

}
