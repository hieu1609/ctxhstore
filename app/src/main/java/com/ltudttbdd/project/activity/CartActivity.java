package com.ltudttbdd.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ltudttbdd.project.R;
import com.ltudttbdd.project.adapter.CartAdapter;

import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity {

    ListView listViewCart;
    TextView txtNotification;
    TextView txtTotalPrice;
    Button btnCartPayment;
    Button btnReturnHome;
    Toolbar toolbarCart;
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Mappings();
        ActionToolbar();
        CheckData();
        EventUtil();
    }

    private void EventUtil() {
        long totalPrice = 0;
        for (int i = 0; i < MainActivity.arrayCart.size(); i++) {
            totalPrice += MainActivity.arrayCart.get(i).getPriceproduct();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTotalPrice.setText(decimalFormat.format(totalPrice) + "VNĐ");
    }

    private void CheckData() {
        if (MainActivity.arrayCart.size() <= 0) {
            cartAdapter.notifyDataSetChanged();
            txtNotification.setVisibility(View.VISIBLE);
            listViewCart.setVisibility(View.INVISIBLE);
        }
        else {
            cartAdapter.notifyDataSetChanged();
            txtNotification.setVisibility(View.INVISIBLE);
            listViewCart.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarCart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarCart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Mappings() {
        listViewCart = findViewById(R.id.listviewcart);
        txtNotification = findViewById(R.id.textviewnotification);
        txtTotalPrice = findViewById(R.id.textviewtotalprice);
        btnCartPayment = findViewById(R.id.buttoncartpayment);
        btnReturnHome = findViewById(R.id.buttonreturnhome);
        toolbarCart = findViewById(R.id.toolbarcart);
        cartAdapter = new CartAdapter(CartActivity.this, MainActivity.arrayCart);
        listViewCart.setAdapter(cartAdapter);
    }
}
