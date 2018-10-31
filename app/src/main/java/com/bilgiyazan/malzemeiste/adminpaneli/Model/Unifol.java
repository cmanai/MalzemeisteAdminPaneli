package com.bilgiyazan.malzemeiste.adminpaneli.Model;


import java.util.List;

/**
 * Created by Toshiba on 23/12/2016.
 */

public class Unifol {
    private String Unifol_Name;
    private List<Unifol_Value> Unifol_Value;


    public Unifol(String Unifol_Name, List<Unifol_Value> Unifol_Value) {

        this.Unifol_Name = Unifol_Name;
        this.Unifol_Value = Unifol_Value;


    }

    public Unifol() {


    }


    public String getUnifol_Name() {
        return Unifol_Name;
    }

    public void setUnifol_Name(String unifol_Name) {
        Unifol_Name = unifol_Name;
    }

    public List<Unifol_Value> getUnifol_Value() {
        return Unifol_Value;
    }

    public void setUnifol_Value(List<Unifol_Value> unifol_Value) {
        Unifol_Value = unifol_Value;
    }
}

