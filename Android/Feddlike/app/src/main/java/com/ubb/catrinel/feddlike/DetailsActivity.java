package com.ubb.catrinel.feddlike;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.ubb.catrinel.feddlike.Model.Article;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by User on 11/5/2017.
 */

public class DetailsActivity extends AppCompatActivity {
    private Article article;
    private NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        article = (Article) getIntent().getSerializableExtra("article");

        numberPicker = findViewById(R.id.ratingPicker);
        numberPicker.setMaxValue(5);
        numberPicker.setMinValue(1);
        numberPicker.setWrapSelectorWheel(false);

        showDetails(article);
        createChart();
    }

    public void showDetails(Article article){
        TextView titleView = findViewById(R.id.title);
        titleView.setText(article.getTitle());
        TextView authorView = findViewById(R.id.author);
        authorView.setText(article.getAuthor());
        TextView descriptionView = findViewById(R.id.description);
        descriptionView.setText(article.getDescription());
        TextView topicView = findViewById(R.id.topic);
        topicView.setText(article.getTopic());
    }

    private void createChart(){
        LineChart chart = findViewById(R.id.artistChart);
        Description description = new Description();
        description.setText("A Chart Presenting the evolution of the article's rating");
        chart.setDescription(description);

        int x = 0;
        List<Double> data = article.getRatings();
        List<Entry> entries = new ArrayList<>();
        for(Double p : data)
            entries.add(new Entry(x++,p.floatValue()));
        LineDataSet dataSet = new LineDataSet(entries, article.getTitle());
        dataSet.setColor(R.color.colorPrimaryDark);
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();
    }

    public void save(View view){
        TextView titleView = findViewById(R.id.title);
        article.setTitle(titleView.getText().toString());
        TextView authorView = findViewById(R.id.author);
        article.setAuthor(authorView.getText().toString());
        TextView descriptionView = findViewById(R.id.description);
        article.setDescription(descriptionView.getText().toString());
        TextView topicView = findViewById(R.id.topic);
        article.setTopic(topicView.getText().toString());
        article.setRating((double) numberPicker.getValue());
        Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
        intent.putExtra("article",article);
        setResult(RESULT_OK,intent);
        finish();
    }
}
