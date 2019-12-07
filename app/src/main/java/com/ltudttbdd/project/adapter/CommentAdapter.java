package com.ltudttbdd.project.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ltudttbdd.project.R;
import com.ltudttbdd.project.model.Rating;
import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter{

        Context context;
        ArrayList<Rating> arrayRating;

        public CommentAdapter(Context context, ArrayList<Rating> arrayproduct) {
            this.context = context;
            this.arrayRating = arrayproduct;

        }

        @Override
        public int getCount() {
            return arrayRating.size();
        }

        @Override
        public Object getItem(int i) {
            return arrayRating.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        public class ViewHoler {
            public TextView txtuser, txtcomment;
            public RatingBar rbProduct;

        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Rating rating = (Rating) getItem(i);
            ViewHoler viewHoler = null;

            if (view == null ) {
                viewHoler = new ViewHoler();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.list_comment, null);
                viewHoler.txtuser = view.findViewById(R.id.tv3_lvi_user);
                viewHoler.txtcomment = view.findViewById(R.id.tv3_lvi_product);
                viewHoler.rbProduct = view.findViewById(R.id.rb_lvi_product);
                LayerDrawable starsFilter = (LayerDrawable) viewHoler.rbProduct.getProgressDrawable();
                starsFilter.setColorFilter(Color.rgb(255, 140, 0), PorterDuff.Mode.SRC_ATOP);
                view.setTag(viewHoler);
            }
            else {
                viewHoler = (ViewHoler) view.getTag();
            }
            viewHoler.txtuser.setText(String.valueOf(rating.getIduser()));
            if(rating.getComment() == "null") {
                viewHoler.txtcomment.setText("Đánh giá sao");
            }
            else {
                viewHoler.txtcomment.setText(rating.getComment());
                viewHoler.txtcomment.setMovementMethod(new ScrollingMovementMethod());
            }

            viewHoler.rbProduct.setRating(rating.getRate());
            return view;
        }


}

