package com.example.edgemedicaloficial.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.edgemedicaloficial.Adapter.AppointmentAdapter;
import com.example.edgemedicaloficial.Adapter.EspecialidadAdapter;
import com.example.edgemedicaloficial.Adapter.HorasAdapter;
import com.example.edgemedicaloficial.Dao.AppDataBase;
import com.example.edgemedicaloficial.Dao.userDao;
import com.example.edgemedicaloficial.Model.mAddAppoinment.AddAppointmentBody;
import com.example.edgemedicaloficial.Model.mAddAppoinment.AddAppointmentResponse;
import com.example.edgemedicaloficial.Model.mAppointment.AppointmentResponse;
import com.example.edgemedicaloficial.Model.mEspecialidades.EspecialidadResponse;
import com.example.edgemedicaloficial.Model.mHoras.Horas;
import com.example.edgemedicaloficial.Model.mHoras.HorasResponse;
import com.example.edgemedicaloficial.Model.mLogin.Login;
import com.example.edgemedicaloficial.Model.mRegistro.User;
import com.example.edgemedicaloficial.R;
import com.example.edgemedicaloficial.Rest.ApiClient;
import com.example.edgemedicaloficial.Rest.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAppointmentActivity extends AppCompatActivity implements View.OnClickListener, HorasAdapter.ItemListener {

    private int dia;
    private int mes;
    private int anio;
    private int hora;
    private int minutos;

    AppDataBase db;

    userDao userdb;

    List<User> list;

    EditText txtFecha;
    EditText txtDescripcion;
    EditText txtHora;

    static AddAppointmentActivity addAppointmentActivity;

    String cita;


    Spinner spnHoras;

    Button btFecha;
    Button btHora;
    Button btAddAppointment;


    ProgressDialog dialog4;


    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        addAppointmentActivity = this;



        btAddAppointment = findViewById(R.id.btAdd);

        btHora = findViewById(R.id.btnHora);
        btFecha = findViewById(R.id.btnFecha);
        txtHora = findViewById(R.id.txtHora);
        txtFecha = findViewById(R.id.txtFecha);
        txtDescripcion = findViewById(R.id.descripcion);


        spnHoras = findViewById(R.id.spnHoras);



        if (MainActivity.getInstance() != null)
        {
            MainActivity.getInstance().finish();
        }

        btFecha.setOnClickListener(this);
        btHora.setOnClickListener(this);

        btFecha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                getHorasDisponibles();

                spnHoras.setVisibility(View.VISIBLE);

            }
        });


        btAddAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                db = Room.databaseBuilder(AddAppointmentActivity.this,
                        AppDataBase.class, "db_edge")
                        .fallbackToDestructiveMigration().allowMainThreadQueries().build();

                userdb =db.getUser();
                list = userdb.getUser();

                String id;
                id =  list.get(0).getId_paciente();
                String doctor;
                doctor = getIntent().getStringExtra("id");
                cita = txtFecha.getText().toString() + " " + btHora.getText().toString();
                String descripcion = txtDescripcion.getText().toString();

                if (txtDescripcion.getText().toString().isEmpty())
                {
                    Toast.makeText(AddAppointmentActivity.this,"Please add a Description",Toast.LENGTH_SHORT).show();
                    txtDescripcion.setHintTextColor(getResources().getColor(R.color.red));
                    txtDescripcion.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            txtDescripcion.setHintTextColor(getResources().getColor(R.color.colorGray));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                else if (btFecha.getText().toString().equals("Select Date"))
                {
                    Toast.makeText(AddAppointmentActivity.this,"Please add a Date",Toast.LENGTH_SHORT).show();
                }
                else if (btHora.getText().toString().equals("Select Time"))
                {
                    Toast.makeText(AddAppointmentActivity.this,"Please add a Time",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    agregarAppointment(id,doctor,cita,descripcion);
                }



            }
        });

        spnHoras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String horaI = parent.getSelectedItem().toString();
                if (dialog4.isShowing())
                {
                    dialog4.dismiss();
                }
                btHora.setText(horaI);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void agregarAppointment(String id,String doctor,String fecha, String descripcion)
    {
        dialog4 = new ProgressDialog(this);
        dialog4.setMessage("Creating Appointment");
        dialog4.show();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        AddAppointmentBody body = new AddAppointmentBody(
            id+"",
            doctor+"",
                fecha+"",
                descripcion+""
        );

        String json = new Gson().toJson(body);
        Log.i("body", json+"");

        Call<AddAppointmentResponse> callT = apiInterface.addAppointment(body);

        callT.enqueue(new Callback<AddAppointmentResponse>() {
            @Override
            public void onResponse(Call<AddAppointmentResponse> call, Response<AddAppointmentResponse> response) {
                final AddAppointmentResponse Res = response.body();

                String json = new Gson().toJson(Res);
                Log.i("bodyRes",json+"");

                if (Res.getErrorCode() == 0)
                {
                    Intent intent = new Intent(AddAppointmentActivity.this, MainActivity.class);

                    Toast.makeText(AddAppointmentActivity.this,"Appointment Created Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else if (Res.getErrorCode() == 2)
                {
                    Toast.makeText(AddAppointmentActivity.this,"Error",Toast.LENGTH_SHORT).show();
                }
                if (dialog4.isShowing())
                {
                    dialog4.dismiss();
                }

            }

            @Override
            public void onFailure(Call<AddAppointmentResponse> call, Throwable t) {
                if (!call.isCanceled())
                {
                    Log.e("Error ", ""+t);
                    if (dialog4.isShowing())
                    {
                        dialog4.dismiss();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {


        getHorasDisponibles();
        if (v == btFecha)
        {
            final Calendar calendar = Calendar.getInstance();
            dia = calendar.get(Calendar.DAY_OF_MONTH);
            mes = calendar.get(Calendar.MONTH);
            anio = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    txtFecha.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                    btFecha.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                }
            }
            ,anio,mes,dia);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();


        }
        if (v == btHora)
        {
            final Calendar calendar = Calendar.getInstance();
            hora = calendar.get(Calendar.HOUR_OF_DAY);
            minutos = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    txtHora.setText(hourOfDay+":"+minute);
                    btHora.setText(hourOfDay+":"+minute);
                }
            },hora,minutos,false);
            timePickerDialog.show();




        }

    }

    private void getHorasDisponibles()
    {
        dialog4 = new ProgressDialog(this);
        dialog4.setMessage("Searching for available hours");
        dialog4.show();
        apiInterface = new ApiClient().getApiClient().create(ApiInterface.class);

        String fechita = btFecha.getText().toString();

        Call<HorasResponse> callT = apiInterface.getHorasDisponibles(fechita);

        callT.enqueue(new Callback<HorasResponse>() {
            @Override
            public void onResponse(Call<HorasResponse> call, Response<HorasResponse> response) {
                final HorasResponse Res = response.body();

                String json = new Gson().toJson(Res);
                Log.i("bodyRes", json + "");

                if (Res.getErrorCode() == 0)
                {
                    HorasAdapter adapter = new HorasAdapter(AddAppointmentActivity.this,Res.getMsg(),AddAppointmentActivity.this);
                    spnHoras.setAdapter(new ArrayAdapter<Horas>(AddAppointmentActivity.this,android.R.layout.simple_spinner_dropdown_item,Res.getMsg()));
                }

                if (dialog4.isShowing())
                {
                    dialog4.dismiss();
                }
            }

            @Override
            public void onFailure(Call<HorasResponse> call, Throwable t) {
                if (!call.isCanceled())
                {
                    Log.e("Error",""+t);
                    if (dialog4.isShowing())
                    {
                        dialog4.dismiss();
                    }
                }
            }
        });
    }


    @Override
    public void onItemClick(Horas item) {
        btHora.setText(item.getHora());
    }

    public static AddAppointmentActivity getInstance(){
        return addAppointmentActivity;
    }
}
