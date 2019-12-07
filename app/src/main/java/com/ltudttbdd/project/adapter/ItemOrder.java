package com.ltudttbdd.project.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import com.ltudttbdd.project.activity.CartActivity;
import com.ltudttbdd.project.activity.Confirm;
import com.ltudttbdd.project.activity.MainActivity;
import com.ltudttbdd.project.activity.Received;
import com.ltudttbdd.project.activity.SignUpActivity;
import com.ltudttbdd.project.model.Order;
import com.ltudttbdd.project.ultil.CheckConnection;
import com.ltudttbdd.project.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.ltudttbdd.project.activity.MainActivity.user;


public class ItemOrder extends BaseAdapter {
    Context context;
    ArrayList<Order> arrayOrder;

    public ItemOrder(Context context, ArrayList<Order> arrayproduct) {
        this.context = context;
        this.arrayOrder = arrayproduct;

    }

    @Override
    public int getCount() {
        return arrayOrder.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayOrder.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHoler {
        public TextView txorder, txname, txcount, txprice, txdate;
        public Button imgRemove;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHoler viewHoler = null;

        if (view == null) {
            viewHoler = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_order, null);
            viewHoler.txorder = view.findViewById(R.id.id);
            viewHoler.txname = view.findViewById(R.id.tv1_lvi_product1);
            viewHoler.txcount = view.findViewById(R.id.count1);
            viewHoler.txprice = view.findViewById(R.id.cart1);
            viewHoler.txdate = view.findViewById(R.id.tv3_lvi_product1);
            viewHoler.imgRemove = view.findViewById(R.id.imgcartremove);
            view.setTag(viewHoler);

        } else {
            viewHoler = (ItemOrder.ViewHoler) view.getTag();
        }
        final Order order = (Order) getItem(i);
        viewHoler.txorder.setText(String.valueOf(order.getIdorder()));
        viewHoler.txname.setText(order.getName());
        viewHoler.txcount.setText(String.valueOf(order.getCount()));
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHoler.txprice.setText(decimalFormat.format(order.getPrice()) + " Đ");
        viewHoler.txdate.setText(String.valueOf(order.getDate()));
        if (order.getCategory() == 3 || order.getCategory() == 4) {
            viewHoler.imgRemove.setVisibility(View.GONE);
        } else {
            viewHoler.imgRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view1) {

                    //Tạo đối tượng
                    AlertDialog.Builder b = new AlertDialog.Builder(context);
                    //Thiết lập tiêu đề
                    b.setTitle("Xác nhận xóa đơn hàng");
                    b.setMessage("Bạn có chắc chắn muốn xóa đơn hàng không?");
                    // Nút Ok
                    b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            arrayOrder.remove(arrayOrder.get(i));
                            final HashMap<String, Integer> postParams = new HashMap<String, Integer>();
                            postParams.put("orderId", order.getIdorder());
                            final JSONObject jsonObject = new JSONObject(postParams);
                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, Server.urlcancel, jsonObject, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Toast.makeText(context, "Hủy đơn hàng thành công", Toast.LENGTH_LONG).show();
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
                            RequestQueue requestQueue = Volley.newRequestQueue(context);
                            requestQueue.add(jsonObjectRequest);
                            notifyDataSetChanged();
                        }
                    });
                    //Nút Cancel
                    b.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    //Tạo dialog
                    AlertDialog al = b.create();
                    //Hiển thị
                    al.show();
                }
            });

        }
        return view;
    }


}
