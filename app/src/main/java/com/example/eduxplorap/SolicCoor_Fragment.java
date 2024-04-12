package com.example.eduxplorap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Use the {@link SolicCoor_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SolicCoor_Fragment extends Fragment {

    TextView tvTodasSolic, tvTodasSolic2, tvTodasSolic3, tvTodasSolic4;

    RequestQueue rq;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SolicCoor_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SolicCoor_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SolicCoor_Fragment newInstance(String param1, String param2) {
        SolicCoor_Fragment fragment = new SolicCoor_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_solic_coor, container, false);
        tvTodasSolic = view.findViewById(R.id.tvNombre);
        tvTodasSolic.setMovementMethod(new ScrollingMovementMethod());
        tvTodasSolic2 = view.findViewById(R.id.tvNombre2);
        tvTodasSolic2.setMovementMethod(new ScrollingMovementMethod());
        tvTodasSolic3 = view.findViewById(R.id.tvNombre3);
        tvTodasSolic3.setMovementMethod(new ScrollingMovementMethod());
        tvTodasSolic4 = view.findViewById(R.id.tvNombre4);
        tvTodasSolic4.setMovementMethod(new ScrollingMovementMethod());
        rq = Volley.newRequestQueue(requireContext());
        mostrar();
        // Inflate the layout for this fragment
        return view;
    }

    public void mostrar(){
        final TextView[] textViews = {tvTodasSolic, tvTodasSolic2, tvTodasSolic3, tvTodasSolic4};
        String url = "https://busc-int-upt-0f93f68ff11c.herokuapp.com/TraerSolicitudes.php";
        JsonArrayRequest requerimento = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for(int i = 0; i < 4; i++){
                    try {
                        JSONObject objeto = new JSONObject(jsonArray.get(i).toString());
                        textViews[i].append("Empresa: "+ objeto.getString("nombreEmpresa")+"\n");
                        textViews[i].append("Distancia: "+"\n");
                        textViews[i].append("Url: "+ objeto.getString("Contacto")+"\n");
                        textViews[i].append("\n");
                        textViews[i].append("Grupo: "+ objeto.getString("grupo")+"\n");
                        textViews[i].append("\n");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

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