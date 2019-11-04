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
    ArrayList<Product> arraybracelet;

    public BraceletAdapter(Context context, ArrayList<Product> arraybracelet) {
        this.context = context;
        this.arraybracelet = arraybracelet;
    }

    @Override
    public int getCount() {
        return arraybracelet.size();
    }

    @Override
    public Object getItem(int i) {
        return arraybracelet.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHoler {
        public TextView txtbraceletname, txtbraceletprice, txtbraceletdescription;
        public ImageView imgbracelet;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BraceletAdapter.ViewHoler viewHoler = null;
        if (view == null) {
            viewHoler = new BraceletAdapter.ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_bracelet, null);
            viewHoler.txtbraceletname = view.findViewById(R.id.textviewbraceletname);
            viewHoler.txtbraceletprice = view.findViewById(R.id.textviewbraceletprice);
            viewHoler.txtbraceletdescription = view.findViewById(R.id.textviewbraceletdescription);
            viewHoler.imgbracelet = view.findViewById(R.id.imageviewbracelet);
            view.setTag(viewHoler);
        }
        else {
            viewHoler = (BraceletAdapter.ViewHoler) view.getTag();
        }
        Product product = (Product) getItem(i);
        viewHoler.txtbraceletname.setText(product.getProductName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHoler.txtbraceletprice.setText("Giá: " + decimalFormat.format(product.getPrice()) + " VNĐ");
        viewHoler.txtbraceletdescription.setMaxLines(2);
        viewHoler.txtbraceletdescription.setEllipsize(TextUtils.TruncateAt.END);
        viewHoler.txtbraceletdescription.setText(product.getDescription());
        Picasso.get().load(product.getProductImage())
                .placeholder(R.drawable.defaultimg)
                .error((R.drawable.errorimg))
                .into(viewHoler.imgbracelet);
        return view;
    }
}
