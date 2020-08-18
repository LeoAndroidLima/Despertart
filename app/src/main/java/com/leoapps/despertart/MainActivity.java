package com.leoapps.despertart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.infideap.drawerbehavior.Advance3DDrawerLayout;
import com.irozon.sneaker.Sneaker;
import com.leoapps.despertart.activity.LoginActivity;
import com.leoapps.despertart.fragment.InicioFragment;
import com.leoapps.despertart.fragment.MinhaContaFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Advance3DDrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);



        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        drawer.setViewScale(GravityCompat.START, 0.96f);
        drawer.setRadius(GravityCompat.START, 20);
        drawer.setViewElevation(GravityCompat.START, 8);
        drawer.setViewRotation(GravityCompat.START, 15);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        InicioFragment inicioFragment = new InicioFragment();
        fragmentTransaction.replace(R.id.frameContainer, inicioFragment);
        fragmentTransaction.commit();

        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user == null){

                    navigationView.getMenu().getItem(1).setVisible(true);
                    navigationView.getMenu().getItem(2).setVisible(false);
                    navigationView.getMenu().getItem(3).setVisible(false);
                    navigationView.getMenu().getItem(4).setVisible(false);

                }else {

                    navigationView.getMenu().getItem(1).setVisible(false);
                    navigationView.getMenu().getItem(2).setVisible(true);
                    navigationView.getMenu().getItem(3).setVisible(true);
                    navigationView.getMenu().getItem(4).setVisible(true);

                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        int id = item.getItemId();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_home){

            InicioFragment inicioFragment = new InicioFragment();
            fragmentTransaction.replace(R.id.frameContainer, inicioFragment);
            fragmentTransaction.commit();

        }else if(id == R.id.nav_entrar){

            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(getBaseContext(), android.R.anim.fade_in, android.R.anim.fade_out);
            startActivity(intent, options.toBundle());

        }else if (id == R.id.nav_conta){

            MinhaContaFragment minhaContaFragment = new MinhaContaFragment();
            fragmentTransaction.replace(R.id.frameContainer, minhaContaFragment);
            fragmentTransaction.commit();


        }else if (id == R.id.nav_compras){


        }else if (id == R.id.nav_deslogar){

            FirebaseAuth.getInstance().signOut();
            Sneaker.with(this) // Activity, Fragment or ViewGroup
                    .setTitle("Sucesso")
                    .setMessage("Você foi desconectado")
                    .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                    .sneakSuccess();

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

