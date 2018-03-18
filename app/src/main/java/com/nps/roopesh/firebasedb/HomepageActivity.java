package com.nps.roopesh.firebasedb;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class HomepageActivity extends AppCompatActivity {



    //static String s_email,s_password;
    static FirebaseAuth firebaseAuth;
    static DatabaseReference databaseUsers;
    FirebaseAuth firebaseAuth1;
    DatabaseReference databaseUsers1;
    String ab="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        firebaseAuth1=firebaseAuth;
        databaseUsers1=databaseUsers;
        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }




            databaseUsers1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String a = "";
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                       display_message(builder,"",userSnapshot.child("user_name").getValue(String.class));

                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        Toast.makeText(getApplicationContext(),ab,Toast.LENGTH_LONG).show();








    }

    void display_message(AlertDialog.Builder builder,String title,String msg){


        builder.setTitle(title).setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                }).show();


    }


    public void uploadToDatabase(User user){
        String id= databaseUsers1.push().getKey();
        databaseUsers1.child(id).setValue(user);
    }


}
