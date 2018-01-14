package com.ubb.catrinel.feddlike;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {

    private DatabaseReference referenceDB;
    private ArticleRepository repo;
    private ArticleListAdapter articleListAdapter;
    private ListView listView;
    private ExecutorService executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        repo = new ArticleRepository(getApplicationContext());
        listView = findViewById(R.id.listView);
        executor = Executors.newFixedThreadPool(10);

        Button button = findViewById(R.id.add_Button);
        button.setOnClickListener((v)-> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivityForResult(intent,2);
        });

        createList();
        onArticleSelected();
        populateDB();
    }

    private void onArticleSelected() {
        ListView listView = findViewById(R.id.listView);
        AdapterView.OnItemClickListener listener = (parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
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
            case 2 :{
                if(resultCode == RESULT_OK){
                    Article article = (Article) data.getSerializableExtra("article");
                    article.setId(repo.getLastIdD());
                    executor.submit(()-> referenceDB.push().setValue(article));
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setType("text/plain");
                    intent.setData(Uri.parse("mailto:"));
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"tipitza@gmail.com"});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Add article");
                    intent.putExtra(Intent.EXTRA_TEXT, article.getAuthor() + " added a new article!" );
                    try {
                        startActivity(Intent.createChooser(intent, "Send mail using..."));
                    }catch (android.content.ActivityNotFoundException ex){
                        Toast.makeText(MainActivity.this, "There are no Email clients installed!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }
    }


    public void createList(){
        articleListAdapter = new ArticleListAdapter(this,R.layout.list_item, repo.getArticleList());
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


    public void populateDB(){
        referenceDB.removeValue();
        repo.clear();

        Article article1 = new Article(1,"Spring Boot", "Spring Boot is designed to get you up and running" +
                " as quickly as possible, with minimal upfront configuration of Spring. Spring Boot takes " +
                "an opinionated view of building production ready applications.",
                "spring.io", "Spring");
        Article article2 = new Article(2,"A submarine restaurant in Norway", "Thanks to the architects of " +
                "Snøhetta and this crazy project, Norway could become the first European country to have " +
                "a submarine restaurant. Named “Under”, which also means “wonder” in Norwegian, the building " +
                "will be constructed five meters deep and could welcome between 80 and 100 people.",
                "fubiz", "Design");
        Article article3 = new Article(3,"PS Plus: free games from November", "Greetings, PlayStation " +
                "Plus members. November is another huge month, so strap in and get ready!" +
                "As we continue to celebrate the one year anniversary of PS VR*, we are giving " +
                "PlayStation Plus members another bonus PlayStation VR game.",
                "PlayStation Blog", "Gaming");
        Article article4 = new Article(4,"Test article", "Some text here", "Me", "Other");

        referenceDB.push().setValue(article1);
        referenceDB.push().setValue(article2);
        referenceDB.push().setValue(article3);
        referenceDB.push().setValue(article4);
    }

    public void deleteArticle(View view){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        dialogBuilder.setMessage("Are you sure you want to delete the Article ?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    final int position = listView.getPositionForView((View) view.getParent());
                    Article article = new Article();
                    int id = repo.getArticleList().get(position).getId();
                    article.setId(id);
                    executor.submit((Runnable) () -> referenceDB.child(repo.getKey(article)).removeValue());
                    Toast.makeText(MainActivity.this,"Remove successful",Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }).setNegativeButton("No", (dialogInterface, i) -> {
                    Toast.makeText(MainActivity.this,"Remove failed",Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                });
        dialogBuilder.show();
    }
}
