package com.ltudttbdd.project.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ltudttbdd.project.R;
import com.ltudttbdd.project.model.User;
import com.ltudttbdd.project.ultil.CheckConnection;
import com.ltudttbdd.project.ultil.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.ltudttbdd.project.activity.MainActivity.isLogin;
import static com.ltudttbdd.project.activity.MainActivity.user;

public class LoggedActivity extends AppCompatActivity {

    TextView edtUsername, edtEmail;
    Toolbar toolbarlogged;
    Button btnConfigAccout, btnPurcharse, btnMyRate, btnLogout;
    ImageView imageView;
    ImageView imageView1;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_activity);
        imageView  = findViewById(R.id.image);
        this.imageView.setImageResource(R.drawable.vy);
        imageView1 = findViewById(R.id.ImageView);
        this.imageView1.setImageResource(R.drawable.ctxh);
        Mappings();
        ActionToolbar();
//        btnreturn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        edtUsername.setText(user.name);
        edtEmail.setText(user.email);
        textView.setText(user.name);
//        btnsignup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent signup = new Intent(getApplicationContext(), SignUpActivity.class);
//                startActivity(signup);
//            }
//        });
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            EventButton();
        } else {
            CheckConnection.ShowToastShort(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối Internet");
        }
    }

    private void EventButton() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bỏ đi khoảng trắng trim()

//                final HashMap<String, String> postParams = new HashMap<String, String>();
//
//                final JSONObject jsonObject = new JSONObject(postParams);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Server.urlLogout, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TestLogout", "onResponse: đã log out ");
                        isLogin =false;
                        Intent notLoggedAc = new Intent(getApplicationContext(), SignInActivity.class);
                        startActivity(notLoggedAc);
                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Authorization", "Bearer" + user.token);
                        return headers;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(jsonObjectRequest);
            }
        });
        btnConfigAccout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edituser = new Intent(getApplicationContext(), EditUser.class);
                startActivity(edituser);
                finish();
            }
        });
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarlogged);
        getSupportActionBar().setTitle("Thông tin tài khoản");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable newbackbtn = getResources().getDrawable(R.drawable.ic_back);
        getSupportActionBar().setHomeAsUpIndicator(newbackbtn);
        toolbarlogged.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void Mappings() {
        textView = findViewById(R.id.text4);
        edtEmail = findViewById(R.id.useremailtext);
        edtUsername = findViewById(R.id.usernametext);
        btnLogout = findViewById(R.id.logout);
        toolbarlogged = findViewById(R.id.toolbar);
        btnConfigAccout = findViewById(R.id.configaccount);
//        btnreturn = findViewById(R.id.returnbutton);
//        btnsignup = findViewById(R.id.signupbutton);
    }
}
