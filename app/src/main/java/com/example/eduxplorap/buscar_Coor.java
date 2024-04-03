package com.example.eduxplorap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
 * Use the {@link buscar_Coor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class buscar_Coor extends Fragment {

    TextView tvBus,tvListo,tvBuscar;

    Spinner Sr_uno,Sr_dos;

    Button btnBuscar;

    RequestQueue rq;

    String idCarreraSeleccionada;

    ArrayList<String> idCarrerasList = new ArrayList<>(); // ArrayList para almacenar los idCarrera

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public buscar_Coor() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment buscar_Coor.
     */
    // TODO: Rename and change types and number of parameters
    public static buscar_Coor newInstance(String param1, String param2) {
        buscar_Coor fragment = new buscar_Coor();
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
        View view = inflater.inflate(R.layout.fragment_buscar__coor, container, false);
        tvBus = view.findViewById(R.id.tvBus);
        tvListo = view.findViewById(R.id.tvListo);
        Sr_uno = view.findViewById(R.id.Sr_uno);
        Sr_dos = view.findViewById(R.id.Sr_dos);
        btnBuscar = view.findViewById(R.id.btnBuscar);
        rq = Volley.newRequestQueue(requireContext());
        carreraSr();

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar();
            }
        });

        Sr_uno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // position es la posición del elemento seleccionado en el Spinner Sr_uno
                // Puedes usar esta posición para acceder al elemento correspondiente en el ArrayList idCarrerasList

                // Por ejemplo, para obtener el idCarrera en la posición seleccionada:
                idCarreraSeleccionada = String.valueOf(position + 1);
                Log.d("ID_CARRERA_SELECTED", "idCarreraSeleccionada: " + idCarreraSeleccionada);
                materiaSr();
                // Luego puedes usar este idCarrera según sea necesario en tu lógica
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Este método se llama cuando no se selecciona ningún elemento
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
                        String idCarrera = objeto.getString("idCarrera");
                        adapter.add(nombre);
                        idCarrerasList.add(idCarrera); // Agrega el idCarrera al ArrayList
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                Sr_uno.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                Toast.makeText(getContext(),volleyError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        rq.add(requerimento);
    }

    public void materiaSr() {

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

                    Sr_dos.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                Toast.makeText(getContext(), volleyError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        rq.add(requerimento2);
    }

    public void buscar(){
        String url3 = "https://busc-int-upt-0f93f68ff11c.herokuapp.com/buscar.php?idMateria="+idCarreraSeleccionada;

        JsonArrayRequest requerimento3 = new JsonArrayRequest(Request.Method.GET, url3, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject empresa = jsonArray.getJSONObject(i);
                        String nombreEmpresa = empresa.getString("Nombre");
                        // Mostrar el nombre de la empresa en un TextView
                        tvBuscar.append("Nombre de la empresa: " + nombreEmpresa + "\n");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                Toast.makeText(getContext(),volleyError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        rq.add(requerimento3);
    }


// Fin
}