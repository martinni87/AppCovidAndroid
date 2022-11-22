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

    //Declaramos btnFinalizar para el botón del formulario
    private Button btnFinalizar;
    //camposFormulario almacena con un bucle foreach los datos de entrada de cada campo del formulario
    private LinkedHashMap<String, EditText> camposFormulario = new LinkedHashMap<>();
    //datosPaciente recoge la información de camposFormulario en formato String para pasarlo como bundle a la siguiente Activity
    private LinkedHashMap<String, String> datosPaciente = new LinkedHashMap<>();
    //Variables que almacenarán el objeto radioButton seleccionado en la vista.
    private RadioButton rbCelsius, rbFahrenheit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperatura);

        //Asignamos el objeto botón
        btnFinalizar = findViewById(R.id.btnFinalizarTemp);

        //Con la llamada al método put del Map, asignamos pares Clave:Valor, en este caso String:EditText
        camposFormulario.put("nombre",(EditText) findViewById(R.id.etNombre));
        camposFormulario.put("apellidos",(EditText) findViewById(R.id.etApellidos));
        camposFormulario.put("temperatura",(EditText) findViewById(R.id.etTemperatura));
        camposFormulario.put("ciudad",(EditText) findViewById(R.id.etCiudad));
        camposFormulario.put("provincia",(EditText) findViewById(R.id.etProvincia));

        //Asignamos los objetos RadioButton
        rbCelsius = findViewById(R.id.rbCelsius);
        rbFahrenheit = findViewById(R.id.rbFahrenheit);

        //Creamos el listener y llamamos al método clickOnFinalizar cuando se presione el botón finalizar
        btnFinalizar.setOnClickListener(clickOnFinalizar);
    }

    //Método clickOnFinalizar
    protected View.OnClickListener clickOnFinalizar = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Sentencia if con validación. Entramos si el método validacionOK devuelve true (todos los campos rellenos)
            if (validacionOK()){
                //putStringValuesIntoList() es un método que usa un foreach para recorrer camposFormulario
                //extraer con getValue los valores Strings asociados a cada clave y asignarlos a datosPaciente
                putStringValuesIntoList();
                //Creamos el bundle y el intent para configurar el cambio de activity
                Bundle bundle = new Bundle();
                Intent intent = new Intent(TemperaturaActivity.this,TempResultsActivity.class);

                //Añadimos cada conjunto CLAVE:VALOR de datosPaciente al bundle
                for (Map.Entry<String,String> entry:datosPaciente.entrySet()){
                    bundle.putString(entry.getKey(), entry.getValue());
                }

                //Nos queda asignar el tipo de escala, que lo hacemos con el operador ternario, según esté marcado celsius o no
                //Si ninguno está marcado (Celsius o Fahrenheit) nunca llegamos aquí, porque habría fallado la validación
                //por lo que en este punto sí o sí estará marcado Celsius o Fahrenheit
                String escala = (rbCelsius.isChecked() ? "Celsius" : "Fahrenheit");

                //Añadimos la escala al bundle
                bundle.putString("escala",escala);

                //Cargamos el bundle en el intent y pasamos a la siguiente activity
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    };

    private boolean validacionOK(){
        //Seteamos cada campo con setError null al inicio y cuando un usuario vaya a editar un campo para corregir.
        camposFormulario.get("nombre").setError(null);
        camposFormulario.get("apellidos").setError(null);
        camposFormulario.get("temperatura").setError(null);
        camposFormulario.get("ciudad").setError(null);
        camposFormulario.get("provincia").setError(null);
        rbCelsius.setError(null);
        rbFahrenheit.setError(null);

        //Recorremos camposFormulario y si algún campo está vacío, saltará el error y no permitirá continuar.
        for (Map.Entry<String,EditText> entry:camposFormulario.entrySet()) {
            if ("".equalsIgnoreCase(entry.getValue().getText()+"")){
                entry.getValue().requestFocus();
                entry.getValue().setError("Es obligatorio rellenar todos los campos");
                return false; //Devolvemos false si alguno falla y se sale del bucle y del método
            }
        }
        //Comprobados los campos, verificamos que se haya marcado o bien rbCelsius o rbFahrenheit
        if (!rbCelsius.isChecked() && !rbFahrenheit.isChecked()){
                rbCelsius.requestFocus();
                //El mensaje de error lo damos en forma de Toast, por simplificar el ejercicio.
                //Para un setError("mensaje") sería necesario separar la comprobación de ambos RadioButton
                Toast.makeText(getApplicationContext(), "Seleccionar unidad de medida", Toast.LENGTH_SHORT).show();
            return false;//Devolvemos false si alguno falla y se sale del bucle y del método
        }
        return true;//Devolvemos true si todas las comprobaciones son correctas
    }

    private void putStringValuesIntoList(){
        // Bucle for each que recorre campos formulario y asigna cada conjunto clave valor a datos paciente
        //extrayendo de los objetos EditText el valor String almacenado.
        for (Map.Entry<String,EditText> entry:camposFormulario.entrySet()) {
            datosPaciente.put(entry.getKey(), entry.getValue().getText()+"");
        }
    }
}