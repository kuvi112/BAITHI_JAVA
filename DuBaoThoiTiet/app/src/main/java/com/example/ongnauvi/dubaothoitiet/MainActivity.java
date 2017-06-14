package com.example.ongnauvi.dubaothoitiet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText edtTim;
    Button btnOk, btnNgayTT;
    TextView txtTP, txtQG, txtNhietDo, txtTrangThai, txtDoAm, txtMay, txtGio, txtNgayCN;
    ImageView Im;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        GetCurrenWeatherData("nha trang");
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TP = edtTim.getText().toString();
                if(TP.equals(""))
                {
                    GetCurrenWeatherData("nha trang");
                }
                else
                {
                    GetCurrenWeatherData(TP);
                }
            }
        });
        btnNgayTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                String TP = edtTim.getText().toString();
                intent.putExtra("name",TP);
                startActivity(intent);
            }
        });
    }

    public void GetCurrenWeatherData(String Data)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+Data+"&units=metric&appid=14a03038a70c2399724546bb53effb30";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject  = new JSONObject(response);

                            String Ngay = jsonObject.getString("dt");
                            String TenTP = jsonObject.getString("name");
                            txtTP.setText("Tên Thành Phố: "+TenTP);

                            long l = Long.valueOf(Ngay);
                            Date date = new Date(l*1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
                            String Day = simpleDateFormat.format(date);

                            txtNgayCN.setText(Day);

                            JSONArray jsonArray = jsonObject.getJSONArray("weather");

                            JSONObject jsonObjectweather = jsonArray.getJSONObject(0);

                            String status = jsonObjectweather.getString("main");
                            String icon = jsonObjectweather.getString("icon");

                            Picasso.with(MainActivity.this).load("http://openweathermap.org/img/w/"+icon+".png").into(Im);

                            txtTrangThai.setText(status);

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");

                            String NhietDo = jsonObjectMain.getString("temp");
                            String DoAm = jsonObjectMain.getString("humidity");

                            Double a = Double.valueOf(NhietDo);
                            String nhietdo = String.valueOf(a.intValue());
                            txtNhietDo.setText(nhietdo+" °C");
                            txtDoAm.setText(DoAm+" %");

                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                            String Gio = jsonObjectWind.getString("speed");
                            txtGio.setText(Gio+" m/s");

                            JSONObject jsonObjectclouds = jsonObject.getJSONObject("clouds");
                            String May = jsonObjectclouds.getString("all");
                            txtMay.setText(May+" %");

                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String QG = jsonObjectSys.getString("country");
                            txtQG.setText("Tên Quốc Gia: "+QG);
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

    private void AnhXa()
    {
        edtTim = (EditText)findViewById(R.id.edtDiaDiem);
        txtTP = (TextView)findViewById(R.id.txtTenTP);
        txtQG = (TextView)findViewById(R.id.txtTenQG);
        txtNhietDo = (TextView)findViewById(R.id.txtNhietDo);
        txtTrangThai = (TextView)findViewById(R.id.txtTrangThai);
        txtDoAm = (TextView)findViewById(R.id.txtDoAm);
        txtMay = (TextView)findViewById(R.id.txtMay);
        txtGio = (TextView)findViewById(R.id.txtGio);
        txtNgayCN = (TextView)findViewById(R.id.txtNgay);
        btnOk = (Button)findViewById(R.id.btnOk);
        btnNgayTT = (Button)findViewById(R.id.btnNgayTT);
        Im = (ImageView)findViewById(R.id.imvTT);
    }
}
