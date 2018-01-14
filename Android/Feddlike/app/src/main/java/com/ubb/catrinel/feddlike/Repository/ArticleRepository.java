package com.ubb.catrinel.feddlike.Repository;

import android.content.Context;

import com.ubb.catrinel.feddlike.Model.Article;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 11/5/2017.
 */

public class ArticleRepository {
    private Map<String,Article> articles;
    private int lastID;

    public ArticleRepository(Context context){
        articles = new HashMap<>();
        lastID = 1;
    }

    public Article findById(Integer id){
        for(Article article :articles.values())
            if(article.getId().equals(id))
                return article;
        return null;
    }

    public List<Article> getArticleList() {
        return new ArrayList<>(articles.values());
    }

    public void add(String key,Article article){
        if(article.getId()>lastID)
            lastID = article.getId();
        articles.put(key,article);
    }

    public void remove(Article article){
        Iterator<Map.Entry<String,Article>> it;
        it = articles.entrySet().iterator();
        Map.Entry<String,Article> entry;
        while (it.hasNext()){
            entry = it.next();
            if(entry.getValue().getId().equals(article.getId()))
                articles.remove(entry.getKey());
        }
    }

    public void update(Article article){
        Article oldArticle = findById(article.getId());
        oldArticle.setRating(article.getRating());
        oldArticle.setTopic(article.getTopic());
        oldArticle.setDescription(article.getDescription());
        oldArticle.setAuthor(article.getAuthor());
        oldArticle.setTitle(article.getTitle());
    }

    public void remove(Integer id){
        articles.values().remove(findById(id));
    }

    public Integer getLastIdD(){
        return ++lastID;
    }

    public String getKey(Article article){
        for(Map.Entry<String,Article> entry : articles.entrySet())
            if(entry.getValue().getId().equals(article.getId()))
                return entry.getKey();
        return null;
    }

    public void clear(){
        articles.clear();
    }
}
