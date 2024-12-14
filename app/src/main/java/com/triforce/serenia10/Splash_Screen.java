package com.triforce.serenia10;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Splash_Screen extends AppCompatActivity {
    private ProgressBar pgbProgreso;
    private int progreso = 0;
    private SharedPreferences preferences;
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            pgbProgreso.incrementProgressBy(msg.arg1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        pgbProgreso = findViewById(R.id.pgbProgreso);
        pgbProgreso.setProgress(0);

        Thread timer = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    for(int i = 0; i < 10; i++) {
                        sleep(500);
                        Message msg = Message.obtain();
                        msg.arg1 = 10;
                        handler.sendMessage(msg);
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }finally{
                    Intent abreVentanaPrincipal;
                    preferences = getSharedPreferences("login", MODE_PRIVATE);
                    boolean login = preferences.getBoolean("login", false);
                    if(!login){
                        abreVentanaPrincipal = new Intent();
                        abreVentanaPrincipal.setClassName("com.triforce.serenia10",
                                "com.triforce.serenia10.LogIn");
                    }
                    else{
                        abreVentanaPrincipal = new Intent();
                        abreVentanaPrincipal.setClassName("com.triforce.serenia10",
                                "com.triforce.serenia10.MainActivity");
                    }
                    startActivity(abreVentanaPrincipal);
                }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        preferences = getSharedPreferences("Login", MODE_PRIVATE);
    }

}