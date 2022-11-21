package com.example.primera_aplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedHashMap;
import java.util.Map;

public class TempResultsActivity extends AppCompatActivity {

    private LinkedHashMap<String, EditText> camposFormulario = new LinkedHashMap<>();
    private TextView colorResultado;
    private Button btnVolverMenu;
    private double temperatureValue;
    private String escala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_results);

        camposFormulario.put("nombre",(EditText) findViewById(R.id.etNombre));
        camposFormulario.put("apellidos",(EditText) findViewById(R.id.etApellidos));
        camposFormulario.put("temperatura",(EditText) findViewById(R.id.etTemperatura));
        camposFormulario.put("ciudad",(EditText) findViewById(R.id.etCiudad));
        camposFormulario.put("provincia", (EditText) findViewById(R.id.etProvincia));

        colorResultado = findViewById(R.id.view_color_resultado);

        btnVolverMenu = findViewById(R.id.btnVolverMenu);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        for (Map.Entry<String,EditText> entry:camposFormulario.entrySet()){
            entry.getValue().setText(bundle.getString(entry.getKey()));
        }

        String temp = String.valueOf(camposFormulario.get("temperatura").getText());

        temperatureValue = Double.parseDouble(temp);

        escala = bundle.getString("escala");

        testTemperatura(temperatureValue,escala);

        btnVolverMenu.setOnClickListener(clickOnVolverMenu);

    }

    protected View.OnClickListener clickOnVolverMenu = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(TempResultsActivity.this,OptionsActivity.class);
            startActivity(intent);
        }
    };

    public void testTemperatura(double value, String escala){
        int resultado = 0;

        if ((value < 37 && escala.equalsIgnoreCase("celsius"))
                || (value < 98.6 && escala.equalsIgnoreCase("fahrenheit"))){
            colorResultado.setText("Temperatura normal");
            colorResultado.setBackgroundColor(Color.GREEN);
        }
        else if ((value < 38 && escala.equalsIgnoreCase("celsius"))
                || (value < 100.4 && escala.equalsIgnoreCase("fahrenheit"))){
            colorResultado.setText("Precaución. Febrícula");
            colorResultado.setBackgroundColor(Color.rgb(253,132,31));
        }
        else{
            colorResultado.setTextColor(Color.WHITE);
            colorResultado.setText("¡Atención! Fiebre");
            colorResultado.setBackgroundColor(Color.RED);
        }
    }
}