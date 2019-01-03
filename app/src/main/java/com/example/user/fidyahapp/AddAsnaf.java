package com.example.user.fidyahapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.fidyahapp.Model.AsnafDetails;
import com.example.user.fidyahapp.Model.RegisterUser;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddAsnaf extends AppCompatActivity {
    Firebase databaseReference;
    EditText edtName, edtLat, edtLongi;
    RecyclerView list;
    String asnafname, asnafLat, asnafLongi;

    Firebase firebaseRef;
    FirebaseDatabase firebaseDatabase;
    ArrayList<AsnafDetails> asnafDetailsArrayList;
    AsnafNameAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addasnaf_activity);

        edtName = findViewById(R.id.edt_asnaf_name);
        edtLat = findViewById(R.id.edt_latitude);
        edtLongi = findViewById(R.id.edt_longitude);
        list = findViewById(R.id.ll_list_asnaf);

        asnafDetailsArrayList = new ArrayList<AsnafDetails>();
        asnafDetailsArrayList.clear();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = new Firebase(StaticData.FIREBASE_DATABASE_URL + "AsnafDetails/");

        findViewById(R.id.ll_add_asnaf).setVisibility(View.GONE);
        findViewById(R.id.ll_add_asnaf2).setVisibility(View.GONE);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setItemAnimator(new DefaultItemAnimator());
        findViewById(R.id.btn_add_asnaf).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.ll_add_asnaf).setVisibility(View.VISIBLE);
                findViewById(R.id.ll_add_asnaf2).setVisibility(View.GONE);
            }
        });

        findViewById(R.id.btn_add_asnaf2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAsnafList();
                findViewById(R.id.ll_add_asnaf2).setVisibility(View.VISIBLE);
                findViewById(R.id.ll_add_asnaf).setVisibility(View.GONE);
            }
        });

        findViewById(R.id.txt_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOut();
            }
        });

        findViewById(R.id.btn_reg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAsnaf(edtName.getText().toString().trim(), edtLat.getText().toString().trim(), edtLongi.getText().toString().trim());
            }
        });
    }

    /*register the new Asnaf*/ /*id here is for branch name*/
    private void registerAsnaf(String name, String lat, String longi) {
        String id = name;
        databaseReference.child(id).child("AsnafName").setValue(name);
        databaseReference.child(id).child("Latitude").setValue(lat);
        databaseReference.child(id).child("Longitude").setValue(longi);
        Toast.makeText(this, "New Asnaf's Registered Successful", Toast.LENGTH_SHORT).show();
        ClearText();
    }

    /*set text field to "" after process*/
    private void ClearText() {
        edtLongi.setText("");
        edtLat.setText("");
        edtName.setText("");
        asnafDetailsArrayList.clear();
    }

    /*get all the asnaf list in firebase database*/
    private void getAsnafList() {
        asnafDetailsArrayList.clear();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    AsnafDetails asnafDetails = dataSnapshot1.getValue(AsnafDetails.class);
                    asnafDetails.setKeyValue(dataSnapshot1.getKey());
                    asnafDetailsArrayList.add(asnafDetails);
                    Log.e("chcek", "chek: " + asnafDetailsArrayList.size() + asnafDetails.getKeyValue());
                }
                adapter = new AsnafNameAdapter(AddAsnaf.this, asnafDetailsArrayList);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    /*clear all variable, storage, and finish all activities before display mainActivity*/
    private void LogOut() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
