package com.example.gestionusuarios;

import android.content.Intent;
import android.os.Bundle;

import com.example.gestionusuarios.usuarios.UsuariosFragment;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.gestionusuarios.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);


        goToFragment(new UsuariosFragment(), false);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public void goToFragment(Fragment fragment, boolean allowBack){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (allowBack)
            ft.addToBackStack(null);

        ft.replace(R.id.fragmentContainer, fragment)
                .commit();
    }

}