package com.ubb.catrinel.feddlike;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ubb.catrinel.feddlike.Model.Article;
import com.ubb.catrinel.feddlike.Repository.ArticleRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserActivity extends AppCompatActivity {

    private DatabaseReference referenceDB;
    private ArticleRepository repo;
    private ArticleListAdapter articleListAdapter;
    private ListView listView;
    private ExecutorService executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        repo = new ArticleRepository(getApplicationContext());
        listView = findViewById(R.id.listView);
        executor = Executors.newFixedThreadPool(10);

        createList();
        onArticleSelected();
    }

    private void onArticleSelected() {
        ListView listView = findViewById(R.id.listView);
        AdapterView.OnItemClickListener listener = (parent, view, position, id) -> {
            Intent intent = new Intent(UserActivity.this,DetailsActivity.class);
            intent.putExtra("article", repo.getArticleList().get(position));
            startActivityForResult(intent,1);
        };
        listView.setOnItemClickListener(listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode){
            case 1 : {
                if(resultCode == RESULT_OK) {
                    Article article = (Article) data.getSerializableExtra("article");
                    executor.submit(()-> {
                        referenceDB.child(repo.getKey(article)).setValue(article);
                    });
                    break;
                }
            }
        }
    }


    public void createList(){
        articleListAdapter = new ArticleListAdapter(this,R.layout.user_list_item, repo.getArticleList());
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(articleListAdapter);
        referenceDB = FirebaseDatabase.getInstance().getReference().child("article");
        referenceDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Article article = dataSnapshot.getValue(Article.class);
                repo.add(dataSnapshot.getKey(),article);
                articleListAdapter.updateListView(repo.getArticleList());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Article article = dataSnapshot.getValue(Article.class);
                repo.update(article);
                articleListAdapter.updateListView(repo.getArticleList());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Article article = dataSnapshot.getValue(Article.class);
                repo.remove(article.getId());
                articleListAdapter.updateListView(repo.getArticleList());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
