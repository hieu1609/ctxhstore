package com.ltudttbdd.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ltudttbdd.project.R;
import com.ltudttbdd.project.model.Notify;

import java.util.ArrayList;

public class NotificationAdapter extends BaseAdapter {
    Context context;
    ArrayList<Notify> arrayNotify;

    public NotificationAdapter(Context context, ArrayList<Notify> arrayNotify){
        this.context = context;
        this.arrayNotify = arrayNotify;
    }

    @Override
    public int getCount(){ return arrayNotify.size(); }

    @Override
    public Object getItem(int i) { return arrayNotify.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    public class ViewHolder {
        TextView txtNotiTitle;
        TextView txtNotiContent;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        ViewHolder viewHolder;
        if(view==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_notification, null);
            viewHolder.txtNotiTitle = view.findViewById(R.id.textview_title);
            viewHolder.txtNotiContent = view.findViewById(R.id.textview_content);
            view.setTag(viewHolder);
        }
        return view;
    }
}
