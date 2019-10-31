package com.ltudttbdd.project.activity;

import android.content.Intent;
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
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ltudttbdd.project.R;
import com.ltudttbdd.project.adapter.PhoneAdapter;
import com.ltudttbdd.project.model.Product;
import com.ltudttbdd.project.ultil.CheckConnection;
import com.ltudttbdd.project.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhoneActivity extends AppCompatActivity {

    Toolbar toolbardrink;
    ListView listviewdrink;
    PhoneAdapter drinkAdapter;
    ArrayList<Product> arraydrink;
    int idphone = 0;
    int page = 1;
    View footerview;
    boolean isLoading = false;
    boolean limitData = false;
    MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        Mappings();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            GetIdProductCategory();
            ActionToolbar();
            GetData();
            LoadMoreData();
        } else {
            CheckConnection.ShowToastShort(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối Internet");
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menucart:
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void LoadMoreData() {
        listviewdrink.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ProductDetailActivity.class);
                intent.putExtra("thongtinsanpham", arraydrink.get(i));
                startActivity(intent);
            }
        });
        listviewdrink.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItem, int totalItem) {
                if (firstItem + visibleItem == totalItem && totalItem != 0 && isLoading == false && limitData == false) {
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void GetData() {
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        String url = Server.urlPhone;
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                int id = 0;
//                String productName = "";
//                int price = 0;
//                String productImage = "";
//                String description = "";
//                int idCategory = 0;
//                if (response != null && response.length() != 2) {
//                    listviewfood.removeFooterView(footerview);
//                    try {
//                        JSONArray jsonArray = new JSONArray(response);
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            id = jsonObject.getInt("id");
//                            productName = jsonObject.getString("product_name");
//                            price = jsonObject.getInt("price");
//                            productImage = jsonObject.getString("product_image");
//                            description = jsonObject.getString("description");
//                            idCategory = jsonObject.getInt("id_category");
//                            arrayfood.add(new Product(id, productName, price, productImage, description, idCategory));
//                            foodAdapter.notifyDataSetChanged();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                else {
//                    limitData = true;
//                    listviewfood.removeFooterView(footerview);
//                    CheckConnection.ShowToastShort(getApplicationContext(), "Đã hết dữ liệu");
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> param = new HashMap<String, String>();
//                param.put("categoryId", "2");
//                return param;
//            }
//        };
//        requestQueue.add(stringRequest);
        final HashMap<String, String> postParams = new HashMap<String, String>();
        postParams.put("categoryId", "2");
        final JSONObject jsonObject = new JSONObject(postParams);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Server.urlPhone, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int id = 0;
                String productName = "";
                int price = 0;
                String productImage = "";
                String description = "";
                int idCategory = 0;
                if (response != null) {
                    listviewdrink.removeFooterView(footerview);
                    try {
                        JSONArray data = (JSONArray) response.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            try {
                                JSONObject item = (JSONObject) data.get(i);
                                id = item.getInt("id");
                                productName = item.getString("product_name");
                                price = item.getInt("price");
                                productImage = item.getString("product_image");
                                description = item.getString("description");
                                idCategory = item.getInt("id_category");
                                arraydrink.add(new Product(id, productName, price, productImage, description, idCategory));
                                drinkAdapter.notifyDataSetChanged();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    limitData = true;
                    listviewdrink.removeFooterView(footerview);
                    CheckConnection.ShowToastShort(getApplicationContext(), "Đã hết dữ liệu");
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


        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
//    private void GetData(int Page) {
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        String url = Server.urlPhone;
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                int id = 0;
//                String productName = "";
//                int price = 0;
//                String productImage = "";
//                String description = "";
//                int idCategory = 0;
//                if (response != null && response.length() != 2) {
//                    listviewphone.removeFooterView(footerview);
//                    try {
//                        JSONArray jsonArray = new JSONArray(response);
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            id = jsonObject.getInt("id");
//                            productName = jsonObject.getString("product_name");
//                            price = jsonObject.getInt("price");
//                            productImage = jsonObject.getString("product_image");
//                            description = jsonObject.getString("description");
//                            idCategory = jsonObject.getInt("id_category");
//                            arrayphone.add(new Product(id, productName, price, productImage, description, idCategory));
//                            phoneAdapter.notifyDataSetChanged();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                else {
//                    limitData = true;
//                    listviewphone.removeFooterView(footerview);
//                    CheckConnection.ShowToastShort(getApplicationContext(), "Đã hết dữ liệu");
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> param = new HashMap<String, String>();
//                param.put("categoryId", String.valueOf(idphone));
//                return param;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }

    private void ActionToolbar() {
        setSupportActionBar(toolbardrink);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardrink.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void GetIdProductCategory() {
        idphone = getIntent().getIntExtra("idCategory", -1);
        Log.d("giatriloaisanpham", idphone + "");
    }

    private void Mappings() {
        toolbardrink = findViewById(R.id.toolbarphone);
        listviewdrink = findViewById(R.id.listviewphone);
        arraydrink = new ArrayList<>();
        drinkAdapter = new PhoneAdapter(getApplicationContext(), arraydrink);
        listviewdrink.setAdapter(drinkAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progress_bar, null);
        myHandler = new MyHandler();
    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:
                  //  listviewdrink.addFooterView(footerview);
                    break;
                case 1:
                    //GetData();
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
