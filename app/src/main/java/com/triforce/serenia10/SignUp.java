package com.triforce.serenia10;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class SignUp extends AppCompatActivity {
    private static final int ADD_ID = Menu.FIRST + 1;
    private EditText txtCorreo, txtNombre, txtNacionalidad, txtContrasena;
    private Button btnRegistro;
    private DataBaseHelper dbHelper = null;
    private Cursor cursor = null;
    private Button btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dbHelper = new DataBaseHelper(this);

        txtCorreo = findViewById(R.id.txtCorreo);
        txtNombre = findViewById(R.id.txtNombre);
        txtNacionalidad = findViewById(R.id.txtNacionalidad);
        txtContrasena = findViewById(R.id.txtContrasena);

        btnAtras = findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abreVentanaPrincipal = new Intent();
                abreVentanaPrincipal.setClassName("com.triforce.serenia10",
                        "com.triforce.serenia10.LogIn");
                startActivity(abreVentanaPrincipal);
                finish();
            }
        });

        btnRegistro = findViewById(R.id.btnRegistro);
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = txtCorreo.getText().toString();
                String nombre = txtNombre.getText().toString();
                String nacionalidad = txtNacionalidad.getText().toString();
                String contrasena = txtContrasena.getText().toString();

                if (!correo.isEmpty() && !nombre.isEmpty() && !nacionalidad.isEmpty() && !contrasena.isEmpty()) {
                    ContentValues cv = new ContentValues();
                    cv.put(DataBaseHelper.EMAIL, correo);
                    cv.put(DataBaseHelper.NOMBRE, nombre);
                    cv.put(DataBaseHelper.PAIS_ORIGEN, nacionalidad);
                    cv.put(DataBaseHelper.CONTRASENA, contrasena);

                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    long userId = db.insert("usuario", null, cv);

                    if (userId != -1) {
                        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putLong("userId", userId);
                        editor.putBoolean("login", true);
                        editor.apply();

                        Toast.makeText(getApplicationContext(), "Usuario registrado exitosamente.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(SignUp.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error al registrar usuario.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Debes llenar todos los campos", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}