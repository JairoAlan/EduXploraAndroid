package com.example.eduxplorap;

import static com.itextpdf.text.pdf.PdfName.FONT;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.Manifest;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link reportes_Coor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class reportes_Coor extends Fragment {

    // Define el código de solicitud de permiso como una constante
    private static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 1;

    Button btnRepo_Gen;
    List<String[]> data = new ArrayList<>();
    RequestQueue rq;

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
        rq = Volley.newRequestQueue(requireContext());
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
                    abrirPDF();
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

            // Crear una tabla con 5 columnas
            PdfPTable table = new PdfPTable(5);

            // Agregar los encabezados de la tabla
            PdfPCell cell1 = new PdfPCell(new Phrase("Empresa"));
            PdfPCell cell2 = new PdfPCell(new Phrase("Grupo"));
            PdfPCell cell3 = new PdfPCell(new Phrase("Usuario"));
            PdfPCell cell4 = new PdfPCell(new Phrase("Carrera"));
            PdfPCell cell5 = new PdfPCell(new Phrase("Estado"));

            // Agregar los encabezados a la tabla
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);

            // Obtener los datos del servidor
            String url = "https://busc-int-upt-0f93f68ff11c.herokuapp.com/obtenerUsuarios.php";
            JsonArrayRequest requerimento = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    for(int i=0;i<jsonArray.length();i++){
                        try {
                            JSONObject objeto = new JSONObject(jsonArray.get(i).toString());
                            // Agregar los datos a la tabla
                            table.addCell(objeto.getString("nombreEmpresa"));
                            table.addCell(objeto.getString("grupo"));
                            table.addCell(objeto.getString("nombreUsuario"));
                            table.addCell(objeto.getString("carrera"));
                            table.addCell(objeto.getString("estadoActual"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    // Agregar la tabla al documento
                    try {
                        doc.add(table);
                    } catch (DocumentException e) {

                        throw new RuntimeException(e);
                    }

                    // Cerrar el documento y el flujo de salida

                    try {
                        doc.close();
                        fos.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addTitlePage(Document doc) throws DocumentException {

        Paragraph preface = new Paragraph();
        // Agrega un espacio
        addEmptyLine(preface, 1);
        // Agrega un título
        preface.add(new Paragraph("Reporte General de Visitas Guiadas"));
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
                Toast.makeText(getActivity(), "Se Rechazo el Acceso", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void abrirPDF() {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/Reporte.pdf";
        File file = new File(path);

        // Obtener el URI del archivo utilizando FileProvider
        Uri fileUri = FileProvider.getUriForFile(requireContext(), requireContext().getPackageName() + ".provider", file);

        // Crear un intent para abrir el PDF con una aplicación externa
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(fileUri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // Agregar permiso de lectura al intent
        startActivity(intent);
    }




// Fin
}