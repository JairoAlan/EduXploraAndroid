/**
 * Esta actividad principal representa la pantalla principal de la aplicación.
 * Contiene un DrawerLayout para la navegación lateral, un MaterialToolbar en la parte superior,
 * un BottomNavigationView en la parte inferior y un FrameLayout para cargar los fragmentos.
 * Los fragmentos se cargan según la opción seleccionada en el BottomNavigationView o en el NavigationView.
 * Además, se utilizan varios botones y vistas para interactuar con la actividad.
 */
package com.example.eduxplorap;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.eduxplorap.databinding.ActivityMainBinding;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    // Declaración de variables y elementos de la interfaz de usuario
    Button btnresena, btnresena2, btnresena3;
    DrawerLayout drawerLayout;
    MaterialToolbar materialToolbar;
    FrameLayout frameLayout;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    ActivityMainBinding binding; // Binding para la vista inflada
    private int idUsuario; // ID del usuario actual

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflar la vista y establecerla como contenido de la actividad
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Reemplazar el fragmento por defecto con el fragmento Home
        replaceFragment(new Home_Fragment());

        // Inicializar botones y vistas
        btnresena = findViewById(R.id.btnresena1);
        btnresena2 = findViewById(R.id.btnresena2);
        btnresena3 = findViewById(R.id.btnresena3);
        drawerLayout = findViewById(R.id.drawerLayout);
        materialToolbar = findViewById(R.id.materialToolBar);
        navigationView = findViewById(R.id.navigationView);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Configurar el ActionBarDrawerToggle para el DrawerLayout
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, materialToolbar,
                R.string.drawer_close, R.string.drawer_open);
        drawerLayout.addDrawerListener(toogle);

        // Obtener datos del Intent
        Intent intent = getIntent();
        String usuarioRol = intent.getStringExtra("ROL_USUARIO");
        idUsuario = intent.getIntExtra("ID_USUARIO", 0);

        // Establecer el elemento seleccionado en el BottomNavigationView al inicio
        binding.bottomNavigationView.setSelectedItemId(R.id.ihome);
        // Configurar el Listener para los elementos seleccionados en el BottomNavigationView
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            // Manejar las opciones seleccionadas
            if (item.getItemId() == R.id.ihome) {
                replaceFragment(new Home_Fragment());
            } else if (item.getItemId() == R.id.icheck) {
                // Lógica según el rol del usuario para la opción "icheck"
                if ("docente".equals(usuarioRol)) {
                    // Crear y reemplazar el fragmento con los argumentos adecuados
                    Solicitud_Fragment fragment = new Solicitud_Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("ID_USUARIO", idUsuario);
                    fragment.setArguments(bundle);
                    replaceFragment(fragment);
                } else if ("coordinador".equals(usuarioRol)) {
                    replaceFragment(new SolicCoor_Fragment());
                } else if ("vinculador".equals(usuarioRol)) {
                    replaceFragment(new Solic_Vin_Fragment());
                } else {
                    Toast.makeText(this, "No tienes ningún rol, contacta con el administrador", Toast.LENGTH_SHORT).show();
                }
            } else if (item.getItemId() == R.id.isearch) {
                // Lógica según el rol del usuario para la opción "isearch"
                if ("docente".equals(usuarioRol)) {
                    Buscar_Fragment fragment = new Buscar_Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("ROL_USUARIO", idUsuario);
                    bundle.putInt("ID_USUARIO", idUsuario);
                    fragment.setArguments(bundle);
                    replaceFragment(fragment);
                } else if ("coordinador".equals(usuarioRol)) {
                    replaceFragment(new buscar_Coor());
                } else if ("vinculador".equals(usuarioRol)) {
                    replaceFragment(new buscar_Coor());
                } else {
                    Toast.makeText(this, "No tienes ningún rol, contacta con el administrador", Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        });

        // Configurar el Listener para los elementos del MaterialToolbar
        binding.materialToolBar.setOnMenuItemClickListener(item -> {
            // Manejar las opciones del MaterialToolbar
            if (item.getItemId() == R.id.iNoti) {
                replaceFragment(new reportes_Coor());
            } else if (item.getItemId() == R.id.iCuenta) {
                replaceFragment(new localizaciongps());
            }
            return true;
        });

        // Configurar el Listener para los elementos seleccionados en el NavigationView
        binding.navigationView.setNavigationItemSelectedListener(item -> {
            // Manejar las opciones seleccionadas en el NavigationView
            if (item.getItemId() == R.id.iQueEs) {
                replaceFragment(new Menu_QueEs_Fragment());
            } else if (item.getItemId() == R.id.iEva) {
                replaceFragment(new Menu_Evaluar_Fragment());
            } else if (item.getItemId() == R.id.iFav) {
                replaceFragment(new Menu_Favoritos_Fragment());
            } else if (item.getItemId() == R.id.iPoli) {
                replaceFragment(new Menu_Politica_Fragments());
            } else if (item.getItemId() == R.id.iComen) {
                Menu_Comentarios_Fragment fragment = new Menu_Comentarios_Fragment();
                Bundle bundle = new Bundle();
                bundle.putInt("ID_USUARIO", idUsuario);
                fragment.setArguments(bundle);
                replaceFragment(fragment);
            } else if (item.getItemId() == R.id.iConfig) {
                replaceFragment(new Menu_Config_Fragment());
            }
            return true;
        });
    }

    /**
     * Método para reemplazar fragmentos en el contenedor FrameLayout.
     * @param fragment El fragmento que se va a mostrar.
     */
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
        // Cerrar el menú desplegable después de seleccionar una opción
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
}
