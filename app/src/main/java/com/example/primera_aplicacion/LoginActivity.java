package com.example.primera_aplicacion;

//import static com.example.primera_aplicacion.common.Constants.DB_NAME;

import static com.example.primera_aplicacion.common.Constants.LOGIN_PASS;
import static com.example.primera_aplicacion.common.Constants.LOGIN_USER;
import static com.example.primera_aplicacion.common.Constants.SP_PREFERENCES_DIRECTORY;
import static com.example.primera_aplicacion.common.Constants.SP_USERS_KEY;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    // Variables de objetos en activity layout
    private Button btnEntrar; //Almacenará el botón ENTRAR
    private EditText etUser, etPassword; //Almacenará datos de campos usuario y contraseña
    private CheckBox rememberUser; //Almacenará la información sobre si se recuerda o no el usuario
//    private AppDatabase database; //Una vez creados los models y el dao, instanciamos un objeto de AppDatabase

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

        //Creamos la instancia que controla la database
//        database = Room.databaseBuilder(LoginActivity.this, AppDatabase.class,DB_NAME)
                //.allowMainThreadQueries() //Esto permite la ejecución de hilos de segundo plano en primer plano... NO HACERLO ESTO ES SOLO PRUEBA
                //.fallbackToDestructiveMigration() //Si hay un cambio en la base de datos, se borra lo que hay. Esto solo está bien usarlo en pruebas.
                //En lugar de allowMainThreadQueries vamos a usar consultas asíncronas en hilos secundarios
                //En kotlin son corrutinas y en java tenemos RxJava o LiveData y Guava
//                .build();

        // Si hay datos guardados en las sharedpreferences, los cargamos
        loadUserName();
        //TODO mejorar logging con sharedpreferences

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

            //Método que guarda el intento de inicio de sesión en la base de datos
//            saveInDatabase(user,password,remember);

            // Si el método areCredencialsValid devuelve true, se crea el Intent y se lanza la siguiente Activity
            if (areCredencialsValid(user, password)) {
                Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                startActivity(intent);
            }
        }
    };

    // Método validaciones, pasamos como parámetros un String para el usuario y otro para la contraseña
    private boolean areCredencialsValid(String user, String password) {
        //Al inicio seteamos errores a null, para que desaparezcan cuando el usuario inicia por primera vez, o hace una corrección.
        etUser.setError(null);
        etPassword.setError(null);

        //Verificación 1, debe rellenarse todos los campos
        if ("".equalsIgnoreCase(user) && "".equalsIgnoreCase(password)) {
            //Si no se han rellenado, se marcan los dos con error (circulo lateral)
            etUser.setError("Usuario obligatorio");
            etPassword.setError("Contraseña obligatoria");
            //Al fallar mandamos el foco del cursor al primer dato (users) y retornamos false en la verificación
            etUser.requestFocus();
            return false;
        }

        //Verificación 2, si no se cumple la condición anterior (porque uno de los dos se ha rellenado) vemos cuál es el vacío
        if ("".equalsIgnoreCase(user)) {
            //Si el usuario no se ha rellenado, se marca con error, se hace foco y retornamos false
            etUser.setError("Usuario obligatorio");
            etUser.requestFocus();
            return false;
        }
        if ("".equalsIgnoreCase(password)){
            //Si el usuario se ha rellenado pero no el password, se marca passs con error, se hace foco y retornamos false
            etPassword.setError("Contraseña obligatoria"); //Idem
            etPassword.requestFocus();
            return false;
        }

// VALIDACIONES ANTIGUAS. DEPRECATED
        //Verificación 2 el usuario debe ser admin
        if (!LOGIN_USER.equalsIgnoreCase(user)) {
            etUser.setError("Usuario incorrecto");
            etUser.requestFocus();
            return false; //Al fallar retorna false
        }
        //Verificación 3 la contraseña debe ser admin
        if (!LOGIN_PASS.equalsIgnoreCase(password)) {
            etPassword.setError("Contraseña incorrecta");
            etPassword.requestFocus();
            return false; //Al fallar retorna false
        }
        //Si ninguna de las condiciones anteriores se cumple, entonces el resultado es true y se valida el Login
        return true;
    }

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
        spEditor.apply(); //Si en lugar de apply usa
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

//    private boolean checkValidationHTTP(String user, String password){
//        //Variable que almacenará el estado de la validación de credenciales
//        JSONObject[] agents = new JSONObject[1];
//        boolean validation[] = {false};
////        boolean validateCredentials[] = {false};
//        //Realizamos conexión con servicios web para verificar usuario y contraseña
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        String url = "http://192.168.0.15/simple-web-service/app/auth.php";
//
//        //Creamos un conjunto CLAVE => VALOR que contendrá los datos del JSON que vamos a enviar.
//        HashMap<String,String> parameters = new HashMap<>();
//        parameters.put("user",user);
//        parameters.put("passwd",password);
//
//        //Codificamos el conjunto como JSONObject
//        JSONObject jsonDataParameter = new JSONObject(parameters);
//
//        //Creamos el cuerpo de la Request, incluimos el método POST, la url a la que llamamos y pasamos parámetros JSON.
//        //Luego asignamos acciones en caso de respones válida o error.
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                Request.Method.POST, url, jsonDataParameter,
//                response ->{
////                    Log.d(HTTP_REQUEST_TAG, "response: " + response);
//                    //Obtenemos el JsonArray
//                    agents[0] = response;
//                    try {
//                        Log.d(HTTP_REQUEST_TAG, "Success: " + agents[0].getBoolean("success"));
//                        validation[0] = agents[0].getBoolean("success");
////                        validateCredentials[0] = agents[0].getBoolean("success");
////                        Log.d(HTTP_REQUEST_TAG, "checkValidationHTTP: " + validateCredentials[0]);
//                    } catch (JSONException e) {
//                        Log.d(HTTP_REQUEST_TAG, "Success error: " + e.getMessage());
//                    }
//                },
//                error -> {
//                    Log.d(HTTP_REQUEST_TAG, "onErrorResponse: " + error.getMessage());
//                }
//        );
//
//        //Añadimos la request a la cola.
//        queue.add(jsonObjectRequest);
//
//        if (!validation[0]){
//            etUser.setError("Credenciales incorrectas");
//            etPassword.setError("Credenciales incorrectas");
//            etUser.requestFocus();
//            return false;
//        }
//
//        return true;
//    }

    //Método para guardar intentos de inicio de sesión en la base de datos
//    private void saveInDatabase(String user, String password, boolean remember){
//        //Dao al que queremos acceder
//        LoginDao loginDao = database.loginDao();
//
//        //Login
//        Login login = new Login();
////        login.id = (int) Math.round(Math.random() * (9999 - 0 + 1)); //El id no es autoincremental, por lo que tenemos que asignarlo cada vez
//        login.user = user;
//        login.password = password;
//        login.remember = remember;

        //Insertamos en la DB el dato del intento de acceso
//        loginDao.insertData(login);

        //Vemos el insert hecho en el Logcat en debug
//        Single<List<Login>> logins = loginDao.getAll();
//        Log.d("Mis pruebas",String.valueOf(logins));
//    }
}