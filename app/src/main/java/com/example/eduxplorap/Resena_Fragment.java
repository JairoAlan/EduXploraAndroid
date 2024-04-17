package com.example.eduxplorap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Resena_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Resena_Fragment extends Fragment {

    EditText etActividad, etObjetivos, etCalificacion, etRecomendacion, etJustificacion, etObservaciones;
    Button btnGuardar;
    String Favoritos="Si";

    RequestQueue rq;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Resena_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Resena_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Resena_Fragment newInstance(String param1, String param2) {
        Resena_Fragment fragment = new Resena_Fragment();
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
        View view = inflater.inflate(R.layout.fragment_resena_, container, false);
        etActividad = view.findViewById(R.id.etActividad);
        etObjetivos = view.findViewById(R.id.etObjetivos);
        etCalificacion = view.findViewById(R.id.etCalificacion);
        etRecomendacion = view.findViewById(R.id.etRecomendacion);
        etJustificacion = view.findViewById(R.id.etJustificacion);
        etObservaciones = view.findViewById(R.id.etObservaciones);
        btnGuardar = view.findViewById(R.id.btnGuardar);

        rq = Volley.newRequestQueue(getContext());
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuardarResenia();

            }
        });

        return view;
    }

    public void GuardarResenia(){
        String url = "https://busc-int-upt-0f93f68ff11c.herokuapp.com/Res.php?Actividades="+etActividad.getText()+"&Objetivos="+etObjetivos.getText()+"&Calificacion="+etCalificacion.getText()+"&Recomendacion=" +etRecomendacion.getText()+"&Justificacion="+etJustificacion.getText()+"&Observacion="+etObservaciones.getText()+"&Favoritos="+Favoritos;
        JSONObject object = new JSONObject();
//        try {
//            object.put("Actividades",tvActividad.getText());
//            object.put("Objetivos",tvObjetivos.getText());
//            object.put("Calificacion",tvCalificacion.getText());
//            object.put("Recomendacion",tvRecomendacion.getText());
//            object.put("Justificacion",tvJustificacion.getText());
//            object.put("Observacion",tvObservaciones.getText());
//            object.put("Favoritos",Favoritos);
//
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
        JsonObjectRequest jasonObjtRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Toast.makeText(getContext(), "La reseña se guardo correctamente", Toast.LENGTH_SHORT).show();
                regresarAevaluar(new Menu_Evaluar_Fragment());
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "Se guardo correctamente", Toast.LENGTH_SHORT).show();
                regresarAevaluar(new Menu_Evaluar_Fragment());
            }
        });
            rq.add(jasonObjtRequest);
        }
    public void regresarAevaluar(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

}

