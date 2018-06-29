package com.example.jooyoung.freeder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.graphics.Bitmap;

import com.example.jooyoung.freeder.ListItem;
import com.example.jooyoung.freeder.R;

import java.util.ArrayList;

public class ListAdapter2 extends BaseAdapter implements View.OnClickListener {
    ArrayList<ListItem> listitem = new ArrayList<ListItem>();
    private View.OnClickListener mOnClickListner = null;

    public interface ListBtnClickListner {
        void onListBtnClick(int position);
    }

    public ListAdapter2() {

    }

    public ListAdapter2(View.OnClickListener onClickListener) {
        mOnClickListner = onClickListener;
    }

    @Override
    public int getCount() {
        return listitem.size();
    }

    @Override
    public String getItem(int position) {
        return listitem.get(position).getTitle();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.d_dayitem2, parent, false);
        }

        TextView eventname = (TextView) convertView.findViewById(R.id.event_name2);
        TextView event_day = (TextView) convertView.findViewById(R.id.event_d_day2);
        CheckBox event_favorite = (CheckBox) convertView.findViewById(R.id.event_favorite2);

        ListItem listItem = listitem.get(position);

        eventname.setText(listItem.getTitle());
        event_day.setText("D-" + listItem.getDday());
        event_favorite.setChecked(listItem.isFavorite_check());

        if (mOnClickListner != null) {
            event_favorite.setTag(listItem.getTitle());
            event_favorite.setOnClickListener(mOnClickListner);
        }


        return convertView;
    }

    public void addItem(String name, String dday,boolean favorite) {
        ListItem item = new ListItem();
        item.setTitle(name);
        item.setDday(dday);
        item.setFavorite_check(favorite);

        listitem.add(item);
    }

    @Override
    public void onClick(View v) {

    }
}
