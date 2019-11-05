package com.ltudttbdd.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ltudttbdd.project.R;
import com.ltudttbdd.project.model.ProductCategory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {
    ArrayList<ProductCategory> arrayListCategory;
    Context context;

    public CategoryAdapter(ArrayList<ProductCategory> arrayListCategory, Context context) {
        this.arrayListCategory = arrayListCategory;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListCategory.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListCategory.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        TextView txtCategoryName;
        ImageView imgCategoryImage;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_view_product, null);
            viewHolder.txtCategoryName = view.findViewById(R.id.textviewloaisp);
            viewHolder.imgCategoryImage = view.findViewById(R.id.imageviewloaisp);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ProductCategory productCategory = (ProductCategory) getItem(i);
        viewHolder.txtCategoryName.setText(productCategory.getCategoryName());
        Picasso.get().load(productCategory.getCategoryImage())
                .placeholder(R.drawable.defaultimg)
                .error(R.drawable.defaultimg)
                .into(viewHolder.imgCategoryImage);
        return view;
    }
}
