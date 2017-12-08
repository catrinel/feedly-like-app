package com.ubb.catrinel.feddlike;

import android.content.DialogInterface;
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

import com.ubb.catrinel.feddlike.Model.Article;
import com.ubb.catrinel.feddlike.Repository.ArticleRepository;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArticleRepository repo;
    private ArticleListAdapter articleListAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repo = new ArticleRepository(getApplicationContext());
        listView = findViewById(R.id.listView);

        Button button = findViewById(R.id.add_Button);
        button.setOnClickListener((v)-> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivityForResult(intent,2);
        });

        populateList();
        onArticleSelected();
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
                    repo.update(article);
                    articleListAdapter.updateListView(repo.getArticleList());
                    break;
                }
            }
            case 2 :{
                if(resultCode == RESULT_OK){
                    Article article = (Article) data.getSerializableExtra("article");
                    repo.add(article);
                    articleListAdapter.updateListView(repo.getArticleList());
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


    public void populateList(){
        articleListAdapter = new ArticleListAdapter(this,R.layout.list_item, repo.getArticleList());
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(articleListAdapter);
    }

    public void deleteArticle(View view){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        dialogBuilder.setMessage("Are you sure you want to delete the Article ?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    final int position = listView.getPositionForView((View) view.getParent());
                    Article article = new Article();
                    int id = repo.getArticleList().get(position).getId();
                    article.setId(id);
                    repo.remove(article);
                    articleListAdapter.updateListView(repo.getArticleList());
                    Toast.makeText(MainActivity.this,"Remove successful",Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }).setNegativeButton("No", (dialogInterface, i) -> {
                    Toast.makeText(MainActivity.this,"Remove failed",Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                });
        dialogBuilder.show();
    }
}
