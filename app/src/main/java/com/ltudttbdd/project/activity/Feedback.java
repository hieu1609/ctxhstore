package com.ltudttbdd.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.ltudttbdd.project.ultil.CheckConnection;
import com.ltudttbdd.project.ultil.Server;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.ltudttbdd.project.activity.MainActivity.user;

public class Feedback extends AppCompatActivity {
    EditText edttitle, edtcontent;
    Button btnconfirm;
    Toolbar toolbaruser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
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
                    final String name = edttitle.getText().toString().trim();
                    final String phone = edtcontent.getText().toString().trim();
                    if (name.length() > 0 && phone.length() > 0) {
                        final HashMap<String, String> postParams = new HashMap<String, String>();
                        postParams.put("feedbackTitle", edttitle.getText().toString());
                        postParams.put("feedbackContent", edtcontent.getText().toString());
                        final JSONObject jsonObject = new JSONObject(postParams);
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Server.urlfeedback, jsonObject, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(Feedback.this, "Cảm ơn bạn đã góp ý", Toast.LENGTH_LONG).show();
                                Intent logger = new Intent(Feedback.this, LoggedActivity.class);
                                startActivity(logger);
                                finish();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";
                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                HashMap<String, String> headers = new HashMap<String, String>();
                                headers.put("Authorization", "Bearer" + user.token);
                                return headers;
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
        btnconfirm = findViewById(R.id.confirmbutton);
        edttitle = findViewById(R.id.texttitle);
        edtcontent = findViewById(R.id.textcontent);
        toolbaruser = findViewById(R.id.toolbaruser);
    }

    private void ActionBar() {
        setSupportActionBar(toolbaruser);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbaruser.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cart = new Intent(getApplicationContext(), LoggedActivity.class);
                startActivity(cart);
                finish();
            }
        });
    }


}
