package com.example.user.fidyahapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.fidyahapp.Model.RegisterUser;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URLEncoder;

public class RegisterActivity extends AppCompatActivity {

    DatabaseReference rootRef, userDetails, userNameRef, passRef, userEmailRef, userPhoneRef;
    Firebase databaseReference;
    FirebaseDatabase firebaseDatabase;
    EditText etemial, etpassword, etname, etphoneNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        etemial = findViewById(R.id.edtEmail);
        etpassword = findViewById(R.id.edtPassword);
        etname = findViewById(R.id.edtName);
        etphoneNo = findViewById(R.id.edtPhone);

//        databaseReference = new Firebase(StaticData.FIREBASE_DATABASE_URL + "UserDetails/");
        databaseReference = new Firebase(StaticData.FIREBASE_DATABASE_URL + "UserDetails");

        findViewById(R.id.txtRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(etemial.getText().toString(), etpassword.getText().toString(), etname.getText().toString(), etphoneNo.getText().toString());
            }
        });
    }

    /*register new staff*/
    private void registerUser(String email, String password, String username, String userPhone) {
//        String id = email;
//        RegisterUser registerUser = new RegisterUser();
//        databaseReference.child(id).child("UserEmail").setValue(email);
//        databaseReference.child(id).child("UserName").setValue(username);
//        databaseReference.child(id).child("UserPassword").setValue(password);
//        databaseReference.child(id).child("UserPhone").setValue(userPhone);
//        Toast.makeText(this, "Register Successful", Toast.LENGTH_SHORT).show();

        try{
            String id = email;
            id = EncodeString(email);
            RegisterUser registerUser = new RegisterUser();
            databaseReference.child(id).child("UserEmail").setValue(EncodeString(email));
            databaseReference.child(id).child("UserName").setValue(EncodeString(username));
            databaseReference.child(id).child("UserPassword").setValue(password);
            databaseReference.child(id).child("UserPhone").setValue(userPhone);
            Toast.makeText(this, "Register Successful", Toast.LENGTH_SHORT).show();
            ClearText();
        }catch (Throwable e){e.printStackTrace();}
    }

    /*set text to "" after register*/
    private void ClearText() {
        etemial.setText("");
        etphoneNo.setText("");
        etpassword.setText("");
        etname.setText("");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        StaticData.isAdmin = false;
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }

    public static String DecodeString(String string) {
        return string.replace(",", ".");
    }
}
