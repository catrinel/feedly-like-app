package com.ubb.catrinel.feddlike;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ubb.catrinel.feddlike.Model.Article;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }

    public void addArticle(View view) {
        String errors = new String();
        EditText titleEditText = findViewById(R.id.title);

        EditText authorEditText = findViewById(R.id.author);

        EditText topicEditText = findViewById(R.id.topic);

        EditText descriptionEditText = findViewById(R.id.description);

        if (topicEditText.getText().toString().matches("")) {
            errors += "You must enter a topic!\n";
        }
        if (authorEditText.getText().toString().matches("")) {
            errors += "You must enter an author!\n";
        }
        if (titleEditText.getText().toString().matches("")) {
            errors += "You must enter a title!\n";
        }
        if (descriptionEditText.getText().toString().matches("")) {
            errors += "You must enter a description!";
        }

        if(!errors.isEmpty()){
            Toast.makeText(this, errors, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(AddActivity.this,MainActivity.class);
        intent.putExtra("article",new Article(titleEditText.getText().toString(),
                descriptionEditText.getText().toString(), authorEditText.getText().toString(),
                    topicEditText.getText().toString()));
        setResult(RESULT_OK,intent);
        finish();

    }
}
