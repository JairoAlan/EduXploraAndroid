package com.example.eduxplorap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    EditText tvusr, tvcontra;
    Button btnlogin;
    String baseUrl = "https://busc-int-upt-0f93f68ff11c.herokuapp.com/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            tvusr = findViewById(R.id.tvusr);
            tvcontra = findViewById(R.id.tvcontra);
            btnlogin = findViewById(R.id.btnlogin);

            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();
                }
            });

            return insets;
        });
    }

    private void login() {
        if (tvusr.getText().toString().isEmpty()) {
            Toast.makeText(this, "Ingrese el usuario", Toast.LENGTH_SHORT).show();
        } else if (tvcontra.getText().toString().isEmpty()) {
            Toast.makeText(this, "Ingrese la contraseña", Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Por favor espera...");
            progressDialog.show();

            String str_email = tvusr.getText().toString().trim();
            String str_password = tvcontra.getText().toString().trim();

            String url = baseUrl + "?Usuario=" + str_email + "&Password=" + str_password;

            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.has("0")) {
                            JSONObject userObject = jsonObject.getJSONObject("0");
                            String rol = userObject.getString("Rol");
                            int idUsuario = userObject.getInt("idUsuario"); // Obtener el idUsuario
                            Toast.makeText(Login.this, "Bienvenido " + rol, Toast.LENGTH_SHORT).show();
                            // Iniciar la actividad principal y pasar el rol y el idUsuario como extras
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            intent.putExtra("ROL_USUARIO", rol);
                            intent.putExtra("ID_USUARIO", idUsuario);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this, "Inicio de sesión fallido", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(Login.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(Login.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
            requestQueue.add(request);
        }
    }

    // Fin
}