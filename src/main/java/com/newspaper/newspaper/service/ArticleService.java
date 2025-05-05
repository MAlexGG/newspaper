package com.newspaper.newspaper.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.newspaper.newspaper.exception.EntityNotFoundException;
import com.newspaper.newspaper.model.Article;
import com.newspaper.newspaper.model.Image;
import com.newspaper.newspaper.model.User;
import com.newspaper.newspaper.repository.ArticleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ArticleService {

    ArticleRepository articleRepository;
    UserService userService;
    ImageService imageService;

    public ArticleService(ArticleRepository articleRepository, UserService userService, ImageService imageService){
        this.articleRepository = articleRepository;
        this.userService = userService;
        this.imageService = imageService;
    }

    public Article createArticle(Article article , MultipartFile file, int userId){ 
        if (file != null && !file.isEmpty()) {
            try {
               Image uploadedImage = imageService.uploadImage(file);
               article.setImage(uploadedImage);
            } catch (IOException e) {
                throw new RuntimeException("Error uploading image", e);
            }
        }
        article.setAuthor(userService.getUser(userId));
        return articleRepository.save(article);
    }

    public Article getArticleById(int id){
        Optional<Article> article = articleRepository.findById(id);
        return unwrapArticle(article);
    }

    public List<Article> getArticlesByUserId(int userId){    
        User user = userService.getUser(userId);
        List<Article> articles = articleRepository.findByAuthor(user);
        return articles;
    }

    public String deleteArticle(int id){
        Optional<Article> article = articleRepository.findById(id);
        if(article.isEmpty()) return "Article does not exist";
        Article unwrapArticle = unwrapArticle(article);
        imageService.deleteImage(unwrapArticle.getImage().getId());
        articleRepository.deleteById(id);
        return (unwrapArticle.getTitle() + " deleted successfully");
    }

    public Article updateArticle(int id, Article newArticle, MultipartFile file){
        Article article = getArticleById(id);
        article.setTitle(newArticle.getTitle());
        article.setContent(newArticle.getContent());
        article.setCategory(newArticle.getCategory());
        if(!file.isEmpty()){
            try {
                imageService.deleteImage(article.getImage().getId());
                Image uploadedImage = imageService.uploadImage(file);
                article.setImage(uploadedImage);
             } catch (IOException e) {
                 throw new RuntimeException("Error uploading image", e);
             }
        }
        return articleRepository.save(article);
    }

    public List<Article> getArticlesByAuthorName(String name){
        List<Article> articles = articleRepository.findByAuthorName(name);
        return articles;
    }


    public static Article unwrapArticle(Optional<Article> entity){
        if(entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(Article.class);
    }




}

