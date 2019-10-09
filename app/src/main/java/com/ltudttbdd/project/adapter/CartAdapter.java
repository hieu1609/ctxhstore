package com.ltudttbdd.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ltudttbdd.project.R;
import com.ltudttbdd.project.model.Cart;
import com.ltudttbdd.project.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {

    Context context;
    ArrayList<Cart> arrayCart;

    public CartAdapter(Context context, ArrayList<Cart> arrayCart) {
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
        public TextView txtCartName, txtCartPrice;
        public ImageView imgCart;
        public Button btnMinus, btnValues, btnPlus;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoler viewHoler = null;
        if (view == null) {
            viewHoler = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_cart, null);
            viewHoler.txtCartName = view.findViewById(R.id.textviewcartname);
            viewHoler.txtCartPrice = view.findViewById(R.id.textviewcartprice);
            viewHoler.imgCart = view.findViewById(R.id.imageviewcart);
            viewHoler.btnMinus = view.findViewById(R.id.buttonminus);
            viewHoler.btnPlus = view.findViewById(R.id.buttonplus);
            viewHoler.btnValues = view.findViewById(R.id.buttonvalues);
            view.setTag(viewHoler);
        }
        else {
            viewHoler = (CartAdapter.ViewHoler) view.getTag();
        }
        Cart cart = (Cart) getItem(i);
        viewHoler.txtCartName.setText(cart.getNameproduct());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHoler.txtCartPrice.setText(decimalFormat.format(cart.getPriceproduct()) + " VNĐ");
        Picasso.get().load(cart.getImageproduct())
                .placeholder(R.drawable.noimg)
                .error((R.drawable.errorimg))
                .into(viewHoler.imgCart);
        viewHoler.btnValues.setText(String.valueOf(cart.getNumberproduct()));
        return view;
    }
}
