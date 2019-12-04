package com.ltudttbdd.project.activity;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.camera2.CaptureResult;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import com.ltudttbdd.project.model.Product;
import com.ltudttbdd.project.ultil.CheckConnection;
import com.ltudttbdd.project.ultil.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import static com.ltudttbdd.project.activity.MainActivity.isLogin;
import static com.ltudttbdd.project.activity.MainActivity.user;

public class RatingActivity extends AppCompatActivity {
    Product arrayproduct;
    Toolbar toolbarproductdetail;
    ImageView imgproductdetail;
    TextView txtname, txtprice, txrate;
    EditText txcmt;
    RatingBar rbProduct, rbrating;
    Button btrating;
    int id = 0;
    String productName = "";
    int price = 0;
    String productImage = "";
    int idCategory = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        Mappings();
        rateBarClick();
        ActionToolbar();
        GetInformation();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            EventButton();
        } else {
            CheckConnection.ShowToastShort(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối Internet");
        }

    }

    private void GetInformation() {
        Product product = (Product) getIntent().getSerializableExtra("thongtinsanpham");
        float rate = 0;
        if(product.getRating() != 0){
            rate = product.getRating();
        }
        id = product.getId();
        productName = product.getProductName();
        price = product.getPrice();
        productImage = product.getProductImage();
        idCategory = product.getIdCategory();
        txtname.setText(productName);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtprice.setText("Giá: " + decimalFormat.format(price) + " Đ");
        Picasso.get().load(productImage)
                .placeholder(R.drawable.defaultimg)
                .error(R.drawable.errorimg)
                .into(imgproductdetail);

        if(rate == 0){
            rbProduct.setVisibility(View.GONE);
        }
        else {
            rbProduct.setRating(rate);
        }

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarproductdetail);
        getSupportActionBar().setTitle("đánh giá sản phẩm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable newbackbtn = getResources().getDrawable(R.drawable.ic_back);
        getSupportActionBar().setHomeAsUpIndicator(newbackbtn);
        toolbarproductdetail.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) { finish();
                    }
        });
    }

    private void EventButton() {
        btrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String cmt = txcmt.getText().toString();
                final float rating = rbrating.getRating();
                if ( rating > 0) {
                    final HashMap<String, Object> postParams = new HashMap<String, Object>();
                    postParams.put("productId", id);
                    postParams.put("rating", rating);
                    postParams.put("comment", cmt);
                    final JSONObject jsonObject = new JSONObject(postParams);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Server.urlrating, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(RatingActivity.this, "Cảm ơn sự đánh giá của bạn", Toast.LENGTH_LONG).show();
//                            Intent logger = new Intent(RatingActivity.this, ProductDetailActivity.class);
//                            arrayproduct.rating = (arrayproduct.rating + rbrating.getRating()) / 2;
//                            logger.putExtra("thongtinsanpham", arrayproduct);
//                            startActivity(logger);
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
                }
            }
        });
    }

            private void rateBarClick(){ rbrating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    txrate.setText(String.valueOf(rating));
                 }
                });
            }

            private void Mappings() {
                toolbarproductdetail = findViewById(R.id.toolbar);
                imgproductdetail = findViewById(R.id.imageviewproductdetail);
                txtname = findViewById(R.id.textviewnameproductdetail);
                txtprice = findViewById(R.id.textviewpriceproductdetail);
                rbrating = findViewById(R.id.rating);
                btrating = findViewById(R.id.buttonrate);
                txcmt = findViewById(R.id.editcmt);
                rbProduct = findViewById(R.id.rb_lvi_product);
                txrate = findViewById(R.id.rate);
            }

}
