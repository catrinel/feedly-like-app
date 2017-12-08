package com.ubb.catrinel.feddlike.Repository;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.ubb.catrinel.feddlike.FeedlyConnection;
import com.ubb.catrinel.feddlike.Model.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 11/5/2017.
 */

public class ArticleRepository {
    private final FeedlyConnection connection;

    /*public ArticleRepository(Context context) {
        connection = Room.databaseBuilder(context, FeedlyConnection.class,"feedlyDB")
                .allowMainThreadQueries().build();
    }*/

    public ArticleRepository(Context context) {
        connection = Room.databaseBuilder(context, FeedlyConnection.class,"feedlyDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        Article article1 = new Article("Spring Boot", "Spring Boot is designed to get you up and running" +
                " as quickly as possible, with minimal upfront configuration of Spring. Spring Boot takes " +
                "an opinionated view of building production ready applications.",
                "spring.io", "Spring");
        Article article2 = new Article("A submarine restaurant in Norway", "Thanks to the architects of " +
                "Snøhetta and this crazy project, Norway could become the first European country to have " +
                "a submarine restaurant. Named “Under”, which also means “wonder” in Norwegian, the building " +
                "will be constructed five meters deep and could welcome between 80 and 100 people.",
                "fubiz", "Design");
        Article article3 = new Article("PS Plus: free games from November", "Greetings, PlayStation " +
                "Plus members. November is another huge month, so strap in and get ready!" +
                "As we continue to celebrate the one year anniversary of PS VR*, we are giving " +
                "PlayStation Plus members another bonus PlayStation VR game.",
                "PlayStation Blog", "Gaming");
        Article article4 = new Article("Test article", "Some text here", "Me", "Other");

        connection.articleDAO().deleteAll();
        connection.articleDAO().insert(article1);
        connection.articleDAO().insert(article2);
        connection.articleDAO().insert(article3);
        connection.articleDAO().insert(article4);
    }

    public Article findById(Integer id){
        return connection.articleDAO().findById(id);
    }

    public List<Article> getArticleList() {
        return connection.articleDAO().getAll();
    }

    public void add(Article article){
        connection.articleDAO().insert(article);
    }

    public void remove(Article article){
        connection.articleDAO().delete(article);
    }

    public void update(Article article){
        connection.articleDAO().update(article);
    }
}
