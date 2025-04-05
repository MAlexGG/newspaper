package com.newspaper.newspaper.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.newspaper.newspaper.exception.EntityNotFoundException;
import com.newspaper.newspaper.model.Article;
import com.newspaper.newspaper.model.User;
import com.newspaper.newspaper.repository.ArticleRepository;

@Service
public class ArticleService {

    ArticleRepository articleRepository;
    UserService userService;

    public ArticleService(ArticleRepository articleRepository, UserService userService){
        this.articleRepository = articleRepository;
        this.userService = userService;
    }

    public Article createArticle(Article article, int userId){
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
        articleRepository.deleteById(id);
        return (unwrapArticle(article).getTitle() + " deleted successfully");
    }

    public Article updateArticle(int id, Article newArticle){
        Article article = getArticleById(id);
        article.setTitle(newArticle.getTitle());
        article.setContent(newArticle.getContent());
        article.setCategory(newArticle.getCategory());
        return articleRepository.save(article);
    }


    public static Article unwrapArticle(Optional<Article> entity){
        if(entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(Article.class);
    }




}

