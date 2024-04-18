package com.example.eduxplorap;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
 * Use the {@link Menu_Evaluar_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Menu_Evaluar_Fragment extends Fragment {

    TextView tvNombreR, tvNombreR2, tvNombreR3, tvNombreR4;
    Button btnresena, btnresena2, btnresena3, btnresena4;
    final String[] nombresEmpresas = new String[4];
    final String[] grupos = new String[4];
    RequestQueue rq;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Menu_Evaluar_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Menu_Evaluar_Fragment.
     */public static Menu_Evaluar_Fragment newInstance(String param1, String param2) {
        Menu_Evaluar_Fragment fragment = new Menu_Evaluar_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Verificar si hay argumentos
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el diseño para este fragmento
        View view = inflater.inflate(R.layout.fragment_menu__evaluar_, container, false);
        // Inicializar vistas
        tvNombreR = view.findViewById(R.id.tvNombreR);
        tvNombreR.setMovementMethod(new ScrollingMovementMethod());
        tvNombreR2 = view.findViewById(R.id.tvNombreR2);
        tvNombreR2.setMovementMethod(new ScrollingMovementMethod());
        tvNombreR3 = view.findViewById(R.id.tvNombreR3);
        tvNombreR3.setMovementMethod(new ScrollingMovementMethod());
        tvNombreR4 = view.findViewById(R.id.tvNombreR4);
        tvNombreR4.setMovementMethod(new ScrollingMovementMethod());
        btnresena = view.findViewById(R.id.btnresena1);
        btnresena2 = view.findViewById(R.id.btnresena2);
        btnresena3 = view.findViewById(R.id.btnresena3);
        btnresena4 = view.findViewById(R.id.btnresena4);

        // Inicializar la cola de solicitudes Volley
        rq = Volley.newRequestQueue(getContext());
        // Mostrar la lista de empresas
        mostrar();

        // Configurar listeners de botones para abrir el fragmento de reseña
        btnresena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iraEvaluar(new Resena_Fragment());
            }
        });
        btnresena2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iraEvaluar(new Resena_Fragment());
            }
        });
        btnresena3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iraEvaluar(new Resena_Fragment());
            }
        });
        btnresena4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iraEvaluar(new Resena_Fragment());
            }
        });
        return view;
    }

    /**
     * Método para obtener y mostrar la lista de empresas disponibles.
     * Realiza una solicitud HTTP para obtener los datos del servidor.
     */
    public void mostrar() {
        // Arreglo de textViews para mostrar información de las empresas
        final TextView[] textViews = {tvNombreR, tvNombreR2, tvNombreR3, tvNombreR4};
        // URL para la solicitud HTTP
        String url = "https://busc-int-upt-0f93f68ff11c.herokuapp.com/TraerSolicitudesRes.php";
        // Realizar la solicitud JSONArrayRequest
        JsonArrayRequest requerimento = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                // Obtener la longitud mínima entre el tamaño del JSONArray y el tamaño de textViews
                int length = Math.min(jsonArray.length(), textViews.length);
                // Iterar sobre los elementos JSON obtenidos
                for (int i = 0; i < length; i++) {
                    try {
                        JSONObject objeto = new JSONObject(jsonArray.get(i).toString());
                        // Mostrar la información de la empresa en los textViews
                        textViews[i].append("Nombre: " + objeto.getString("nombreEmpresa") + "\n");
//                        textViews[i].append("Distancia: "+"\n");
                        textViews[i].append("Url: " + objeto.getString("Contacto") + "\n");
                        textViews[i].append("\n");
                        textViews[i].append("Grupo: " + objeto.getString("grupo") + "\n");
                        textViews[i].append("\n");
                        nombresEmpresas[i] = objeto.getString("nombreEmpresa");
//                        distancias[i] = ""; // Aquí podrías obtener la distancia si está disponible en el JSON
                        grupos[i] = objeto.getString("grupo");
                    } catch (JSONException e) {
                        // Manejar excepciones JSON
                        throw new RuntimeException(e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // Manejar errores de respuesta
//                volleyError.printStackTrace();
//                Toast.makeText(getContext(),volleyError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        // Agregar la solicitud a la cola de solicitudes
        rq.add(requerimento);
    }

    /**
     * Método para cambiar al fragmento de reseña.
     * @param fragment El fragmento de reseña a mostrar.
     */
    public void iraEvaluar(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}