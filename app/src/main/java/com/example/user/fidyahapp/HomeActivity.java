package com.example.user.fidyahapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.user.fidyahapp.Model.AsnafDetails;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback {
    Firebase firebaseRef;
    FirebaseDatabase firebaseDatabase;
    ArrayList<AsnafDetails> asnafDetailsArrayList;

    private GoogleMap gMap;
    private SupportMapFragment mapFragment;
    private LocationManager locationManager;
    private double lat = 0;
    private double longi = 0;
    private LatLng latLng;
    private Geocoder geocoder;
    private Boolean change = false;
    static final int REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        asnafDetailsArrayList = new ArrayList<AsnafDetails>();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.id_map);
        mapFragment.getMapAsync(this);

        findViewById(R.id.ll_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, AddAsnaf.class));
            }
        });

        /*get permission from user to access GPS, location*/
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseRef = new Firebase(StaticData.FIREBASE_DATABASE_URL + "AsnafDetails");
        getAsnafDetails();
    }

    /*get user current location*/
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            lat = location.getLatitude();
            longi = location.getLongitude();
            latLng = new LatLng(lat, longi);

            Log.d("location", "getLocation: " + lat + " " + longi);

            /*customize the user location marker*/
            BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.icon_location);
            Bitmap bitmap = bitmapDrawable.getBitmap();
            int height = 90;
            int width = 90;
            Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, width, height, false);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16);
            gMap.animateCamera(cameraUpdate);
            gMap.addMarker(new MarkerOptions().position(latLng).title("You're Here")
                    .snippet(String.valueOf(latLng))
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

            getAddress(lat, longi);

        }
    }

    /*get the address when user click the marker*/
    private void getAddress(double lati, double longit) {
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lati, longit, 1);
            Address obj = addresses.get(0);

            String add = obj.getAddressLine(0);
//            add = add + "\n" + obj.getCountryName();
//            add = add + "\n" + obj.getCountryCode();
//            add = add + "\n" + obj.getAdminArea();
//            add = add + "\n" + obj.getPostalCode/();
//            add = add + "\n" + obj.getSubAdminArea();
//            add = add + "\n" + obj.getLocality();
//            add = add + "\n" + obj.getThoroughfare();

//            String fulladdress = add;
            Log.d("map", "getAddress: " + add);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*load asnaf detail from database*/
    private void getAsnafDetails() {
        asnafDetailsArrayList.clear();
        asnafDetailsArrayList = new ArrayList<>();
        firebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    AsnafDetails asnafDetails = dataSnapshot1.getValue(AsnafDetails.class);
                    asnafDetails.setKeyValue(dataSnapshot1.getKey());
                    System.out.println(asnafDetails.getAsnafName()); //Testing
                    System.out.println(asnafDetails.getLatitude()); //Testing
                    System.out.println(asnafDetails.getLongitude()); //Testing
                    asnafDetailsArrayList.add(asnafDetails);

                    addmarkerAsnaf();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    /*get permission from user to access GPS, location*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION:
                getLocation();
                break;
        }
    }

    /*initiate the map */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        /*In Xioami phone can't run?*/
//        googleMap.setMyLocationEnabled(true);
        gMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        getLocation();

        gMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                gMap.clear();
                gMap.addMarker(new MarkerOptions().position(latLng).title("You Picked Here").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                double a = latLng.latitude;
                double b = latLng.longitude;
                getAddress(a, b);
            }

        });

    }

    /*show all marker(location) for every asnaf*/
    public void addmarkerAsnaf() {
        for (int i = 0; i < asnafDetailsArrayList.size(); i++) {
            Log.e("map", "getLati2: " + asnafDetailsArrayList.toString());
            createMarker(asnafDetailsArrayList.get(i).getLatitude(), asnafDetailsArrayList.get(i).getLongitude(), asnafDetailsArrayList.get(i).getAsnafName(), null, 0);
        }
    }

    /*point the marker based on lat, longi*/
    protected Marker createMarker(double lati, double longit, String title, String snippet, int iconDesc) {
        return gMap.addMarker(new MarkerOptions()
                .position(new LatLng(lati, longit))
                .anchor(0.5f, 0.5f).title(title));
    }

    @Override
    protected void onResume() {
        super.onResume();
        try{
        asnafDetailsArrayList.clear();
        asnafDetailsArrayList = new ArrayList<>();
    }catch (Exception e){e.printStackTrace();}}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
