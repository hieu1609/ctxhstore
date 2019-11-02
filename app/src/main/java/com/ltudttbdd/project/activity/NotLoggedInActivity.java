package com.ltudttbdd.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import static com.ltudttbdd.project.activity.MainActivity.isLogin;
import static com.ltudttbdd.project.activity.MainActivity.user;

public class NotLoggedInActivity extends AppCompatActivity {

    EditText edtPassword, edtEmail;
    Button btnlogin, btnregister;
    Toolbar toolbarnotlogged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.not_logged_in_activity);
        Mappings();
        ActionToolbar();
//        btnlogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signin = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(signin);
            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(signup);
            }
        });
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            //EventButton();
        } else {
            CheckConnection.ShowToastShort(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối Internet");
        }
    }

//    private void EventButton() {
//                btnconfirm.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                //bỏ đi khoảng trắng trim()
//
//                final String email = edtEmail.getText().toString().trim();
//                final String password = edtPassword.getText().toString().trim();
//                if (email.length() > 0 && password.length() > 0) {
//
//                    final HashMap<String, String> postParams = new HashMap<String, String>();
//
//                    postParams.put("email", edtEmail.getText().toString());
//                    postParams.put("password", edtPassword.getText().toString());
//
//                    final JSONObject jsonObject = new JSONObject(postParams);
//                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Server.urlLogin, jsonObject, new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//
//                            try {
//                                JSONObject data = (JSONObject) response.getJSONObject("data");
//                                try {
//                                    JSONObject userres = (JSONObject) data.getJSONObject("user");
//                                    int userid = userres.getInt("id");
//                                    String useremail = userres.getString("email");
//                                    String username = userres.getString("name");
//                                    String userphone = userres.getString("phone");
//                                    String useraddress = userres.getString("address");
//                                    user = new User(userid,useremail,username,userphone,useraddress);
//                                    Toast.makeText(NotLoggedInActivity.this, "Dang nhap thanh cong", Toast.LENGTH_LONG).show();
//                                    isLogin = true;
//                                    finish();
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//
//                        }
//                    }) {
//
//                        @Override
//                        public String getBodyContentType() {
//                            return "application/json; charset=utf-8";
//                        }
//                    };
//                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                    requestQueue.add(jsonObjectRequest);
//                } else {
//                    CheckConnection.ShowToastShort(getApplicationContext(), "Bạn hãy kiểm tra lại dữ liệu");
//                }
//            }
//        });
//    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarnotlogged);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarnotlogged.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

    }
    private void Mappings() {
        toolbarnotlogged = findViewById(R.id.toolbarnotlogged);
        btnlogin = findViewById(R.id.login);
        btnregister= findViewById(R.id.register);
    }
}
