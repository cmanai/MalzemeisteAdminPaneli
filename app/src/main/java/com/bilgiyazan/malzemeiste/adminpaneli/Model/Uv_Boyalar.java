package com.bilgiyazan.malzemeiste.adminpaneli.Model;


import java.util.List;

/**
 * Created by Toshiba on 23/12/2016.
 */

public class Uv_Boyalar {
    private String Uv_Boyalar_Name;
    private List<Uv_Boyalar_Value> Uv_Boyalar_Value;


    public Uv_Boyalar(String Uv_Boyalar_Name, List<Uv_Boyalar_Value> Uv_Boyalar_Value) {

        this.Uv_Boyalar_Name = Uv_Boyalar_Name;
        this.Uv_Boyalar_Value = Uv_Boyalar_Value;


    }

    public Uv_Boyalar() {
    }


    public String getUv_boyalar_Name() {
        return Uv_Boyalar_Name;
    }

    public void setUv_boyalar_Name(String uv_boyalar_Name) {
        this.Uv_Boyalar_Name = uv_boyalar_Name;
    }

    public List<Uv_Boyalar_Value> getUv_boyalar_Value() {
        return Uv_Boyalar_Value;
    }

    public void setUv_boyalar_Value(List<Uv_Boyalar_Value> uv_boyalar_Value) {
        this.Uv_Boyalar_Value = uv_boyalar_Value;
    }
}

