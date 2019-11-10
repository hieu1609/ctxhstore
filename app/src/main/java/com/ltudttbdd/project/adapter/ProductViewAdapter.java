package com.ltudttbdd.project.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ltudttbdd.project.R;
import com.ltudttbdd.project.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> arrayProduct;

    public ProductViewAdapter(Context context, ArrayList<Product> arrayproduct) {
        this.context = context;
        this.arrayProduct = arrayproduct;
    }

    @Override
    public int getCount() {
        return arrayProduct.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayProduct.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHoler {
        public TextView txt_ProductName, txt_ProductPrice, txt_ProductDescription;
        public ImageView imgProduct;
        public RatingBar rbProduct;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoler viewHoler = null;
        if (view == null) {
            viewHoler = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = inflater.inflate(R.layout.list_bracelet, null);
//            viewHoler.txtbraceletname = view.findViewById(R.id.textviewbraceletname);
//            viewHoler.txtbraceletprice = view.findViewById(R.id.textviewbraceletprice);
//            viewHoler.txtbraceletdescription = view.findViewById(R.id.textviewbraceletdescription);
//            viewHoler.imgbracelet = view.findViewById(R.id.imageviewbracelet);
            view = inflater.inflate(R.layout.item_product_list, null);
            viewHoler.txt_ProductName = view.findViewById(R.id.tv1_lvi_product);
            viewHoler.txt_ProductPrice = view.findViewById(R.id.tv2_lvi_product);
            viewHoler.txt_ProductDescription = view.findViewById(R.id.tv3_lvi_product);
            viewHoler.imgProduct = view.findViewById(R.id.imgv_lvi_product);
            viewHoler.rbProduct = view.findViewById(R.id.rb_lvi_product);
//            Drawable stars = viewHoler.rbbracelet.getProgressDrawable();
//            DrawableCompat.setTint(stars, Color.YELLOW);
            LayerDrawable starsFilter = (LayerDrawable) viewHoler.rbProduct.getProgressDrawable();
            starsFilter.setColorFilter(Color.rgb(255,140,0), PorterDuff.Mode.SRC_ATOP);
            view.setTag(viewHoler);
        }
        else {
            viewHoler = (ViewHoler) view.getTag();
        }
        Product product = (Product) getItem(i);
        viewHoler.txt_ProductName.setText(product.getProductName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHoler.txt_ProductPrice.setText(decimalFormat.format(product.getPrice()) + " Đ");
        viewHoler.txt_ProductDescription.setMaxLines(2);
        viewHoler.txt_ProductDescription.setEllipsize(TextUtils.TruncateAt.END);
        viewHoler.txt_ProductDescription.setText(product.getDescription());
        viewHoler.rbProduct.setRating((float)4);
        Picasso.get().load(product.getProductImage())
                .placeholder(R.drawable.noimg)
                .error((R.drawable.errorimg))
                .into(viewHoler.imgProduct);
        return view;
    }
}
