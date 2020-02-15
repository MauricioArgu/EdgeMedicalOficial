package com.example.edgemedicaloficial.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.room.Room;

import android.app.DatePickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.edgemedicaloficial.Dao.AppDataBase;
import com.example.edgemedicaloficial.Dao.registroDao;
import com.example.edgemedicaloficial.Dao.userDao;
import com.example.edgemedicaloficial.Model.mRegistro.RegistroBody;
import com.example.edgemedicaloficial.Model.mRegistro.RegistroResponse;
import com.example.edgemedicaloficial.R;
import com.example.edgemedicaloficial.Rest.ApiClient;
import com.example.edgemedicaloficial.Rest.ApiInterface;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ProgressDialog dialog;

    ApiInterface apiService;

    private Transition transition;
    private Transition salida;

    private Button btSignUp;
    private Button btFacebook;
    private ImageButton btnBirth;

    private int dia;
    private int mes;
    private int anio;

    private EditText txtUsername;
    private EditText txtName;
    private EditText txtLastName;
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtConfirmPass;
    private EditText txtPhoneNumber;
    private EditText txtBirth;

    private Spinner spnCountry;


    AppDataBase db;
    registroDao registrodb;
    userDao userdb;



    public static final long DURATION_TRANSITION = 1000;

    static RegisterActivity registerActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Explode explode = new Explode();
        explode.setDuration(DURATION_TRANSITION);
        explode.setInterpolator(new DecelerateInterpolator());

        getWindow().setEnterTransition(explode);
        setContentView(R.layout.activity_register);

        db = Room.databaseBuilder(this,
                AppDataBase.class, "db_edge").allowMainThreadQueries().build();

        registrodb = db.getRegistroDao();
        userdb = db.getUser();

        txtUsername    = findViewById(R.id.txtUsername);
        txtName        = findViewById(R.id.txtName);
        txtLastName    = findViewById(R.id.txtLastName);
        txtEmail       = findViewById(R.id.txtEmail);
        txtPassword    = findViewById(R.id.txtPassword);
        txtConfirmPass = findViewById(R.id.txtConfirmPassword);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        txtBirth       = findViewById(R.id.txtBirth);

        spnCountry     = findViewById(R.id.spnCountry);

        btSignUp       = findViewById(R.id.btSignUp);
        btFacebook     = findViewById(R.id.btSignUp);
        btnBirth       = findViewById(R.id.btnBirth);

        btnBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                final Calendar calendar = Calendar.getInstance();
                dia = calendar.get(Calendar.DAY_OF_MONTH);
                mes = calendar.get(Calendar.MONTH);
                anio = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtBirth.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                    }
                }
                        ,anio,mes,dia);
                datePickerDialog.show();
            }
        });

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tUsername = txtUsername.getText().toString();
                String tPais = spnCountry.getSelectedItem().toString();
                String tName = txtName.getText().toString();
                String tLastName = txtLastName.getText().toString();
                String tEmail = txtEmail.getText().toString();
                String tPassword = txtPassword.getText().toString();
                String tConfirmPass = txtConfirmPass.getText().toString();
                String tPhoneNumber = txtPhoneNumber.getText().toString();
                String tBirth = txtBirth.getText().toString();

                Context context = getApplicationContext();
                CharSequence user = "Please complete the all fields";
                CharSequence pass = "Entered passwords don't match";
                int duration = Toast.LENGTH_SHORT;
                if (txtUsername.getText().toString().isEmpty())
                {
                    Toast.makeText(context, user, duration).show();
                    txtUsername.setHintTextColor(getResources().getColor(R.color.red));
                    txtUsername.requestFocus();
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
                else if (txtEmail.getText().toString().isEmpty())
                {
                    Toast.makeText(context, user, duration).show();
                    txtEmail.setHintTextColor(getResources().getColor(R.color.red));
                    txtEmail.requestFocus();
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
                else if (txtPassword.getText().toString().isEmpty())
                {
                    Toast.makeText(context, user, duration).show();
                    txtPassword.setHintTextColor(getResources().getColor(R.color.red));
                    txtPassword.requestFocus();
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
                else if (txtConfirmPass.getText().toString().isEmpty())
                {
                    Toast.makeText(context, user, duration).show();
                    txtConfirmPass.setHintTextColor(getResources().getColor(R.color.red));
                    txtConfirmPass.requestFocus();
                    txtConfirmPass.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            txtConfirmPass.setHintTextColor(getResources().getColor(R.color.colorGray));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                else if (txtName.getText().toString().isEmpty())
                {
                    Toast.makeText(context, user, duration).show();
                    txtName.setHintTextColor(getResources().getColor(R.color.red));
                    txtName.requestFocus();
                    txtName.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            txtName.setHintTextColor(getResources().getColor(R.color.colorGray));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                else if (txtLastName.getText().toString().isEmpty())
                {
                    Toast.makeText(context, user, duration).show();
                    txtLastName.setHintTextColor(getResources().getColor(R.color.red));
                    txtLastName.requestFocus();
                    txtLastName.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            txtLastName.setHintTextColor(getResources().getColor(R.color.colorGray));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                else if (txtPhoneNumber.getText().toString().isEmpty())
                {
                    Toast.makeText(context, user, duration).show();
                    txtPhoneNumber.setHintTextColor(getResources().getColor(R.color.red));
                    txtPhoneNumber.requestFocus();
                    txtPhoneNumber.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            txtPhoneNumber.setHintTextColor(getResources().getColor(R.color.colorGray));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                else if (txtBirth.getText().toString().isEmpty())
                {
                    Toast.makeText(context, user, duration).show();
                    txtBirth.setHintTextColor(getResources().getColor(R.color.red));
                    txtBirth.requestFocus();
                    txtBirth.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            txtBirth.setHintTextColor(getResources().getColor(R.color.colorGray));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                else if (!txtPassword.getText().toString().equals(txtConfirmPass.getText().toString()))
                {

                    Toast.makeText(context, pass, duration).show();
                }
                else
                {
                    transition = new Explode();
                    postRegistro(tUsername,tPassword,tPais,tName,tLastName,tBirth,tEmail,tPhoneNumber);
                }
            }
        });

        Spinner spinner = findViewById(R.id.spnCountry);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.countries, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.spiner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        registerActivity = this;
    }

    public static RegisterActivity getInstance(){
        return registerActivity;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void startTermsActivity(){
        transition.setDuration(DURATION_TRANSITION);
        transition.setInterpolator(new DecelerateInterpolator());

        getWindow().setExitTransition(transition);

        Intent intent = new Intent(this,TermsConditionsActivity.class);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());



    }

    public void postRegistro(String username, String password, String pais, String nombres, String apellidos, String birth, String email, String telefono)
    {
        dialog = new ProgressDialog(RegisterActivity.this);
        dialog.setMessage("Loading");
        dialog.show();

        apiService = ApiClient.getApiClient().create(ApiInterface.class);

        RegistroBody body = null;
        try
        {
            body = new RegistroBody(
                    ""+username,
                    ""+password,
                    ""+pais,
                    ""+nombres,
                    ""+apellidos,
                    ""+birth,
                    ""+email,
                    ""+telefono);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        String json = new Gson().toJson(body);
        Log.i("bodyRes", json+"");

        Call<RegistroResponse> callT = apiService.setRegistroUsuarioApp(body);

        callT.enqueue(new Callback<RegistroResponse>() {
            @Override
            public void onResponse(Call<RegistroResponse> call, Response<RegistroResponse> response) {
                final RegistroResponse Res = response.body();

                String json = new Gson().toJson(Res);
                Log.i("bodyRes", json+"");

                if (Res.getErrorCode() == 0)
                {
                    Toast.makeText(RegisterActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                    startTermsActivity();
                }
                else if (Res.getErrorCode() == 2)
                {
                    Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
                else if (Res.getErrorCode() == 3)
                {
                    Toast.makeText(RegisterActivity.this, "Username isn't available", Toast.LENGTH_SHORT).show();
                }
                else if (Res.getErrorCode() == 4)
                {
                    Toast.makeText(RegisterActivity.this, "Email isn't available", Toast.LENGTH_SHORT).show();
                }
                if (dialog.isShowing())
                {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<RegistroResponse> call, Throwable t) {

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
}
