package com.triforce.serenia10;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Stats extends AppCompatActivity {
    private TextView txtUsuario;
    private TextView txtNacionalidad;
    private TextView txtMeditaciones3;
    private ConstraintLayout btnMeditar, btnChat;
    private Button btnflecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        btnChat = findViewById(R.id.btnChat);
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Stats.this, Chat.class);
                startActivity(intent);
                finish();
            }
        });

        btnflecha = findViewById(R.id.flecha);
        btnflecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Stats.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        txtUsuario = findViewById(R.id.txtUsuario);
        txtNacionalidad = findViewById(R.id.txtNacionalidad);
        txtMeditaciones3 = findViewById(R.id.txtMeditaciones3);

        btnMeditar = findViewById(R.id.btnMeditar);
        btnMeditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Stats.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        DataBaseHelper dbHelper = new DataBaseHelper(this);

        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        long userId = preferences.getLong("userId", -1); // -1 es el valor por defecto en caso de error

        if (userId != -1) {
            // Recuperar el nombre del usuario desde la base de datos
            String nombreUsuario = dbHelper.getNombreUsuarioById(userId);

            String[] userDetails = dbHelper.getNacionalidadYMeditaciones(userId);
            String nacionalidad = userDetails[0] != null ? userDetails[0] : "No disponible";
            String meditaciones = userDetails[1] != null ? userDetails[1] : "0";

            txtUsuario.setText(nombreUsuario);
            txtNacionalidad.setText(nacionalidad);
            txtMeditaciones3.setText(meditaciones);

        } else {
            txtUsuario.setText("Error al obtener el usuario actual");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        DataBaseHelper dbHelper = new DataBaseHelper(this);

        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        long userId = preferences.getLong("userId", -1);
        SharedPreferences preferencesmed = getSharedPreferences("meditaciones", MODE_PRIVATE);
        int no_med_realizadas = preferencesmed.getInt("no_med_realizadas", 0);

        if (userId != -1) {
            String nombreUsuario = dbHelper.getNombreUsuarioById(userId);

            String[] userDetails = dbHelper.getNacionalidadYMeditaciones(userId);
            String nacionalidad = userDetails[0] != null ? userDetails[0] : "No disponible";
            String meditaciones = userDetails[1] != null ? userDetails[1] : "0";
            txtMeditaciones3.setText(String.valueOf(no_med_realizadas));

            // Actualizar los TextViews
            txtUsuario.setText(nombreUsuario != null ? nombreUsuario : "Usuario desconocido");
            txtNacionalidad.setText(nacionalidad);}
    }

}