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
 * Use the {@link Menu_Favoritos_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Menu_Favoritos_Fragment extends Fragment {

    TextView tvFav, tvFav2, tvFav3, tvFav4, tvFav5, tvFav6, tvFav7, tvFav8, tvFav9;
    RequestQueue rq;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Menu_Favoritos_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Menu_Favoritos_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Menu_Favoritos_Fragment newInstance(String param1, String param2) {
        Menu_Favoritos_Fragment fragment = new Menu_Favoritos_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_menu__favoritos_, container, false);
        tvFav = view.findViewById(R.id.tvFav);
        tvFav.setMovementMethod(new ScrollingMovementMethod());
        tvFav2 = view.findViewById(R.id.tvFav2);
        tvFav2.setMovementMethod(new ScrollingMovementMethod());
        tvFav3 = view.findViewById(R.id.tvFav3);
        tvFav3.setMovementMethod(new ScrollingMovementMethod());
        tvFav4 = view.findViewById(R.id.tvFav4);
        tvFav4.setMovementMethod(new ScrollingMovementMethod());
        tvFav5 = view.findViewById(R.id.tvFav5);
        tvFav5.setMovementMethod(new ScrollingMovementMethod());
        tvFav6 = view.findViewById(R.id.tvFav6);
        tvFav6.setMovementMethod(new ScrollingMovementMethod());
        tvFav7 = view.findViewById(R.id.tvFav7);
        tvFav7.setMovementMethod(new ScrollingMovementMethod());
        tvFav8 = view.findViewById(R.id.tvFav8);
        tvFav8.setMovementMethod(new ScrollingMovementMethod());
        tvFav9 = view.findViewById(R.id.tvFav9);
        tvFav9.setMovementMethod(new ScrollingMovementMethod());

        rq = Volley.newRequestQueue(requireContext());
        mostrar();

        return view;
    }

    public void mostrar(){
        final TextView[] textViews = {tvFav, tvFav2, tvFav3, tvFav4, tvFav5, tvFav6, tvFav7, tvFav8, tvFav9};
        String url = "https://busc-int-upt-0f93f68ff11c.herokuapp.com/BuscarEmpresas.php";
        JsonArrayRequest requerimento = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                int length = Math.min(jsonArray.length(), textViews.length);
                for(int i = 0; i < length; i++){
                    try {
                        JSONObject objeto = new JSONObject(jsonArray.get(i).toString());
                        textViews[i].append("Empresa: "+ objeto.getString("Nombre")+"\n");
                        textViews[i].append("URL: "+ objeto.getString("Contacto")+"\n");
                        textViews[i].append("\n");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "No hay Conexion a Internet ", Toast.LENGTH_SHORT).show();
                //volleyError.printStackTrace();
                //Toast.makeText(getContext(),volleyError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        rq.add(requerimento);
    }
}