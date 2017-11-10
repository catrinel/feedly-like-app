package com.ubb.catrinel.feddlike;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }

    public void addArticle(View view) {
        String errors = new String();
        EditText titleEditText = (EditText) findViewById(R.id.title);

        EditText authorEditText = (EditText) findViewById(R.id.author);

        EditText topicEditText = (EditText) findViewById(R.id.topic);

        EditText descriptionEditText = (EditText) findViewById(R.id.description);

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

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"tipitza@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Add article");
        intent.putExtra(Intent.EXTRA_TEXT, authorEditText.getText() + " added a new article!" );
        try {
            startActivity(Intent.createChooser(intent, "Send mail using..."));
        }catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(AddActivity.this, "There are no Email clients installed!", Toast.LENGTH_SHORT).show();
        }

    }
}
