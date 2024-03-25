package com.example.eduxplorap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link buscar_Coor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class buscar_Coor extends Fragment {

    TextView tvBus,tvListo;
    RadioGroup rgIdCar, rgNom;
    RadioButton rbCar,rbMateria;

    Button btnBuscar;

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
        rgIdCar = view.findViewById(R.id.rgIdCar);
        rgNom = view.findViewById(R.id.rgNom);
        rbCar = view.findViewById(R.id.rbCar);
        rbMateria = view.findViewById(R.id.rbMateria);
        btnBuscar = view.findViewById(R.id.btnBuscar);

        return view;
    }
}