package com.ltudttbdd.project.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ltudttbdd.project.R;
import com.ltudttbdd.project.adapter.ItemOrder;
import com.ltudttbdd.project.model.Order;
import com.ltudttbdd.project.ultil.CheckConnection;
import com.ltudttbdd.project.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.ltudttbdd.project.activity.MainActivity.user;

public class Shipping extends AppCompatActivity {
    Toolbar toolbar;
    ListView listview;
    ItemOrder OrderAdapter;
    ArrayList<Order> arrayOrdrer;
    View footerview;
    MyHandler myHandler;
    boolean isLoading = false;
    boolean limitData = false;
    int page = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

            Mappings();
            if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                Mappings();
                ActionToolbar();
                GetData(page);
                LoadMoreData();
            } else {
                CheckConnection.ShowToastShort(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối Internet");
                finish();
            }
        }
    private void LoadMoreData() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ItemOrder.class);
                intent.putExtra("thongtinsanpham", arrayOrdrer.get(i));
                startActivity(intent);
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
                    Shipping.ThreadData threadData = new Shipping.ThreadData();
                    threadData.start();
                }
            }
        });
    }
        private void GetData(int page) {

            final HashMap<String, Object> postParams = new HashMap<String, Object>();
            postParams.put("page", String.valueOf(page));
            final JSONObject jsonObject = new JSONObject(postParams);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Server.urlshipping, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    int id = 0;
                    String name = "";
                    int count = 0;
                    int price = 0;
                    String date = "";
                    if (response != null) {
                        listview.removeFooterView(footerview);
                        try {
                            JSONArray data = (JSONArray) response.getJSONArray("data");
                            if (data.length() == 0) {
                                limitData = true;
                                listview.removeFooterView(footerview);
                                CheckConnection.ShowToastShort(getApplicationContext(), "Đã hết dữ liệu");
                                return;
                            }
                            for (int i = 0; i < data.length(); i++) {
                                try {
                                    JSONObject item = (JSONObject) data.get(i);
                                    id = item.getInt("order_id");
                                    name = item.getString("product_name");
                                    count = item.getInt("product_number");
                                    price = item.getInt("product_price");
                                    date = item.getString("created_at");
                                    arrayOrdrer.add(new Order(3, id, name, count, price, date));
                                    OrderAdapter.notifyDataSetChanged();

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
        private void Mappings() {
            toolbar = findViewById(R.id.toolbar);
            listview = findViewById(R.id.lv_act_produclist);
            arrayOrdrer = new ArrayList<>();
            OrderAdapter = new ItemOrder(getApplicationContext(), arrayOrdrer);
            listview.setAdapter(OrderAdapter);
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            footerview = inflater.inflate(R.layout.progress_bar, null);
            myHandler = new MyHandler();
        }
        private void ActionToolbar() {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Đơn hàng đang vận chuyển");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Drawable newbackbtn = getResources().getDrawable(R.drawable.ic_back);
            getSupportActionBar().setHomeAsUpIndicator(newbackbtn);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent signin = new Intent(Shipping.this, OrderPreview.class);
                    startActivity(signin);
                    finish();
                }
            });
         }
    public class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:
                    listview.addFooterView(footerview);
                    break;
                case 1:
                    GetData(++page);
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
