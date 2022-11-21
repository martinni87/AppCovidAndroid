package com.example.primera_aplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.LinkedHashMap;
import java.util.Map;

public class TemperaturaActivity extends AppCompatActivity {

    private Button btnFinalizar;
    private LinkedHashMap<String, EditText> camposFormulario = new LinkedHashMap<>();
    private LinkedHashMap<String, String> datosPaciente = new LinkedHashMap<>();
    private RadioButton rbCelsius, rbFahrenheit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperatura);

        btnFinalizar = findViewById(R.id.btnFinalizarTemp);

        camposFormulario.put("nombre",(EditText) findViewById(R.id.etNombre));
        camposFormulario.put("apellidos",(EditText) findViewById(R.id.etApellidos));
        camposFormulario.put("temperatura",(EditText) findViewById(R.id.etTemperatura));
        camposFormulario.put("ciudad",(EditText) findViewById(R.id.etCiudad));
        camposFormulario.put("provincia",(EditText) findViewById(R.id.etProvincia));

        rbCelsius = findViewById(R.id.rbCelsius);
        rbFahrenheit = findViewById(R.id.rbFahrenheit);

        btnFinalizar.setOnClickListener(clickOnFinalizar);
    }

    protected View.OnClickListener clickOnFinalizar = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (validacionOK()){
                putStringValuesIntoList();
                Bundle bundle = new Bundle();
                Intent intent = new Intent(TemperaturaActivity.this,TempResultsActivity.class);

                for (Map.Entry<String,String> entry:datosPaciente.entrySet()){
                    bundle.putString(entry.getKey(), entry.getValue());
                }

                String escala = (rbCelsius.isChecked() ? "Celsius" : "Fahrenheit");
                bundle.putString("escala",escala);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    };

    private boolean validacionOK(){
        camposFormulario.get("nombre").setError(null);
        camposFormulario.get("apellidos").setError(null);
        camposFormulario.get("temperatura").setError(null);
        camposFormulario.get("ciudad").setError(null);
        camposFormulario.get("provincia").setError(null);

        rbCelsius.setError(null);
        rbFahrenheit.setError(null);

        for (Map.Entry<String,EditText> entry:camposFormulario.entrySet()) {
            if ("".equalsIgnoreCase(entry.getValue().getText()+"")){
                entry.getValue().requestFocus();
                entry.getValue().setError("Es obligatorio rellenar todos los campos");
                return false;
            }
        }
        if (!rbCelsius.isChecked() && !rbFahrenheit.isChecked()){
                rbCelsius.requestFocus();
                Toast.makeText(getApplicationContext(), "Seleccionar unidad de medida", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void putStringValuesIntoList(){
        for (Map.Entry<String,EditText> entry:camposFormulario.entrySet()) {
            datosPaciente.put(entry.getKey(), entry.getValue().getText()+"");
        }
    }
}