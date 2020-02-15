package com.example.edgemedicaloficial.Activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Transition;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.example.edgemedicaloficial.Dao.AppDataBase;
import com.example.edgemedicaloficial.Dao.userDao;
import com.example.edgemedicaloficial.Model.mRegistro.User;
import com.example.edgemedicaloficial.R;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final long DURATION_TRANSITION = 100;
    private Transition transition;

    AppDataBase db;

    userDao userdb;

    List<User> list;

    String nombre;
    String apellido;
    String userName;
    String email;
    String birth;

    TextView txtUserName;
    TextView txtEmail;
    TextView txtBirth;

    static ProfileActivity profileActivity;


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

        setContentView(R.layout.activity_profile);


        profileActivity = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);

        txtUserName = findViewById(R.id.txtUser);
        txtEmail = findViewById(R.id.txtEmail);
        txtBirth = findViewById(R.id.txtBirth);

        db = Room.databaseBuilder(ProfileActivity.this,
                AppDataBase.class, "db_edge")
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();

        userdb =db.getUser();
        list = userdb.getUser();

        nombre = list.get(0).getNombres();
        apellido = list.get(0).getApellidos();
        userName = nombre + " " + apellido;
        email = list.get(0).getEmail();
        birth = list.get(0).getBirthdate();




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


        txtUserName.setText(userName);
        txtEmail.setText(email);
        txtBirth.setText(birth);


    }

    private void startLoginActivity() {
        transition.setDuration(DURATION_TRANSITION);
        transition.setInterpolator(new DecelerateInterpolator());

        getWindow().setExitTransition(transition);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());

    }

    private void startMainActivity() {
        transition.setDuration(DURATION_TRANSITION);
        transition.setInterpolator(new DecelerateInterpolator());

        getWindow().setExitTransition(transition);

        Intent intent = new Intent(this, MainActivity.class);
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

                AlertDialog alertDialog = new AlertDialog.Builder(ProfileActivity.this).create();
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


    public static ProfileActivity getInstance() {
        return profileActivity;
    }
}