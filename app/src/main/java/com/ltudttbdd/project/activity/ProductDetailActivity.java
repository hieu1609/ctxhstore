package com.ltudttbdd.project.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ltudttbdd.project.R;
import com.ltudttbdd.project.model.Cart;
import com.ltudttbdd.project.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ProductDetailActivity extends AppCompatActivity {

    Toolbar toolbarproductdetail;
    ImageView imgproductdetail;
    TextView txtname, txtprice, txtdescription;
    Spinner spinner;
    Button btnaddtocard;
    int id = 0;
    String productName = "";
    int price = 0;
    String productImage = "";
    String description = "";
    int idCategory = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Mappings();
        ActionToolbar();
        GetInformation();
        CatchEventSpinner();
        EventButton();
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
                            if (MainActivity.arrayCart.get(i).getNumberproduct() >= 30) {
                                MainActivity.arrayCart.get(i).setNumberproduct(30);
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
    }

    private void CatchEventSpinner() {
        Integer[] number = new Integer[] {1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, number);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformation() {
        Product product = (Product) getIntent().getSerializableExtra("thongtinsanpham");
        id = product.getId();
        productName = product.getProductName();
        price = product.getPrice();
        productImage = product.getProductImage();
        description = product.getDescription();
        idCategory = product.getIdCategory();
        txtname.setText(productName);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtprice.setText("Giá: " + decimalFormat.format(price) + " VNĐ");
        txtdescription.setText(description);
        Picasso.get().load(productImage)
                .placeholder(R.drawable.noimg)
                .error(R.drawable.errorimg)
                .into(imgproductdetail);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarproductdetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarproductdetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Mappings() {
        toolbarproductdetail = findViewById(R.id.toolbarproductdetail);
        imgproductdetail = findViewById(R.id.imageviewproductdetail);
        txtname = findViewById(R.id.textviewnameproductdetail);
        txtprice = findViewById(R.id.textviewpriceproductdetail);
        txtdescription = findViewById(R.id.texviewdescriptionproductdetail);
        spinner = findViewById(R.id.spinnerproductdetail);
        btnaddtocard = findViewById(R.id.buttonproductdetail);
    }
}
