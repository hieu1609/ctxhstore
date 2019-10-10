package com.ltudttbdd.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ltudttbdd.project.R;
import com.ltudttbdd.project.adapter.CartAdapter;
import com.ltudttbdd.project.ultil.CheckConnection;

import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity {

    ListView listViewCart;
    TextView txtNotification;
    static TextView txtTotalPrice;
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
        CatchOnItemListView();
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
                }
                else {
                    CheckConnection.ShowToastShort(getApplicationContext(), "Giỏ hàng của bạn đang trống");
                }
            }
        });
    }

    private void CatchOnItemListView() {
        listViewCart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this); /////??
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (MainActivity.arrayCart.size() <= 0) {
                            txtNotification.setVisibility(View.VISIBLE);
                        }
                        else {
                            MainActivity.arrayCart.remove(position);
                            cartAdapter.notifyDataSetChanged();
                            EventUtil();
                            if (MainActivity.arrayCart.size() <= 0) {
                                txtNotification.setVisibility(View.VISIBLE);
                            }
                            else {
                                txtNotification.setVisibility(View.INVISIBLE);
                                cartAdapter.notifyDataSetChanged();
                                EventUtil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        cartAdapter.notifyDataSetChanged();
                        EventUtil();
                    }
                });
                builder.show();
                return true;
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
