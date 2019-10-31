package com.ltudttbdd.project.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ltudttbdd.project.R;
import com.ltudttbdd.project.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DrinkAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> arrayDrink;

    public DrinkAdapter(Context context, ArrayList<Product> arrayDrink) {
        this.context = context;
        this.arrayDrink = arrayDrink;
    }

    @Override
    public int getCount() {
        return arrayDrink.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayDrink.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHoler {
        public TextView txtdrinkname, txtdrinkprice, txtdrinkdescription;
        public ImageView imgdrink;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        DrinkAdapter.ViewHoler viewHoler = null;
        if (view == null) {
            viewHoler = new DrinkAdapter.ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_food, null);
            viewHoler.txtdrinkname = view.findViewById(R.id.textviewfoodname);
            viewHoler.txtdrinkprice = view.findViewById(R.id.textviewfoodprice);
            viewHoler.txtdrinkdescription = view.findViewById(R.id.textviewfooddescription);
            viewHoler.imgdrink = view.findViewById(R.id.imageviewfood);
            view.setTag(viewHoler);
        }
        else {
            viewHoler = (DrinkAdapter.ViewHoler) view.getTag();
        }
        Product product = (Product) getItem(i);
        viewHoler.txtdrinkname.setText(product.getProductName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHoler.txtdrinkprice.setText("Giá: " + decimalFormat.format(product.getPrice()) + " Đ");
        viewHoler.txtdrinkdescription.setMaxLines(2);
        viewHoler.txtdrinkdescription.setEllipsize(TextUtils.TruncateAt.END);
        viewHoler.txtdrinkdescription.setText(product.getDescription());
        Picasso.get().load(product.getProductImage())
                .placeholder(R.drawable.noimg)
                .error((R.drawable.errorimg))
                .into(viewHoler.imgdrink);
        return view;
    }
}
