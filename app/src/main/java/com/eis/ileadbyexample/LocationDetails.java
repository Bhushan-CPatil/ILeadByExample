package com.eis.ileadbyexample;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eis.ileadbyexample.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class LocationDetails extends FragmentActivity implements OnMapReadyCallback /*{

    TextView fctime,lctime,fcadd,lcad,ttltime,date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);
        getSupportActionBar().hide();

        fctime = findViewById(R.id.fctime);
        lctime = findViewById(R.id.lctime);
        fcadd = findViewById(R.id.fstadd);
        lcad = findViewById(R.id.lstadd);
        ttltime = findViewById(R.id.ttlwrk);
        date = findViewById(R.id.date);

        fctime.setText(getIntent().getStringExtra("fctime"));
        fcadd.setText(getIntent().getStringExtra("fcadd"));
        lctime.setText(getIntent().getStringExtra("lctime"));
        lcad.setText(getIntent().getStringExtra("lcadd"));
        ttltime.setText(getIntent().getStringExtra("wrktime"));
        date.setText(getIntent().getStringExtra("date"));

    }
}*/
{

    private GoogleMap mMap;
    TextView fctime,lctime,fcadd,lcad,ttltime,date;
    ProgressDialog progressDialog;
    String fclatlang,lclatlang;
    ImageButton back;
    List<LatLng> polygon = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);
        fctime = findViewById(R.id.fctime);
        lctime = findViewById(R.id.lctime);
        fcadd = findViewById(R.id.fstadd);
        lcad = findViewById(R.id.lstadd);
        ttltime = findViewById(R.id.ttlwrk);
        date = findViewById(R.id.date);
        back = findViewById(R.id.back);

        fctime.setText(getIntent().getStringExtra("fctime"));
        fcadd.setText(getIntent().getStringExtra("fcadd"));
        lctime.setText(getIntent().getStringExtra("lctime"));
        lcad.setText(getIntent().getStringExtra("lcadd"));
        ttltime.setText(getIntent().getStringExtra("wrktime"));
        date.setText(getIntent().getStringExtra("date"));
        fclatlang = getIntent().getStringExtra("fclatlang");
        lclatlang = getIntent().getStringExtra("lclatlang");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressDialog = new ProgressDialog(LocationDetails.this);

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

            String[] location = fclatlang.split(",");

            Double lat = Double.valueOf(location[0]);
            Double lang = Double.valueOf(location[1]);

            LatLng cc = new LatLng(lat,lang);
            polygon.add(cc);

            mMap.addMarker(new MarkerOptions().position(cc).title("First Call"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cc,11));
            Marker marker = mMap.addMarker(new MarkerOptions().position(cc).title("First Call"));
            marker.showInfoWindow();

            String[] location1 = lclatlang.split(",");

            lat = Double.valueOf(location1[0]);
            lang = Double.valueOf(location1[1]);

            LatLng cc1 = new LatLng(lat,lang);
            polygon.add(cc1);

            mMap.addMarker(new MarkerOptions().position(cc1).title("Last Call"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(cc1));
            Marker marker1 = mMap.addMarker(new MarkerOptions().position(cc1).title("Last Call"));
            marker1.showInfoWindow();

        }
}


