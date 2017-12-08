package com.ubb.catrinel.feddlike;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.ubb.catrinel.feddlike.Model.Article;
import com.ubb.catrinel.feddlike.Model.ArticleDAO;

/**
 * Created by User on 12/8/2017.
 */

@Database(entities = {Article.class},version = 2)
public abstract class FeedlyConnection extends RoomDatabase{
    public abstract ArticleDAO articleDAO();
}
