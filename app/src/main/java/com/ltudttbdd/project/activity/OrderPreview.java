package com.ltudttbdd.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

import static com.ltudttbdd.project.activity.MainActivity.isLogin;
import static com.ltudttbdd.project.activity.MainActivity.user;

public class OrderPreview extends AppCompatActivity {
    Toolbar toolbarlogged;
    Button btnReceived, btnConfirm, btnShipping, btncompleted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_preview);

                Mappings();
                ActionToolbar();
                if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                    EventButton();
                } else {
                    CheckConnection.ShowToastShort(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối Internet");
                }
            }

            private void EventButton() {
                btnReceived.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent edituser = new Intent(getApplicationContext(), Received.class);
                        startActivity(edituser);
                        finish();
                    }

            });
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent edituser = new Intent(getApplicationContext(), Confirm.class);
                        startActivity(edituser);
                        finish();
                    }
                });
                btnShipping.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent edituser = new Intent(getApplicationContext(), Shipping.class);
                        startActivity(edituser);
                        finish();
                    }
                });
                btncompleted.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent edituser = new Intent(getApplicationContext(), Completed.class);
                        startActivity(edituser);
                        finish();
                    }
                });

            }

            private void ActionToolbar() {
                setSupportActionBar(toolbarlogged);
                getSupportActionBar().setTitle("Xem đơn hàng");
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

                toolbarlogged = findViewById(R.id.toolbar);
                btnReceived = findViewById(R.id.received);
                btnConfirm = findViewById(R.id.comfirm);
                btnShipping = findViewById(R.id.shipping);
                btncompleted = findViewById(R.id.completed);

            }

}
