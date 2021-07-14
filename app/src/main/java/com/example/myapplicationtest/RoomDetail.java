package com.example.myapplicationtest;

import android.os.Bundle;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;

public class RoomDetail extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        Toolbar mToolbar= (Toolbar) findViewById(R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(view -> onBackPressed());
        ArrayList<String> data = getIntent().getStringArrayListExtra("data");
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
                // Set title of Detail page
        collapsingToolbar.setTitle(data.get(0));
        TextView councilDetail = (TextView) findViewById(R.id.council_detail);
        councilDetail.setText(data.get(1));
        TextView placeDetail = (TextView) findViewById(R.id.place_detail);
        placeDetail.setText(data.get(2));
        TextView locationDetail = (TextView) findViewById(R.id.place_location);
        locationDetail.setText(data.get(3));
        TextView date = (TextView) findViewById(R.id.place_date);
        date.setText(data.get(4));
        TextView time = (TextView) findViewById(R.id.place_time);
        time.setText(data.get(5));

    }
}
