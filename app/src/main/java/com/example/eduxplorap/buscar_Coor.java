package com.example.eduxplorap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link buscar_Coor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class buscar_Coor extends Fragment {

    TextView tvBus,tvListo;

    Spinner Sr_uno,Sr_dos;

    Button btnBuscar;

    RequestQueue rq;

    String materia;

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

        // Se selecciona el item
        Sr_uno.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                materia = parent.getItemAtPosition(position).toString();
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
                        adapter.add(nombre);
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

    public void materiaSr(){

        String url2 = "http://localhost/eduxplora/filtrom.php?idCarrera="+materia;
    }
}