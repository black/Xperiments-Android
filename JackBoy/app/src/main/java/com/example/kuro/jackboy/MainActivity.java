package com.example.kuro.jackboy;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.kuro.jackboy.AirPollution.AirPollutionFragment;
import com.example.kuro.jackboy.Breathlizer.BreathlizerFragment;
import com.example.kuro.jackboy.GasSensor.GasSensorFragment;
import com.example.kuro.jackboy.WaterPollution.WaterPollutionFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        GridView gridview = (GridView)findViewById(R.id.gridView);
        gridview.setAdapter(new GridAdapter(this));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (id == R.id.breathlizer) {
            setTitle("Breathlizer");
            BreathlizerFragment fragment = new BreathlizerFragment();
            ft.replace(R.id.content, fragment);
            ft.commit();
        } else if (id == R.id.airpollution) {
            setTitle("Air Pollution");
            AirPollutionFragment fragment = new AirPollutionFragment();
            ft.replace(R.id.content, fragment);
            ft.commit();
        } else if (id == R.id.waterpollution) {
            setTitle("Water Pollution");
            WaterPollutionFragment fragment = new WaterPollutionFragment();
            ft.replace(R.id.content, fragment);
            ft.commit();

        } else if (id == R.id.gassensor) {
            setTitle("Gas Sensor");
            GasSensorFragment fragment = new GasSensorFragment();
            ft.replace(R.id.content, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void breathlizerTile(View view) {
        final FragmentTransaction frag = getSupportFragmentManager().beginTransaction();
        setTitle("Breathlizer");
        BreathlizerFragment fragment = new BreathlizerFragment();
        frag.replace(R.id.content, fragment);
        frag.commit();
    }

    public void airPollutionTile(View view) {
        final FragmentTransaction frag = getSupportFragmentManager().beginTransaction();
        setTitle("Air Pollution");
        AirPollutionFragment fragment = new AirPollutionFragment();
        frag.replace(R.id.content, fragment);
        frag.commit();
    }

    public void gasSensorTile(View view) {
    }

    public void waterPollutionTile(View view) {
    }

}
