package com.example.eduxplorap;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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


    Button btnresena,btnresena2,btnresena3;
    DrawerLayout drawerLayout;
    MaterialToolbar materialToolbar;

    FrameLayout frameLayout;

    NavigationView navigationView;

    BottomNavigationView bottomNavigationView;

    ActivityMainBinding binding;
    private int idUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new Home_Fragment());

        btnresena = (Button) findViewById(R.id.btnresena1);
        btnresena2 = (Button) findViewById(R.id.btnresena2);
        btnresena3 = (Button) findViewById(R.id.btnresena3);
        drawerLayout = findViewById(R.id.drawerLayout);
        materialToolbar = findViewById(R.id.materialToolBar);
        navigationView = findViewById(R.id.navigationView);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout,materialToolbar,
                R.string.drawer_close,R.string.drawer_open);

        drawerLayout.addDrawerListener(toogle);
        //Intent intent = getIntent();
        //String rolUsuario = intent.getStringExtra("ROL_USUARIO");
        //idUsuario = intent.getIntExtra("ID_USUARIO", 0);

        Intent intent = getIntent();
        String usuarioRol = intent.getStringExtra("ROL_USUARIO");
        idUsuario = intent.getIntExtra("ID_USUARIO", 0);
        binding.bottomNavigationView.setOnItemSelectedListener(item ->{

            if(item.getItemId() == R.id.ihome){
                replaceFragment(new Home_Fragment());
            }else if(item.getItemId() == R.id.icheck){
                if ("docente".equals(usuarioRol)) {
                    replaceFragment(new Solicitud_Fragment());
                } else if ("coordinador".equals(usuarioRol)) {
                    replaceFragment(new SolicCoor_Fragment());
                } else if ("vinculador".equals(usuarioRol)) {
                    replaceFragment(new Solic_Vin_Fragment());
                } else {
                    Toast.makeText(this, "No Tienes ningun Rol, contacta con el Admin", Toast.LENGTH_SHORT).show();
                }
                // replaceFragment(new SolicCoor_Fragment());
            }else if(item.getItemId() == R.id.isearch){
                replaceFragment(new Buscar_Fragment());
            }

            return true;
        });

        binding.materialToolBar.setOnMenuItemClickListener(item ->{
            if(item.getItemId() == R.id.iNoti){
                replaceFragment(new reportes_Coor());
            }else if(item.getItemId() == R.id.iCuenta){
                replaceFragment(new localizaciongps());
            }

            return true;
        });

        binding.navigationView.setNavigationItemSelectedListener(item -> {
            if(item.getItemId() == R.id.iQueEs){
                replaceFragment(new Menu_QueEs_Fragment());
            }else if(item.getItemId() == R.id.iEva){
                replaceFragment(new Menu_Evaluar_Fragment());
            }
            else if(item.getItemId() == R.id.iFav)
            {
                replaceFragment(new Menu_Favoritos_Fragment());
            }
            else if(item.getItemId() == R.id.iPoli)
            {
                replaceFragment(new Menu_Politica_Fragments());
            }
            else if(item.getItemId() == R.id.iComen)
            {
                Menu_Comentarios_Fragment fragment = new Menu_Comentarios_Fragment();

                // Crear un bundle para pasar el idUsuario al fragmento
                Bundle bundle = new Bundle();
                bundle.putInt("ID_USUARIO", idUsuario); // Pasar el idUsuario al bundle
                fragment.setArguments(bundle);

                replaceFragment(fragment);
            }
            else if(item.getItemId() == R.id.iConfig)
            {
                replaceFragment(new Menu_Config_Fragment());
            }
            return true;
        });

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}