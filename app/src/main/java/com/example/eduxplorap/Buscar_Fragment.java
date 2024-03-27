package com.example.eduxplorap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
 * Use the {@link Buscar_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Buscar_Fragment extends Fragment {

    Spinner spCarrera, spMateria;

    RequestQueue rq;

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buscar_, container, false);
        spCarrera = view.findViewById(R.id.spCarrera);
        spMateria = view.findViewById(R.id.spMateria);
        rq = Volley.newRequestQueue(requireContext());
        mostrar();
        return view;
    }

    public void mostrar(){
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
                spCarrera.setAdapter(adapter);
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
}