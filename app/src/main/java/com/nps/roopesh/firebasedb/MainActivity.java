package com.nps.roopesh.firebasedb;

import android.location.Address;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button AddUserButton,SignInButton;
    DatabaseReference databaseUsers;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);


        AddUserButton=(Button)findViewById(R.id.AddUserButton);
        SignInButton=(Button)findViewById(R.id.SignInButton);

        databaseUsers= FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();

        //signup button
        AddUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username_text;
                String password_text;

                username_text=username.getText().toString();
                password_text=password.getText().toString();
                User user=new User(username_text,password_text);


                if(TextUtils.isEmpty(username_text)){
                    show_message("Username can't be empty");
                    return;
                }

                if(TextUtils.isEmpty(password_text)){
                    show_message("Password can't be empty");
                    return;
                }


                if(password_text.length()<=6){
                    show_message("Password must be more than 6 characters long");
                    return;
                }



                registerUser(username_text,password_text);
                //uploadToDatabase(user);



            }
        });

        //login button
        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String username_text;
                String password_text;

                username_text=username.getText().toString();
                password_text=password.getText().toString();

                if(TextUtils.isEmpty(username_text)){
                    show_message("Username can't be empty");
                    return;
                }

                if(TextUtils.isEmpty(password_text)){
                    show_message("Password can't be empty");
                    return;
                }

                User user=new User(username_text,password_text);

                login(username_text,password_text);





            }
        });



    }

    private void registerUser(String username_text, String password_text){
        firebaseAuth.createUserWithEmailAndPassword(username_text,password_text).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"registered",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"some problem with registration",Toast.LENGTH_SHORT).show();
                }



            }
        });
    }

    private void login(String username_text,String password_text){
        firebaseAuth.signInWithEmailAndPassword(username_text,password_text).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Sign in successful",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"some problem with signin",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }


    void show_message(String s){
        Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
    }


    public void uploadToDatabase(User user){
        String id= databaseUsers.push().getKey();
        databaseUsers.child(id).setValue(user);
    }






}
