package com.example.eduxplorap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(() -> {
            // Iniciar la siguiente actividad
            Intent intent = new Intent(this, login.class);
            startActivity(intent);
            finish();
        }, 5000);
    }
}