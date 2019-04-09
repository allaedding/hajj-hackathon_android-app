package com.wolfsoft.Ammen;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by wolfsoft4 on 22/1/18.
 */

public class Property_detail extends AppCompatActivity implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private ArrayList<LatLng> listLatLng;
    private GoogleApiClient gclient;
    private TextView price, address, bed, bath, sqft;
    private ImageView imagev,btn;
    private String strprice, straddress, strbed, strbath, strsqft, strimage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentedetail);


        Intent i = getIntent();

        strprice = i.getStringExtra("price");
        straddress = i.getStringExtra("address");
        strbath = i.getStringExtra("bath");
        strbed = i.getStringExtra("bed");
        strsqft = i.getStringExtra("sqft");
        strimage = i.getStringExtra("image");


        price = (TextView) findViewById(R.id.tvprice);
        address = (TextView) findViewById(R.id.tvaddress);
        bath = (TextView) findViewById(R.id.tvshower);
        bed = (TextView) findViewById(R.id.tvbed);
        sqft = (TextView) findViewById(R.id.tvsqft);
        imagev = (ImageView) findViewById(R.id.imagev);
        btn = (ImageView) findViewById(R.id.btn);

        price.setText(strprice);
        address.setText(straddress);
        bed.setText(strbed);
        bath.setText(strbath);
        sqft.setText(strsqft);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(in);
            }
        });
        gclient = new GoogleApiClient
                .Builder(this)
                .addApi(AppIndex.API)
                .build();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(Property_detail.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(21.488498046, 39.187332584)).zoom(12).build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //show current location
        googleMap.setMyLocationEnabled(true); // false to disable
        // Zooming Buttons
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        //Zooming Functionality
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        //Compass Functionality
        googleMap.getUiSettings().setCompassEnabled(true);
        //My Location Button
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        //Map Rotate Gesture
        googleMap.getUiSettings().setRotateGesturesEnabled(true);


    }


}
