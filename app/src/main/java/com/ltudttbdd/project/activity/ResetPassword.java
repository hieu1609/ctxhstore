package com.ltudttbdd.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class ResetPassword extends AppCompatActivity {
    EditText edtpassnew, edtcpass, editmail;
    Button btnconfirm;
    Toolbar toolbaruser;
    String token = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

            Mappings();
            ActionBar();
            if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                EventButton();
            } else {
                CheckConnection.ShowToastShort(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối Internet");
            }
        }

        private void EventButton() {
            btnconfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //bỏ đi khoảng trắng trim()
                    final String mail = editmail.getText().toString().trim();
                    if(mail.length() > 0){
                        final HashMap<String, String> postParams = new HashMap<String, String>();
                        postParams.put("email", editmail.getText().toString());
                        final JSONObject jsonObject = new JSONObject(postParams);
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Server.urlmail, jsonObject, new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response) {
                                    Toast.makeText(ResetPassword.this, "Kiểm tra mail của bạn", Toast.LENGTH_LONG).show();
                                    Intent logger = new Intent(ResetPassword.this, SignInActivity.class);
                                    startActivity(logger);
                                    finish();

                            }
                        }
                                , new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";
                            }

                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        requestQueue.add(jsonObjectRequest);
                    } else {
                        CheckConnection.ShowToastShort(getApplicationContext(), "Bạn hãy kiểm tra lại dữ liệu");
                    }

                }
            });
        }

        private void Mappings() {
            edtpassnew = findViewById(R.id.textpasswordregis);
            edtcpass = findViewById(R.id.textconfirmpasswordregis);
            btnconfirm = findViewById(R.id.buttonconfirm);
            toolbaruser = findViewById(R.id.toolbaruser);
            editmail = findViewById(R.id.textemail);
        }

        private void ActionBar() {
            setSupportActionBar(toolbaruser);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbaruser.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent cart = new Intent(getApplicationContext(), SignInActivity.class);
                    startActivity(cart);
                    finish();
                }
            });
        }
}
