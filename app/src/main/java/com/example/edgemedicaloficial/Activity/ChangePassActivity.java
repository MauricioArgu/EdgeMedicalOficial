package com.example.edgemedicaloficial.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Explode;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.edgemedicaloficial.Model.mRecovery.Step2Body;
import com.example.edgemedicaloficial.Model.mRecovery.Step2Response;
import com.example.edgemedicaloficial.Model.mRecovery.Step3Body;
import com.example.edgemedicaloficial.Model.mRecovery.Step3Response;
import com.example.edgemedicaloficial.R;
import com.example.edgemedicaloficial.Rest.ApiClient;
import com.example.edgemedicaloficial.Rest.ApiInterface;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassActivity extends AppCompatActivity {

    private Transition transition;

    static ChangePassActivity changePassActivity;

    ApiInterface apiInterface;
    ProgressDialog dialog;

    String email;


    public static final long DURATION_TRANSITION = 1000;

    EditText txtPassword;
    EditText txtConfPassword;
    Button btPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Explode explode = new Explode();
        explode.setDuration(LoginActivity.DURATION_TRANSITION);
        explode.setInterpolator(new DecelerateInterpolator());


        getWindow().setEnterTransition(explode);
        setContentView(R.layout.activity_change_pass);

        btPass = findViewById(R.id.btPass);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfPassword = findViewById(R.id.txtConfPassword);
        changePassActivity = this;
        email = getIntent().getStringExtra("email");

        btPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence error = "Please complete the all fields";
                CharSequence error2 = "Entered Passwords Don't Match";
                int duration = Toast.LENGTH_SHORT;
                if (LoginActivity.getInstance() != null){
                    LoginActivity.getInstance().finish();
                }
                if (txtPassword.getText().toString().isEmpty())
                {
                    Toast.makeText(context, error, duration).show();
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
                else if (txtConfPassword.getText().toString().isEmpty())
                {
                    Toast.makeText(context,error,duration).show();
                    txtConfPassword.setHintTextColor(getResources().getColor(R.color.red));
                    txtConfPassword.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            txtConfPassword.setHintTextColor(getResources().getColor(R.color.colorGray));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                else if (!txtPassword.getText().toString().equals(txtConfPassword.getText().toString()))
                {
                    Toast.makeText(context,error2,duration).show();
                }
                else
                {

                    transition = new Explode();
                    String password = txtPassword.getText().toString();
                    passwordRecoveryStep3(email,password);
                }


            }
        });
    }

    private void startLoginActivity(){
        transition.setDuration(DURATION_TRANSITION);
        transition.setInterpolator(new DecelerateInterpolator());

        getWindow().setExitTransition(transition);

        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());

    }

    public void passwordRecoveryStep3(String correo, String password)
    {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
        apiInterface = new ApiClient().getApiClient().create(ApiInterface.class);

        Step3Body body = null;

        try {
            body = new Step3Body(
                    ""+correo,
                    ""+password
            );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        String json = new Gson().toJson(body);
        Log.i("bodyRes", json+"");

        Call<Step3Response> callT = apiInterface.passwordRecoveryStep3(body);

        callT.enqueue(new Callback<Step3Response>() {
            @Override
            public void onResponse(Call<Step3Response> call, Response<Step3Response> response) {
                final Step3Response Res =response.body();
                String json = new Gson().toJson(Res);
                Log.i("bodyRes", json + "");

                if (Res.getErrorCode() == 0)
                {
                    startLoginActivity();
                }
                else if (Res.getErrorCode() == 2)
                {
                    Toast.makeText(ChangePassActivity.this,"Error on the server",Toast.LENGTH_SHORT).show();
                }

                else if (Res.getErrorCode() == 6)
                {
                    Toast.makeText(ChangePassActivity.this, "Email does not exist", Toast.LENGTH_SHORT).show();
                }
                if (dialog.isShowing())
                {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Step3Response> call, Throwable t) {
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

    public static ChangePassActivity getInstance(){
        return changePassActivity;
    }

}
