package com.dev.ric.mensageiro.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dev.ric.mensageiro.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    // Write a message to the database
    //FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference myRef = database.getReference("ricardo");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //myRef.setValue("configurado");
    }
}
