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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private EditText loginUser, loginPassword;
    private TextView naRegistraciju;
    private Button login;
    private FirebaseAuth autentikacijaa;
    private ProgressDialog progress;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postavkaa();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        FirebaseUser userr = autentikacijaa.getCurrentUser();

        if(userr != null){
            finish();
            startActivity(new Intent(MainActivity.this, Mapa.class));
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = loginUser.getText().toString();
                String pass = loginPassword.getText().toString();
                validacijaa(user, pass);
            }
        });


        naRegistraciju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Registracija.class));
            }
        });


    }




    private void postavkaa(){
        loginUser = (EditText)findViewById(R.id.logUser);
        loginPassword = (EditText)findViewById(R.id.logPass);
        naRegistraciju = (TextView)findViewById(R.id.tvReg);
        login = (Button)findViewById(R.id.btLogin);
        autentikacijaa = FirebaseAuth.getInstance();
        progress = new ProgressDialog(this);



    }

    private void validacijaa(String user_name, String password){

        progress.setMessage("Login je u toku!");
        progress.show();

        autentikacijaa.signInWithEmailAndPassword(user_name, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progress.dismiss();
                    Toast.makeText(MainActivity.this,"Login je uspješan", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,Mapa.class));

                }else {
                    progress.dismiss();
                    Toast.makeText(MainActivity.this,"Login je neuspješan!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}