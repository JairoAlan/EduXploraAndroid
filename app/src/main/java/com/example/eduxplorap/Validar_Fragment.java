package com.example.eduxplorap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    TextView tvTodasSolic;

    RequestQueue rq;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Validar_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Validar_Fragment.
     */
    // TODO: Rename and change types and number of parameters
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
        View view = inflater.inflate(R.layout.fragment_validar_, container, false);
        tvTodasSolic = view.findViewById(R.id.tvTodasSolic);
        rq = Volley.newRequestQueue(requireContext());
        mostrar();
        return view;
    }

    public void mostrar(){
        tvTodasSolic.setText("");
        String url = "https://busc-int-upt-0f93f68ff11c.herokuapp.com/obtenerUsuarios.php";
        JsonArrayRequest requerimento = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for(int i=0;i<jsonArray.length();i++){
                    try {
                        JSONObject objeto = new JSONObject(jsonArray.get(i).toString());
                        tvTodasSolic.append("idSolicitud: "+ objeto.getString("idSolicitud")+"\n");
                        tvTodasSolic.append("idEmpresa: "+ objeto.getString("idEmpresa")+"\n");
                        tvTodasSolic.append("nombreEmpresa: "+ objeto.getString("nombreEmpresa")+"\n");
                        tvTodasSolic.append("grupo: "+ objeto.getString("grupo")+"\n");
                        tvTodasSolic.append("idUsuario: "+ objeto.getString("idUsuario")+"\n");
                        tvTodasSolic.append("carrera: "+ objeto.getString("carrera")+"\n");
                        tvTodasSolic.append("estadoActual: "+ objeto.getString("estadoActual")+"\n");
                        tvTodasSolic.append("contactoEmpresa: "+ objeto.getString("contactoEmpresa")+"\n");
                        tvTodasSolic.append("nombreUsuario: "+ objeto.getString("nombreUsuario")+"\n");
                        tvTodasSolic.append("________________________________________\n");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
    }
}