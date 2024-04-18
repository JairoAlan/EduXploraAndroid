/**
 * Este fragmento se utiliza para mostrar un formulario de comentarios donde los usuarios pueden ingresar su correo electrónico y mensaje.
 * Los datos ingresados se envían a través de una solicitud HTTP POST a la API definida en la constante API_URL.
 */
package com.example.eduxplorap;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Menu_Comentarios_Fragment extends Fragment {

    // URL de la API para enviar comentarios
    private static final String API_URL = "https://busc-int-upt-0f93f68ff11c.herokuapp.com/contactoV.php";

    // Campos de entrada de texto para correo electrónico y mensajes
    EditText tvCorreoComent, tvMensajeComent;
    Button btnenviarcoment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu__comentarios_, container, false);

        // Inicializar vistas
        tvCorreoComent = rootView.findViewById(R.id.tvCorreoComent);
        tvMensajeComent = rootView.findViewById(R.id.tvMensajeComent);

        // Recuperar el idUsuario del Bundle
        Bundle bundle = getArguments();
        int idUsuario = 0;
        if (bundle != null) {
            idUsuario = bundle.getInt("ID_USUARIO", 0); // 0 es el valor predeterminado en caso de que no se pueda obtener el idUsuario
            // Hacer lo que necesites con el idUsuario en el fragmento...
        }

        // Botón para enviar comentarios
        Button btnenviarcoment = rootView.findViewById(R.id.btnenviarcoment);
        final int finalIdUsuario = idUsuario;

        // Configurar el clic del botón de enviar
        btnenviarcoment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llamar al método para enviar comentarios con el idUsuario
                enviarDatos(finalIdUsuario);
            }
        });

        return rootView;
    }

    /**
     * Método para enviar los datos de correo electrónico y mensaje a través de una solicitud HTTP POST.
     * Se valida que ambos campos estén llenos antes de enviar la solicitud.
     */
    private void enviarDatos(int idUsuario) {
        // Obtener los valores ingresados en los campos de texto
        String correo = tvCorreoComent.getText().toString().trim();
        String mensaje = tvMensajeComent.getText().toString().trim();

        // Validar que los campos no estén vacíos
        if (correo.isEmpty() || mensaje.isEmpty()) {
            // Mostrar mensaje de advertencia si algún campo está vacío
            Toast.makeText(getActivity(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }
        // Construir la URL con los parámetros para la solicitud POST
        String url = API_URL + "?ID_UsuarioMensaje=" + idUsuario + "&Email=" + correo + "&Mensaje=" + mensaje;

        // Ejecutar la tarea asíncrona para enviar los datos
        new EnviarDatosTask().execute(url);
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
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");

                // Obtener el código de respuesta de la solicitud
                int responseCode = conn.getResponseCode();

                // Cerrar la conexión
                conn.disconnect();

                // Devolver true si la solicitud fue exitosa (código 200 OK)
                return responseCode == HttpURLConnection.HTTP_OK;
            } catch (IOException e) {
                // Registrar cualquier error ocurrido durante la solicitud
                Log.e("EnviarDatosTask", "Error al enviar datos: " + e.getMessage());
                return false;
            }
        }

        /**
         * Método que se ejecuta después de completar la solicitud HTTP POST.
         * Muestra un mensaje indicando si el mensaje se envió correctamente o no.
         */
        @Override
        protected void onPostExecute(Boolean success) {
            // Mostrar un mensaje según el resultado de la solicitud
            if (success) {
                Toast.makeText(getActivity(), "Mensaje enviado correctamente", Toast.LENGTH_SHORT).show();
                // Limpiar los campos después de enviar el mensaje
                tvCorreoComent.setText("");
                tvMensajeComent.setText("");
            } else {
                Toast.makeText(getActivity(), "Error al enviar mensaje", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
