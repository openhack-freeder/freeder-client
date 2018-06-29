package com.example.jooyoung.freeder.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jooyoung.freeder.ListItem;
import com.example.jooyoung.freeder.R;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter implements View.OnClickListener {
    ArrayList<ListItem> listitem = new ArrayList<ListItem>();
    private View.OnClickListener mOnClickListner = null;
    private String category;

    public interface ListBtnClickListner {
        void onListBtnClick(int position);
    }

    public ListAdapter() {

    }

    public ListAdapter(View.OnClickListener onClickListener) {
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
            convertView = inflater.inflate(R.layout.d_dayitem, parent, false);
        }

        ProgressBar d_day_status = (ProgressBar) convertView.findViewById(R.id.d_day_progress);
        TextView eventname = (TextView) convertView.findViewById(R.id.event_name);
        TextView event_day = (TextView) convertView.findViewById(R.id.event_d_day);
        CheckBox event_favorite = (CheckBox) convertView.findViewById(R.id.event_favorite);
        ImageView color = (ImageView)convertView.findViewById(R.id.color);


        ListItem listItem = listitem.get(position);

        eventname.setText(listItem.getTitle());
        event_day.setText("D-" + listItem.getDday());
        d_day_status.setProgress(Integer.parseInt(listItem.getDday()));
        event_favorite.setChecked(listItem.isFavorite_check());
        if(category.equals("전체")){
            color.setImageResource(R.drawable.all_color);
        }
        else if(category.equals("영화")){
            color.setImageResource(R.drawable.movie_color);
        }
        else if(category.equals("축제,행사")){
            color.setImageResource(R.drawable.festival_color);
        }
        else if(category.equals("연극,뮤지컬")){
            color.setImageResource(R.drawable.musical_color);
        }
        else{
            color.setImageResource(R.drawable.etc_color);
        }


        if (mOnClickListner != null) {
            event_favorite.setTag(listItem.getTitle());
            event_favorite.setOnClickListener(mOnClickListner);
        }


        return convertView;
    }

    public void addItem(String name, String dday,boolean favorite,String category) {
        ListItem item = new ListItem();
        item.setTitle(name);
        item.setDday(dday);
        item.setFavorite_check(favorite);
        this.category = category;


        listitem.add(item);
    }

    @Override
    public void onClick(View v) {

    }
}
