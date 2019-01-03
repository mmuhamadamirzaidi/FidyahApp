package com.example.user.fidyahapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.fidyahapp.Model.AdminDetails;
import com.example.user.fidyahapp.Model.RegisterUser;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    Firebase firebaseReference;
    FirebaseDatabase firebaseDatabase;
    EditText edtUsername, edtPassword;
    TextView txtLogin;
    String username, password;

    ArrayList<RegisterUser> registerUserArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        /*database reference pointing to root of database*/
        firebaseDatabase = FirebaseDatabase.getInstance();
        if (StaticData.isAdmin) {
            firebaseReference = new Firebase(StaticData.FIREBASE_DATABASE_URL +"AdminDetails");
        } else {
            firebaseReference = new Firebase(StaticData.FIREBASE_DATABASE_URL+"UserDetails");
        }

        Log.e("adminStatus", "adminStatus: " + StaticData.isAdmin);
        registerUserArrayList = new ArrayList<RegisterUser>();
        edtUsername = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);

        findViewById(R.id.txtLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StaticData.isAdmin) {
                    loginAdmin();
                } else {
                    loginUser();
                }

            }
        });
    }


    /*function for login*/
    private void loginUser() {
        firebaseReference.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                try {
                    for (com.firebase.client.DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        RegisterUser registerUser = dataSnapshot1.getValue(RegisterUser.class);
                        System.out.println(registerUser.getUserName()); //Testing
                        if (edtUsername.getText().toString().trim().equals(registerUser.getUserName()) && edtPassword.getText().toString().trim().equals(registerUser.getUserPassword())) {
                            finish();
                            StaticData.isAdmin = false;
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            Toast.makeText(LoginActivity.this, "Success Login...", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
                Toast.makeText(LoginActivity.this, "Invalid Username or Password...", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*function for Adminlogin*/
    private void loginAdmin() {
        firebaseReference.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                /*this one is wrong path because child path is not correct*/
//                try {
//                    Log.e("String","Striong:"+ StaticData.isAdmin);
//                    for (com.firebase.client.DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                        AdminDetails registerUser = dataSnapshot1.getValue(AdminDetails.class);
//                        System.out.println(registerUser.getAdminUsername()); //Testing
//                        if (edtUsername.getText().toString().trim().equals(registerUser.getAdminUsername()) && edtPassword.getText().toString().trim().equals(registerUser.getAdminPassword())) {
//                            finish();
//                            StaticData.isAdmin = true;
//                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//                            Toast.makeText(LoginActivity.this, "Success Login...", Toast.LENGTH_SHORT).show();
//                        }
//                        Log.e("String","Striong:"+ registerUser.getAdminPassword() + " " + registerUser.getAdminUsername());
//
//                    }
//                } catch (Throwable e) {
//                    e.printStackTrace();
//                }

                try {
                    String adminUsername = dataSnapshot.child("adminUsername").getValue(String.class);
                    String adminPassword = dataSnapshot.child("adminPassword").getValue(String.class);
                    Log.e("String", "Striong:" + adminPassword + " " + adminUsername);
                    if (edtUsername.getText().toString().trim().equals(adminUsername) && edtPassword.getText().toString().trim().equals(adminPassword)) {
                        finish();
                        StaticData.isAdmin = true;
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        Toast.makeText(LoginActivity.this, "Success Login...", Toast.LENGTH_SHORT).show();
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
                StaticData.isAdmin = false;
                Toast.makeText(LoginActivity.this, "Invalid Username or Password...", Toast.LENGTH_SHORT).show();
            }
        });

    }

}