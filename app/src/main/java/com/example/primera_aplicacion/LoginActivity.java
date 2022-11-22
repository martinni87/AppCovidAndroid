package com.example.primera_aplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    // Variables de objetos en activity layout
    private Button btnEntrar; //Almacenará el botón ENTRAR
    private EditText etUser, etPassword; //Almacenará datos de campos usuario y contraseña


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Asignamos a las variables creadas un objeto del layout
        btnEntrar = findViewById(R.id.btnEntrar);
        etUser = findViewById(R.id.etUser);
        etPassword = findViewById(R.id.etPassword);

        // Asignamos una acción de click al botón
        btnEntrar.setOnClickListener(doLogin);

    }

    //La acción del botón ENTRAR viene definida por el siguiente método
    private final View.OnClickListener doLogin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //Asignamos valor a variables locales para facilitar la lectura del código.
            String user = String.valueOf(etUser.getText());
            String password = etPassword.getText() + "";

            // Si el método areCredencialsValid devuelve true, se crea el Intent y se lanza la siguiente Activity
            if (areCredencialsValid(user, password)) {
                Intent intent = new Intent(LoginActivity.this, OptionsActivity.class);
                startActivity(intent);
            }
        }

        // Método validaciones, pasamos como parámetros un String para el usuario y otro para la contraseña
        private boolean areCredencialsValid(String user, String password) {
            //Al inicio seteamos errores a null, para que desaparezcan cuando el usuario inicia por primera vez, o hace una corrección.
            etUser.setError(null);
            etPassword.setError(null);
            //Verificación 1, debe rellenarse todos los campos
            if ("".equalsIgnoreCase(user) || "".equalsIgnoreCase(password)) {
                etUser.setError("Usuario obligatorio"); //Si no se ha rellenado, se marca error (circulo lateral)
                etPassword.setError("Contraseña obligatoria"); //Idem
                etUser.requestFocus(); //Si no se ha rellenado, el cursor hace foco en el campo y lo marca para escribir
                return false; //Al fallar retorna false
            }
            //Verificación 2 el usuario debe ser admin
            if (!"admin".equalsIgnoreCase(user)) {
                etUser.setError("Usuario incorrecto");
                etUser.requestFocus();
                return false; //Al fallar retorna false
            }
            //Verificación 3 la contraseña debe ser admin
            if (!"admin".equalsIgnoreCase(password)) {
                etPassword.setError("Contraseña incorrecta");
                etPassword.requestFocus();
                return false; //Al fallar retorna false
            }
            return true; //Al validarse correcto retorna true
        }

    };
}