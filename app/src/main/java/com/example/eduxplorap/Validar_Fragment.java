package com.example.eduxplorap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Validar_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Validar_Fragment extends Fragment {

    Button btnPend,btnAcep,btnRecha;
    TextView tvTodasSolic;

    RequestQueue rq;

    // Parámetros para la inicialización del fragmento
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Validar_Fragment() {
        // Constructor público vacío requerido por Android
    }

    /**
     * Use este método de fábrica para crear una nueva instancia de
     * este fragmento utilizando los parámetros proporcionados.
     *
     * @param param1 Parámetro 1.
     * @param param2 Parámetro 2.
     * @return A new instance of fragment Validar_Fragment.
     */
    public static Validar_Fragment newInstance(String param1, String param2) {
        Validar_Fragment fragment = new Validar_Fragment();
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
        // Inflar el diseño para este fragmento
        View view = inflater.inflate(R.layout.fragment_validar_, container, false);

        // Inicializar los botones y el TextView
        btnPend = view.findViewById(R.id.btnPend);
        btnAcep = view.findViewById(R.id.btnAcep);
        btnRecha = view.findViewById(R.id.btnRecha);
        tvTodasSolic = view.findViewById(R.id.tvTodasSolic);
        tvTodasSolic.setMovementMethod(new ScrollingMovementMethod()); // Habilitar desplazamiento para el TextView

        // Inicializar la cola de solicitudes Volley
        rq = Volley.newRequestQueue(requireContext());

        // Mostrar las solicitudes
        mostrar();

        // Retornar la vista del fragmento
        return view;
    }

    // Método para mostrar las solicitudes
    public void mostrar(){
        tvTodasSolic.setText(""); // Limpiar el TextView
        String url = "https://busc-int-upt-0f93f68ff11c.herokuapp.com/obtenerUsuarios.php";
        JsonArrayRequest requerimento = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                // Iterar sobre el JSONArray recibido del servidor
                for(int i=0;i<jsonArray.length();i++){
                    try {
                        JSONObject objeto = new JSONObject(jsonArray.get(i).toString());
                        // Agregar los detalles de la solicitud al TextView
                        tvTodasSolic.append("Empresa: "+ objeto.getString("nombreEmpresa")+"\n");
                        tvTodasSolic.append("Grupo: "+ objeto.getString("grupo")+"\n");
                        tvTodasSolic.append("Usuario: "+ objeto.getString("nombreUsuario")+"\n");
                        tvTodasSolic.append("Carrera: "+ objeto.getString("carrera")+"\n");
                        tvTodasSolic.append("Estado: "+ objeto.getString("estadoActual")+"\n");
                        tvTodasSolic.append("\n");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                // Mostrar un mensaje de error en caso de que falle la solicitud
                Toast.makeText(getContext(),volleyError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        // Agregar la solicitud a la cola de solicitudes
        rq.add(requerimento);
    }
}
