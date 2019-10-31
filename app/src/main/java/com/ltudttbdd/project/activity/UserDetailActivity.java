package com.ltudttbdd.project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ltudttbdd.project.R;
import com.ltudttbdd.project.model.Product;
import com.ltudttbdd.project.model.ProductCategory;
import com.ltudttbdd.project.ultil.CheckConnection;
import com.ltudttbdd.project.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserDetailActivity extends AppCompatActivity {

    EditText edtName, edtPhone, edtEmail, edtAddress;
    Button btnconfirm, btnreturn;
    int numOfItem, orderId;
    int numRes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
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
                if (name.length() > 0 && phone.length() > 0 && email.length() > 0 && address.length() > 0) {
                    //truyền gửi dữ liệu ra ngoài (phương thức, đường dẫn, nội dung)
//                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlOrder, new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(final String orderId) {
//                            Log.d("madonhang", orderId);
//                            if (Integer.parseInt(orderId) > 0) {
//                                RequestQueue queue =  Volley.newRequestQueue(getApplicationContext());
//                                StringRequest request = new StringRequest(Request.Method.POST, Server.urlOrderDetail, new Response.Listener<String>() {
//                                    @Override
//                                    public void onResponse(String response) {
//                                        if (response.equals("1")) {
//                                            MainActivity.arrayCart.clear();
//                                            CheckConnection.ShowToastShort(getApplicationContext(), "Bạn đã đặt hàng thành công");
////                                            Intent intent = new Intent();
////                                            intent.setClass(getApplicationContext(), MainActivity.class);
////                                            startActivity(intent);
//                                            finish();
//                                            CheckConnection.ShowToastShort(getApplicationContext(), "Mời bạn tiếp tục mua hàng");
//                                        }
//                                        else {
//                                            CheckConnection.ShowToastShort(getApplicationContext(), "Dữ liệu giỏ hàng của bạn bị lỗi");
//                                        }
//                                    }
//                                }, new Response.ErrorListener() {
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//                                    }
//                                }){
//                                    @Override
//                                    protected Map<String, String> getParams() throws AuthFailureError {
//                                        JSONArray jsonArray = new  JSONArray();
//                                        for (int i = 0; i < MainActivity.arrayCart.size(); i++) {
//                                            JSONObject jsonObject = new JSONObject();
//                                            try {
//                                                jsonObject.put("order_id", orderId);
//                                                jsonObject.put("product_id", MainActivity.arrayCart.get(i).getIdproduct());
//                                                jsonObject.put("product_name", MainActivity.arrayCart.get(i).getNameproduct());
//                                                jsonObject.put("product_price", MainActivity.arrayCart.get(i).getPriceproduct());
//                                                jsonObject.put("product_number", MainActivity.arrayCart.get(i).getNumberproduct());
//                                            } catch (JSONException e) {
//                                                e.printStackTrace();
//                                            }
//                                            jsonArray.put(jsonObject);
//                                        }
//                                        HashMap <String, String> hashMap = new HashMap<String, String>();
//                                        hashMap.put("json", jsonArray.toString());
//                                        return hashMap;
//                                    }
//                                };
//                                queue.add(request);
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//
//                        }
//                    }){
//                        @Override
//                        protected Map<String, String> getParams() throws AuthFailureError {
//                            //thêm các biến và giá trị để gửi đi
//                            HashMap<String, String> hashMap = new HashMap<String, String>();
//                            hashMap.put("name", name);
//                            hashMap.put("phone", phone);
//                            hashMap.put("email", email);
//                            hashMap.put("address", address);
//                            return hashMap;
//                        }
//                    };
//                    requestQueue.add(stringRequest);
                    final HashMap<String, String> postParams = new HashMap<String, String>();
                    postParams.put("name", edtName.getText().toString());
                    postParams.put("phone", edtPhone.getText().toString());
                    postParams.put("address", edtAddress.getText().toString());
                    postParams.put("email", edtEmail.getText().toString());

                    final JSONObject jsonObject = new JSONObject(postParams);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Server.urlOrder, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONArray data = (JSONArray) response.getJSONArray("data");

                                    try {
                                        JSONObject id = (JSONObject) data.get(0);
                                        orderId = id.getInt("id");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            for (numOfItem = 0; numOfItem < MainActivity.arrayCart.size(); numOfItem++) {
                                final HashMap<String, String> postParams = new HashMap<String, String>();
                                postParams.put("orderId", String.valueOf(orderId));
                                postParams.put("productId", String.valueOf(MainActivity.arrayCart.get(numOfItem).getIdproduct()));
                                postParams.put("productName", String.valueOf(MainActivity.arrayCart.get(numOfItem).getNameproduct()));
                                postParams.put("productPrice", String.valueOf(MainActivity.arrayCart.get(numOfItem).getPriceproduct()));
                                postParams.put("productNumber", String.valueOf(MainActivity.arrayCart.get(numOfItem).getNumberproduct()));

                                final JSONObject jsonObject = new JSONObject(postParams);
                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Server.urlOrderDetail, jsonObject, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        numRes++;
                                        Log.d("huhuhuhhtttt",  String.valueOf(numRes));
                                        if (MainActivity.arrayCart.size() == numRes) {
                                            MainActivity.arrayCart.clear();
                                            numRes = 0;
                                            Log.d("huhuhu", "dat thanh cong ");
                                            CheckConnection.ShowToastShort(getApplicationContext(), "Bạn đã đặt hàng thành công");
                                            Intent intent = new Intent();
                                            intent.setClass(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            CheckConnection.ShowToastShort(getApplicationContext(), "Mời bạn tiếp tục mua hàng");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {


                                        Toast.makeText(UserDetailActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                                    }
                                }) {

                                    @Override
                                    public String getBodyContentType() {
                                        return "application/json; charset=utf-8";
                                    }


                                };
                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                requestQueue.add(jsonObjectRequest);
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
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(jsonObjectRequest);
                } else {
                    CheckConnection.ShowToastShort(getApplicationContext(), "Bạn hãy kiểm tra lại dữ liệu");
                }
            }
        });
    }

    private void Mappings() {
        edtName = findViewById(R.id.edittextusername);
        edtPhone = findViewById(R.id.edittextphonenumber);
        edtEmail = findViewById(R.id.edittextemail);
        edtAddress = findViewById(R.id.edittextaddress);
        btnconfirm = findViewById(R.id.buttonconfirm);
        btnreturn = findViewById(R.id.buttonreturn);


    }
}
