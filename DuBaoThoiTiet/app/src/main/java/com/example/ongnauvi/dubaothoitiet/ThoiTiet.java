package com.example.ongnauvi.dubaothoitiet;

/**
 * Created by OngNauVi on 13/06/2017.
 */

public class ThoiTiet {
    public String Ngay;
    public String Status;
    public String Hinh;
    public String Max;
    public String Min;

    public ThoiTiet(String ngay, String status, String hinh, String max, String min) {
        Ngay = ngay;
        Status = status;
        Hinh = hinh;
        Max = max;
        Min = min;
    }
}
