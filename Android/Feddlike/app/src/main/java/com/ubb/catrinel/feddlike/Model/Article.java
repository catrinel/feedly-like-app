package com.ubb.catrinel.feddlike.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 11/5/2017.
 */

public class Article implements Serializable{

    private Integer id;
    private String title;
    private String description;
    private String author;
    private String topic;
    private Double rating;
    private String history;

    public Article() {
        rating = 0.0;
        history = "0.0";
    }

    public Article(String title, String description, String author, String topic) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.topic = topic;
        rating = 0.0;
        this.history = rating.toString();
    }

    public Article(Integer id,String title, String description, String author, String topic) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.topic = topic;
        rating = 0.0;
        this.history = rating.toString();
    }

    /*public Article(Integer id, String title, String description, String author, String topic) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.topic = topic;
    }*/

    public void setData(Article article){
        this.id = article.getId();
        this.description = article.getDescription();
        this.rating = article.getRating();
        this.author = article.getAuthor();
        this.title = article.getTitle();
        this.topic = article.getTopic();
        this.history = article.getHistory();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        if(!this.rating.equals(rating)){
            this.rating = (this.rating+rating)/2.0;
        }
        history+=";" + rating.toString();
    }

    public List<Double> allRatings(){
        List<Double> ratings = new ArrayList<>();
        String[] tokens = history.split(";");
        for (String token : tokens)
            ratings.add(Double.parseDouble(token));
        return ratings;
    }

    String getHistory() {
        return history;
    }

    void setHistory(String history) {
        this.history = history;
    }

    public boolean empty(){
        return title==null && description==null && author==null;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }
}
