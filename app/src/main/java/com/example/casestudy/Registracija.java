package com.example.casestudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registracija extends AppCompatActivity {

    private EditText registracijaUser, registracijaPassword, registracijaMail;
    private TextView naLogin;
    private Button registracija;
    private FirebaseAuth autentikacija;
    private ProgressDialog progresss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija);
        postavka();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        registracija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progresss.setMessage("Registracija je u toku.");
                progresss.show();

                if(validacija()){
                    String user_email = registracijaMail.getText().toString().trim();
                    String user_password = registracijaPassword.getText().toString().trim();

                    autentikacija.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                progresss.dismiss();
                                Toast.makeText(Registracija.this, "Registracija uspješno obavljena!",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(Registracija.this, MainActivity.class));
                            } else {
                                progresss.dismiss();
                                Toast.makeText(Registracija.this, "Registracija nije uspješno obavljena!",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }

            }
        });


        naLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Registracija.this, MainActivity.class));
            }
        });



    }

    private void postavka(){
        registracijaUser = (EditText)findViewById(R.id.regUser);
        registracijaPassword = (EditText)findViewById(R.id.regPass);
        registracijaMail = (EditText)findViewById(R.id.regMail);
        registracija = (Button)findViewById(R.id.btReg);
        naLogin = (TextView)findViewById(R.id.tvLogin);
        progresss = new ProgressDialog(this);
        autentikacija = FirebaseAuth.getInstance();
    }

    private Boolean validacija(){
        Boolean result = false;

        String korIme = registracijaUser.getText().toString();
        String pass = registracijaPassword.getText().toString();
        String mail = registracijaMail.getText().toString();

        if (korIme.isEmpty() || pass.isEmpty() || mail.isEmpty() ){
            Toast.makeText(this, "Molimo vas unesite podatke u sva polja.", Toast.LENGTH_SHORT).show();
        }

        else {
            result = true;
        }

        return result;
    }
}
