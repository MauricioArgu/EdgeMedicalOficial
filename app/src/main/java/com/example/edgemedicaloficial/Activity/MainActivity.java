package com.example.edgemedicaloficial.Activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Transition;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;

import com.example.edgemedicaloficial.Adapter.EspecialidadAdapter;
import com.example.edgemedicaloficial.Dao.AppDataBase;
import com.example.edgemedicaloficial.Dao.registroDao;
import com.example.edgemedicaloficial.Dao.userDao;
import com.example.edgemedicaloficial.Model.mEspecialidades.EspecialidadResponse;
import com.example.edgemedicaloficial.Model.mEspecialidades.Especialidades;
import com.example.edgemedicaloficial.Model.mRegistro.User;
import com.example.edgemedicaloficial.R;
import com.example.edgemedicaloficial.Rest.ApiClient;
import com.example.edgemedicaloficial.Rest.ApiInterface;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, EspecialidadAdapter.ItemListener{

    private  static final long DURATION_TRANSITION = 1000;
    private Transition transition;

    static MainActivity mainActivity;

    RecyclerView especialidadesRecyclerView;
    ApiInterface apiInterface;
    ProgressDialog dialog;

    AppDataBase db;
    registroDao registrodb;
    userDao userdb;
    List<User> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Explode explode = new Explode();
        explode.setDuration(DURATION_TRANSITION);
        explode.setInterpolator(new DecelerateInterpolator());

        if (DoctorActivity.getInstance() != null)
        {
            DoctorActivity.getInstance().finish();
        }
        if (DocActivity.getInstance() != null)
        {
            DocActivity.getInstance().finish();
        }
        if (AddAppointmentActivity.getInstance() != null)
        {
            AddAppointmentActivity.getInstance().finish();
        }

        explode.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {

                if (LoginActivity.getInstance() != null){
                    LoginActivity.getInstance().finish();
                }
                if (SplashActivity.getInstance() != null){
                    SplashActivity.getInstance().finish();
                }
                if (ProfileActivity.getInstance() != null){
                    ProfileActivity.getInstance().finish();
                }
                if (HistoryActivity.getInstance() != null){
                    HistoryActivity.getInstance().finish();
                }
                if (ContactActivity.getInstance() != null){
                    ContactActivity.getInstance().finish();
                }
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });

        getWindow().setEnterTransition(explode);

        setContentView(R.layout.activity_main);

        especialidadesRecyclerView = findViewById(R.id.list_esp);
        especialidadesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getEspecialidades();



        mainActivity = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawer,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        navigationView.bringToFront();
    }

    private void getEspecialidades()
    {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
        apiInterface = new ApiClient().getApiClient().create(ApiInterface.class);

        Call<EspecialidadResponse> callT = apiInterface.getEspecialidades();

        callT.enqueue(new Callback<EspecialidadResponse>() {
            @Override
            public void onResponse(Call<EspecialidadResponse> call, Response<EspecialidadResponse> response) {
                final EspecialidadResponse Res = response.body();

                if (Res.getErrorCode() == 0)
                {
                    EspecialidadAdapter adapter = new EspecialidadAdapter(MainActivity.this,Res.getMsg(),MainActivity.this);
                    especialidadesRecyclerView.setAdapter(adapter);
                }

                if (dialog.isShowing())
                {
                    dialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<EspecialidadResponse> call, Throwable t)
            {
                if (!call.isCanceled())
                {
                    Log.e("Error",""+t);
                    if (dialog.isShowing())
                    {
                        dialog.dismiss();
                    }
                }

            }
        });
    }

    public static MainActivity getInstance(){
        return mainActivity;
    }

    private void startLoginActivity() {
        transition.setDuration(DURATION_TRANSITION);
        transition.setInterpolator(new DecelerateInterpolator());

        getWindow().setExitTransition(transition);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());

    }

    private void startProfileActivity() {
        transition.setDuration(DURATION_TRANSITION);
        transition.setInterpolator(new DecelerateInterpolator());

        getWindow().setExitTransition(transition);

        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());

    }

    private void startHistoryActivity() {
        transition.setDuration(DURATION_TRANSITION);
        transition.setInterpolator(new DecelerateInterpolator());

        getWindow().setExitTransition(transition);

        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());

    }

    private void startContactActivity() {
        transition.setDuration(DURATION_TRANSITION);
        transition.setInterpolator(new DecelerateInterpolator());

        getWindow().setExitTransition(transition);

        Intent intent = new Intent(this, ContactActivity.class);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());

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
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //here is the main place where we need to work on.
        int id = item.getItemId();
        switch (id) {

            case R.id.nav_home:

                break;
            case R.id.nav_profile:
                transition = new Explode();
                startProfileActivity();
                break;
            case R.id.nav_history:
                transition = new Explode();
                startHistoryActivity();
                break;
            case R.id.nav_contact:
                transition = new Explode();
                startContactActivity();
                break;
            case R.id.nav_logout:
                db = Room.databaseBuilder(this,
                        AppDataBase.class, "db_edge")
                        .fallbackToDestructiveMigration().allowMainThreadQueries().build();

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Edge Medical");
                alertDialog.setMessage("Do you want to Logout?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Exit",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                userdb =db.getUser();
                                list = userdb.getUser();

                                userdb.deleteAll();
                                transition = new Explode();
                                if (LoginActivity.getInstance() != null)
                                {
                                    LoginActivity.getInstance().finish();
                                }
                                startLoginActivity();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(Especialidades item)
    {
        Intent intent = new Intent(this, DoctorActivity.class);
        intent.putExtra("idEsp",""+item.getId());
        intent.putExtra("nombre", ""+item.getNombre());
        startActivity(intent);

    }
}
