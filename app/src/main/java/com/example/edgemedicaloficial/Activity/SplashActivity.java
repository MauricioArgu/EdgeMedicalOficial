package com.example.edgemedicaloficial.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Explode;
import android.transition.Transition;
import android.util.Base64;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;

import com.example.edgemedicaloficial.Dao.AppDataBase;
import com.example.edgemedicaloficial.Dao.registroDao;
import com.example.edgemedicaloficial.Dao.userDao;
import com.example.edgemedicaloficial.Model.mRegistro.User;
import com.example.edgemedicaloficial.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private

    Transition transition;

    private static final String TAG = "HASH";
    AppDataBase db;
    registroDao registrodb;
    List<User> list;
    private long splashDelay = 3000;

    userDao userdb;

    static SplashActivity splashActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        db = Room.databaseBuilder(this,
                AppDataBase.class, "db_edge")
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();

        registrodb = db.getRegistroDao();
        userdb = db.getUser();

        list = userdb.getUser();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (list.size()>0)
                {
                    startMainActivity();
                }
                else
                {
                    startLoginActivity();
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, splashDelay);

        printHashKey(this);




    }

    public  void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }
    }

    private void startMainActivity() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();


    }

    private void startLoginActivity() {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    public static SplashActivity getInstance(){
        return splashActivity;
    }

}
