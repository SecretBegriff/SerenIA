package com.triforce.serenia10;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Chat extends AppCompatActivity {
    private ConstraintLayout btnMeditar, btnStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        btnMeditar = findViewById(R.id.btnMeditar);
        btnMeditar.setOnClickListener(view -> {
            Intent intent = new Intent(Chat.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        btnStats = findViewById(R.id.btnStats);
        btnStats.setOnClickListener(view -> {
            Intent intent = new Intent(Chat.this, Stats.class);
            startActivity(intent);
            finish();
        });

    }
}