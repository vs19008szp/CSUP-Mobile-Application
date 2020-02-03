package com.example.casestudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Podaci extends AppCompatActivity {

    long maxid =0;
    LatLng latlng;
    EditText title, opis, narudzbina;
    Dostava dostava;
    DatabaseReference reff;
    Double latt, longg;
    RadioButton radio;
    RadioGroup grupa;
    String snip,dodaci;
    boolean check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podaci);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        latlng = (LatLng) getIntent().getParcelableExtra("location");
        latt = latlng.latitude;
        longg = latlng.longitude;
        title = (EditText) findViewById(R.id.title);
        opis = (EditText) findViewById(R.id.snip);
        narudzbina = (EditText) findViewById(R.id.narudzbina);
        grupa = (RadioGroup) findViewById(R.id.grupa);

        dostava = new Dostava();





        reff = FirebaseDatabase.getInstance().getReference().child("Dostava");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    maxid = (dataSnapshot.getChildrenCount());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        final Button snimi = (Button) findViewById(R.id.snimi);
        snimi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if (check = false){
                    Toast.makeText(Podaci.this,"Molimo vas izaberite opciju za ogrlicu.", Toast.LENGTH_SHORT).show();
                } else {


                    int radioID = grupa.getCheckedRadioButtonId();

                    radio = findViewById(radioID);

                    dodaci = (String) radio.getText();

                    snip = "Vaša Narudžbina: " + narudzbina.getText() + "\n" + "Dodaci: " + dodaci + "\n" + "Informacije kupca: " + opis.getText();
                }

                MarkerOptions marker = new MarkerOptions().position(latlng);
                if (title.getText() != null || snip != null) {

                    dostava.setLatt(latt);
                    dostava.setLongg(longg);
                    dostava.setTitle(title.getText().toString());
                    dostava.setSnip(snip);
                    reff.child(String.valueOf(maxid+1 )).setValue(dostava);

                    marker.title(title.getText().toString());
                    marker.snippet(snip);
                }

                Intent resultIntent = new Intent();
                resultIntent.putExtra("marker", marker);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

    }

    public void check (View view){
        check = true;
    }
}

