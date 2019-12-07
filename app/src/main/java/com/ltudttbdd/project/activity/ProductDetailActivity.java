package com.ltudttbdd.project.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ltudttbdd.project.R;
import com.ltudttbdd.project.adapter.CommentAdapter;
import com.ltudttbdd.project.model.Cart;
import com.ltudttbdd.project.model.Product;
import com.ltudttbdd.project.model.Rating;
import com.ltudttbdd.project.ultil.CheckConnection;
import com.ltudttbdd.project.ultil.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static com.ltudttbdd.project.activity.MainActivity.isLogin;

public class ProductDetailActivity extends AppCompatActivity {

    Toolbar toolbarproductdetail;
    ImageView imgproductdetail;
    TextView txtname, txtprice, txtdescription, txdanhgia;
    Spinner spinner;
    Button btnaddtocard, btrate;
    RatingBar rbProduct;
    Product arrayproduct;
    ListView listview;
    int page = 1;
    View footerview;
    boolean isLoading = false;
    boolean limitData = false;
    MyHandler myHandler;
    CommentAdapter commentAdapter;
    ArrayList<Rating> arrayrating;
    int id = 0;
    String productName = "";
    int price = 0;
    String productImage = "";
    String description = "";
    int idCategory = 0;
    float rate = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Mappings();
        ActionToolbar();
        GetInformation();
        CatchEventSpinner();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            GetData(page);

            LoadMoreData();
            EventButton();
        } else {
            CheckConnection.ShowToastShort(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối Internet");
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
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), CommentAdapter.class);
                intent.putExtra("thongtinsanpham", arrayrating.get(i));
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
                    ProductDetailActivity.ThreadData threadData = new ProductDetailActivity.ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void EventButton() {
        btnaddtocard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.arrayCart.size() > 0) {
                    int getnumber = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for (int i = 0; i < MainActivity.arrayCart.size(); i++) {
                        if (MainActivity.arrayCart.get(i).getIdproduct() == id) {
                            //cập nhật số lượng mới
                            MainActivity.arrayCart.get(i).setNumberproduct(MainActivity.arrayCart.get(i).getNumberproduct() + getnumber);
                            if (MainActivity.arrayCart.get(i).getNumberproduct() >= 10) {
                                MainActivity.arrayCart.get(i).setNumberproduct(10);
                            }
                            MainActivity.arrayCart.get(i).setPriceproduct(price * MainActivity.arrayCart.get(i).getNumberproduct());
                            exists = true;
                        }
                    }
                    if (exists == false) {
                        int number = Integer.parseInt(spinner.getSelectedItem().toString());
                        long newprice = number * price;
                        MainActivity.arrayCart.add(new Cart(id, productName, newprice, productImage, number));
                    }
                }
                else {
                    int number = Integer.parseInt(spinner.getSelectedItem().toString());
                    long newprice = number * price;
                    MainActivity.arrayCart.add(new Cart(id, productName, newprice, productImage, number));
                }
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin == false) {
                    Intent rate1 = new Intent(getApplicationContext(), SignInActivity.class);
                    startActivity(rate1);
                    finish();
                }
                else {
                    Intent rate1 = new Intent(getApplicationContext(), RatingActivity.class);
                    arrayproduct = new Product(id, productName, price, productImage, description, idCategory, rate);
                    rate1.putExtra("thongtinsanpham", arrayproduct);
                    startActivity(rate1);
                    finish();
                }
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] number = new Integer[] {1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, number);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformation() {


        Product product = (Product) getIntent().getSerializableExtra("thongtinsanpham");
        if(product.getRating() != 0){
            rate = product.getRating();
        }
        id = product.getId();
        productName = product.getProductName();
        price = product.getPrice();
        productImage = product.getProductImage();
        description = product.getDescription();
        idCategory = product.getIdCategory();
        txtname.setText(productName);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtprice.setText("Giá: " + decimalFormat.format(price) + " Đ");
        txtdescription.setText(description);
        Picasso.get().load(productImage)
                .placeholder(R.drawable.defaultimg)
                .error(R.drawable.errorimg)
                .into(imgproductdetail);
        rbProduct = findViewById(R.id.rb_lvi_product);
        if(rate == 0){
            rbProduct.setVisibility(View.GONE);
            txdanhgia.setVisibility(View.GONE);
        }
        else {
            rbProduct.setRating(rate);
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarproductdetail);
        getSupportActionBar().setTitle("Chi tiết sản phẩm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable newbackbtn = getResources().getDrawable(R.drawable.ic_back);
        getSupportActionBar().setHomeAsUpIndicator(newbackbtn);
        toolbarproductdetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void GetData(int page) {

        final HashMap<String, Object> postParams = new HashMap<String, Object>();
        postParams.put("productId", id);
        postParams.put("page", String.valueOf(page));
        final JSONObject jsonObject = new JSONObject(postParams);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Server.urlcomment, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String iduser = "";
                String comment = "";
                float r = 0;
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
                               iduser = item.getString("name");
                                comment = item.getString("comment");
                                r = (float) (item.getDouble("rating"));
                                arrayrating.add(new Rating(id, iduser, comment, r));
                                commentAdapter.notifyDataSetChanged();

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


        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void Mappings() {
        txdanhgia = findViewById(R.id.textdanhgia);
        toolbarproductdetail = findViewById(R.id.toolbar);
        imgproductdetail = findViewById(R.id.imageviewproductdetail);
        txtname = findViewById(R.id.textviewnameproductdetail);
        txtprice = findViewById(R.id.textviewpriceproductdetail);
        txtdescription = findViewById(R.id.texviewdescriptionproductdetail);
        txtdescription.setMovementMethod(new ScrollingMovementMethod());
        spinner = findViewById(R.id.spinnerproductdetail);
        btnaddtocard = findViewById(R.id.buttonproductdetail);
        btrate = findViewById(R.id.buttonrate);
        rbProduct = findViewById(R.id.rb_lvi_product);
        listview = findViewById(R.id.lv_act_produclist);
        arrayrating = new ArrayList<>();
        commentAdapter = new CommentAdapter(getApplicationContext(), arrayrating);
        listview.setAdapter(commentAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progress_bar, null);
        myHandler = new MyHandler();
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
