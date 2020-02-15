package com.example.edgemedicaloficial.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.room.Room;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Explode;
import android.transition.Transition;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edgemedicaloficial.Dao.AppDataBase;
import com.example.edgemedicaloficial.Dao.registroDao;
import com.example.edgemedicaloficial.Dao.userDao;
import com.example.edgemedicaloficial.Model.mLogin.Login;
import com.example.edgemedicaloficial.Model.mLogin.LoginBody;
import com.example.edgemedicaloficial.Model.mLogin.LoginResponse;
import com.example.edgemedicaloficial.Model.mRegistro.Registro;
import com.example.edgemedicaloficial.Model.mRegistro.RegistroResponse;
import com.example.edgemedicaloficial.Model.mRegistro.User;
import com.example.edgemedicaloficial.R;
import com.example.edgemedicaloficial.Rest.ApiClient;
import com.example.edgemedicaloficial.Rest.ApiInterface;
import com.facebook.CallbackManager;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class LoginActivity extends AppCompatActivity{


    private Transition transition;
    private Transition salida;

    private EditText txtUsername;
    private EditText txtPassword;
    private Button btLogin;
    private Button btRegister;
    private Button btFacebook;

    private TextView tvForgot;

    static LoginActivity loginActivity;

    ProgressDialog dialog;
    ApiInterface apiService;
    AppDataBase db;
    registroDao registrodb;
    userDao userdb;
    private CallbackManager callbackManager;



    public static final long DURATION_TRANSITION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginActivity = this;

        final Explode explode = new Explode();
        explode.setDuration(LoginActivity.DURATION_TRANSITION);
        explode.setInterpolator(new DecelerateInterpolator());

        explode.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                if(MainActivity.getInstance() !=  null){
                    MainActivity.getInstance().finish();
                }
                if(ProfileActivity.getInstance() != null){
                    ProfileActivity.getInstance().finish();
                }
                if (HistoryActivity.getInstance() != null){
                    HistoryActivity.getInstance().finish();
                }
                if (ContactActivity.getInstance() != null){
                    ContactActivity.getInstance().finish();
                }
                if(ChangePassActivity.getInstance() !=  null){
                    ChangePassActivity.getInstance().finish();
                }
                if(TermsConditionsActivity.getInstance() !=  null){
                    TermsConditionsActivity.getInstance().finish();
                }
                if(RegisterActivity.getInstance() != null){
                    RegisterActivity.getInstance().finish();
                }
                if(ConfPassActivity.getInstance() != null){
                    ConfPassActivity.getInstance().finish();
                }
                if(ForgotActivity.getInstance() != null){
                    ForgotActivity.getInstance().finish();
                }
                if (SplashActivity.getInstance() != null){
                    SplashActivity.getInstance().finish();
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


        setContentView(R.layout.activity_login);

        db = Room.databaseBuilder(this,
                AppDataBase.class, "db_edge").allowMainThreadQueries().build();

        registrodb = db.getRegistroDao();
        userdb = db.getUser();

        callbackManager = CallbackManager.Factory.create();

        tvForgot    = findViewById(R.id.tvForgot);

        btRegister  = findViewById(R.id.btRegister);
        btLogin     = findViewById(R.id.btLogin);
        btLogin     = findViewById(R.id.btLogin);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);


        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transition = new Explode();
                startRegisterActivity();
            }
        });



        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                salida = new Explode();
                startForgotActivity();


            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence user = "Please complete the all fields";


                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                int duration = Toast.LENGTH_SHORT;
                if (txtUsername.getText().toString().isEmpty())
                {
                    Toast.makeText(context,user,duration).show();
                    txtUsername.setHintTextColor(getResources().getColor(R.color.red));
                    txtUsername.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                                txtUsername.setHintTextColor(getResources().getColor(R.color.colorGray));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                else if (txtPassword.getText().toString().isEmpty())
                {
                    Toast.makeText(context,user,duration).show();
                    txtPassword.setHintTextColor(getResources().getColor(R.color.red));
                    txtPassword.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                            txtPassword.setHintTextColor(getResources().getColor(R.color.colorGray));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                else
                {
                    transition = new Explode();
                    postLogin(username,password);

                }
            }
        });

    }



    public static LoginActivity getInstance(){
        return loginActivity;
    }

    @SuppressWarnings("unchecked")

    private void startForgotActivity(){
        salida.setDuration(DURATION_TRANSITION);
        salida.setInterpolator(new DecelerateInterpolator());

        getWindow().setExitTransition(salida);

        Intent intent = new Intent(LoginActivity.this,ForgotActivity.class);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this).toBundle());




    }

    @SuppressWarnings("unchecked")

    private void startMainActivity(){
        transition.setDuration(DURATION_TRANSITION);
        transition.setInterpolator(new DecelerateInterpolator());

        getWindow().setExitTransition(transition);

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());



    }

    @SuppressWarnings("unchecked")

    private void startRegisterActivity(){
        transition.setDuration(DURATION_TRANSITION);
        transition.setInterpolator(new DecelerateInterpolator());

        getWindow().setExitTransition(transition);

        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());



    }

    public void postLogin(String username, String password){
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Cargando");
        dialog.show();
        apiService = ApiClient.getApiClient().create(ApiInterface.class);

        LoginBody body = null;
        try {
            body = new LoginBody(
                    ""+username,
                    ""+password
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        String json = new Gson().toJson(body);
        Log.i("bodyRes", json+"");

        Call<LoginResponse> callT = apiService.getLoginUsuarioApp(body);

        callT.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse( Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                final LoginResponse Res = response.body();

                String json = new Gson().toJson(Res);
                Log.i("bodyRes", json + "");

                if( Res.getErrorCode() == 0){

                    List<User> r = (List<User>)Res.getMsg();

                    registrodb.deleteAll();
                    userdb.deleteAll();
                    userdb.insert(r.get(0));

                    startMainActivity();

                }else if(Res.getErrorCode() == 2)
                {
                    Toast.makeText(LoginActivity.this, "Incorrect data", Toast.LENGTH_SHORT).show();
                }

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NotNull final Call<LoginResponse> call, final Throwable t) {
                if (!call.isCanceled()) {
                    //onError();
                    Log.e("Error",""+t);
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });
    }



}