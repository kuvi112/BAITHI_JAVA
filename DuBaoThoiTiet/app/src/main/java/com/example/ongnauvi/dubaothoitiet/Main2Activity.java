package com.example.ongnauvi.dubaothoitiet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {

    ImageView ImBack;
    TextView txtTenTP;
    ListView LV;
    CustomAdapter customAdapter;
    ArrayList<ThoiTiet> Mang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        String TP = intent.getStringExtra("name");

        AnhXa();

        if(TP.equals(""))
        {
            Get7days("nha trang");
        }
        else
        {
            Get7days(TP);
        }

        ImBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void Get7days(String data)
    {
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q="+data+"&units=metric&cnt=7&appid=14a03038a70c2399724546bb53effb30";
        RequestQueue requestQueue = Volley.newRequestQueue(Main2Activity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectTP = jsonObject.getJSONObject("city");
                            String TP = (String) jsonObjectTP.get("name");
                            txtTenTP.setText(TP);

                            JSONArray jsonObjectarray = jsonObject.getJSONArray("list");

                            for(int i = 0 ;i < jsonObjectarray.length() ; i++)
                            {
                                JSONObject jsonObjectList  = jsonObjectarray.getJSONObject(i);
                                String Ngay = jsonObjectList.getString("dt");

                                long l = Long.valueOf(Ngay);
                                Date date = new Date(l*1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
                                String Day = simpleDateFormat.format(date);

                                JSONObject jsonObjectDo =jsonObjectList.getJSONObject("temp");
                                String Max = jsonObjectDo.getString("max");
                                String Min = jsonObjectDo.getString("min");

                                Double a = Double.valueOf(Max);
                                String nhietdoMax = String.valueOf(a.intValue());
                                Double b = Double.valueOf(Min);
                                String nhietdoMin = String.valueOf(b.intValue());

                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String Status = jsonObjectWeather.getString("description");
                                String Icon = jsonObjectWeather.getString("icon");
                                Mang.add(new ThoiTiet(Day,Status,Icon,nhietdoMax,nhietdoMin));
                            }
                            customAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
    public void AnhXa()
    {
        ImBack = (ImageView)findViewById(R.id.ImvTroVe);
        txtTenTP = (TextView)findViewById(R.id.txtTp);
        LV = (ListView)findViewById(R.id.LV);
        Mang = new ArrayList<ThoiTiet>();
        customAdapter =new CustomAdapter(Main2Activity.this,Mang);
        LV.setAdapter(customAdapter);
    }
}
