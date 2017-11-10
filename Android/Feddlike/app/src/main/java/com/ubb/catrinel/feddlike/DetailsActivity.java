package com.ubb.catrinel.feddlike;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by User on 11/5/2017.
 */

public class DetailsActivity extends AppCompatActivity {
    private String oldTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        String title = getIntent().getStringExtra("title");
        String author = getIntent().getStringExtra("author");
        String description = getIntent().getStringExtra("description");
        String topic = getIntent().getStringExtra("topic");
        oldTitle = title;

        showDetails(title,description,author,topic);
    }

    public void showDetails(String name, String description, String author, String topic){
        TextView titleView = (TextView)findViewById(R.id.title);
        titleView.setText(name);
        TextView authorView = (TextView)findViewById(R.id.author);
        authorView.setText(author);
        TextView descriptionView = (TextView)findViewById(R.id.description);
        descriptionView.setText(description);
        TextView topicView = (TextView)findViewById(R.id.topic);
        topicView.setText(topic);
    }

    public void save(View view){
        TextView titleView = (TextView)findViewById(R.id.title);
        String title = titleView.getText().toString();
        TextView authorView = (TextView)findViewById(R.id.author);
        String author = authorView.getText().toString();
        TextView descriptionView = (TextView)findViewById(R.id.description);
        String description = descriptionView.getText().toString();
        TextView topicView = (TextView)findViewById(R.id.topic);
        String topic = topicView.getText().toString();

        MainActivity.getArticleRepository().updateArticle(oldTitle, title,description, author, topic);

        Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
