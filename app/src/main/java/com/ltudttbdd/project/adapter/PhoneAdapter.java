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

public class PhoneAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> arrayPhone;

    public PhoneAdapter(Context context, ArrayList<Product> arrayPhone) {
        this.context = context;
        this.arrayPhone = arrayPhone;
    }

    @Override
    public int getCount() {
        return arrayPhone.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayPhone.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHoler {
        public TextView txtphonename, txtphoneprice, txtphonedescription;
        public ImageView imgphone;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoler viewHoler = null;
        if (view == null) {
            viewHoler = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_phone, null);
            viewHoler.txtphonename = view.findViewById(R.id.textviewphonename);
            viewHoler.txtphoneprice = view.findViewById(R.id.textviewphoneprice);
            viewHoler.txtphonedescription = view.findViewById(R.id.textviewphonedescription);
            viewHoler.imgphone = view.findViewById(R.id.imageviewphone);
            view.setTag(viewHoler);
        }
        else {
            viewHoler = (ViewHoler) view.getTag();
        }
        Product product = (Product) getItem(i);
        viewHoler.txtphonename.setText(product.getProductName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHoler.txtphoneprice.setText("Giá: " + decimalFormat.format(product.getPrice()) + " VNĐ");
        viewHoler.txtphonedescription.setMaxLines(2);
        viewHoler.txtphonedescription.setEllipsize(TextUtils.TruncateAt.END);
        viewHoler.txtphonedescription.setText(product.getDescription());
        Picasso.get().load(product.getProductImage())
                .placeholder(R.drawable.noimg)
                .error((R.drawable.errorimg))
                .into(viewHoler.imgphone);
        return view;
    }
}
