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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Menu_Comentarios_Fragment extends Fragment {

    private static final String API_URL = "https://busc-int-upt-0f93f68ff11c.herokuapp.com/contactoV.php";

    EditText tvCorreoComent, tvMensajeComent;
    Button btnenviarcoment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu__comentarios_, container, false);

        tvCorreoComent = rootView.findViewById(R.id.tvCorreoComent);
        tvMensajeComent = rootView.findViewById(R.id.tvMensajeComent);
        // Recuperar el idUsuario del Bundle
        Bundle bundle = getArguments();
        int idUsuario = 0;
        if (bundle != null) {
            idUsuario = bundle.getInt("ID_USUARIO", 0); // 0 es el valor predeterminado en caso de que no se pueda obtener el idUsuario
            // Hacer lo que necesites con el idUsuario en el fragmento...
        }

        Button btnenviarcoment = rootView.findViewById(R.id.btnenviarcoment);
        final int finalIdUsuario = idUsuario;
        btnenviarcoment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarDatos(finalIdUsuario);
            }
        });

        return rootView;
    }

    private void enviarDatos(int idUsuario) {
        String correo = tvCorreoComent.getText().toString().trim();
        String mensaje = tvMensajeComent.getText().toString().trim();

        if (correo.isEmpty() || mensaje.isEmpty()) {
            Toast.makeText(getActivity(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = API_URL + "?ID_UsuarioMensaje=" + idUsuario + "&Email=" + correo + "&Mensaje=" + mensaje;

        new EnviarDatosTask().execute(url);
    }

    private class EnviarDatosTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");

                int responseCode = conn.getResponseCode();

                conn.disconnect();

                return responseCode == HttpURLConnection.HTTP_OK;
            } catch (IOException e) {
                Log.e("EnviarDatosTask", "Error al enviar datos: " + e.getMessage());
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(getActivity(), "Mensaje enviado correctamente", Toast.LENGTH_SHORT).show();
                tvCorreoComent.setText("");
                tvMensajeComent.setText("");
            } else {
                Toast.makeText(getActivity(), "Error al enviar mensaje", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
