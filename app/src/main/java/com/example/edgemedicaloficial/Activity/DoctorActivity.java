package com.example.edgemedicaloficial.Activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.transition.Explode;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edgemedicaloficial.Adapter.DoctorAdapter;
import com.example.edgemedicaloficial.Adapter.EspecialidadAdapter;
import com.example.edgemedicaloficial.Model.mDoctor.DocByEspecialidadResponse;
import com.example.edgemedicaloficial.Model.mDoctor.Doctor;
import com.example.edgemedicaloficial.Model.mEspecialidades.EspecialidadResponse;
import com.example.edgemedicaloficial.R;
import com.example.edgemedicaloficial.Rest.ApiClient;
import com.example.edgemedicaloficial.Rest.ApiInterface;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorActivity extends AppCompatActivity implements DoctorAdapter.ItemListener{

    private static final long DURATION_TRANSITION = 100;
    private Transition transition;

    TextView empty;
    TextView nombes;
    TextView telefono;


    RecyclerView docRecyclerView;
    ApiInterface apiInterface;
    ProgressDialog dialog;

    String idEspecialidad;
    String nombreEsp;

    static DoctorActivity doctorActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        if (MainActivity.getInstance() != null)
        {
            MainActivity.getInstance().finish();
        }


        docRecyclerView = findViewById(R.id.list_doc);
        docRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getDoctor();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nombreEsp = getIntent().getStringExtra("nombre");
        getSupportActionBar().setTitle(nombreEsp);

        doctorActivity = this;

    }

    private void getDoctor()
    {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
        apiInterface = new ApiClient().getApiClient().create(ApiInterface.class);


        idEspecialidad = getIntent().getStringExtra("idEsp");

        Call<DocByEspecialidadResponse> callT = apiInterface.getDocByEspecialidad(idEspecialidad);

        callT.enqueue(new Callback<DocByEspecialidadResponse>() {
            @Override
            public void onResponse(Call<DocByEspecialidadResponse> call, Response<DocByEspecialidadResponse> response) {
                final DocByEspecialidadResponse Res = response.body();
                if (Res.getErrorCode() == 0)
                {
                    DoctorAdapter adapter = new DoctorAdapter(DoctorActivity.this,Res.getMsg(),DoctorActivity.this);
                    docRecyclerView.setAdapter(adapter);
                }
                else
                {
                    docRecyclerView.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                    Toast.makeText(DoctorActivity.this,"No doctors yet",Toast.LENGTH_SHORT).show();
                }

                if (dialog.isShowing())
                {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<DocByEspecialidadResponse> call, Throwable t) {
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

    public static DoctorActivity getInstance()
    {
        return doctorActivity;
    }

    @Override
    public void onItemClick(Doctor item) {
        Intent intent = new Intent(this,DocActivity.class);
        intent.putExtra("id",""+item.getId());
        intent.putExtra("nombres", ""+item.getNombres());
        intent.putExtra("email", ""+item.getEmail());
        intent.putExtra("tel",""+item.getTel());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
            startMainActivity();

    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
