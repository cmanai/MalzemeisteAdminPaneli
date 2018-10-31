package com.bilgiyazan.malzemeiste.adminpaneli.Model;

import java.util.List;

/**
 * Created by Toshiba on 23/12/2016.
 */

public class Temizleme {
    private String Temizleme_Name;
    private List<Temizleme_Value> Temizleme_Value;


    public Temizleme(String Temizleme_Name, List<Temizleme_Value> Temizleme_Value) {

        this.Temizleme_Name = Temizleme_Name;
        this.Temizleme_Value = Temizleme_Value;


    }

    public Temizleme() {
    }

    public String getTemizleme_Name() {
        return Temizleme_Name;
    }

    public void setTemizleme_Name(String temizleme_Name) {
        Temizleme_Name = temizleme_Name;
    }

    public List<com.bilgiyazan.malzemeiste.adminpaneli.Model.Temizleme_Value> getTemizleme_Value() {
        return Temizleme_Value;
    }

    public void setTemizleme_Value(List<com.bilgiyazan.malzemeiste.adminpaneli.Model.Temizleme_Value> temizleme_Value) {
        Temizleme_Value = temizleme_Value;
    }
}

