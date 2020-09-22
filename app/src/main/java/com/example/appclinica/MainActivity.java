package com.example.appclinica;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appclinica.ui.centers.CentersFragment;
import com.example.appclinica.ui.home.HomeFragment;
import com.example.appclinica.ui.home.LoginFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavView;

    private boolean session;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            initToolbar();
            initFab();
            initNavigation();

            //session = getSession();
            //showBottomNavigation(session);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (session) getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    private void showBothNavigation(boolean isShowable) {
        navigationView.setVisibility(isShowable? View.VISIBLE : View.GONE);
        bottomNavView.setVisibility(isShowable? View.VISIBLE : View.GONE);
    }

    private void showBottomNavigation(boolean isShowable) {
        bottomNavView.setVisibility(isShowable? View.VISIBLE : View.GONE);
    }

    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    private void initFab() {

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Gracias por contactarse, enseguida nos comunicamos con usted.", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:contacto@portusalud.com?subject=Contacto");
                intent.setData(data);
                startActivity(intent);
            }
        });

    }

    private void initNavigation() {

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        bottomNavView = findViewById(R.id.bottom_nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_login,
                R.id.nav_home, R.id.nav_centers, R.id.nav_contact,
                R.id.nav_share, R.id.nav_send, R.id.nav_myinfo, R.id.nav_session,
                R.id.bottom_home, R.id.bottom_registry, R.id.bottom_report)
                .setDrawerLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(bottomNavView, navController);

        //closeSession();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

    public boolean getSession( ){
        SharedPreferences prefs = getSharedPreferences("PREFERENCIAS", Context.MODE_PRIVATE);
        return prefs.getBoolean ("SESSION", false);
    }

    public void closeSession( ){
        SharedPreferences prefs = getSharedPreferences("PREFERENCIAS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("SESSION", false);
        editor.commit();
    }

}