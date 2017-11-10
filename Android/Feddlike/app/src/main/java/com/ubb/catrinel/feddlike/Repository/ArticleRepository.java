package com.ubb.catrinel.feddlike.Repository;

import com.ubb.catrinel.feddlike.Model.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 11/5/2017.
 */

public class ArticleRepository {
    private List<Article> articleList;

    public ArticleRepository(List<Article> articleList) {
        this.articleList = articleList;
    }

    public ArticleRepository() {
        this.articleList = new ArrayList<>();
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

        this.articleList.add(article1);
        this.articleList.add(article2);
        this.articleList.add(article3);
        this.articleList.add(article4);
    }

    public int findByTitle(String title){
        for(int i = 0; i < articleList.size(); i++){
            if (articleList.get(i).getTitle().equals(title))
                return i;
        }
        return -1;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    public void updateArticle(String oldTitle, String title, String description, String author, String topic){
        int position = this.findByTitle(oldTitle);
        if (position!= -1){
            Article article = this.getArticleList().get(position);
            article.setAuthor(author);
            article.setDescription(description);
            article.setTitle(title);
            article.setTopic(topic);
        }
    }
}
