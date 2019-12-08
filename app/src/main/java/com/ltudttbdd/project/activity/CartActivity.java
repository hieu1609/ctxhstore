package com.ltudttbdd.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ltudttbdd.project.R;
import com.ltudttbdd.project.adapter.CartAdapter;
import com.ltudttbdd.project.ultil.CheckConnection;

import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity {

    ListView listViewCart;
    static TextView txtTotalPrice, text;
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
        if(listViewCart != null) {
            text.setVisibility(View.GONE);
        }
        EventButton();
    }

    private void EventButton() {
        btnReturnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnCartPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.arrayCart.size() > 0) {
                    Intent intent = new Intent(getApplicationContext(), UserDetailActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    CheckConnection.ShowToastShort(getApplicationContext(), "Giỏ hàng của bạn đang trống");
                }
            }
        });
    }

    public static void EventUtil() {
        long totalPrice = 0;
        for (int i = 0; i < MainActivity.arrayCart.size(); i++) {
            totalPrice += MainActivity.arrayCart.get(i).getPriceproduct();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTotalPrice.setText(decimalFormat.format(totalPrice) + " Đ");
    }

    private void CheckData() {
        if (MainActivity.arrayCart.size() <= 0) {
            cartAdapter.notifyDataSetChanged();
            listViewCart.setVisibility(View.INVISIBLE);
        }
        else {
            cartAdapter.notifyDataSetChanged();
            listViewCart.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarCart);
        getSupportActionBar().setTitle("Giỏ hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable newbackbtn = getResources().getDrawable(R.drawable.ic_back);
        getSupportActionBar().setHomeAsUpIndicator(newbackbtn);
        toolbarCart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Mappings() {
        text = findViewById(R.id.text);
        listViewCart = findViewById(R.id.listviewcart);
        txtTotalPrice = findViewById(R.id.textviewtotalprice);
        btnCartPayment = findViewById(R.id.buttoncartpayment);
        btnReturnHome = findViewById(R.id.buttonreturnhome);
        toolbarCart = findViewById(R.id.toolbar);
        cartAdapter = new CartAdapter(CartActivity.this, MainActivity.arrayCart);
        listViewCart.setAdapter(cartAdapter);

    }
}
