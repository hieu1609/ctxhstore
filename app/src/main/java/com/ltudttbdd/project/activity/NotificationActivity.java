package com.ltudttbdd.project.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ltudttbdd.project.R;
import com.ltudttbdd.project.adapter.CartAdapter;
import com.ltudttbdd.project.adapter.NotificationAdapter;
import com.ltudttbdd.project.adapter.ProductViewAdapter;
import com.ltudttbdd.project.model.Notify;
import com.ltudttbdd.project.model.Product;
import com.ltudttbdd.project.ultil.CheckConnection;
import com.ltudttbdd.project.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.ltudttbdd.project.activity.MainActivity.user;

public class NotificationActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listview;
    NotificationAdapter notifyAdapter;
    ArrayList<Notify> arraynotify;
    View footerview;
    boolean isLoading = false;
    boolean limitData = false;
    NotificationActivity.MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bracelet);
        setContentView(R.layout.activity_product_list);
        Mappings();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            TriggerData();
            ActionToolbar();
            GetData();
        } else {
            CheckConnection.ShowToastShort(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối Internet");
            finish();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menucart:
//                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
//                startActivity(intent);
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
    private void TriggerData() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listview.setBackgroundResource(R.color.white);
                ChangeStatusNotify();
            }
        });
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItem, int totalItem) {
                if (firstItem + visibleItem == totalItem && totalItem != 0 && isLoading == false && limitData == false) {
                    isLoading = true;
                    NotificationActivity.ThreadData threadData = new NotificationActivity.ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void ChangeStatusNotify(){
        final HashMap<String, String> postParams = new HashMap<String, String>();
        postParams.put("notificationID", "1");//ko hiue
        final JSONObject jsonObject = new JSONObject(postParams);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, Server.urlSeenNotification, jsonObject , new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response){

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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void GetData() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Server.urlGetNotification, null , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String title, content;
                int seen;
                if (response != null) {
                    listview.removeFooterView(footerview);
                    try {
                        JSONArray data = (JSONArray) response.getJSONArray("data");
                        if(data.length() == 0){
                            limitData = true;
                            listview.removeFooterView(footerview);
                            CheckConnection.ShowToastShort(getApplicationContext(), "Đã hết dữ liệu");
                            return;
                        }
                        for (int i = 0; i < data.length(); i++) {
                            try {
                                JSONObject item = (JSONObject) data.get(i);
                                title = item.getString("title");
                                content = item.getString("content");
                                seen = item.getInt("seen");
                                arraynotify.add(new Notify(title, content, seen));
                                notifyAdapter.notifyDataSetChanged();
                                Log.d("nooti", "onResponse: " + title + " " + content + " " + seen);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thông báo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable newbackbtn = getResources().getDrawable(R.drawable.ic_back);
        getSupportActionBar().setHomeAsUpIndicator(newbackbtn);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

//    private void GetIdProductCategory() {
//        idbracelet = getIntent().getIntExtra("idCategory", -1);
//        Log.d("giatriloaisanpham", idbracelet + "");
//    }


    private void Mappings() {
        toolbar = findViewById(R.id.toolbar);
        listview = findViewById(R.id.lv_act_produclist);
        arraynotify = new ArrayList<>();
        notifyAdapter = new NotificationAdapter(arraynotify, getApplicationContext());
        listview.setAdapter(notifyAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progress_bar, null);
        myHandler = new NotificationActivity.MyHandler();
    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:
                    listview.addFooterView(footerview);
                    break;
                case 1:
                    GetData();
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread {
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = myHandler.obtainMessage(1);
            myHandler.sendMessage(message);
            super.run();
        }
    }
}
