package com.ubb.catrinel.feddlike.Model;

/**
 * Created by User on 11/5/2017.
 */

public class Article {
    private String title;
    private String description;
    private String author;
    private String topic;

    public Article(String title, String description, String author, String topic) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.topic = topic;
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
