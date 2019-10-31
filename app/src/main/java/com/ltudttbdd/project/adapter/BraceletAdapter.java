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

public class BraceletAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> arrayFood;

    public BraceletAdapter(Context context, ArrayList<Product> arrayFood) {
        this.context = context;
        this.arrayFood = arrayFood;
    }

    @Override
    public int getCount() {
        return arrayFood.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayFood.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHoler {
        public TextView txtfoodname, txtfoodprice, txtfooddescription;
        public ImageView imgfood;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BraceletAdapter.ViewHoler viewHoler = null;
        if (view == null) {
            viewHoler = new BraceletAdapter.ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_food, null);
            viewHoler.txtfoodname = view.findViewById(R.id.textviewfoodname);
            viewHoler.txtfoodprice = view.findViewById(R.id.textviewfoodprice);
            viewHoler.txtfooddescription = view.findViewById(R.id.textviewfooddescription);
            viewHoler.imgfood = view.findViewById(R.id.imageviewfood);
            view.setTag(viewHoler);
        }
        else {
            viewHoler = (BraceletAdapter.ViewHoler) view.getTag();
        }
        Product product = (Product) getItem(i);
        viewHoler.txtfoodname.setText(product.getProductName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHoler.txtfoodprice.setText("Giá: " + decimalFormat.format(product.getPrice()) + " Đ");
        viewHoler.txtfooddescription.setMaxLines(2);
        viewHoler.txtfooddescription.setEllipsize(TextUtils.TruncateAt.END);
        viewHoler.txtfooddescription.setText(product.getDescription());
        Picasso.get().load(product.getProductImage())
                .placeholder(R.drawable.noimg)
                .error((R.drawable.errorimg))
                .into(viewHoler.imgfood);
        return view;
    }
}
