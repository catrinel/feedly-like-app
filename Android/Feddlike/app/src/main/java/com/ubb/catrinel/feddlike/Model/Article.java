package com.ubb.catrinel.feddlike.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 11/5/2017.
 */

@Entity(tableName = "article")
public class Article implements Serializable{

    @PrimaryKey(autoGenerate = true)
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

    public Article(Integer id, String title, String description, String author, String topic) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.topic = topic;
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
        if(this.rating==0.0){
            this.rating = rating;
            history=";" + this.rating.toString();
        }
        if(!this.rating.equals(rating)){
            this.rating = (this.rating+rating)/2.0;
            history+=";" + this.rating.toString();
        }
    }

    public List<Double> getRatings(){
        List<Double> ratings = new ArrayList<>();
        String[] tokens = history.split(";");
        for (String token : tokens)
            ratings.add(Double.parseDouble(token));
        return ratings;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
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
