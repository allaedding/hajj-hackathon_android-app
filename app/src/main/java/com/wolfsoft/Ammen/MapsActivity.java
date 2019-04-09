package com.wolfsoft.Ammen;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private ArrayList<LatLng> readItems(int resource) throws JSONException {
        ArrayList<LatLng> list = new ArrayList<LatLng>();
        InputStream inputStream = getResources().openRawResource(resource);
        String json = new Scanner(inputStream).useDelimiter("\\A").next();
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            double lat = object.getDouble("lat");
            double lng = object.getDouble("lng");
            list.add(new LatLng(lat, lng));
        }
        return list;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        int[] colors = {
                Color.GREEN,    // green(0-50)
                Color.GREEN,    // yellow(51-100)
                Color.GREEN, // Color.rgb(255,165,0), //Orange(101-150)
                Color.GREEN,              //red(151-200)
                Color.GREEN,// Color.rgb(153,50,204), //dark orchid(201-300)
                Color.GREEN//Color.rgb(165,42,42) //brown(301-500)
        };

        float[] startpoints = {
                0.1F, 0.2F, 0.3F, 0.4F, 0.6F, 1.0F
        };
        Gradient gradient = new Gradient(colors,startpoints);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(21.488498046, 39.187332584);



        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                sydney).zoom(5).build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        List<LatLng> list = null;

        List<WeightedLatLng> list1 = null ;

        // Get the data: latitude/longitude positions of police stations.
        try {
            list = readItems(R.raw.jedah);
            list1 = weigt(list);
        } catch (JSONException e) {
            Toast.makeText(this, "Problem reading list of locations.", Toast.LENGTH_LONG).show();
        }
        HeatmapTileProvider mProvider = new HeatmapTileProvider.Builder()
                .weightedData(list1)
                //.data(list)
                .gradient(gradient)
                .build();
        // Add a tile overlay to the map, using the heat map tile provider.
        TileOverlay mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));

       // FirebaseDatabase database = FirebaseDatabase.getInstance();
        // DatabaseReference myRef = database.getReference("message1");

        //myRef.setValue("Hello, Worldsdsqd!");
    }

    private List<WeightedLatLng> weigt(List<LatLng> list) {
        ArrayList<WeightedLatLng> list1 = new ArrayList<WeightedLatLng>();

        for (LatLng s : list){
            WeightedLatLng i = new WeightedLatLng(s,100.0);

            list1.add(i);
        }

        return list1;
    }
}
