package com.ubb.catrinel.feddlike.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by User on 12/8/2017.
 */

@Dao
public interface ArticleDAO {

    @Query("Select * from article")
    List<Article> getAll();

    @Query("Select * from article where id=:id")
    Article findById(int id);

    @Update
    void update(Article article);

    @Insert
    void insert(Article article);

    @Delete
    void delete(Article article);

    @Query("Delete from article")
    void deleteAll();
}
