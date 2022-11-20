package com.example.primera_aplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(volver);
    }

    private View.OnClickListener volver = view -> {
        Intent intent = new Intent(MainActivity2.this, LoginActivity.class);
        startActivity(intent);
    };

}