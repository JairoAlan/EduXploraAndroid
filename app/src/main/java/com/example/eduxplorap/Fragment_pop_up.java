/**
 * Esta clase representa un fragmento emergente utilizado para enviar datos a través de una solicitud HTTP POST.
 * Los datos ingresados incluyen el grupo, el cuatrimestre y el número de alumnos.
 * La URL de destino para la solicitud POST está definida en la constante API_URL.
 */
package com.example.eduxplorap;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Fragment_pop_up extends AppCompatActivity {

    // URL de la API para enviar datos
    private static final String API_URL = "https://busc-int-upt-0f93f68ff11c.herokuapp.com/obtSolV.php";

    // Campos de entrada de texto para grupo, cuatrimestre y número de alumnos
    EditText etGrupo, etCuatrimestre, etAlumnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pop_up);

        // Habilitar el ajuste de bordes
        EdgeToEdge.enable(this);

        // Obtener métricas de la ventana
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        // Calcular dimensiones de la ventana emergente
        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;
        getWindow().setLayout((int)(ancho * 0.95),(int)(alto * 0.75));

        // Inicializar vistas
        etGrupo = findViewById(R.id.etgrupo);
        etCuatrimestre = findViewById(R.id.etcuatrimestre);
        etAlumnos = findViewById(R.id.etalumnos);

        // Botón para enviar los datos ingresados
        Button buttonEnviar = findViewById(R.id.buttonEnviar);

        // Recuperar los datos de la actividad anterior
        Bundle extras = getIntent().getExtras();
        int idUsuario = 0;
        int idEmpresa = 0; // Agregar la variable para almacenar el idEmpresa
        if (extras != null) {
            idUsuario = extras.getInt("ID_USUARIO", 0);
            idEmpresa = extras.getInt("ID_EMPRESA", 0); // Obtener el idEmpresa de los extras
        }

        // Variables finales para el idUsuario y el idEmpresa
        final int finalIdUsuario = idUsuario;
        final int finalIdEmpresa = idEmpresa;

        // Configurar el clic del botón de enviar
        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llamar al método para enviar datos con el idUsuario y el idEmpresa
                enviarDatos(finalIdUsuario, finalIdEmpresa);
            }
        });
    }

    /**
     * Método para enviar los datos ingresados a través de una solicitud HTTP POST.
     * Los datos incluyen el grupo, el cuatrimestre, el número de alumnos, el idUsuario y el idEmpresa.
     * Se valida que todos los campos estén llenos antes de enviar la solicitud.
     */
    private void enviarDatos(int idUsuario, int idEmpresa) {
        // Obtener los valores ingresados en los campos de texto
        String grupo = etGrupo.getText().toString().trim();
        String cuatrimestre = etCuatrimestre.getText().toString().trim();
        String alumnos = etAlumnos.getText().toString().trim();

        // Validar que los campos no estén vacíos
        if (grupo.isEmpty() || cuatrimestre.isEmpty() || alumnos.isEmpty() ) {
            // Mostrar mensaje de advertencia si algún campo está vacío
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        // Construir la URL con los parámetros para la solicitud POST
        try {
            String url = API_URL + "?idEmpresa=" + idEmpresa + // Agregar idEmpresa a la URL
                    "&grupo=" + URLEncoder.encode(grupo, "UTF-8") +
                    "&idUsuario=" + idUsuario +
                    "&carrera=" + URLEncoder.encode("Sistemas", "UTF-8") +  // La carrera se establece como "Sistemas"
                    "&noAlumnos=" + URLEncoder.encode(alumnos, "UTF-8");

            // Ejecutar la tarea asíncrona para enviar los datos
            new EnviarDatosTask().execute(url);
        } catch (IOException e) {
            Toast.makeText(this, "Error al construir la URL", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Clase interna para realizar la solicitud HTTP POST de forma asíncrona.
     * Verifica si la solicitud fue exitosa y muestra un mensaje correspondiente.
     */
    private class EnviarDatosTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... urls) {
            try {
                // Establecer la conexión y enviar la solicitud POST
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                // Obtener el código de respuesta de la solicitud
                int responseCode = conn.getResponseCode();

                // Cerrar la conexión
                conn.disconnect();

                // Devolver true si la solicitud fue exitosa (código 200 OK)
                return responseCode == HttpURLConnection.HTTP_OK;
            } catch (IOException e) {
                // Devolver false si ocurrió un error durante la solicitud
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            // Mostrar un mensaje según el resultado de la solicitud
            if (success) {
                Toast.makeText(Fragment_pop_up.this, "Datos enviados correctamente", Toast.LENGTH_SHORT).show();
                // Limpiar los campos después de enviar los datos
                etGrupo.setText("");
                etCuatrimestre.setText("");
                etAlumnos.setText("");
            } else {
                Toast.makeText(Fragment_pop_up.this, "Error al enviar los datos", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
