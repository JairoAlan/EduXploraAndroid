package com.example.eduxplorap;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Menu_Comentarios_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Menu_Comentarios_Fragment extends Fragment {

    private static final String API_URL = "https://busc-int-upt-0f93f68ff11c.herokuapp.com/contactoV.php";

    EditText tvCorreoComent, tvMensajeComent;
    Button btnenviarcoment;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Menu_Comentarios_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Menu_Comentarios_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Menu_Comentarios_Fragment newInstance(String param1, String param2) {
        Menu_Comentarios_Fragment fragment = new Menu_Comentarios_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu__comentarios_, container, false);

        tvCorreoComent = view.findViewById(R.id.tvCorreoComent);
        tvMensajeComent = view.findViewById(R.id.tvMensajeComent);
        btnenviarcoment = view.findViewById(R.id.btnenviarcoment);

        // Recuperar el idUsuario del Bundle
        Bundle bundle = getArguments();
        int idUsuario = 0;
        if (bundle != null) {
            idUsuario = bundle.getInt("ID_USUARIO", 0); // 0 es el valor predeterminado en caso de que no se pueda obtener el idUsuario
            // Hacer lo que necesites con el idUsuario en el fragmento...
        }

        Button btnenviarcoment = view.findViewById(R.id.btnenviarcoment);
        final int finalIdUsuario = idUsuario;
        btnenviarcoment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarDatos(finalIdUsuario);
            }
        });


        return view;
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