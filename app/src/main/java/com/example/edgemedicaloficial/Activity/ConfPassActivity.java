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
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.edgemedicaloficial.Model.mRecovery.Step1Response;
import com.example.edgemedicaloficial.Model.mRecovery.Step2Body;
import com.example.edgemedicaloficial.Model.mRecovery.Step2Response;
import com.example.edgemedicaloficial.R;
import com.example.edgemedicaloficial.Rest.ApiClient;
import com.example.edgemedicaloficial.Rest.ApiInterface;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.edgemedicaloficial.Activity.ForgotActivity.DURATION_TRANSITION;

public class ConfPassActivity extends AppCompatActivity {

    private EditText num1;
    private EditText num2;
    private EditText num3;
    private EditText num4;
    private EditText num5;

    private Button btContinue;

    ApiInterface apiInterface;
    ProgressDialog dialog;

    String email;

    private Transition transition;

    static ConfPassActivity confPassActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Explode explode = new Explode();
        explode.setDuration(DURATION_TRANSITION);
        explode.setInterpolator(new DecelerateInterpolator());


        getWindow().setEnterTransition(explode);
        setContentView(R.layout.activity_conf_pass);

        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);
        num5 = findViewById(R.id.num5);
        btContinue = findViewById(R.id.btContinue);

        email = getIntent().getStringExtra("email");

        confPassActivity = this;

        btContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence error = "Please complete the code";
                int duration = Toast.LENGTH_SHORT;
                if (num1.getText().toString().isEmpty())
                {
                    Toast.makeText(context,error,duration).show();
                }
                else if (num2.getText().toString().isEmpty())
                {
                    Toast.makeText(context,error,duration).show();
                }
                else if (num3.getText().toString().isEmpty())
                {
                    Toast.makeText(context,error,duration).show();
                }
                else if (num4.getText().toString().isEmpty())
                {
                    Toast.makeText(context,error,duration).show();
                }
                else if (num5.getText().toString().isEmpty())
                {
                    Toast.makeText(context,error,duration).show();
                }
                else
                {
                    transition = new Explode();
                    String codigo = num1.getText().toString()+num2.getText().toString()+num3.getText().toString()+num4.getText().toString()+num5.getText().toString();
                    passwordRecoveryStep2(email,codigo);

                }

            }
        });

        num1.requestFocus();

        num2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_DEL)
                {

                    if (num2.getText().toString().length()!=0){
                        num2.setText("");
                    }
                    else
                    {
                        num1.setText("");
                        num1.requestFocus();
                    }
                }
                return false;
            }
        });

        num3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_DEL)
                {

                    if (num3.getText().toString().length()!=0){
                        num3.setText("");
                    }
                    else
                    {
                        num2.setText("");
                        num2.requestFocus();
                    }
                }
                return false;
            }
        });

        num4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_DEL)
                {

                    if (num4.getText().toString().length()!=0){
                        num4.setText("");
                    }
                    else
                    {
                        num3.setText("");
                        num3.requestFocus();
                    }
                }
                return false;
            }
        });

        num5.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_DEL)
                {

                    if (num5.getText().toString().length()!=0){
                        num5.setText("");
                    }
                    else
                    {
                        num4.setText("");
                        num4.requestFocus();
                    }
                }
                return false;
            }
        });

        num1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (num1.getText().toString().length()>0){
                    num2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        num2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (num2.getText().toString().length()>0){
                    num3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        num3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (num3.getText().toString().length()>0){
                    num4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        num4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



                if (num4.getText().toString().length()>0){
                    num5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @SuppressWarnings("unchecked")

    private void startChangePassAcivity(){
        transition.setDuration(DURATION_TRANSITION);
        transition.setInterpolator(new DecelerateInterpolator());

        getWindow().setExitTransition(transition);

        Intent intent = new Intent(this, ChangePassActivity.class);
        intent.putExtra("email",""+email);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());

    }

    public void passwordRecoveryStep2(String correo, String codigo)
    {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
        apiInterface = new ApiClient().getApiClient().create(ApiInterface.class);

        Step2Body body = null;

        try {
            body = new Step2Body(
                    ""+correo,
                    ""+codigo
            );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        String json = new Gson().toJson(body);
        Log.i("bodyRes", json+"");

        Call<Step2Response> callT = apiInterface.passwordRecoveryStep2(body);

        callT.enqueue(new Callback<Step2Response>() {
            @Override
            public void onResponse(Call<Step2Response> call, Response<Step2Response> response) {
                final Step2Response Res =response.body();
                String json = new Gson().toJson(Res);
                Log.i("bodyRes", json + "");

                if (Res.getErrorCode() == 0)
                {
                    startChangePassAcivity();
                }

                else if (Res.getErrorCode() == 5)
                {
                    Toast.makeText(ConfPassActivity.this, "Incorrect Code", Toast.LENGTH_SHORT).show();
                }
                if (dialog.isShowing())
                {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Step2Response> call, Throwable t) {
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

    public static ConfPassActivity getInstance(){
        return confPassActivity;
    }


}
