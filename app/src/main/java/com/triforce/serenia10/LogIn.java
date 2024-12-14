package com.triforce.serenia10;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LogIn extends AppCompatActivity {
    private Button btnLogin, btnRegistrar;
    private EditText edtUsuario, edtContrasena;
    private Cursor cursor = null;
    private DataBaseHelper dbHelper = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        edtUsuario = findViewById(R.id.edtUsuario);
        edtContrasena = findViewById(R.id.edtContrasena);

        dbHelper = new DataBaseHelper(this);

        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                String packagename = "com.triforce.serenia10";
                String classname = "com.triforce.serenia10.SignUp";
                intent.setClassName(packagename, classname);

                startActivity(intent);
            }
        });

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contrasena = edtContrasena.getText().toString();
                String usuario = edtUsuario.getText().toString();
                cursor = dbHelper.getReadableDatabase().rawQuery("SELECT email, contrasena FROM usuario WHERE email = ? AND contrasena = ?", new String[]{usuario, contrasena});

                 if (cursor.moveToFirst()) {
                     SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
                     SharedPreferences.Editor editor = preferences.edit();
                     editor.putBoolean("login", true);
                     editor.apply();
                    Intent intent = new Intent();
                    String packagename = "com.triforce.serenia10";
                    String classname = "com.triforce.serenia10.MainActivity";
                    intent.setClassName(packagename, classname);

                    startActivity(intent);
                }
                 else{
                     Toast.makeText(LogIn.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                 }

            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }

}
