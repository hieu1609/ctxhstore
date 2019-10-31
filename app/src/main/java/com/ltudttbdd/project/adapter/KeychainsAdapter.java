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

public class KeychainsAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> arraykeychains;

    public KeychainsAdapter(Context context, ArrayList<Product> arraykeychains) {
        this.context = context;
        this.arraykeychains = arraykeychains;
    }

    @Override
    public int getCount() {
        return arraykeychains.size();
    }

    @Override
    public Object getItem(int i) {
        return arraykeychains.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHoler {
        public TextView txtkeychainsname, txtkeychainsprice, txtkeychainsdescription;
        public ImageView imgkeychains;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        KeychainsAdapter.ViewHoler viewHoler = null;
        if (view == null) {
            viewHoler = new KeychainsAdapter.ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_keychains, null);
            viewHoler.txtkeychainsname = view.findViewById(R.id.textviewkeychainsname);
            viewHoler.txtkeychainsprice = view.findViewById(R.id.textviewkeychainsprice);
            viewHoler.txtkeychainsdescription = view.findViewById(R.id.textviewkeychainsdescription);
            viewHoler.imgkeychains = view.findViewById(R.id.imageviewkeychains);
            view.setTag(viewHoler);
        }
        else {
            viewHoler = (KeychainsAdapter.ViewHoler) view.getTag();
        }
        Product product = (Product) getItem(i);
        viewHoler.txtkeychainsname.setText(product.getProductName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHoler.txtkeychainsprice.setText("Giá: " + decimalFormat.format(product.getPrice()) + " VNĐ");
        viewHoler.txtkeychainsdescription.setMaxLines(2);
        viewHoler.txtkeychainsdescription.setEllipsize(TextUtils.TruncateAt.END);
        viewHoler.txtkeychainsdescription.setText(product.getDescription());
        Picasso.get().load(product.getProductImage())
                .placeholder(R.drawable.noimg)
                .error((R.drawable.errorimg))
                .into(viewHoler.imgkeychains);
        return view;
    }
}
