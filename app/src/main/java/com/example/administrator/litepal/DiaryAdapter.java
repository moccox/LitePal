package com.example.administrator.litepal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class DiaryAdapter extends ArrayAdapter<Item> {
    int resourceId;
    public DiaryAdapter(Context context,int textViewResourceId,List<Item> objects){
        super(context, textViewResourceId, objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Item item = getItem(position);    //获取当前的Item实例

        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        //布局初始化
        TextView tvId = (TextView) view.findViewById(R.id.item_id);
        TextView tvTitle = (TextView) view.findViewById(R.id.item_title);
        TextView tvTime = (TextView) view.findViewById(R.id.item_time);
        //放入内容
        tvId.setText(item.getiId());
        tvTitle.setText(item.getiTitle());
        tvTime.setText(item.getiTime());
        return view;
    }
}
