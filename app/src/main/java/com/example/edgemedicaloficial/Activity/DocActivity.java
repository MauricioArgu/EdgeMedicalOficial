package com.example.edgemedicaloficial.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.transition.Explode;
import android.transition.Transition;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edgemedicaloficial.R;

public class DocActivity extends AppCompatActivity {

    Transition transition;

    TextView txtDocName;
    TextView txtEmail;

    String id;
    String nombres;
    String email;
    String tel;



    static DocActivity docActivity;


    public static final long DURATION_TRANSITION = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Explode explode = new Explode();
        explode.setDuration(DURATION_TRANSITION);
        explode.setInterpolator(new DecelerateInterpolator());


        getWindow().setEnterTransition(explode);

        setContentView(R.layout.activity_doc);


        id = getIntent().getStringExtra("id");
        nombres = getIntent().getStringExtra("nombres");
        email = getIntent().getStringExtra("email");
        tel = getIntent().getStringExtra("tel");


        docActivity = this;

        Button btCall;
        Button btAddAppoitnment;

        txtDocName = findViewById(R.id.txtDocName);
        txtEmail = findViewById(R.id.txtEmail);


        txtDocName.setText(nombres);
        txtEmail.setText(email);

        btCall = findViewById(R.id.btCall);
        btAddAppoitnment = findViewById(R.id.btAppointment);

        btCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DocActivity.this, CallActivity.class);
                startActivity(intent);
            }
        });

        btAddAppoitnment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transition = new Explode();
                transition.setDuration(DURATION_TRANSITION);
                transition.setInterpolator(new DecelerateInterpolator());

                getWindow().setExitTransition(transition);

                Intent intent = new Intent(DocActivity.this, AddAppointmentActivity.class);
                intent.putExtra("id",""+id);
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(DocActivity.this).toBundle());
            }
        });
    }

    public static DocActivity getInstance()
    {
        return docActivity;
    }
}
