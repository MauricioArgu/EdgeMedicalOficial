package com.example.edgemedicaloficial.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.edgemedicaloficial.R;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;

import org.json.JSONException;
import org.json.JSONObject;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class CallActivity extends AppCompatActivity implements Session.SessionListener, PublisherKit.PublisherListener {

    static CallActivity callActivity;

    private static String API_KEY = "46463212";
    private static String SESSION_ID = "2_MX40NjQ2MzIxMn5-MTU4MDA5MzQ5MTc2N34xSEs0YzljTkxWNzg0NnZrb2N4N1ZhWkt-fg";
    private static String TOKEN = "T1==cGFydG5lcl9pZD00NjQ2MzIxMiZzaWc9ZDMwNWFkZDU3YWY4YmE3YzBjMjQ2ZTBiZDg2OTA3YTA1MmY1NDgxYTpzZXNzaW9uX2lkPTJfTVg0ME5qUTJNekl4TW41LU1UVTRNREE1TXpRNU1UYzJOMzR4U0VzMFl6bGpUa3hXTnpnME5uWnJiMk40TjFaaFdrdC1mZyZjcmVhdGVfdGltZT0xNTgwMDkzNTI5Jm5vbmNlPTAuOTAzODM2NTYwMjU5NjM5MiZyb2xlPXB1Ymxpc2hlciZleHBpcmVfdGltZT0xNTgwNjk4MzI3JmluaXRpYWxfbGF5b3V0X2NsYXNzX2xpc3Q9";
    private static final String LOG_TAG = CallActivity.class.getSimpleName();

    private static final int RC_SETTINGS_SCREEN_PERM = 123;
    private static final int RC_VIDEO_APP_PERM = 124;

    private Session session;

    private FrameLayout PublisherContainer;
    private FrameLayout SubscriberContainer;

    private Publisher publisher;
    private Subscriber subscriber;

    private ImageButton call;
    private ImageButton mic;
    private ImageButton noMic;
    private  ImageButton cam;
    private ImageButton noCam;

    public void fetchSessionConnectionData() {
        RequestQueue reqQueue = Volley.newRequestQueue(this);
        reqQueue.add(new JsonObjectRequest(Request.Method.GET,
                "https://prueba1-webrtc.herokuapp.com/" + "/session",
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    API_KEY = response.getString("apiKey");
                    SESSION_ID = response.getString("sessionId");
                    TOKEN = response.getString("token");

                    Log.i(LOG_TAG, "API_KEY: " + API_KEY);
                    Log.i(LOG_TAG, "SESSION_ID: " + SESSION_ID);
                    Log.i(LOG_TAG, "TOKEN: " + TOKEN);

                    session = new Session.Builder(CallActivity.this, API_KEY, SESSION_ID).build();
                    session.setSessionListener(CallActivity.this);
                    session.connect(TOKEN);

                } catch (JSONException error) {
                    Log.e(LOG_TAG, "Web Service error: " + error.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG_TAG, "Web Service error: " + error.getMessage());
            }
        }));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestPermissions();
        call=findViewById(R.id.call);
        mic=findViewById(R.id.mic);
        noMic=findViewById(R.id.noMic);
        cam=findViewById(R.id.cam);
        noCam=findViewById(R.id.noCam);

        if (MainActivity.getInstance() != null)
        {
            MainActivity.getInstance().finish();
        }



        //Colgar La Llamda
        call.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (session != null)
                {
                    session.disconnect();

                    DocActivity.getInstance().finish();
                    DoctorActivity.getInstance().finish();
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    DocActivity.getInstance().finish();
                    DoctorActivity.getInstance().finish();
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        });

        //Mutear La Llamada
        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (publisher != null)
                {
                    publisher.setPublishAudio(false);
                    mic.setVisibility(View.GONE);
                    noMic.setVisibility(View.VISIBLE);
                }
            }
        });

        //Desutear La Llamada
        noMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (publisher != null)
                {
                    publisher.setPublishAudio(true);
                    mic.setVisibility(View.VISIBLE);
                    noMic.setVisibility(View.GONE);
                }
            }
        });

        //Quitar Video
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (publisher != null)
                {
                    publisher.setPublishVideo(false);
                    cam.setVisibility(View.GONE);
                    noCam.setVisibility(View.VISIBLE);
                    PublisherContainer.setVisibility(View.INVISIBLE);
                }
            }
        });

        //Devolver Camara
        noCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (publisher != null)
                {
                    publisher.setPublishVideo(true);
                    cam.setVisibility(View.VISIBLE);
                    noCam.setVisibility(View.GONE);
                    PublisherContainer.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void requestPermissions() {
        String[] perms = { Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };
        if (EasyPermissions.hasPermissions(this, perms)) {
            // initialize view objects from your layout
            PublisherContainer = findViewById(R.id.publisher_container);
            SubscriberContainer = findViewById(R.id.subscriber_container);

            // initialize and connect to the session
            fetchSessionConnectionData();

        } else {
            EasyPermissions.requestPermissions(this, "This app needs access to your camera and mic to make video calls", RC_VIDEO_APP_PERM, perms);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {


            session.disconnect();

            Intent intent = new Intent(this, DocActivity.class);
            startActivity(intent);
            finish();

        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onConnected(Session session) {
        publisher = new Publisher.Builder(this).build();
        publisher.setPublisherListener(this);

        PublisherContainer.addView(publisher.getView());

        if (publisher.getView() instanceof GLSurfaceView){
            ((GLSurfaceView) publisher.getView()).setZOrderOnTop(true);
        }

        session.publish(publisher);
    }

    @Override
    public void onDisconnected(Session session) {

    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {
        if (subscriber == null) {
            subscriber = new Subscriber.Builder(this, stream).build();
            session.subscribe(subscriber);
            SubscriberContainer.addView(subscriber.getView());
        }
    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {
        if (subscriber != null) {
            subscriber = null;
            SubscriberContainer.removeAllViews();
        }
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {

    }

    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {

    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {

        session.unpublish(publisher);
    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {

    }
}