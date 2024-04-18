/**
 * Esta actividad representa la pantalla de inicio de sesión de la aplicación.
 * Permite al usuario ingresar su nombre de usuario y contraseña para acceder a la aplicación.
 * También se realiza una solicitud HTTP para autenticar al usuario utilizando Volley.
 */
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

    // Declaración de variables de la interfaz de usuario y URL base para la solicitud de inicio de sesión
    EditText tvusr, tvcontra;
    Button btnlogin;
    String baseUrl = "https://busc-int-upt-0f93f68ff11c.herokuapp.com/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Habilitar el ajuste de bordes para la actividad
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Configurar el Listener para aplicar los insets de los system bars y configurar el inicio de sesión
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            // Inicializar vistas
            tvusr = findViewById(R.id.tvusr);
            tvcontra = findViewById(R.id.tvcontra);
            btnlogin = findViewById(R.id.btnlogin);

            // Configurar el Listener para el botón de inicio de sesión
            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();
                }
            });

            return insets;
        });
    }

    /**
     * Método para realizar la solicitud de inicio de sesión al servidor.
     * Realiza la validación de los campos de entrada y muestra un ProgressDialog durante la solicitud.
     * Muestra mensajes de error o éxito según la respuesta del servidor.
     */
    private void login() {
        // Validar que se ingresen el usuario y la contraseña
        if (tvusr.getText().toString().isEmpty()) {
            Toast.makeText(this, "Ingrese el usuario", Toast.LENGTH_SHORT).show();
        } else if (tvcontra.getText().toString().isEmpty()) {
            Toast.makeText(this, "Ingrese la contraseña", Toast.LENGTH_SHORT).show();
        } else {
            // Mostrar ProgressDialog durante la solicitud HTTP
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Por favor espera...");
            progressDialog.show();

            // Obtener el usuario y la contraseña ingresados
            String str_email = tvusr.getText().toString().trim();
            String str_password = tvcontra.getText().toString().trim();

            // Construir la URL para la solicitud HTTP
            String url = baseUrl + "?Usuario=" + str_email + "&Password=" + str_password;

            // Realizar la solicitud HTTP utilizando Volley
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        // Analizar la respuesta JSON del servidor
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.has("0")) {
                            // Si la respuesta contiene un objeto "0", el inicio de sesión fue exitoso
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
                            // Si no se recibe un objeto "0", el inicio de sesión falló
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

            // Agregar la solicitud a la cola de solicitudes de Volley
            RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
            requestQueue.add(request);
        }
    }
}
