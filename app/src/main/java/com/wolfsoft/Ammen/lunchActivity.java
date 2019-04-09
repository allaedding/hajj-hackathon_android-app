package com.wolfsoft.Ammen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import customfonts.Button_Roboto_Medium;

public class lunchActivity extends AppCompatActivity {
    Button_Roboto_Medium btn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);
        btn = (Button_Roboto_Medium) findViewById(R.id.connect);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),TabMainActivity.class);
                startActivity(intent);

            }
        });
    }
}
