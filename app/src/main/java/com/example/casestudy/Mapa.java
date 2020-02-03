package com.example.casestudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Mapa extends AppCompatActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    private static final LatLng PODGORICA1 = new LatLng(42.435823,19.258363);
    private static final int EDIT_REQUEST = 1;
    DatabaseReference reff;
    Marker marker;
    List<Dostava> listaDostave;
    FirebaseAuth autentikacijaa;



    private LatLngBounds PODGORICA = new LatLngBounds(
            new LatLng(42.425660, 19.204863), new LatLng(42.471007, 19.319681));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        reff = FirebaseDatabase.getInstance().getReference().child("Psi");
        autentikacijaa = FirebaseAuth.getInstance();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        listaDostave = new ArrayList<>();
        reff.push().setValue(marker);

        final Button button = findViewById(R.id.dodaj);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {

                Toast.makeText(Mapa.this, "Dodajte lokaciju dostave!",
                        Toast.LENGTH_LONG).show();



                final Button btn = (Button) v;
                btn.setEnabled(false);
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {Thread.sleep(// minutes to sleep
                                20 *    // seconds to a minute
                                        1000); } //milliseconds
                        catch (InterruptedException e) {e.printStackTrace();}

                        Mapa.this.runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run(){ btn.setEnabled(true); }
                        });

                    }
                }).start();




                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        Intent edit = new Intent(Mapa.this, Podaci.class);
                        edit.putExtra("location", latLng);
                        Mapa.this.startActivityForResult(edit, EDIT_REQUEST);

                        mMap.setOnMapClickListener(null);
                    }
                });


            }


        });



    }


    @Override
    public void onMapReady(GoogleMap googleMap) {




        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PODGORICA1, 13));

        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()){



                    mMap.setInfoWindowAdapter(new CustomWindowAdapter(Mapa.this));

                    Dostava dostava = s.getValue(Dostava.class);

                    listaDostave.add(dostava);
                    for (int i = 0; i < listaDostave.size();i++){

                        LatLng lokacija = new LatLng(dostava.getLatt(),dostava.getLongg());
                        if (mMap != null){
                            marker = mMap.addMarker(new MarkerOptions().position(lokacija).title(dostava.getTitle()).snippet(dostava.getSnip()));
                        }
                    }

                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mMap.setLatLngBoundsForCameraTarget(PODGORICA);
        mMap.setMinZoomPreference(13.0f);


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                return false;
            }
        });






    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (EDIT_REQUEST) : {
                if (resultCode == Activity.RESULT_OK) {
                    mMap.setInfoWindowAdapter(new CustomWindowAdapter(Mapa.this));
                    MarkerOptions markerOptions = data.getParcelableExtra("marker");
                    mMap.addMarker(markerOptions);

                }
                break;
            }
        }
    }




}
