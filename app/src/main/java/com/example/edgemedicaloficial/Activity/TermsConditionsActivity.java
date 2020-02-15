package com.example.edgemedicaloficial.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Transition;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.edgemedicaloficial.R;

public class TermsConditionsActivity extends AppCompatActivity {

    Transition transition;
    CheckBox cbTerms;
    Button btCreate;

    public static final long DURATION_TRANSITION = 1000;

    static  TermsConditionsActivity termsConditionsActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Explode explode = new Explode();
        explode.setDuration(DURATION_TRANSITION);
        explode.setInterpolator(new DecelerateInterpolator());


        getWindow().setEnterTransition(explode);
        setContentView(R.layout.activity_terms_conditions);

        cbTerms = findViewById(R.id.cbTerms);
        btCreate = findViewById(R.id.btCreate);

        termsConditionsActivity = this;

        cbTerms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cbTerms.isChecked()){
                    btCreate.setEnabled(true);
                    btCreate.setBackground(getDrawable(R.drawable.bg_ui1));
                }
                else {
                    btCreate.setEnabled(false);
                    btCreate.setBackground(getDrawable(R.drawable.bg_ui_gray));
                }
            }
        });

        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginActivity.getInstance() != null){
                    LoginActivity.getInstance().finish();
                }
                transition = new Explode();
                startLoginAcivity();
                Context context = getApplicationContext();
                CharSequence text = "Your Account Has Been Created!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }

    @SuppressWarnings("unchecked")

    private void startLoginAcivity(){
        transition.setDuration(DURATION_TRANSITION);
        transition.setInterpolator(new DecelerateInterpolator());

        getWindow().setExitTransition(transition);

        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());

    }

    public static TermsConditionsActivity getInstance(){
        return termsConditionsActivity;
    }
}