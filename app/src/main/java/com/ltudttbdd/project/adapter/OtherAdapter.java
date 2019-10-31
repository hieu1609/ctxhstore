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

public class OtherAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> arrayother;

    public OtherAdapter(Context context, ArrayList<Product> arrayother) {
        this.context = context;
        this.arrayother = arrayother;
    }

    @Override
    public int getCount() {
        return arrayother.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayother.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHoler {
        public TextView txtothername, txtotherprice, txtotherdescription;
        public ImageView imgother;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoler viewHoler = null;
        if (view == null) {
            viewHoler = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_other, null);
            viewHoler.txtothername = view.findViewById(R.id.textviewothername);
            viewHoler.txtotherprice = view.findViewById(R.id.textviewotherprice);
            viewHoler.txtotherdescription = view.findViewById(R.id.textviewotherdescription);
            viewHoler.imgother = view.findViewById(R.id.imageviewother);
            view.setTag(viewHoler);
        }
        else {
            viewHoler = (ViewHoler) view.getTag();
        }
        Product product = (Product) getItem(i);
        viewHoler.txtothername.setText(product.getProductName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHoler.txtotherprice.setText("Giá: " + decimalFormat.format(product.getPrice()) + " VNĐ");
        viewHoler.txtotherdescription.setMaxLines(2);
        viewHoler.txtotherdescription.setEllipsize(TextUtils.TruncateAt.END);
        viewHoler.txtotherdescription.setText(product.getDescription());
        Picasso.get().load(product.getProductImage())
                .placeholder(R.drawable.noimg)
                .error((R.drawable.errorimg))
                .into(viewHoler.imgother);
        return view;
    }
}
