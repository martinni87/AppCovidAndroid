package com.example.primera_aplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    // Variables de objetos en activity layout
    private Button btnEntrar;
    private EditText etUser, etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // A las variables creadas asignamos un objeto del layout
        btnEntrar = findViewById(R.id.btnEntrar);
        etUser = findViewById(R.id.etUser);
        etPassword = findViewById(R.id.etPassword);

        // Asignamos una acción de click al botón
        btnEntrar.setOnClickListener(doLogin);

    }

    private final View.OnClickListener doLogin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String user = String.valueOf(etUser.getText());
            String password = etPassword.getText() + "";

            // Validaciones
            if (areCredencialsValid(user, password)) {
                Intent intent = new Intent(LoginActivity.this, ConfigurationActivity.class);
                startActivity(intent);
            }
        }

        // Método validaciones
        private boolean areCredencialsValid(String user, String password) {
            etUser.setError(null);
            etPassword.setError(null);
            if ("".equalsIgnoreCase(user) || "".equalsIgnoreCase(password)) {
                etUser.setError("Usuario obligatorio");
                etPassword.setError("Contraseña obligatoria");
                etUser.requestFocus();
                return false;
            }
            if (!"admin".equalsIgnoreCase(user)) {
                etUser.setError("Usuario incorrecto");
                etUser.requestFocus();
                return false;
            }
            if (!"admin".equalsIgnoreCase(password)) {
                etPassword.setError("Contraseña incorrecta");
                etPassword.requestFocus();
                return false;
            }
            return true;
        }

    };
}