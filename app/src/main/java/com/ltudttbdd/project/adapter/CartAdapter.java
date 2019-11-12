package com.ltudttbdd.project.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ltudttbdd.project.R;
import com.ltudttbdd.project.activity.CartActivity;
import com.ltudttbdd.project.activity.MainActivity;
import com.ltudttbdd.project.model.Cart;
import com.ltudttbdd.project.ultil.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.ltudttbdd.project.activity.CartActivity.EventUtil;

public class CartAdapter extends BaseAdapter {

    CartActivity context;
    ArrayList<Cart> arrayCart;

    public CartAdapter(CartActivity context, ArrayList<Cart> arrayCart) {
        this.context = context;
        this.arrayCart = arrayCart;
    }

    @Override
    public int getCount() {
        return arrayCart.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayCart.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHoler {
        public TextView txtCartName, txtCartPrice, txtValues;
        public ImageView imgCart, imgRemove;
        public Button btnMinus, btnPlus;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHoler viewHoler = null;
        if (view == null) {
            viewHoler = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_cart, null);
            viewHoler.txtCartName = view.findViewById(R.id.textviewcartname);
            viewHoler.txtCartPrice = view.findViewById(R.id.textviewcartprice);
            viewHoler.imgCart = view.findViewById(R.id.cartProductImg);
            viewHoler.btnMinus = view.findViewById(R.id.buttonminus);
            viewHoler.btnPlus = view.findViewById(R.id.buttonplus);
            viewHoler.txtValues = view.findViewById(R.id.txt_values);
            viewHoler.imgRemove = view.findViewById(R.id.imgcartremove);
            view.setTag(viewHoler);
        }
        else {
            viewHoler = (CartAdapter.ViewHoler) view.getTag();
        }
        Cart cart = (Cart) getItem(i);
        viewHoler.txtCartName.setText(cart.getNameproduct());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHoler.txtCartPrice.setText(decimalFormat.format(cart.getPriceproduct()) + " Đ");
        Picasso.get().load(cart.getImageproduct())
                .placeholder(R.drawable.defaultimg)
                .error((R.drawable.errorimg))
                .into(viewHoler.imgCart);
        viewHoler.txtValues.setText(String.valueOf(cart.getNumberproduct()));
        int numberProduct = Integer.parseInt(viewHoler.txtValues.getText().toString());
        if (numberProduct > 9) {
//            viewHoler.btnPlus.setVisibility(View.INVISIBLE);
//            viewHoler.btnMinus.setVisibility(View.VISIBLE);
            viewHoler.btnPlus.setEnabled(false);
            viewHoler.btnMinus.setEnabled(true);
            viewHoler.txtValues.setText("10");
            int newNumber = 10;
            int nowNumber = MainActivity.arrayCart.get(i).getNumberproduct();
            long nowPrice = MainActivity.arrayCart.get(i).getPriceproduct();
            long newPrice = (nowPrice * newNumber) / nowNumber;
            MainActivity.arrayCart.get(i).setNumberproduct(newNumber);
            MainActivity.arrayCart.get(i).setPriceproduct(newPrice);
            DecimalFormat decimal = new DecimalFormat("###,###,###");
            viewHoler.txtCartPrice.setText(decimal.format(newPrice) + " Đ");
            EventUtil();
        }
        else if (numberProduct <= 1) {
//            viewHoler.btnPlus.setVisibility(View.INVISIBLE);
//            viewHoler.btnMinus.setVisibility(View.VISIBLE);
            viewHoler.btnPlus.setEnabled(true);
            viewHoler.btnMinus.setEnabled(false);
        }
        else if (numberProduct > 1) {
//            viewHoler.btnPlus.setVisibility(View.INVISIBLE);
//            viewHoler.btnMinus.setVisibility(View.VISIBLE);
            viewHoler.btnPlus.setEnabled(true);
            viewHoler.btnMinus.setEnabled(true);
        }
        final ViewHoler finalViewHoler = viewHoler;
        viewHoler.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newNumber = Integer.parseInt(finalViewHoler.txtValues.getText().toString()) + 1;
                int nowNumber = MainActivity.arrayCart.get(i).getNumberproduct();
                long nowPrice = MainActivity.arrayCart.get(i).getPriceproduct();
                long newPrice = (nowPrice * newNumber) / nowNumber;
                MainActivity.arrayCart.get(i).setNumberproduct(newNumber);
                MainActivity.arrayCart.get(i).setPriceproduct(newPrice);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHoler.txtCartPrice.setText(decimalFormat.format(newPrice) + " Đ");
                EventUtil();
                if (newNumber > 9) {
//            viewHoler.btnPlus.setVisibility(View.INVISIBLE);
//            viewHoler.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHoler.btnPlus.setEnabled(false);
                    finalViewHoler.btnMinus.setEnabled(true);
                    finalViewHoler.txtValues.setText(String.valueOf(newNumber));
                }
                else {
//            finalViewHoler.btnPlus.setVisibility(View.VISIBLE);
//            finalViewHoler.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHoler.btnPlus.setEnabled(true);
                    finalViewHoler.btnMinus.setEnabled(true);
                    finalViewHoler.txtValues.setText(String.valueOf(newNumber));
                }
            }
        });
        viewHoler.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newNumber = Integer.parseInt(finalViewHoler.txtValues.getText().toString()) - 1;
                int nowNumber = MainActivity.arrayCart.get(i).getNumberproduct();
                long nowPrice = MainActivity.arrayCart.get(i).getPriceproduct();
                long newPrice = (nowPrice * newNumber) / nowNumber;
                MainActivity.arrayCart.get(i).setNumberproduct(newNumber);
                MainActivity.arrayCart.get(i).setPriceproduct(newPrice);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHoler.txtCartPrice.setText(decimalFormat.format(newPrice) + " Đ");
                EventUtil();
                if (newNumber < 2) {
                    finalViewHoler.btnPlus.setEnabled(true);
                    finalViewHoler.btnMinus.setEnabled(false);
                    finalViewHoler.txtValues.setText(String.valueOf(newNumber));
                }
                else {
                    finalViewHoler.btnPlus.setEnabled(true);
                    finalViewHoler.btnMinus.setEnabled(true);
                    finalViewHoler.txtValues.setText(String.valueOf(newNumber));
                }
            }
        });
        viewHoler.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context); /////??
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i1) {
                        if (MainActivity.arrayCart.size() <= 0) {
                            CheckConnection.ShowToastShort(context, "Giỏ hàng của bạn đang trống");
                        }
                        else {
                            MainActivity.arrayCart.remove(arrayCart.get(i));
                            notifyDataSetChanged();
                            EventUtil();
                            if (MainActivity.arrayCart.size() <= 0) {
                                CheckConnection.ShowToastShort(context, "Giỏ hàng của bạn đang trống");
                            }
                            else {
                                notifyDataSetChanged();
                                EventUtil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i1) {
                        notifyDataSetChanged();
                        EventUtil();
                    }
                });
                builder.show();
            }
        });

        return view;
    }
}
