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
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edgemedicaloficial.Adapter.AppointmentAdapter;
import com.example.edgemedicaloficial.Adapter.DoctorAdapter;
import com.example.edgemedicaloficial.Adapter.HistoryViewAdapter;
import com.example.edgemedicaloficial.Dao.AppDataBase;
import com.example.edgemedicaloficial.Dao.userDao;
import com.example.edgemedicaloficial.Model.mAppointment.Appointment;
import com.example.edgemedicaloficial.Model.mAppointment.AppointmentResponse;
import com.example.edgemedicaloficial.Model.mDoctor.DocByEspecialidadResponse;
import com.example.edgemedicaloficial.Model.mRegistro.User;
import com.example.edgemedicaloficial.R;
import com.example.edgemedicaloficial.Rest.ApiClient;
import com.example.edgemedicaloficial.Rest.ApiInterface;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,AppointmentAdapter.ItemListener {

    RecyclerView pendingRecyclerView;
    RecyclerView doneRecyclerView;
    RecyclerView processRecyclerView;

    AppDataBase db;

    userDao userdb;

    List<User> list;

    TextView txtProcess;
    TextView txtPending;
    TextView txtDone;

    ApiInterface apiInterface;
    ProgressDialog dialog;

    private static final long DURATION_TRANSITION = 100;
    private Transition transition;

    static HistoryActivity historyActivity;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Explode explode = new Explode();
        explode.setDuration(LoginActivity.DURATION_TRANSITION);
        explode.setInterpolator(new DecelerateInterpolator());

        explode.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                if (MainActivity.getInstance() != null){
                    MainActivity.getInstance().finish();
                }
                if (ContactActivity.getInstance() != null){
                    ContactActivity.getInstance().finish();
                }
                if (ProfileActivity.getInstance() != null){
                    ProfileActivity.getInstance().finish();
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

        setContentView(R.layout.activity_history);


        pendingRecyclerView = findViewById(R.id.list_pending);
        doneRecyclerView = findViewById(R.id.list_done);
        processRecyclerView= findViewById(R.id.list_process);

        txtProcess = findViewById(R.id.txtProcess);
        txtPending = findViewById(R.id.txtPending);
        txtDone    = findViewById(R.id.txtDone);


        pendingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        doneRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        processRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        pendingRecyclerView.setNestedScrollingEnabled(false);
        doneRecyclerView.setNestedScrollingEnabled(false);
        processRecyclerView.setNestedScrollingEnabled(false);

        historyActivity = this;


        getDone();
        getAcepted();
        getProcess();

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

    private void getProcess()
    {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
        apiInterface = new ApiClient().getApiClient().create(ApiInterface.class);

        db = Room.databaseBuilder(HistoryActivity.this,
                AppDataBase.class, "db_edge")
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();

        userdb =db.getUser();
        list = userdb.getUser();

        String id;
        id =  list.get(0).getId_paciente();


        Call<AppointmentResponse> callT = apiInterface.getCitaInProcess(id);

        callT.enqueue(new Callback<AppointmentResponse>() {
            @Override
            public void onResponse(Call<AppointmentResponse> call, Response<AppointmentResponse> response) {
                final AppointmentResponse Res = response.body();
                if (Res.getErrorCode() == 0)
                {
                    txtProcess.setVisibility(View.VISIBLE);
                    AppointmentAdapter adapter = new AppointmentAdapter(HistoryActivity.this,Res.getMsg(),HistoryActivity.this);
                    processRecyclerView.setAdapter(adapter);
                }
                else if(Res.getErrorCode() == 3)
                {
                    processRecyclerView.setVisibility(View.GONE);
                }

                if (dialog.isShowing())
                {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<AppointmentResponse> call, Throwable t) {
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


    private void getDone()
    {
        apiInterface = new ApiClient().getApiClient().create(ApiInterface.class);

        db = Room.databaseBuilder(HistoryActivity.this,
                AppDataBase.class, "db_edge")
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();

        userdb =db.getUser();
        list = userdb.getUser();

        String id;
        id =  list.get(0).getId_paciente();


        Call<AppointmentResponse> callT = apiInterface.getCitasDone(id);

        callT.enqueue(new Callback<AppointmentResponse>() {
            @Override
            public void onResponse(Call<AppointmentResponse> call, Response<AppointmentResponse> response) {
                final AppointmentResponse Res = response.body();
                if (Res.getErrorCode() == 0)
                {
                    txtDone.setVisibility(View.VISIBLE);
                    AppointmentAdapter adapter = new AppointmentAdapter(HistoryActivity.this,Res.getMsg(),HistoryActivity.this);
                    doneRecyclerView.setAdapter(adapter);
                }
                else if (Res.getErrorCode() == 3)
                {
                    doneRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<AppointmentResponse> call, Throwable t) {
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


    private void getAcepted()
    {
        apiInterface = new ApiClient().getApiClient().create(ApiInterface.class);

        db = Room.databaseBuilder(HistoryActivity.this,
                AppDataBase.class, "db_edge")
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();

        userdb =db.getUser();
        list = userdb.getUser();

        String id;
        id =  list.get(0).getId_paciente();


        Call<AppointmentResponse> callT = apiInterface.getCitasAceptadas(id);

        callT.enqueue(new Callback<AppointmentResponse>() {
            @Override
            public void onResponse(Call<AppointmentResponse> call, Response<AppointmentResponse> response) {
                final AppointmentResponse Res = response.body();
                if (Res.getErrorCode() == 0)
                {
                    txtPending.setVisibility(View.VISIBLE);
                    AppointmentAdapter adapter = new AppointmentAdapter(HistoryActivity.this,Res.getMsg(),HistoryActivity.this);
                    pendingRecyclerView.setAdapter(adapter);
                }

                else if(Res.getErrorCode()==3)
                {
                    pendingRecyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<AppointmentResponse> call, Throwable t) {
                if (!call.isCanceled())
                {
                    Log.e("Error",""+t);
                }
            }
        });
    }



    private void startMainActivity() {
        transition.setDuration(DURATION_TRANSITION);
        transition.setInterpolator(new DecelerateInterpolator());

        getWindow().setExitTransition(transition);


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());

    }

    private void startContactActivity() {
        transition.setDuration(DURATION_TRANSITION);
        transition.setInterpolator(new DecelerateInterpolator());

        getWindow().setExitTransition(transition);

        Intent intent = new Intent(this, ContactActivity.class);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());

    }

    private void startProfileActivity() {
        transition.setDuration(DURATION_TRANSITION);
        transition.setInterpolator(new DecelerateInterpolator());

        getWindow().setExitTransition(transition);

        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());

    }

    private void startLoginActivity() {
        transition.setDuration(DURATION_TRANSITION);
        transition.setInterpolator(new DecelerateInterpolator());

        getWindow().setExitTransition(transition);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            transition = new Explode();
            startMainActivity();
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
                transition = new Explode();
                startMainActivity();
                break;
            case R.id.nav_profile:
                transition = new Explode();
                startProfileActivity();
                break;
            case R.id.nav_history:
                break;
            case R.id.nav_contact:
                transition = new Explode();
                startContactActivity();
                break;
            case R.id.nav_logout:
                db = Room.databaseBuilder(this,
                        AppDataBase.class, "db_edge")
                        .fallbackToDestructiveMigration().allowMainThreadQueries().build();

                AlertDialog alertDialog = new AlertDialog.Builder(HistoryActivity.this).create();
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


    public static HistoryActivity getInstance() {
        return historyActivity;
    }

    @Override
    public void onItemClick(Appointment item) {

    }
}
