package com.example.ongnauvi.dubaothoitiet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by OngNauVi on 13/06/2017.
 */

public class CustomAdapter extends BaseAdapter {
    Context context;

    public CustomAdapter(Context context, ArrayList<ThoiTiet> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    ArrayList<ThoiTiet> arrayList;


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.listview,null);

        ThoiTiet thoiTiet = arrayList.get(position);

        TextView txtNgay = (TextView) convertView.findViewById(R.id.Ngay);
        TextView txtStatus = (TextView) convertView.findViewById(R.id.TrangThai);
        TextView txtMax = (TextView) convertView.findViewById(R.id.Max);
        TextView txtMin = (TextView) convertView.findViewById(R.id.Min);
        ImageView Im = (ImageView) convertView.findViewById(R.id.Imv);

        txtNgay.setText(thoiTiet.Ngay);
        txtStatus.setText(thoiTiet.Status);
        txtMax.setText(thoiTiet.Max+"°C");
        txtMin.setText(thoiTiet.Min+"°C");
        Picasso.with(context).load("http://openweathermap.org/img/w/"+thoiTiet.Hinh+".png").into(Im);
        return convertView;
    }
}
