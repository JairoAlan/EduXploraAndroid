package com.example.eduxplorap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.jar.JarException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Solic_Vin_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Solic_Vin_Fragment extends Fragment {

    // Declaración de vistas y botones
    TextView tvTodasSolic, tvTodasSolic2, tvTodasSolic3, tvTodasSolic4;
    Button btnAceptarV1, btnAceptarV2, btnAceptarV3, btnAceptarV4;
    Button btnCancelarV1, btnCancelarV2, btnCancelarV3, btnCancelarV4;
    // Arreglos para almacenar nombres de empresas, URLs y grupos
    final String[] nombresEmpresas = new String[4];
    final String[] urls = new String[4];
    final String[] grupos = new String[4];

    // Cola de solicitudes HTTP
    RequestQueue rq;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Solic_Vin_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Solic_Vin_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Solic_Vin_Fragment newInstance(String param1, String param2) {
        Solic_Vin_Fragment fragment = new Solic_Vin_Fragment();
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
        // Inflar el diseño para este fragmento
        View view = inflater.inflate(R.layout.fragment_solic_vin, container, false);

        // Inicializar las vistas y los botones
        tvTodasSolic = view.findViewById(R.id.tvNombreV);
        tvTodasSolic.setMovementMethod(new ScrollingMovementMethod());
        tvTodasSolic2 = view.findViewById(R.id.tvNombreV2);
        tvTodasSolic2.setMovementMethod(new ScrollingMovementMethod());
        tvTodasSolic3 = view.findViewById(R.id.tvNombreV3);
        tvTodasSolic3.setMovementMethod(new ScrollingMovementMethod());
        tvTodasSolic4 = view.findViewById(R.id.tvNombreV4);
        tvTodasSolic4.setMovementMethod(new ScrollingMovementMethod());
        btnAceptarV1 = view.findViewById(R.id.btnAceptarV1);
        btnAceptarV2 = view.findViewById(R.id.btnAceptarV2);
        btnAceptarV3 = view.findViewById(R.id.btnAceptarV3);
        btnAceptarV4 = view.findViewById(R.id.btnAceptarV4);
        btnCancelarV1 = view.findViewById(R.id.btnCancelarV1);
        btnCancelarV2 = view.findViewById(R.id.btnCancelarV2);
        btnCancelarV3 = view.findViewById(R.id.btnCancelarV3);
        btnCancelarV4 = view.findViewById(R.id.btnCancelarV4);
        rq = Volley.newRequestQueue(requireContext());
        mostrar();
        btnAceptarV1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AceptarVisita(1);
                btnCancelarV1.setEnabled(false);
            }
        });
        btnAceptarV2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AceptarVisita(2);
                btnCancelarV2.setEnabled(false);
            }
        });
        btnAceptarV3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AceptarVisita(3);
                btnCancelarV3.setEnabled(false);
            }
        });
        btnAceptarV4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AceptarVisita(4);
                btnCancelarV4.setEnabled(false);
            }
        });
        btnCancelarV1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelarVisita(1);
                btnAceptarV1.setEnabled(false);
            }
        });
        btnCancelarV2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelarVisita(2);
                btnAceptarV2.setEnabled(false);
            }
        });
        btnCancelarV3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelarVisita(3);
                btnAceptarV3.setEnabled(false);
            }
        });
        btnCancelarV4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelarVisita(4);
                btnAceptarV4.setEnabled(false);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
    // Método para mostrar las solicitudes de visita
    public void mostrar(){
        // Arreglo de textViews para mostrar los detalles de las solicitudes
        final TextView[] textViews = {tvTodasSolic, tvTodasSolic2, tvTodasSolic3, tvTodasSolic4};

        // URL para obtener las solicitudes de visita desde el servidor
        String url = "https://busc-int-upt-0f93f68ff11c.herokuapp.com/TraerSolicitudesVin.php";

        // Solicitud JSON para obtener los datos del servidor
        JsonArrayRequest requerimento = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                int length = Math.min(jsonArray.length(), textViews.length);
                for(int i = 0; i < length; i++){
                    try {
                        JSONObject objeto = new JSONObject(jsonArray.get(i).toString());
                        // Mostrar los detalles de la solicitud en el textView correspondiente
                        textViews[i].append("Nombre: "+ objeto.getString("nombreEmpresa")+"\n");
                        textViews[i].append("Distancia: "+"\n"); // Puedes obtener la distancia aquí si está disponible en el JSON
                        textViews[i].append("Url: "+ objeto.getString("Contacto")+"\n");
                        textViews[i].append("\n");
                        textViews[i].append("Grupo: "+ objeto.getString("grupo")+"\n");
                        textViews[i].append("\n");
                        // Almacenar los datos de la solicitud en los arreglos correspondientes
                        nombresEmpresas[i] = objeto.getString("nombreEmpresa");
//                        distancias[i] = ""; // Aquí podrías obtener la distancia si está disponible en el JSON
                        urls[i] = objeto.getString("Contacto");
                        grupos[i] = objeto.getString("grupo");
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

    // Método para cancelar una visita
    public void CancelarVisita(int num){
        // URL para cancelar la visita
        String url = "https://busc-int-upt-0f93f68ff11c.herokuapp.com/EstadoRechazado.php?Nombre="+nombresEmpresas[num-1]+"&grupo="+grupos[num-1];

        JSONObject object = new JSONObject();
//        try {
//            object.put("Nombre",nombresEmpresas[num-1]);
//            object.put("grupo",grupos[num-1]);
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
        // Solicitud JSON para cancelar la visita
        JsonObjectRequest jasonObjtRequest = new JsonObjectRequest(Request.Method.PUT, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Toast.makeText(getContext(), "La solicitud ha cancelado", Toast.LENGTH_SHORT).show();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "La solicitud ha cancelado", Toast.LENGTH_SHORT).show();
            }
        });
        rq.add(jasonObjtRequest);
    }

    public void AceptarVisita(int num){
        String url = "https://busc-int-upt-0f93f68ff11c.herokuapp.com/EstadoAceptado.php?Nombre="+nombresEmpresas[num-1]+"&grupo="+grupos[num-1];
        JSONObject object = new JSONObject();
//        try {
//            object.put("Nombre",nombresEmpresas[num-1]);
//            object.put("grupo",grupos[num-1]);
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
        JsonObjectRequest jasonObjtRequest = new JsonObjectRequest(Request.Method.PUT, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Toast.makeText(getContext(), "La solicitud ha sido aceptada", Toast.LENGTH_SHORT).show();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "La solicitud ha sido aceptada", Toast.LENGTH_SHORT).show();
            }
        });
        rq.add(jasonObjtRequest);
    }

}