package com.ltudttbdd.project.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.ltudttbdd.project.R;
import com.ltudttbdd.project.adapter.ProductAdapter;
import com.ltudttbdd.project.adapter.ProductCategoryAdapter;
import com.ltudttbdd.project.model.Cart;
import com.ltudttbdd.project.model.Product;
import com.ltudttbdd.project.model.ProductCategory;
import com.ltudttbdd.project.ultil.CheckConnection;
import com.ltudttbdd.project.ultil.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewmanhinhchinh;
    NavigationView navigationView;
    ListView listViewmanhinhchinh;
    DrawerLayout drawerLayout;
    ArrayList<ProductCategory> arrayProductCategory;
    ProductCategoryAdapter productCategoryAdapter;
    ArrayList<Product> arrayProduct;
    ArrayList<String> mangquangcao;
    ProductAdapter productAdapter;
    //khai báo static để tất cả có thể sử dụng, không mất dữ liệu khi chuyển qua activity khác
    public static ArrayList<Cart> arrayCart;
    int id = 0;
    String categoryName = "";
    String categoryImage = "";
    String adsImage = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mappings();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            ActionBar();
            ActionViewFlipper();
            GetDataProductCategory();
            GetDataNewProduct();
            listViewmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (i) {
                        case 0:
                            break;
                        case 1:
                            Intent foodIntent = new Intent(MainActivity.this, FoodActivity.class);
                            startActivity(foodIntent);
                            break;
                        case 2:
                            Intent drinkIntent = new Intent(MainActivity.this, DrinkActivity.class);
                            startActivity(drinkIntent);
                            break;
                        case 3:
                            Intent braceletIntent = new Intent(MainActivity.this,BraceletActivity.class);
                            startActivity(braceletIntent);
                            break;

                        case 4:
                            Intent keychainstIntent = new Intent(MainActivity.this,KeyChainsActivity.class);
                            startActivity(keychainstIntent);
                            break;
                        case 5:
                            Intent giftIntent = new Intent(MainActivity.this,GiftActivity.class);
                            startActivity(giftIntent);
                            break;
                        case 6:
                            Intent anotherIntent = new Intent(MainActivity.this,GiftActivity.class);
                            startActivity(anotherIntent);
                            break;
                        case 7:
                            Intent contactIntent = new Intent(MainActivity.this,KeyChainsActivity.class);
                            startActivity(contactIntent);
                            break;
                        case 8:
                            Intent infomationIntent = new Intent(MainActivity.this,GiftActivity.class);
                            startActivity(infomationIntent);
                            break;


                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            });
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

    private void GetDataNewProduct() {
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlNewProduct, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                if(response != null) {
//                    int id = 0;
//                    String productName = "";
//                    int price = 0;
//                    String productImage = "";
//                    String description = "";
//                    int idCategory = 0;
//                    for (int i = 0; i < response.length(); i++) {
//                        try {
//                            JSONObject jsonObject = response.getJSONObject(i);
//                            id = jsonObject.getInt("id");
//                            productName = jsonObject.getString("product_name");
//                            price = jsonObject.getInt("price");
//                            productImage = jsonObject.getString("product_image");
//                            description = jsonObject.getString("description");
//                            idCategory = jsonObject.getInt("id_category");
//                            arrayProduct.add(new Product(id, productName, price, productImage, description, idCategory));
//                            productAdapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        requestQueue.add(jsonArrayRequest);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Server.urlNewProduct, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response != null) {
                        int id = 0;
                        String productName = "";
                        int price = 0;
                        String productImage = "";
                        String description = "";
                        int idCategory = 0;
                        JSONArray data = (JSONArray) response.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            try {
                                JSONObject newItem = (JSONObject) data.get(i);
                                id = newItem.getInt("id");
                                productName = newItem.getString("product_name");
                                price = newItem.getInt("price");
                                productImage = newItem.getString("product_image");
                                description = newItem.getString("description");
                                idCategory = newItem.getInt("id_category");
                                arrayProduct.add(new Product(id, productName, price, productImage, description, idCategory));
                                productAdapter.notifyDataSetChanged();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void GetDataProductCategory() {
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlProductCategory, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                if(response != null) {
//                    for (int i = 0; i < response.length(); i++) {
//                        try {
//                            JSONObject jsonObject = response.getJSONObject(i);
//                            id = jsonObject.getInt("id");
//                            categoryName = jsonObject.getString("category_name");
//                            categoryImage = jsonObject.getString("category_image");
//                            arrayProductCategory.add(new ProductCategory(id, categoryName, categoryImage));
//                            productCategoryAdapter.notifyDataSetChanged(); //update data
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    arrayProductCategory.add(4, new ProductCategory(0, "Liên hệ", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWq2hS1q3YW7MStkX9jyfEqYg3jMmftZ82J7az5oN-thj0oycsnw"));
//                    arrayProductCategory.add(5, new ProductCategory(0, "Thông tin", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQmOD06az3sOuJf2IfL4UTSvQkUalSFM-AJjoV8C7CeN-YjtEu9"));
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                CheckConnection.ShowToastShort(getApplicationContext(), error.toString());
//                Log.d("Error Response", error.toString());
//            }
//        });
//        requestQueue.add(jsonArrayRequest);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Server.urlProductCategory, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = (JSONArray) response.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        try {
                            JSONObject category = (JSONObject) data.get(i);
                            id = category.getInt("id");
                            categoryName = category.getString("category_name");
                            categoryImage = category.getString("category_image");
                            arrayProductCategory.add(new ProductCategory(id, categoryName, categoryImage));
                            productCategoryAdapter.notifyDataSetChanged(); //update data

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    arrayProductCategory.add(data.length()+1, new ProductCategory(0, "Liên hệ", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWq2hS1q3YW7MStkX9jyfEqYg3jMmftZ82J7az5oN-thj0oycsnw"));
                    arrayProductCategory.add(data.length() + 2, new ProductCategory(0, "Thông tin", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQmOD06az3sOuJf2IfL4UTSvQkUalSFM-AJjoV8C7CeN-YjtEu9"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void ActionViewFlipper() {
        mangquangcao = new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Server.urlAds, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = (JSONArray) response.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        try {
                            JSONObject ads = (JSONObject) data.get(i);
                            adsImage = ads.getString("image");
                            mangquangcao.add(adsImage);
                            ImageView imageView = new ImageView(getApplicationContext());
                            Picasso.get().load(mangquangcao.get(i)).into(imageView);
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            viewFlipper.addView(imageView);
                            productCategoryAdapter.notifyDataSetChanged(); //update data

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
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
        //Toast.makeText(MainActivity.this, mangquangcao.get(0), Toast.LENGTH_LONG).show();

        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);

    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Mappings() {
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        recyclerViewmanhinhchinh = (RecyclerView) findViewById(R.id.recyclerview);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        listViewmanhinhchinh = (ListView) findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        arrayProductCategory = new ArrayList<>();
        arrayProductCategory.add(0, new ProductCategory(0, "Trang chính", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSHsXduuMdIQh-cyva9fLRDIt6-zBH_Dr0AwEmzdFFpgKKHBjBWgg"));
        productCategoryAdapter = new ProductCategoryAdapter(arrayProductCategory, getApplicationContext());
        listViewmanhinhchinh.setAdapter(productCategoryAdapter);
        arrayProduct = new ArrayList<>();
        productAdapter = new ProductAdapter(getApplicationContext(), arrayProduct);
        recyclerViewmanhinhchinh.setHasFixedSize(true);
        recyclerViewmanhinhchinh.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerViewmanhinhchinh.setAdapter(productAdapter);
        if (arrayCart != null) {

        } else {
            arrayCart = new ArrayList<>();
        }
    }

}
