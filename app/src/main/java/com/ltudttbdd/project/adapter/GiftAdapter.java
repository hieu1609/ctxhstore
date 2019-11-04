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

public class GiftAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> arraygift;

    public GiftAdapter(Context context, ArrayList<Product> arraygift) {
        this.context = context;
        this.arraygift = arraygift;
    }

    @Override
    public int getCount() {
        return arraygift.size();
    }

    @Override
    public Object getItem(int i) {
        return arraygift.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHoler {
        public TextView txtgiftname, txtgiftprice, txtgiftdescription;
        public ImageView imggift;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GiftAdapter.ViewHoler viewHoler = null;
        if (view == null) {
            viewHoler = new GiftAdapter.ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_gift, null);
            viewHoler.txtgiftname = view.findViewById(R.id.textviewgiftname);
            viewHoler.txtgiftprice = view.findViewById(R.id.textviewgiftprice);
            viewHoler.txtgiftdescription = view.findViewById(R.id.textviewgiftdescription);
            viewHoler.imggift = view.findViewById(R.id.imageviewgift);
            view.setTag(viewHoler);
        }
        else {
            viewHoler = (GiftAdapter.ViewHoler) view.getTag();
        }
        Product product = (Product) getItem(i);
        viewHoler.txtgiftname.setText(product.getProductName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHoler.txtgiftprice.setText("Giá: " + decimalFormat.format(product.getPrice()) + " VNĐ");
        viewHoler.txtgiftdescription.setMaxLines(2);
        viewHoler.txtgiftdescription.setEllipsize(TextUtils.TruncateAt.END);
        viewHoler.txtgiftdescription.setText(product.getDescription());
        Picasso.get().load(product.getProductImage())
                .placeholder(R.drawable.defaultimg)
                .error((R.drawable.errorimg))
                .into(viewHoler.imggift);
        return view;
    }
}
