package com.example.primera_aplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OptionsActivity extends AppCompatActivity {

    private Button btnCerrar, btnMedicion, btnConversor, btnConfiguracion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        btnCerrar = findViewById(R.id.btnOpcionCerrar);
        btnMedicion = findViewById(R.id.btnOpcionTemp);
        btnConversor = findViewById(R.id.btnOpcionConv);
        btnConfiguracion = findViewById(R.id.btnOpcionConf);

        btnCerrar.setOnClickListener(cerrarSesion);
        btnMedicion.setOnClickListener(gotoMedicion);
        btnConversor.setOnClickListener(gotoConversor);
        btnConfiguracion.setOnClickListener(gotoConfiguracion);
    }

    private View.OnClickListener cerrarSesion = view -> {
        Intent intent = new Intent(OptionsActivity.this, LoginActivity.class);
        startActivity(intent);
    };

    private View.OnClickListener gotoMedicion = view -> {
        Intent intent = new Intent(OptionsActivity.this, TemperaturaActivity.class);
        startActivity(intent);
    };

    private View.OnClickListener gotoConversor = view -> {
        Intent intent = new Intent(OptionsActivity.this, ConversorActivity.class);
        startActivity(intent);
    };

    private View.OnClickListener gotoConfiguracion = view -> {
        Intent intent = new Intent(OptionsActivity.this, ConfigurationActivity.class);
        startActivity(intent);
    };



}