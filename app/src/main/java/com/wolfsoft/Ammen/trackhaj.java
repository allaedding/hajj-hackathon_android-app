package com.wolfsoft.Ammen;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class trackhaj extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

        FirebaseAuth auth;
        FirebaseUser user;

        private Circle mCircle;

private GoogleMap mMap;
private GoogleApiClient client;
private LocationRequest locationRequest;
private Location lastlocation;
public static final int REQUEST_LOCATION_CODE = 99;
        double latitude,longitude;
        List<LatLng> list = null;
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //  settingskk save=new settingskk(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
        checkLocationPermission();

        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Toast.makeText(getApplicationContext(), auth.getCurrentUser().getUid().toString(), Toast.LENGTH_SHORT).show();


        WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();
        // Log.d("macadd", address);
        Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();

        }


//**********************************************************************



        public void notification()
        {
                addNotification();
        }
        private void addNotification() {
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.drawable.ic_contacts)
                                .setContentTitle("Person is outside your range...")   //this is the title of notification
                                .setColor(101)
                                .setContentText(".............");   //this is the message showed in notification
                Intent intent = new Intent(getApplicationContext(),getClass());
                PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                long[] pattern = {500,500,500,500,500,500,500,500,500};
                builder.setVibrate(pattern);
                builder.setStyle(new NotificationCompat.InboxStyle());
                builder.setContentIntent(contentIntent);
                // Add as notification
                NotificationManager manager = (NotificationManager) getApplication().getSystemService(getApplication().NOTIFICATION_SERVICE);
                manager.notify(0, builder.build());
        }


//*************************************************************************

@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
        case REQUEST_LOCATION_CODE:
        if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
        //permission granted
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) !=  PackageManager.PERMISSION_GRANTED)
        {
        if(client == null)
        {
        bulidGoogleApiClient();
        }
       // mMap.setMyLocationEnabled(true);
        }
        }
        else
        {
        Toast.makeText(this,"Permission Denied" , Toast.LENGTH_LONG).show();
        }
        }
        }



@Override
public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        bulidGoogleApiClient();
        mMap.setMyLocationEnabled(true);
        }


        }


protected synchronized void bulidGoogleApiClient() {
        client = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        client.connect();

        }

@Override
public void onLocationChanged(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();
        lastlocation = location;

        //*******upload coordinates to firebase here *******

        Log.d("lat = ",""+latitude);
          LatLng latLng = new LatLng(location.getLatitude() , location.getLongitude());
       // LatLng latLng = new LatLng(21.389901 , 39.893107);

        //**********marker was here ************


         mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
         mMap.animateCamera(CameraUpdateFactory.zoomBy(30));


         //CameraPosition cameraPosition = new CameraPosition.Builder().target(list.get(0)).zoom(10).build();
         CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(30).build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                        float[] distance = new float[2];

                        Location.distanceBetween( location.getLatitude(), location.getLongitude(),
                                mCircle.getCenter().latitude, mCircle.getCenter().longitude, distance);


                        if( distance[0] > (mCircle.getRadius() / 2)  ){
                                //Toast.makeText(getBaseContext(), "Outside", Toast.LENGTH_LONG).show();
                                notification();

                        } else {

                        }

                }
        });

        double radiusInMeters = 5.0;
        int strokeColor = 0xffff0000; //red outline
        int shadeColor = 0x44ff0000;
        CircleOptions circleOptions = new CircleOptions().center(latLng).radius(radiusInMeters).fillColor(shadeColor).strokeColor(strokeColor).strokeWidth(8);
        mCircle = mMap.addCircle(circleOptions);

        if(client != null)
        {
        LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
        }
        }




@Override
public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(2000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED)
        {
        LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }
        }


public boolean checkLocationPermission()
        {
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED )
        {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.ACCESS_FINE_LOCATION))
        {
        ActivityCompat.requestPermissions(this,new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
        }
        else
        {
        ActivityCompat.requestPermissions(this,new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
        }
        return false;

        }
        else
        return true;
        }


@Override
public void onConnectionSuspended(int i) {
        }

@Override
public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        }
}
