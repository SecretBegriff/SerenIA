package com.triforce.serenia10;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Meditar extends AppCompatActivity {
    private TextView txtHoras, txtMinutos, txtSegundos, txtPausa;
    private int ContadorH, ContadorM, ContadorS;
    private Handler handler = new Handler();
    private boolean isRunning;
    private SharedPreferences preferences;
    private ConstraintLayout btnPausa, btnTerminar;
    private Cursor cursor = null;
    private DataBaseHelper dbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditar);

        ContadorH = 0;
        ContadorM = 0;
        ContadorS = 0;
        isRunning = true;


        dbHelper = new DataBaseHelper(this);

        txtPausa = findViewById(R.id.txtPausa);
        txtHoras = findViewById(R.id.txtHoras);
        txtMinutos = findViewById(R.id.txtMinutos);
        txtSegundos = findViewById(R.id.txtSegundos);

        btnPausa = findViewById(R.id.btnPausa);
        btnPausa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRunning = !isRunning; // Alternar entre pausar y reanudar
                if (isRunning) {
                    iniciarContador();
                    txtPausa.setText("Pausar");
                }
                else{
                    txtPausa.setText("Continuar");
                }
            }
        });

        btnTerminar = findViewById(R.id.btnTerminar);
        btnTerminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRunning = false;
                String horas = txtHoras.getText().toString();
                String minutos = txtMinutos.getText().toString();
                String segundos = txtSegundos.getText().toString();
                String duracionTotal = String.format("%02d:%02d:%02d", horas, minutos, segundos);

                SharedPreferences preferences = getSharedPreferences("meditaciones", MODE_PRIVATE);
                int no_med_realizadas = preferences.getInt("no_med_realizadas", 0);

                finish();

            }
        });

        iniciarContador();

    }

    private void iniciarContador() {
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isRunning) {
                    txtSegundos.setText(String.format("%02d", ContadorS));
                    txtMinutos.setText(String.format("%02d", ContadorM));
                    txtHoras.setText(String.format("%02d", ContadorH));
                    handler.postDelayed(this, 1000);
                    ContadorS++;

                    if (ContadorS > 59) {
                        ContadorS = 0;
                        ContadorM++;
                    }
                    if(ContadorM > 59){
                        ContadorM = 0;
                        ContadorH++;

                    }
                    if(ContadorH > 99){
                        ContadorH = 0;

                    }
                }
            }
        }, 1000);
    }


}