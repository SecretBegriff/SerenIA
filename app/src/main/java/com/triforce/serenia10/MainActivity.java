package com.triforce.serenia10;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity {
    private ConstraintLayout btnIniciarM;
    private ConstraintLayout btnlogout;
    private DrawerLayout drawerLayout;
    private Button btnOpenMenu;
    private ConstraintLayout btnStats, btnChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnChat = findViewById(R.id.btnChat);
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Chat.class);
                startActivity(intent);
                finish();
            }
        });


        btnStats = findViewById(R.id.btnStats);
        btnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Stats.class);
                startActivity(intent);
                finish();
            }
        });

        btnlogout = findViewById(R.id.btnDetener);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("login", false); // Cambiar estado a no logueado
                editor.apply();

                // Navegar a la pantalla de login
                Intent abreVentanaPrincipal = new Intent();
                abreVentanaPrincipal.setClassName("com.triforce.serenia10",
                        "com.triforce.serenia10.LogIn");
                startActivity(abreVentanaPrincipal);
                finish();
            }
        });

        btnIniciarM = findViewById(R.id.btnIniciarM);
        btnIniciarM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abreMeditar = new Intent(MainActivity.this, Meditar.class);
                startActivity(abreMeditar);
            }
        });
    }
}