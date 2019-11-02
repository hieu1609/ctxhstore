package com.ltudttbdd.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ltudttbdd.project.R;
import com.ltudttbdd.project.ultil.CheckConnection;
import com.ltudttbdd.project.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    EditText edtEmail, edtAddress, edtPassword, edtConfirmPassword, edtName, edtPhone;
    Button btnconfirm, btnreturn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        Mappings();
        btnreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
                final String name = edtName.getText().toString().trim();
                final String phone = edtPhone.getText().toString().trim();
                final String email = edtEmail.getText().toString().trim();
                final String address = edtAddress.getText().toString().trim();
                final String password = edtPassword.getText().toString().trim();
                final String confirmpassword = edtConfirmPassword.getText().toString().trim();
//                if(edtPassword.getText().toString().contentEquals(edtConfirmPassword.getText().toString()) == false){
//                    Toast.makeText(SignUpActivity.this, "Hai mật khẩu không trùng khớp", Toast.LENGTH_LONG).show();
//                }
                if (name.length() > 0 && phone.length() > 0 && email.length() > 0 && address.length() > 0 && password.length() > 0 && confirmpassword.length() > 0) {

                    final HashMap<String, String> postParams = new HashMap<String, String>();
                    postParams.put("name", edtName.getText().toString());
                    postParams.put("phone", edtPhone.getText().toString());
                    postParams.put("address", edtAddress.getText().toString());
                    postParams.put("email", edtEmail.getText().toString());
                    postParams.put("password", edtPassword.getText().toString());
                    postParams.put("confirmPassword", edtConfirmPassword.getText().toString());
                    Log.d("checksignup", edtName.getText().toString());
                    Log.d("checksignup", edtPhone.getText().toString());
                    Log.d("checksignup", edtAddress.getText().toString());
                    Log.d("checksignup", edtEmail.getText().toString());
                    Log.d("checksignup", edtPassword.getText().toString());
                    Log.d("checksignup", edtConfirmPassword.getText().toString());


                    final JSONObject jsonObject = new JSONObject(postParams);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Server.urlRegister, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(SignUpActivity.this, "Bạn đã đăng ký thành công", Toast.LENGTH_LONG).show();
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
        edtName = findViewById(R.id.textusernameregis);
        edtPhone = findViewById(R.id.textphonenumberregis);
        edtEmail = findViewById(R.id.textemailregis);
        edtAddress = findViewById(R.id.textaddressregis);
        edtPassword = findViewById(R.id.textpasswordregis);
        edtConfirmPassword = findViewById(R.id.textconfirmpasswordregis);
        btnconfirm = findViewById(R.id.buttonconfirmregis);
        btnreturn = findViewById(R.id.buttonreturnregis);
    }
}
