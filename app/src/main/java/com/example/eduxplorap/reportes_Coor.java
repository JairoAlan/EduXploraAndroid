package com.example.eduxplorap;

import static com.itextpdf.text.pdf.PdfName.FONT;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import android.Manifest;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link reportes_Coor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class reportes_Coor extends Fragment {

    // Define el código de solicitud de permiso como una constante
    private static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 1;

    Button btnRepo_Gen;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public reportes_Coor() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment reportes_Coor.
     */
    // TODO: Rename and change types and number of parameters
    public static reportes_Coor newInstance(String param1, String param2) {
        reportes_Coor fragment = new reportes_Coor();
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
        View view = inflater.inflate(R.layout.fragment_reportes__coor, container, false);

        btnRepo_Gen = view.findViewById(R.id.btnRepo_Gen);

        btnRepo_Gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verificar si ya se tienen los permisos de escritura en el almacenamiento externo
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // Si no se tienen los permisos, solicitarlos al usuario
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
                } else {
                    // Si ya se tienen los permisos, proceder con la creación del PDF
                    createPDF();

                }
            }
        });







        return view;
    }

    public void createPDF() {
        Document doc = new Document();
        try {
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/Reporte.pdf";
            File file = new File(path);
            FileOutputStream fos = new FileOutputStream(file);
            PdfWriter.getInstance(doc, fos);
            doc.open();
            addTitlePage(doc);
            doc.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addTitlePage(Document doc) throws DocumentException {

        Paragraph preface = new Paragraph();
        // Agrega un espacio
        addEmptyLine(preface, 1);
        // Agrega un título
        preface.add(new Paragraph("Reporte"));
        addEmptyLine(preface, 1);
        // Agrega una fecha
        preface.add(new Paragraph(new Date().toString()));
        addEmptyLine(preface, 2);
        doc.add(preface);
    }

    public void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE) {
            // Verificar si el usuario otorgó los permisos
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permisos otorgados, proceder con la creación del PDF
                createPDF();
            } else {
                // El usuario rechazó los permisos, manejar esta situación según sea necesario
            }
        }
    }




// Fin
}