package com.example.jooyoung.freeder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jooyoung.freeder.EventInformation;
import com.example.jooyoung.freeder.ListItem;
import com.example.jooyoung.freeder.R;

import java.util.ArrayList;

public class ListAdapter_my extends BaseAdapter {
    ArrayList<EventInformation> info = new ArrayList<EventInformation>();
    public ListAdapter_my(){

    }

    @Override
    public int getCount() {
        return info.size();
    }

    @Override
    public Object getItem(int position) {
        return info.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.mypageitem, parent, false);
        }

        TextView name = convertView.findViewById(R.id.mypage_name); // 이름
        TextView date1 = convertView.findViewById(R.id.mypage_date1); // 신청날짜(yyyy/mm/dd -> m월 d일)
        TextView date2 = convertView.findViewById(R.id.mypage_date2); // 기간(yyyy/mm/dd~yyyy/mm/dd)
        TextView date3 = convertView.findViewById(R.id.mypage_date3); // 발표날짜(yyyy/mm/dd -> m월 d일)

        EventInformation EventInfo = info.get(position);

        name.setText(EventInfo.getEvent_name());

        String getEv_day = EventInfo.getEvent_day();
        String[] getEv_ymd = getEv_day.split("/");

        try { // 신청 날짜를 텍스트로 받아서 정수형으로 변환 시도
            int month = Integer.parseInt(getEv_ymd[1]);
            int day = Integer.parseInt(getEv_ymd[2]);
            String getEv_ymd2 = month + "월 " + day + "일";
            date1.setText(getEv_ymd2);
        } catch (Exception e) { // 안 되면 'mm월 dd일'로
            try {
                String getEv_ymd2 = getEv_ymd[1] + "월 " + getEv_ymd[2] + "일";
                date1.setText(getEv_ymd2);
            } catch (Exception e2) {
                // 오류 처리
            }
        }

        date2.setText(EventInfo.getEvent_time()); // 임시
        String[] dead_ymd = EventInfo.getEvent_time().split("/"); // 임시

        date3.setText(EventInfo.getEvent_time()); // 임시



        return convertView;
    }

    public void addItem(EventInformation user_event ){
        info.add(user_event);
    }
}
