package com.example.user.fidyahapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.view.View;

import com.example.user.fidyahapp.Model.AsnafDetails;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StaticData.isAdmin = false;
        findViewById(R.id.home_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int1= new Intent(MainActivity.this, HomeActivity.class);
                startActivity(int1);
            }
        });

        findViewById(R.id.login_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticData.isAdmin = false;
                Intent int1= new Intent(MainActivity.this, LoginActivity.class);
                startActivity(int1);            }
        });

        findViewById(R.id.register_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int1= new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(int1);
            }
        });

        findViewById(R.id.txtadmin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticData.isAdmin = true;
                Intent int1= new Intent(MainActivity.this, LoginActivity.class);
                startActivity(int1);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        StaticData.isAdmin = false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();

    }
}
