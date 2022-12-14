package com.example.primera_aplicacion;

import static com.example.primera_aplicacion.common.Constants.SP_PREFERENCES_DIRECTORY;
import static com.example.primera_aplicacion.common.Constants.SP_USERS_KEY;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    // Variables de objetos en activity layout
    private Button btnEntrar; //Almacenará el botón ENTRAR
    private EditText etUser, etPassword; //Almacenará datos de campos usuario y contraseña
    private CheckBox rememberUser; //Almacenará la información sobre si se recuerda o no el usuario


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Asignamos a las variables creadas un objeto del layout
        btnEntrar = findViewById(R.id.btnEntrar);
        etUser = findViewById(R.id.etUser);
        etPassword = findViewById(R.id.etPassword);
        rememberUser = findViewById(R.id.checkBox);

        // Asignamos una acción de click al botón
        btnEntrar.setOnClickListener(doLogin);

        // Si hay datos guardados en las sharedpreferences, los cargamos
        loadUserName();

    }

    //La acción del botón ENTRAR viene definida por el siguiente método
    private final View.OnClickListener doLogin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //Asignamos valor a variables locales para facilitar la lectura del código.
            String user = String.valueOf(etUser.getText());
            String password = etPassword.getText() + "";
            boolean remember = rememberUser.isChecked();

            //Primero si se ha marcado "recordar usuario" debemos almacenar el valor del campo usuario en un sharedpreference
            //En tal caso la variable remember = true, si no se marca será false.
            //Si remember = true, guardamos el valor de user con el método saveUserName(), caso contrario el valor se establece en null
            //Usamos el operador ternario.
            saveUserName(remember ? user : null);

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

    //Método para guardar el valor del campo usuario en un sharedpreference
    private void saveUserName(String user){
        //Cargamos las sharedpreference guardadas
            /*
                Con MODE_PRIVATE como parámetro del método getSharedPreferences evitamos que otras apps puedan hacer uso
                de lo que se guarde en sharedpreferences.
                savedOptions es el nombre que recibe "el fichero" donde se almacenará la información.
             */
        SharedPreferences spSaved = LoginActivity.this.getSharedPreferences(SP_PREFERENCES_DIRECTORY,MODE_PRIVATE); //Obtenemos info almacenada, solo modo lectura

        //Creamos un editor de los sharedpreferences
        SharedPreferences.Editor spEditor = spSaved.edit();

        //Para guardar el nombre de usuario usamos un putString, recibe clave -> Valor. Hay que terminar la transacción con un commit o apply
        spEditor.putString(SP_USERS_KEY,user);
        spEditor.apply(); //Si en lugar de apply usamos commit, hay que hacerlo en el hilo principal de la aplicación. Puede ralentizar la app si hay muchos datos.
    }

    //Método para recuperar el valor del campo usuario cuando se haya guardado en sharedpreferences
    private void loadUserName(){
        //Cargamos las sharedpreferences guardadas.
        SharedPreferences spSaved = getSharedPreferences(SP_PREFERENCES_DIRECTORY, MODE_PRIVATE);

        //Asignamos el valor de la clave USER al string, valor por defecto null (si no se ha guardado nada).
        String user = spSaved.getString(SP_USERS_KEY,null);

        //Seteamos si el checkbox debe ir marcado o no. La condición (user != null) dará falso si está vacío (unchecked) y true si tiene contenido (checked)
        rememberUser.setChecked(user != null);
        etUser.setText(user);
    }
}