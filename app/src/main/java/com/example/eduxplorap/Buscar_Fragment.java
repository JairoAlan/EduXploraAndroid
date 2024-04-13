package com.example.eduxplorap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Buscar_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Buscar_Fragment extends Fragment {

    TextView tveduX,tvResultado, tvResultado2, tvResultado3, tvResultado4, tvResultado5;
    TextView tvResultado6, tvResultado7, tvResultado8, tvResultado9;
    LinearLayout llResultados,llResultados2;
    Spinner spCarrera, spMateria;

    RequestQueue rq;

    String idCarreraSeleccionada;

    Button btnres1,btnres2,btnres3,btnres4,btnres5,btnres6,btnres7,btnres8,btnres9,
            btnubi1,btnubi2,btnubi3,btnubi4,btnubi5,btnubi6,btnubi7,btnubi8,btnubi9;

    ArrayList<String> idCarrerasList = new ArrayList<>(); // ArrayList para almacenar los idCarrera

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Buscar_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Buscar_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Buscar_Fragment newInstance(String param1, String param2) {
        Buscar_Fragment fragment = new Buscar_Fragment();
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

        View view = inflater.inflate(R.layout.fragment_buscar_, container, false);

        // Spinners
        spCarrera = view.findViewById(R.id.spCarrera);
        spMateria = view.findViewById(R.id.spMateria);
        // Layouts
        llResultados = view.findViewById(R.id.llResultados);
        llResultados2 = view.findViewById(R.id.llResultados2);
        // TextView
        tveduX = view.findViewById(R.id.tveduX);
        tvResultado = view.findViewById(R.id.tvResultado);
        tvResultado.setMovementMethod(new ScrollingMovementMethod());
        tvResultado2 = view.findViewById(R.id.tvResultado2);
        tvResultado2.setMovementMethod(new ScrollingMovementMethod());
        tvResultado3 = view.findViewById(R.id.tvResultado3);
        tvResultado3.setMovementMethod(new ScrollingMovementMethod());
        tvResultado4 = view.findViewById(R.id.tvResultado4);
        tvResultado4.setMovementMethod(new ScrollingMovementMethod());
        tvResultado5 = view.findViewById(R.id.tvResultado5);
        tvResultado5.setMovementMethod(new ScrollingMovementMethod());
        tvResultado6 = view.findViewById(R.id.tvResultado6);
        tvResultado6.setMovementMethod(new ScrollingMovementMethod());
        tvResultado7 = view.findViewById(R.id.tvResultado7);
        tvResultado7.setMovementMethod(new ScrollingMovementMethod());
        tvResultado8 = view.findViewById(R.id.tvResultado8);
        tvResultado8.setMovementMethod(new ScrollingMovementMethod());
        tvResultado9 = view.findViewById(R.id.tvResultado9);
        tvResultado9.setMovementMethod(new ScrollingMovementMethod());

        // Botones de resultado
        btnres1 = view.findViewById(R.id.btnres1);
        btnres2 = view.findViewById(R.id.btnres2);
        btnres3 = view.findViewById(R.id.btnres3);
        btnres4 = view.findViewById(R.id.btnres4);
        btnres5 = view.findViewById(R.id.btnres5);
        btnres6 = view.findViewById(R.id.btnres6);
        btnres7 = view.findViewById(R.id.btnres7);
        btnres8 = view.findViewById(R.id.btnres8);
        btnres9 = view.findViewById(R.id.btnres9);

        // Botones de ubicacion
        btnubi1 = view.findViewById(R.id.btnubi1);
        btnubi2 = view.findViewById(R.id.btnubi2);
        btnubi3 = view.findViewById(R.id.btnubi3);
        btnubi4 = view.findViewById(R.id.btnubi4);
        btnubi5 = view.findViewById(R.id.btnubi5);
        btnubi6 = view.findViewById(R.id.btnubi6);
        btnubi7 = view.findViewById(R.id.btnubi7);
        btnubi8 = view.findViewById(R.id.btnubi8);
        btnubi9 = view.findViewById(R.id.btnubi9);

        rq = Volley.newRequestQueue(requireContext());
        carreraSr();

        spCarrera.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                
                idCarreraSeleccionada = String.valueOf(position + 1);
                materiaSr();
                buscar();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Este método se llama cuando no se selecciona ningún elemento
            }
        });

        // BOTONES DE SOLICITAR
        btnres1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnres2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnres3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnres4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnres5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnres6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnres7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnres8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnres9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // BOTONES DE UBICACION

        btnubi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnubi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnubi3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnubi4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnubi5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnubi6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnubi7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnubi8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnubi9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    public void carreraSr(){
        String url = "https://busc-int-upt-0f93f68ff11c.herokuapp.com/filtroc.php";

        JsonArrayRequest requerimento = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item);

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject objeto = jsonArray.getJSONObject(i);
                        String nombre = objeto.getString("nombre");
                        String ID_Carrera = objeto.getString("ID_Carrera");
                        adapter.add(nombre);
                        idCarrerasList.add(ID_Carrera); // Agrega el idCarrera al ArrayList
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), "No hay Conexion a la Base de Datos", Toast.LENGTH_LONG).show();
                        //throw new RuntimeException(e);
                    }
                }
                spCarrera.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(), "No hay Conexion a Internet", Toast.LENGTH_LONG).show();
                // volleyError.printStackTrace();
                // Toast.makeText(getContext(),volleyError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        rq.add(requerimento);
    }

    public void materiaSr() {
        Log.d("ID_CARRERA_REQUEST", "Solicitando materias para idCarrera: " + idCarreraSeleccionada);
        String url2 = "https://busc-int-upt-0f93f68ff11c.herokuapp.com/filtrom.php?idCarrera="+idCarreraSeleccionada;

        JsonObjectRequest requerimento2 = new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray materiasArray = jsonObject.getJSONArray("materias");

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item);

                    if (materiasArray.length() > 0) {
                        for (int i = 0; i < materiasArray.length(); i++) {
                            JSONObject materiaObject = materiasArray.getJSONObject(i);
                            String nombre = materiaObject.getString("nombre");
                            adapter.add(nombre);
                        }
                    } else {
                        // No hay materias registradas para la carrera proporcionada
                        Toast.makeText(getContext(), "No hay materias registradas para la carrera proporcionada", Toast.LENGTH_SHORT).show();
                    }

                    spMateria.setAdapter(adapter);
                } catch (JSONException e) {
                    //e.printStackTrace();
                    spMateria.setAdapter(null);
                    Toast.makeText(getContext(), "No hay materias registradas para la carrera proporcionada", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(), "No hay materias registradas para la carrera proporcionada", Toast.LENGTH_LONG).show();
                //volleyError.printStackTrace();
                //Toast.makeText(getContext(), volleyError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        rq.add(requerimento2);
    }

    public void buscar() {
        final TextView[] textViews = {tvResultado, tvResultado2, tvResultado3, tvResultado4, tvResultado5, tvResultado6, tvResultado7, tvResultado8, tvResultado9};
        String url3 = "https://busc-int-upt-0f93f68ff11c.herokuapp.com/buscar.php?idMateria="+idCarreraSeleccionada;

        JsonObjectRequest requerimento3 = new JsonObjectRequest(Request.Method.GET, url3, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray empresasArray = jsonObject.getJSONArray("empresas");

                    if (empresasArray.length() == 0) {
                        Toast.makeText(getContext(), "No hay Empresas registradas ", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (empresasArray.length() > 0) {
                        for (int i = 0; i < 9; i++) {
                            JSONObject empresaObject = empresasArray.getJSONObject(i);
                            textViews[i].append("Nombre: " + empresaObject.getString("Nombre"));
                            textViews[i].append("URL: " + empresaObject.getString("Contacto"));
                            textViews[i].append("Descripcion: " + empresaObject.getString("Descripcion"));
                        }
                    } else {
                        // No hay materias registradas para la carrera proporcionada
                        Toast.makeText(getContext(), "No hay Empresas registradas ", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "No hay respuesta a lo solicitado de la Base de Datos", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "No hay conexion a internet", Toast.LENGTH_SHORT).show();
                //volleyError.printStackTrace();
                //Toast.makeText(getContext(), volleyError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        rq.add(requerimento3);
    }

// Fin
}