package com.example.user.fidyahapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.FirebaseDatabase;

public class AsnafPageDetails extends AppCompatActivity {
    static String asnafName;
    static double asnafLat;
    static double asnafLongi;
    static String keyValue;

    Firebase firebaseReference;
    FirebaseDatabase firebaseDatabase;

    /*this function created to help developer get the information from previous class*/
    public static void start(Context context, String asnagName, double asnagLat, double asnafLongitude, String keyValue2) {
        asnafName = asnagName;
        asnafLongi = asnafLongitude;
        asnafLat = asnagLat;
        keyValue = keyValue2;
        context.startActivity(new Intent(context, AsnafPageDetails.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asnaf_detail_page_activity);

        /*database reference pointing to root of database*/
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseReference = new Firebase(StaticData.FIREBASE_DATABASE_URL + "AsnafDetails");

        ((EditText) findViewById(R.id.txt_asnaf_name)).setText(asnafName);
        ((EditText) findViewById(R.id.txt_asnaf_lat)).setText(String.valueOf(asnafLat));
        ((EditText) findViewById(R.id.asnaf_longi)).setText(String.valueOf(asnafLongi));

        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAsnaf();
            }
        });

        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAsnaf();
            }
        });
    }

    /*delete the selected asnaf*/
    private void deleteAsnaf() {
        try {
            firebaseReference.child(keyValue).removeValue();
            Toast.makeText(this, "Asnaf's info removed", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(this, HomeActivity.class));
        } catch (Throwable e) {
            e.printStackTrace();
            Toast.makeText(this, "Asnaf's info failed to remove", Toast.LENGTH_SHORT).show();
        }
    }

    /*update the selected asnaf*/
    private void updateAsnaf() {
        Log.e("str", "as: " + asnafName + " " + keyValue);
        try {
            firebaseReference.child(keyValue).child("AsnafName").setValue(((EditText) findViewById(R.id.txt_asnaf_name)).getText().toString());
            firebaseReference.child(keyValue).child("Latitude").setValue(((EditText) findViewById(R.id.txt_asnaf_lat)).getText().toString());
            firebaseReference.child(keyValue).child("Longitude").setValue(((EditText) findViewById(R.id.asnaf_longi)).getText().toString());
            Toast.makeText(this, "Asnaf's info updated", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(this, HomeActivity.class));
        } catch (Throwable e) {
            e.printStackTrace();
            Toast.makeText(this, "Asnaf's info failed to update", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }
}
