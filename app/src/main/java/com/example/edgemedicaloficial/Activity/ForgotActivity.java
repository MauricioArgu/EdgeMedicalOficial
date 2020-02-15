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

import com.example.edgemedicaloficial.Model.mLogin.LoginBody;
import com.example.edgemedicaloficial.Model.mRecovery.Step1Body;
import com.example.edgemedicaloficial.Model.mRecovery.Step1Response;
import com.example.edgemedicaloficial.R;
import com.example.edgemedicaloficial.Rest.ApiClient;
import com.example.edgemedicaloficial.Rest.ApiInterface;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotActivity extends AppCompatActivity {

    private Button btBackup;
    private EditText txtEmail;

    ApiInterface apiInterface;
    ProgressDialog dialog;

    private Transition transition;

    static ForgotActivity forgotActivity;

    public static final long DURATION_TRANSITION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Explode explode = new Explode();
        explode.setDuration(LoginActivity.DURATION_TRANSITION);
        explode.setInterpolator(new DecelerateInterpolator());


        getWindow().setEnterTransition(explode);
        setContentView(R.layout.activity_forgot);

        forgotActivity = this;

        btBackup = findViewById(R.id.btBackup);
        txtEmail = findViewById(R.id.txtEmail);
        btBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence error = "Please complete the all fields";
                int duration = Toast.LENGTH_SHORT;

                String email = txtEmail.getText().toString();

                if (txtEmail.getText().toString().isEmpty())
                {
                    Toast.makeText(context,error,duration).show();
                    txtEmail.setHintTextColor(getResources().getColor(R.color.red));
                    txtEmail.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            txtEmail.setHintTextColor(getResources().getColor(R.color.colorGray));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                else
                {
                    transition = new Explode();
                    passwordRecoveryStep1(email);
                }

            }
        });


    }

    @SuppressWarnings("unchecked")

    private void startConfPassAcivity(){
        transition.setDuration(DURATION_TRANSITION);
        transition.setInterpolator(new DecelerateInterpolator());

        getWindow().setExitTransition(transition);

        String email = txtEmail.getText().toString();

        Intent intent = new Intent(this, ConfPassActivity.class);
        intent.putExtra("email",""+email);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());

    }

    public void passwordRecoveryStep1(String email)
    {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
        apiInterface = new ApiClient().getApiClient().create(ApiInterface.class);

        Step1Body body = null;

        try {
            body = new Step1Body(
                    ""+email
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        String json = new Gson().toJson(body);
        Log.i("bodyRes", json+"");

        Call<Step1Response> callT = apiInterface.passwordRecoveryStep1(body);

        callT.enqueue(new Callback<Step1Response>() {
            @Override
            public void onResponse(Call<Step1Response> call, Response<Step1Response> response) {
                final Step1Response Res = response.body();
                String json = new Gson().toJson(Res);
                Log.i("bodyRes", json + "");

                if (Res.getErrorCode() == 0)
                {
                    startConfPassAcivity();
                }
                else if (Res.getErrorCode() == 2)
                {
                    Toast.makeText(ForgotActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
                else if (Res.getErrorCode() == 6)
                {
                    Toast.makeText(ForgotActivity.this, "Email does not exist", Toast.LENGTH_SHORT).show();
                }

                if (dialog.isShowing())
                {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Step1Response> call, Throwable t) {
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

    public static ForgotActivity getInstance(){
        return forgotActivity;
    }

}
