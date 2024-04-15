package com.example.eduxplorap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_pop_up#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_pop_up extends Fragment {

    EditText etGrupo,etCuatri,etAlum,etFecha;
    Button btnEnviarSoli;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_pop_up() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_pop_up.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_pop_up newInstance(String param1, String param2) {
        Fragment_pop_up fragment = new Fragment_pop_up();
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
        View view = inflater.inflate(R.layout.fragment_pop_up, container, false);

        etGrupo = view.findViewById(R.id.etGrupo);
        etCuatri = view.findViewById(R.id.etCuatri);
        etAlum = view.findViewById(R.id.etAlum);
        etFecha = view.findViewById(R.id.etFecha);

        btnEnviarSoli = view.findViewById(R.id.btnEnviarSoli);

        btnEnviarSoli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etGrupo.getText().equals(null) && etCuatri.getText().equals(null) && etAlum.getText().equals(null) && etFecha.getText().equals(null)){
                    Toast.makeText(getContext(), " Todos los campos son requeridos ", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), " Enviado ", Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(getContext(), " Enviado ", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}