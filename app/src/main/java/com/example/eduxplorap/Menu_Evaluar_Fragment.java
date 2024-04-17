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
     */
    // TODO: Rename and change types and number of parameters
    public static Menu_Evaluar_Fragment newInstance(String param1, String param2) {
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu__evaluar_, container, false);
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

        rq = Volley.newRequestQueue(getContext()); // Inicializar RequestQueue
        mostrar();
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
    public void mostrar(){
        final TextView[] textViews = {tvNombreR, tvNombreR2, tvNombreR3, tvNombreR4};


        String url = "https://busc-int-upt-0f93f68ff11c.herokuapp.com/TraerSolicitudesRes.php";
        JsonArrayRequest requerimento = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                int length = Math.min(jsonArray.length(), textViews.length);
                for(int i = 0; i < length; i++){
                    try {
                        JSONObject objeto = new JSONObject(jsonArray.get(i).toString());
                        textViews[i].append("Nombre: "+ objeto.getString("nombreEmpresa")+"\n");
//                        textViews[i].append("Distancia: "+"\n");
                        textViews[i].append("Url: "+ objeto.getString("Contacto")+"\n");
                        textViews[i].append("\n");
                        textViews[i].append("Grupo: "+ objeto.getString("grupo")+"\n");
                        textViews[i].append("\n");
                        nombresEmpresas[i] = objeto.getString("nombreEmpresa");
//                        distancias[i] = ""; // Aquí podrías obtener la distancia si está disponible en el JSON
                        grupos[i] = objeto.getString("grupo");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                volleyError.printStackTrace();
//                Toast.makeText(getContext(),volleyError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        rq.add(requerimento);
    }
    public void iraEvaluar(Fragment fragment){
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}