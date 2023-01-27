package com.example.primera_aplicacion.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.primera_aplicacion.R;
import com.example.primera_aplicacion.models.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Objects;


public class HomeFragment extends Fragment {
    HashMap <Integer, User> userList = new HashMap<>();
    TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        Log.d("size", "oncreateview: " + this.userList.size());
        return rootView;
    }
        private void loadFromDataBase (){
            //Lista en donde almacenaremos los usuarios recogidos de la base de datos
            HashMap <Integer,User> userList = new HashMap<>();

            //Realizamos conexión con servicios web para verificar usuario y contraseña
            RequestQueue queue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
            String url = "http://192.168.0.15/simple-web-service/app/list.php";

            //Creamos el cuerpo de la Request, incluimos el método POST, la url a la que llamamos y pasamos parámetros JSON.
            //Luego asignamos acciones en caso de respones válida o error.
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET, url, null,
                    response ->{
                        try {
                            JSONArray agents = response.getJSONArray("temp");
                            for (int i = 0; i < response.length(); i++){
//                                Log.d("COMPROBACION", "loadFromDataBase: " + agents.getJSONObject(i).getString("nombre"));
                                userList.put(i,new User(
                                        agents.getJSONObject(i).getString("nombre"),
                                        agents.getJSONObject(i).getString("apellidos"),
                                        agents.getJSONObject(i).getString("ciudad"),
                                        agents.getJSONObject(i).getString("provincia"),
                                        agents.getJSONObject(i).getInt("temperatura")));
//
//                                Log.d("SIZE", "onCreateView4: "+ userList.size());
                            }
                            this.userList = userList;
//                            Log.d("size", "loadFromDataBase: " + this.userList.get(0).getName());

                        } catch (JSONException e) {
                            Log.d("COMPROBACION", "Error en carga de datos: " + e.getMessage());
                        }
                    },
                    error -> {
                        Log.d("COMPROBACION", "onErrorResponse: " + error.getMessage());
                    }

            );
            //Añadimos la request a la cola.
            queue.add(jsonObjectRequest);


//            return userList;
        }
}