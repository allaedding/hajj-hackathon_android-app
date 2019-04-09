package com.wolfsoft.Ammen;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MainActivity extends AppCompatActivity {
    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
    }

    /**
     * Created by mr_virwus on 7/29/18.
     */

    public static class MyItem implements ClusterItem {

        private final LatLng mPosition;
        private final String mTitle;
        private final String mSnippet;

        public MyItem(double lat, double lng, String title, String snippet) {
            mPosition = new LatLng(lat, lng);
            mTitle = title;
            mSnippet = snippet;
        }

        public MyItem(double lat, double lng) {
            mPosition = new LatLng(lat, lng);
            mTitle = "";
            mSnippet = "";
        }

        @Override
        public LatLng getPosition() {
            return mPosition;
        }

        @Override
        public String getTitle() {
            return mTitle;
        }

        @Override
        public String getSnippet() {
            return mSnippet;
        }
    }
}
