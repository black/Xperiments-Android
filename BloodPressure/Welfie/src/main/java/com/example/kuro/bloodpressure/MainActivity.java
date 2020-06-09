package com.example.kuro.bloodpressure;

import android.annotation.TargetApi;
import android.app.*;
import android.app.AlertDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.*;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.*;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.kuro.bloodpressure.DataBase.DateBaseHelper;
import com.example.kuro.bloodpressure.DataBase.ListDataAdapter;
import com.example.kuro.bloodpressure.Fragments.MeasureFragment;
import com.example.kuro.bloodpressure.Fragments.PeopleFragment;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public Toolbar mtoolbar;
    public TabLayout mtabLayout;
    public ViewPager mviewPager;
    public ViewPagerAdapter viewPagerAdapter;
    ListDataAdapter listDataAdapter;

    Context mcontext = this;
    DateBaseHelper mdateBaseHelper;

    EditText name, weight, height, age;
    RadioGroup mradioGroup;
    RadioButton male, female;

    String gender_value;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        android.support.v7.app.ActionBarDrawerToggle toggle = new android.support.v7.app.ActionBarDrawerToggle(
                this, drawer, mtoolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*----------------------------------------------------------------*/

        mtabLayout = (TabLayout) findViewById(R.id.tablayout);
        mviewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new MeasureFragment(), "MEASURE");
        viewPagerAdapter.addFragments(new PeopleFragment(), "PEOPLE");
        mviewPager.setAdapter(viewPagerAdapter);
        mtabLayout.setupWithViewPager(mviewPager);
        mdateBaseHelper = new DateBaseHelper(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        /*-------------------*/
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
              Log.e("TAG","seconds remaining: " + millisUntilFinished / 1000);
            }
            public void onFinish() {
                createNotification();
            }
        }.start();

        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.cancel(1);
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

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (id == 1) {
//            setTitle("Breathlizer");
//            BreathlizerFragment fragment = new BreathlizerFragment();
//            ft.replace(R.id.content, fragment);
//            ft.commit();
        } else if (id == 2) {
//            setTitle("Air Pollution");
//            AirPollutionFragment fragment = new AirPollutionFragment();
//            ft.replace(R.id.content, fragment);
//            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void createNotification(){
        Intent actionIntent = new Intent(MainActivity.this,MainActivity.class);
        PendingIntent actionpendingIntent = PendingIntent.getActivity(this,222,actionIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder nbuilder = new NotificationCompat.Builder(this);
        nbuilder.setContentTitle("Welfie");
        nbuilder.setContentText("Hey! It's time to take your BP");
        nbuilder.setSmallIcon(R.drawable.heart);
        nbuilder.setDefaults(MainActivity.DEFAULT_KEYS_DIALER);
        nbuilder.setVibrate(new long[]{100,500,100,500});
        nbuilder.setLights(Color.GREEN,500,500);
        nbuilder.addAction(R.drawable.ic_stat_name,"OPEN",actionpendingIntent);

        android.app.Notification notification = nbuilder.build();
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1,notification);

        PowerManager.WakeLock screenOn = ((PowerManager)getSystemService(POWER_SERVICE)).newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "example");
        screenOn.acquire();
//        screenOn.release();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void RadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.male:
                if (checked) gender_value = "male";
            case R.id.female:
                if (checked) gender_value = "female";
        }

    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_person:
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                // Inflate using dialog themed context.
                final Context context = builder.getContext();
                final LayoutInflater inflater = LayoutInflater.from(context);
                final View view = inflater.inflate(R.layout.dialog_box_form, null, false);

                AlertDialog.Builder builder1 = builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        name = (EditText) view.findViewById(R.id.person_name);
                        mradioGroup = (RadioGroup) view.findViewById(R.id.gender_selection);
                        male = (RadioButton) view.findViewById(R.id.male);
                        female = (RadioButton) view.findViewById(R.id.female);
                        weight = (EditText) view.findViewById(R.id.person_weight);
                        height = (EditText) view.findViewById(R.id.person_height);
                        age = (EditText) view.findViewById(R.id.person_age);


                        String user_name = name.getText().toString();
                        String user_gender = gender_value;
                        String user_weight = weight.getText().toString();
                        String user_height = height.getText().toString();
                        String user_age = age.getText().toString();

                        mdateBaseHelper = new DateBaseHelper(mcontext);
                        long result = mdateBaseHelper.addUserInformation(user_name, user_gender, user_weight, user_height, user_age);
                        PeopleFragment Pfrg = (PeopleFragment)viewPagerAdapter.getItem(1);
                        Pfrg.updatePersonList();
                        if (result > 0) {
                            Toast.makeText(getBaseContext(), "DATA SAVED", Toast.LENGTH_LONG).show();
                        } else
                            Toast.makeText(getBaseContext(), "ERROR 404", Toast.LENGTH_LONG).show();

                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setView(view);
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.kuro.bloodpressure/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.kuro.bloodpressure/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}