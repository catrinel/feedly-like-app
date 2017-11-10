package com.ubb.catrinel.feddlike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.ubb.catrinel.feddlike.Model.Article;
import com.ubb.catrinel.feddlike.Repository.ArticleRepository;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static ArticleRepository repo = new ArticleRepository();

    public static ArticleRepository getArticleRepository() {
        return repo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.add_Button);
        button.setOnClickListener((v)-> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });

        populateList();
        onArticleSelected();
    }

    private void onArticleSelected() {
        ListView listView = (ListView) findViewById(R.id.listView);
        final List<Article> articleList = repo.getArticleList();
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                intent.putExtra("title", articleList.get(position).getTitle());
                intent.putExtra("author", articleList.get(position).getAuthor());
                intent.putExtra("description", articleList.get(position).getDescription());
                intent.putExtra("topic", articleList.get(position).getTopic());
                startActivity(intent);
            }
        };
        listView.setOnItemClickListener(listener);
    }


    public void populateList(){
        ArticleListAdapter articleListAdapter;
        articleListAdapter = new ArticleListAdapter(this,R.layout.list_item, repo.getArticleList());
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(articleListAdapter);
    }
}
