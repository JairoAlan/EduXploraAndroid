
package com.example.eduxplorap;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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

public class Buscar_Fragment extends Fragment {

    Spinner spCarrera, spMateria;
    LinearLayout lresultados;

    RequestQueue rq;
    String Unilatitud = "20.1352722";
    String Unilongitud = "-98.383043";

    ArrayList<String> idCarrerasList = new ArrayList<>(); // ArrayList para almacenar los idCarrera

    public Buscar_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_buscar_, container, false);

        // Spinners
        spCarrera = view.findViewById(R.id.spCarrera);
        spMateria = view.findViewById(R.id.spMateria);
        // Layouts
        lresultados = view.findViewById(R.id.lresultados);

        rq = Volley.newRequestQueue(requireContext());
        carreraSr();
        // Recuperar el idUsuario del Bundle
        Bundle bundle = getArguments();
        int idUsuario = 0;
        String rolUsuario = "";
        if (bundle != null) {
            idUsuario = bundle.getInt("ID_USUARIO", 0); // 0 es el valor predeterminado en caso de que no se pueda obtener el idUsuario
            rolUsuario = bundle.getString("ROL_USUARIO", "");
            // Hacer lo que necesites con el idUsuario en el fragmento...
        }

        spCarrera.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String idCarreraSeleccionada = String.valueOf(position + 1);
                materiaSr(idCarreraSeleccionada);
                buscar(idCarreraSeleccionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Este método se llama cuando no se selecciona ningún elemento
            }
        });
        spMateria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String idMateriaSeleccionada = obtenerIdMateriaSeleccionada(); // Obtener el ID de la materia seleccionada
                buscar(idMateriaSeleccionada); // Llamar al método buscar con el ID de la materia seleccionada
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
                        String ID_Carrera = objeto.getString("ID_Carrera");
                        adapter.add(nombre);
                        idCarrerasList.add(ID_Carrera); // Agrega el idCarrera al ArrayList
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), "No hay Conexion a la Base de Datos", Toast.LENGTH_LONG).show();
                    }
                }
                spCarrera.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(), "No hay Conexion a Internet", Toast.LENGTH_LONG).show();
            }
        });
        rq.add(requerimento);
    }

    public void materiaSr(String idCarreraSeleccionada) {
        String url = "https://busc-int-upt-0f93f68ff11c.herokuapp.com/filtrom.php?idCarrera=" + idCarreraSeleccionada;

        JsonObjectRequest requerimento = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
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
                    spMateria.setAdapter(null);
                    Toast.makeText(getContext(), "No hay materias registradas para la carrera proporcionada", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(), "No hay materias registradas para la carrera proporcionada", Toast.LENGTH_LONG).show();
            }
        });

        rq.add(requerimento);
    }

    public void buscar(String idMateriaSeleccionada) {
        String url = "https://busc-int-upt-0f93f68ff11c.herokuapp.com/buscar.php?idMateria=" + idMateriaSeleccionada;
        lresultados.removeAllViews();
        JsonObjectRequest requerimento = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray empresasArray = jsonObject.getJSONArray("empresas");

                    if (empresasArray.length() == 0) {
                        Toast.makeText(getContext(), "No hay Empresas registradas ", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    for (int i = 0; i < empresasArray.length(); i++) {
                        JSONObject empresaObject = empresasArray.getJSONObject(i);

                        // Obtener el idEmpresa y guardarlo en una variable
                        int idEmpresa = empresaObject.getInt("idEmpresa");

                        // Crear un nuevo LinearLayout para cada resultado
                        LinearLayout resultadoLayout = new LinearLayout(requireContext());
                        LinearLayout.LayoutParams paramsResultadoLayout = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        paramsResultadoLayout.setMargins(20, 20, 20, 20);
                        resultadoLayout.setLayoutParams(paramsResultadoLayout);
                        resultadoLayout.setOrientation(LinearLayout.HORIZONTAL);
                        resultadoLayout.setBackgroundResource(R.drawable.borde);
                        int colorGrisAzuladoOscuro = ContextCompat.getColor(requireContext(), R.color.Grisazuladooscuro);

// Establecer el color de fondo del LinearLayout
                        resultadoLayout.setBackgroundColor(colorGrisAzuladoOscuro);

                        // TextView para el ícono
                        TextView iconoTextView = new TextView(requireContext());
                        LinearLayout.LayoutParams paramsIcono = new LinearLayout.LayoutParams(230, 230);
                        paramsIcono.setMargins(10, 10, 10, 10);
                        iconoTextView.setLayoutParams(paramsIcono);
                        iconoTextView.setBackgroundResource(R.drawable.iconoeduxt);

                        // LinearLayout para los detalles
                        LinearLayout detallesLayout = new LinearLayout(requireContext());
                        LinearLayout.LayoutParams paramsDetallesLayout = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        detallesLayout.setLayoutParams(paramsDetallesLayout);
                        detallesLayout.setOrientation(LinearLayout.VERTICAL);

                        // TextView para el resultado
                        TextView resultadoTextView = new TextView(requireContext());
                        LinearLayout.LayoutParams paramsResultadoTextView = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                350
                        );
                        paramsResultadoTextView.setMargins(10, 10, 10, 10);
                        resultadoTextView.setLayoutParams(paramsResultadoTextView);
                        resultadoTextView.setBackgroundResource(R.drawable.borde);
                        resultadoTextView.setTextColor(getResources().getColor(R.color.black));
                        resultadoTextView.setPadding(5, 5, 5, 5);
                        resultadoTextView.setText(empresaObject.getString("Nombre") + "\n" +
                                empresaObject.getString("Contacto") + "\n" +
                                empresaObject.getString("Descripcion"));

                        // Botón de Solicitar
                        Button solicitarButton = new Button(requireContext());
                        LinearLayout.LayoutParams paramsSolicitarButton = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                110
                        );
                        paramsSolicitarButton.setMargins(10, 10, 10, 10);
                        solicitarButton.setLayoutParams(paramsSolicitarButton);
                        solicitarButton.setText("Solicitar");
                        solicitarButton.setTextSize(17);
                        solicitarButton.setTextColor(getResources().getColor(R.color.black));
                        solicitarButton.setBackgroundResource(R.color.melon);
                        solicitarButton.setBackgroundResource(R.drawable.rounded_button_background);

                        solicitarButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(requireContext(), Fragment_pop_up.class);
                                // Recuperar el idUsuario del Bundle si es necesario
                                Bundle bundle = getArguments();
                                int idUsuario = 0;
                                if (bundle != null) {
                                    idUsuario = bundle.getInt("ID_USUARIO", 0); // 0 es el valor predeterminado en caso de que no se pueda obtener el idUsuario
                                }
                                // Pasar el idUsuario como extra
                                intent.putExtra("ID_USUARIO", idUsuario);
                                intent.putExtra("ID_EMPRESA", idEmpresa);
                                startActivity(intent);
                            }
                        });


                        // Botón de Ubicación
                        Button ubicacionButton = new Button(requireContext());
                        LinearLayout.LayoutParams paramsUbicacionButton = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                110
                        );
                        paramsUbicacionButton.setMargins(10, 10, 10, 10);
                        ubicacionButton.setLayoutParams(paramsUbicacionButton);
                        ubicacionButton.setText("Ubicacion");
                        ubicacionButton.setTextSize(17);
                        //ubicacionButton.setGravity(View.TEXT_ALIGNMENT_CENTER);
                        ubicacionButton.setTextColor(getResources().getColor(R.color.black));
                        ubicacionButton.setBackgroundResource(R.color.melon);
                        ubicacionButton.setBackgroundResource(R.drawable.rounded_button_background);

                        ubicacionButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    // Obtener las coordenadas de latitud y longitud del destino
                                    String destinoLatitud = empresaObject.getString("latitud");
                                    String destinoLongitud = empresaObject.getString("longitud");

                                    // Llamar a la función para obtener la dirección entre los dos puntos
                                    direcccionEntreDosPuntos(Unilatitud, Unilongitud, destinoLatitud, destinoLongitud);
                                } catch (JSONException e) {
                                    Toast.makeText(getContext(), "No se pudo obtener la ubicación de la empresa", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                        // Agregar vistas al LinearLayout de detalles
                        detallesLayout.addView(resultadoTextView);
                        detallesLayout.addView(solicitarButton);
                        detallesLayout.addView(ubicacionButton);

                        // Agregar vistas al LinearLayout del resultado
                        resultadoLayout.addView(iconoTextView);
                        resultadoLayout.addView(detallesLayout);

                        // Agregar el LinearLayout del resultado al contenedor principal
                        lresultados.addView(resultadoLayout);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "No hay respuesta a lo solicitado de la Base de Datos", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "No hay conexion a internet", Toast.LENGTH_SHORT).show();
            }
        });

        rq.add(requerimento);
    }
    public void direcccionEntreDosPuntos(String puntoUnolatitud, String puntoUnolongitud, String destinoLatitud, String destinoLongitud){
        Uri mapUri = Uri.parse("https://maps.google.com/maps?saddr="+puntoUnolatitud+","+puntoUnolongitud+"&daddr="+destinoLatitud+","+destinoLongitud);
        Intent intent = new Intent(Intent.ACTION_VIEW, mapUri);
        startActivity(intent);
    }
    private String obtenerIdMateriaSeleccionada() {
        // Obtener el ID de la materia seleccionada del Spinner spMateria
        String idMateriaSeleccionada = "";
        if (spMateria.getSelectedItem() != null) {
            idMateriaSeleccionada = idCarrerasList.get(spMateria.getSelectedItemPosition()); // Obtenemos el ID de la lista basado en la posición seleccionada en el Spinner
        }
        return idMateriaSeleccionada;
    }

}