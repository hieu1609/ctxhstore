package com.ltudttbdd.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
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

public class Completed extends AppCompatActivity {
    Toolbar toolbar;
    ListView listview;
    ItemOrder OrderAdapter;
    ArrayList<Order> arrayOrdrer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

            Mappings();
            if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                Mappings();
                ActionToolbar();
                GetData();
            } else {
                CheckConnection.ShowToastShort(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối Internet");
                finish();
            }
        }
        private void GetData() {

            final HashMap<String, Object> postParams = new HashMap<String, Object>();
            final JSONObject jsonObject = new JSONObject(postParams);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Server.urlcompleted, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    int id = 0;
                    String name = "";
                    int count = 0;
                    int price = 0;
                    String date = "";
                    try {
                        JSONArray data = (JSONArray) response.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            try {
                                JSONObject item = (JSONObject) data.get(i);
                                id = item.getInt("order_id");
                                name = item.getString("product_name");
                                count = item.getInt("product_number");
                                price = item.getInt("product_price");
                                date = item.getString("created_at");
                                arrayOrdrer.add(new Order(4,id, name, count, price,date));
                                OrderAdapter.notifyDataSetChanged();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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
        }
        private void ActionToolbar() {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Đơn hàng đã hoàn thành");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Drawable newbackbtn = getResources().getDrawable(R.drawable.ic_back);
            getSupportActionBar().setHomeAsUpIndicator(newbackbtn);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent signin = new Intent(Completed.this, OrderPreview.class);
                    startActivity(signin);
                    finish();
                }
            });
    }
}
