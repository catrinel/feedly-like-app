package com.ubb.catrinel.feddlike;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static boolean persistenceEnabled = false;
    private DatabaseReference rolesReference;
    private FirebaseAuth firebaseAuth;
    private Map<String,String> UIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginButton = findViewById(R.id.Login_Button);

        UIDs =new HashMap<>();
        if(!persistenceEnabled){
            persistenceEnabled = true;
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
        rolesReference = FirebaseDatabase.getInstance().getReference().child("role");
        firebaseAuth = FirebaseAuth.getInstance();

        rolesReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                UIDs.put(dataSnapshot.getKey(),(String)dataSnapshot.getValue());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                UIDs.remove(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        loginButton.setOnClickListener(view -> {
            String email = ((EditText)findViewById(R.id.email_filed)).getText().toString();
            String password = ((EditText)findViewById(R.id.password_field)).getText().toString();
            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(LoginActivity.this,"No field can be empty!",Toast.LENGTH_LONG).show();
                return;
            }
            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            String uid = authResult.getUser().getUid();
                            Intent intent;
                            if(UIDs.get(uid).equals("ADMIN")){
                                intent = new Intent(LoginActivity.this,MainActivity.class);
                                Toast.makeText(LoginActivity.this,"Hello Mrs. Administrator",Toast.LENGTH_LONG).show();
                            }
                            else
                                intent = new Intent(LoginActivity.this,UserActivity.class);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this,"Invalid username or password!",Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }
}
