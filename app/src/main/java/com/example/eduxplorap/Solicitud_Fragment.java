package com.example.eduxplorap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

public class Solicitud_Fragment extends Fragment {

    LinearLayout Rluno;
    ImageView ivtabla;
    TextView tvTexto;
    Button btnenv, btnace, btnrech;
    RequestQueue rq;
    private View view;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Solicitud_Fragment() {
        // Required empty public constructor
    }

    public static Solicitud_Fragment newInstance(String param1, String param2) {
        Solicitud_Fragment fragment = new Solicitud_Fragment();
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
        view = inflater.inflate(R.layout.fragment_solicitud_, container, false);
        Rluno = view.findViewById(R.id.Rluno);

        rq = Volley.newRequestQueue(requireContext());
        mostrar();
        return view;
    }

    public void mostrar() {
        // Obtener el idUsuario del Bundle de argumentos
        Bundle bundle = getArguments();
        if (bundle != null) {
            int idUsuario = bundle.getInt("ID_USUARIO", 0); // 0 es el valor predeterminado si el ID no está disponible

            String url = "https://busc-int-upt-0f93f68ff11c.herokuapp.com/obtenerUsuarios.php";
            JsonArrayRequest requerimento = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    LinearLayout linearLayout = view.findViewById(R.id.Rluno);
                    linearLayout.removeAllViews(); // Eliminar cualquier vista previa

                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject objeto = jsonArray.getJSONObject(i);
                            // Verificar si la solicitud pertenece al usuario logueado
                            if (objeto.getInt("idUsuario") == idUsuario) {
                                // Crear la vista para esta solicitud
                                LinearLayout itemLayout = new LinearLayout(requireContext());
                                itemLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                itemLayout.setOrientation(LinearLayout.VERTICAL);

                                // Crear un TextView para mostrar los detalles de la solicitud
                                TextView usuarioTextView = new TextView(requireContext());
                                usuarioTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                usuarioTextView.setText("Empresa: " + objeto.getString("nombreEmpresa") + "\n" +
                                        "Grupo: " + objeto.getString("grupo") + "\n" +
                                        "Usuario: " + objeto.getString("nombreUsuario") + "\n" +
                                        "Carrera: " + objeto.getString("carrera") + "\n" +
                                        "estadoActual: " + objeto.getString("estadoActual"));

                                // Crear botones
                                Button btnPend = new Button(requireContext());
                                btnPend.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                btnPend.setText("Env");
                                btnPend.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Acciones al hacer clic en el botón
                                    }
                                });

                                Button btnAcep = new Button(requireContext());
                                btnAcep.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                btnAcep.setText("Acep");
                                btnAcep.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Acciones al hacer clic en el botón
                                    }
                                });

                                Button btnRecha = new Button(requireContext());
                                btnRecha.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                btnRecha.setText("Rech");
                                btnRecha.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Acciones al hacer clic en el botón
                                    }
                                });

                                // Agregar TextView y botones al LinearLayout del item
                                itemLayout.addView(usuarioTextView);
                                itemLayout.addView(btnPend);
                                itemLayout.addView(btnAcep);
                                itemLayout.addView(btnRecha);

                                // Agregar el LinearLayout del item al LinearLayout principal
                                linearLayout.addView(itemLayout);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Error al procesar la respuesta", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    volleyError.printStackTrace();
                    Toast.makeText(getContext(), "Error de red: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            rq.add(requerimento);
        }
    }
}
