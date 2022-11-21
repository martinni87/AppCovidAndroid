package com.example.primera_aplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConversorActivity extends AppCompatActivity {

    Button btnCalcular1, btnCalcular2;
    EditText etCelsius2Convert, etFahrenheit2Convert, celsiusConverted2Fah, fahConverted2Celsius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversor);

        etCelsius2Convert = (EditText) findViewById(R.id.et_celsius_value2convert);
        etFahrenheit2Convert = (EditText) findViewById(R.id.et_fahrenheit_value2convert);
        celsiusConverted2Fah = (EditText) findViewById(R.id.et_celsius_converted2Fahrenheit);
        fahConverted2Celsius = (EditText) findViewById(R.id.et_fahrenheit_converted2Celsius);

        btnCalcular1 = (Button) findViewById(R.id.btnCalcular1);
        btnCalcular2 = (Button) findViewById(R.id.btnCalcular2);

        btnCalcular1.setOnClickListener(view -> {
            String valueString = String.valueOf(etCelsius2Convert.getText());
            float value = Float.parseFloat(valueString);
            float converted = (value * 9/5) + 32;
            String convertedString = String.format("%.01f",converted);
            celsiusConverted2Fah.setText(convertedString);
        });

        btnCalcular2.setOnClickListener(view -> {
            String valueString = String.valueOf(etFahrenheit2Convert.getText());
            float value = Float.parseFloat(valueString);
            float converted = (value - 32) * 5/9;
            String convertedString = String.format("%.01f",converted);
            fahConverted2Celsius.setText(convertedString);
        });
    }
}