//package com.example.primera_aplicacion;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CompoundButton;
//import android.widget.Switch;
//
//public class SettingsActivity extends AppCompatActivity {
//
//    private Switch celsius, fahrenheit;
//    private Button btnGuardar;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_settings);
//
//        celsius = findViewById(R.id.switchCelsius);
//        fahrenheit = findViewById(R.id.switchFahrenheit);
//        btnGuardar = findViewById(R.id.btnGuardar);
//
//        celsius.setOnCheckedChangeListener(checkOnOffFahrenheit);
//        fahrenheit.setOnCheckedChangeListener(checkOnOffCelsius);
//        btnGuardar.setOnClickListener(clickOnGuardar);
//    }
//
//    protected CompoundButton.OnCheckedChangeListener checkOnOffFahrenheit = new CompoundButton.OnCheckedChangeListener() {
//        @Override
//        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//            fahrenheit.setChecked(!celsius.isChecked());
//        }
//    };
//
//    protected CompoundButton.OnCheckedChangeListener checkOnOffCelsius = new CompoundButton.OnCheckedChangeListener() {
//        @Override
//        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//            celsius.setChecked(!fahrenheit.isChecked());
//        }
//    };
//
//    protected View.OnClickListener clickOnGuardar = new View.OnClickListener() {
//        @Override
//        public void onClick(View view){
//            Intent intent = new Intent(SettingsActivity.this, OptionsActivity.class);
//            startActivity(intent);
//        }
//    };
//
//}