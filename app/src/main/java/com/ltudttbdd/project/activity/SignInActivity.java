package com.ltudttbdd.project.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import static com.ltudttbdd.project.activity.MainActivity.isLogin;
import static com.ltudttbdd.project.activity.MainActivity.user;

public class SignInActivity extends AppCompatActivity {

    EditText edtPassword, edtEmail;
    Button btnconfirm, btnsignup;
    Toolbar toolbarsignin;
    //private View alphaColorView;
    int numOfItem, orderId;
    int numRes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);
        Mappings();
        toolbarsignin = findViewById(R.id.toolbar);
        ActionBar();
        //alphaColorView = (View)findViewById(R.id.relativelayout);
        //alphaColorView.setBackgroundColor(getColorWithAlpha(Color.BLACK, 0.2f));
       // btnreturn.setOnClickListener(new View.OnClickListener() {
            //@Override
           // public void onClick(View v) {
         //       finish();
         //   }
       // });
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(signup);
                finish();
            }
        });
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            EventButton();
        } else {
            CheckConnection.ShowToastShort(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối Internet");
        }
    }

    private void ActionBar() {
        setSupportActionBar(toolbarsignin);
        getSupportActionBar().setTitle("Đăng nhập");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable newbackbtn = getResources().getDrawable(R.drawable.ic_back);
        getSupportActionBar().setHomeAsUpIndicator(newbackbtn);
        toolbarsignin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //private int getColorWithAlpha(int color, float ratio) {
      //  int newColor = 0;
        //int alpha = Math.round(Color.alpha(color) * ratio);
        //int r = Color.red(color);
        //int g = Color.green(color);
        //int b = Color.blue(color);
        //newColor = Color.argb(alpha, r, g, b);
        //return newColor;
    //}

    private void EventButton() {
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bỏ đi khoảng trắng trim()

                final String email = edtEmail.getText().toString().trim();
                final String password = edtPassword.getText().toString().trim();
                if (email.length() > 0 && password.length() > 0) {

                    final HashMap<String, String> postParams = new HashMap<String, String>();

                    postParams.put("email", edtEmail.getText().toString());
                    postParams.put("password", edtPassword.getText().toString());

                    final JSONObject jsonObject = new JSONObject(postParams);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Server.urlLogin, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONObject data = (JSONObject) response.getJSONObject("data");
                                try {
                                    JSONObject userres = (JSONObject) data.getJSONObject("user");
                                    String usertoken = (String) data.getString("token");
                                    int userid = userres.getInt("id");
                                    String useremail = userres.getString("email");
                                    String username = userres.getString("name");
                                    String userphone = userres.getString("phone");
                                    String useraddress = userres.getString("address");

                                    user = new User(userid,useremail,username,userphone,useraddress,usertoken);
                                    Toast.makeText(SignInActivity.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                                    isLogin = true;
                                    Intent login = new Intent(getApplicationContext(), LoggedActivity.class);
                                    startActivity(login);
                                    finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("test", String.valueOf(error.networkResponse.statusCode));
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

        edtEmail = findViewById(R.id.textemail);
        edtPassword = findViewById(R.id.textpassword);
        btnconfirm = findViewById(R.id.confirmbutton);
        btnsignup = findViewById(R.id.signupbutton);

    }
}
