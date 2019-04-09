package fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.ui.IconGenerator;
import com.wolfsoft.Ammen.JSONParser;
import com.wolfsoft.Ammen.MyItem;
import com.wolfsoft.Ammen.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.google.android.gms.internal.zzagz.runOnUiThread;

public class crowd extends Fragment implements OnMapReadyCallback {
    private FragmentActivity myContext;
    private GoogleMap mMap;
    private View view;
    MapView mMapView;

    private Handler handler = new Handler();
    JSONParser jsonParser = new JSONParser();
    private final int f = 9000;

    private static final String url_get_data = "http://35.196.112.188:4567/vision";//?place=jeddah&label=crowd";

    private List<LatLng> list = null;
    private ClusterManager<MyItem> mClusterManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_crowd, container, false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        mMapView = (MapView) view.findViewById(R.id.map14);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {


                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);
           //     mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

                // For zooming automatically to the location of the marker
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(21.423716,  39.827696), 15));

                // Initialize the manager with the context and the map.
                // (Activity extends context, so we can pass 'this' in the constructor.)
               // mClusterManager = new ClusterManager<MyItem>(getActivity(), mMap);

                // Point the map's listeners at the listeners implemented by the cluster
                // manager.
               // mMap.setOnCameraIdleListener(mClusterManager);
                //mMap.setOnMarkerClickListener(mClusterManager);


                IconGenerator iconFactory = new IconGenerator(getContext());

                MarkerOptions markerOptions = new MarkerOptions().
                        icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("Your text"))).
                        position(new LatLng(21.425910, 39.825218)).
                        anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());
                mMap.addMarker(markerOptions);

                MarkerOptions markerOptions2 = new MarkerOptions().
                        icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("Your text"))).
                        position(new LatLng(21.424181, 39.831670)).
                        anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());
                mMap.addMarker(markerOptions2);

                MarkerOptions markerOptions4 = new MarkerOptions().
                        icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("Your text"))).
                        position(new LatLng(21.425089, 39.828066)).
                        anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());
                mMap.addMarker(markerOptions4);
                List<LatLng> list = null;

              /*  try {
                    list = readItems(R.raw.jedah);
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "Problem reading list of locations.", Toast.LENGTH_LONG).show();
                }

                for (LatLng s :list) {
                   // mMap.addMarker(new MarkerOptions().position(s).title("Marker Title").snippet("Marker Description")); // icon(BitmapDescriptorFactory.fromResource(R.drawable.g1))
                    MyItem offsetItem = new MyItem(s);
                    mClusterManager.addItem(offsetItem);
                }*/




            }
        });
        return view;
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    public void GetData() {
        handler.postDelayed(new Runnable() {
            public void run() {

                new GetDetails().execute();
                GetData();


            }
        }, f);

    }

    /**
     * run get all product in background
     * */
    class GetDetails extends AsyncTask<String, String, String> {

        /**
         * if start get activity then run progress dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //pDialog = new ProgressDialog(getContext());
            //pDialog.setMessage("Retrieving Data...");
            // pDialog.setIndeterminate(false);
            //  pDialog.setCancelable(true);
            //pDialog.show();
        }

        /**
         * start run get all list and run in background
         * */
        protected String doInBackground(String... params) {

            // ui updates from threads executed
            runOnUiThread(new Runnable() {
                public void run() {
                    // Check tag success
                    int success;
                    // create paramater
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                    List<NameValuePair> params2 = new ArrayList<NameValuePair>();
                    //List<NameValuePair> params3 = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("place", "jamarat_1"));
                    params1.add(new BasicNameValuePair("place", "jamarat_2"));
                    params2.add(new BasicNameValuePair("place", "jamarat_3"));
                    params.add(new BasicNameValuePair("label", "crowd"));
                    params1.add(new BasicNameValuePair("label", "crowd"));
                    params2.add(new BasicNameValuePair("label", "crowd"));
                    params.add(new BasicNameValuePair("json", "true"));
                    params1.add(new BasicNameValuePair("json", "true"));
                    params2.add(new BasicNameValuePair("json", "true"));
                    // get details from the list using http request

                    // JSONObject json = jsonParser.makeHttpRequest(
                    //       url_get_data, "GET", params);

                    //JSONObject json1 = jsonParser.makeHttpRequest(
                    //      url_get_data, "GET", params1);

                    //JSONObject json2 = jsonParser.makeHttpRequest(
                    //      url_get_data, "GET", params2);

                    //getJSONArray("crowd"); // JSON

                    // Array

                    // check our logs with json response

                    // Log.d("crowd", String.valueOf(json.toString()));
                    //jamarat1 = json.getDouble("crowd");
                    //jamarat2 = json1.getDouble("crowd");
                    //jamarat3 = json2.getDouble("crowd");
                    //jamarat1 *= 100.0;
                    //jamarat2 *= 100.0;
                    //jamarat3 *= 100.0;
                    //  Log.d("Single Product Details", json.toString());




                    // Array

                    // get first list object from json array
                    //JSONObject data = productObj.getJSONObject(0);


                }

            });

            return null;
        }

        /**
         * If the job in the background is complete then stop progress reply
         * running
         * **/
        protected void onPostExecute(String file_url) {
            // stop progress dialog for get
            // pDialog.dismiss();
        }
    }
}
