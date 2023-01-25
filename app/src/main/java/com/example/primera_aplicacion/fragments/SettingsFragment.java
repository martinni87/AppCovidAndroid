package com.example.primera_aplicacion.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.primera_aplicacion.NavigationActivity;
import com.example.primera_aplicacion.R;

public class SettingsFragment extends Fragment {
    private Switch celsius, fahrenheit;
    private Button btnGuardar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_settings, container, false);

        celsius = rootView.findViewById(R.id.switchCelsius);
        fahrenheit = rootView.findViewById(R.id.switchFahrenheit);
        btnGuardar = rootView.findViewById(R.id.btnGuardar);

        celsius.setOnCheckedChangeListener(checkOnOffFahrenheit);
        fahrenheit.setOnCheckedChangeListener(checkOnOffCelsius);
        btnGuardar.setOnClickListener(clickOnGuardar);

        return rootView;
    }

    protected CompoundButton.OnCheckedChangeListener checkOnOffFahrenheit = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            fahrenheit.setChecked(!celsius.isChecked());
        }
    };

    protected CompoundButton.OnCheckedChangeListener checkOnOffCelsius = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            celsius.setChecked(!fahrenheit.isChecked());
        }
    };

    protected View.OnClickListener clickOnGuardar = view -> {
        Intent intent = new Intent(getContext(), NavigationActivity.class);
        Toast.makeText(getContext(),"Configuración guardada",Toast.LENGTH_SHORT).show();
        startActivity(intent);
    };
}