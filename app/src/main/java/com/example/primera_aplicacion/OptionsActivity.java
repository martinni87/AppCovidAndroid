package com.example.primera_aplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class OptionsActivity extends AppCompatActivity {

    //Creamos las variables que van a almacenar los datos de los botones de la Activity
    private Button btnCerrar, btnMedicion, btnConversor, btnConfiguracion;
    //Tags para log.d
    private String TAG = "Logger Home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        //Asignamos a cada variable su correspondiente btn por id del activity_options.xml
        btnCerrar = findViewById(R.id.btnOpcionCerrar);
        btnMedicion = findViewById(R.id.btnOpcionTemp);
        btnConversor = findViewById(R.id.btnOpcionConv);
        btnConfiguracion = findViewById(R.id.btnOpcionConf);

        //Asignamos a cada botón un método Listener para detectar la pulsasión. Cada método nos lleva a un activity diferente.
        btnCerrar.setOnClickListener(cerrarSesion);
        btnMedicion.setOnClickListener(gotoMedicion);
        btnConversor.setOnClickListener(gotoConversor);
        btnConfiguracion.setOnClickListener(gotoConfiguracion);

        makeRequest();
    }

    /* Nota sobre los métodos siguientes, en todos los casos declaramos un Intent que definirá de dónde salimos y a dónde vamos
    y luego llamamos al método startActivity pasando como parámetro intent para dirigirnos a ese activity en concreto.
     */

    //Cerrar sesión nos devuelve a LoginActivity
    private View.OnClickListener cerrarSesion = view -> {
        Intent intent = new Intent(OptionsActivity.this, LoginActivity.class);
        startActivity(intent);
    };

    //Go to Medicion nos lleva al activity para tomar datos de medición de temperatura de un paciente.
    private View.OnClickListener gotoMedicion = view -> {
        Intent intent = new Intent(OptionsActivity.this, TemperaturaActivity.class);
        startActivity(intent);
    };

    //Go to conversor nos lleva a una calculadora que convierte celsius a fahrenheit y viceversa
    private View.OnClickListener gotoConversor = view -> {
        Intent intent = new Intent(OptionsActivity.this, ConversorActivity.class);
        startActivity(intent);
    };

    //Go to configuración nos lleva a la vista para configurar celsius o fahrenheit como unidad de medida por defecto
    private View.OnClickListener gotoConfiguracion = view -> {
        Intent intent = new Intent(OptionsActivity.this, ConfigurationActivity.class);
        startActivity(intent);
    };

    //MÉTODO REQUEST LLAMADA HTTPS
    private void makeRequest(){
        Log.d(TAG, "makeRequest: Request iniciada.");
        RequestQueue queue = Volley.newRequestQueue(OptionsActivity.this);
//        String url = "https://elcampico.org";
//        StringRequest request = new StringRequest(
//                Request.Method.GET, url,
//                response -> {
//                    Log.d(TAG, "onResponse: " + response);
//                },
//                error -> {
//                    Log.d(TAG, "onErrorResponse: " + error.getMessage());
//                }
//        );
        String url = "https://valorant-api.com/v1/agents";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response ->{
                    Log.d(TAG, "onResponse: " + response);
                    //Obtenemos el JsonArray
                    try {
                        JSONArray agents = response.getJSONArray("data");
                        for (int i = 0; i < agents.length(); i++){
                            Log.d(TAG, "Agent: " + agents.getJSONObject(i).getString("displayName") + " Presente.");
                        }
                    } catch (JSONException e) {
                        Log.d(TAG, "Error respuesta getJSONArray: " + e.getMessage());
                    }
                },
                error -> {
                    Log.d(TAG, "onErrorResponse: " + error.getMessage());
                }
        );

        //Añadimos la request a la cola.
        queue.add(request);
    }
}