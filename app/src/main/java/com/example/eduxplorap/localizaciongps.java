package com.example.eduxplorap;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link localizaciongps#newInstance} factory method to
 * create an instance of this fragment.
 */
public class localizaciongps extends Fragment {

    MaterialButton BtnpinLocacion,BtnDireccionUno,BtnDireccionDos;

    String latitud,longitud,latitudUno, longitudUno;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public localizaciongps() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment localizaciongps.
     */
    // TODO: Rename and change types and number of parameters
    public static localizaciongps newInstance(String param1, String param2) {
        localizaciongps fragment = new localizaciongps();
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

        View view = inflater.inflate(R.layout.fragment_localizaciongps, container, false);
        BtnpinLocacion = view.findViewById(R.id.BtnpinLocacion);
//        BtnDireccionUno = view.findViewById(R.id.BtnDireccionUno);
//        BtnDireccionDos = view.findViewById(R.id.BtnDireccionDos);

        BtnpinLocacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latitud = "20.1352722";
                longitud = "-98.383043";
                pinLocationMap(latitud,longitud);
            }
        });

//        BtnDireccionUno.setOnClickListener(v-> {
//            latitudUno = "19.3906594";
//            longitudUno = "-99.3084253";
//            direccionActual(latitudUno,longitudUno);
//        });
//
//        BtnDireccionDos.setOnClickListener(v-> {
//            latitud = "20.1352722";
//            longitud = "-98.383043";
//            latitudUno = "19.3906594";
//            longitudUno = "-99.3084253";
//            direcccionEntreDosPuntos(latitud,longitud,latitudUno,longitudUno);
//        });


        return view;
    }

    public void pinLocationMap(String latitud, String longitud){

        Uri mapUri = Uri.parse("https://maps.google.com/maps/search/"+latitud+","+longitud);
        Intent intent = new Intent(Intent.ACTION_VIEW,mapUri);
        startActivity(intent);

    }

    public void direccionActual(String destinoLatitud, String destinoLongitud){
        Uri mapUri = Uri.parse("https://maps.google.com/maps?daddr="+destinoLatitud+","+destinoLongitud);
        Intent intent = new Intent(Intent.ACTION_VIEW,mapUri);
        startActivity(intent);
    }

    public void direcccionEntreDosPuntos(String puntoUnolatitud, String puntoUnolongitud, String destinoLatitud, String destinoLongitud){
        Uri mapUri = Uri.parse("https://maps.google.com/maps?saddr="+puntoUnolatitud+","+puntoUnolongitud+"&daddr="+destinoLatitud+","+destinoLongitud);
        Intent intent = new Intent(Intent.ACTION_VIEW, mapUri);
        startActivity(intent);
    }
    // Funcion para calcular distancia entre dos puntos
    public double calcularDistancia(double latitud1, double longitud1, double latitud2, double longitud2) {
        final int RADIO_TIERRA = 6371; // Radio medio de la Tierra en kilómetros

        double dLatitud = Math.toRadians(latitud2 - latitud1);
        double dLongitud = Math.toRadians(longitud2 - longitud1);

        double a = Math.sin(dLatitud / 2) * Math.sin(dLatitud / 2) +
                Math.cos(Math.toRadians(latitud1)) * Math.cos(Math.toRadians(latitud2)) *
                        Math.sin(dLongitud / 2) * Math.sin(dLongitud / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RADIO_TIERRA * c;
    }


//Fin
}