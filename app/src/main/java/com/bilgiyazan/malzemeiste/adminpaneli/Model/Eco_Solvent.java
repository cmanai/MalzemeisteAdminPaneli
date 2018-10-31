package com.bilgiyazan.malzemeiste.adminpaneli.Model;


import java.util.List;

/**
 * Created by Toshiba on 23/12/2016.
 */

public class Eco_Solvent {
    private String Eco_Solvent_Name;
    private List<Eco_Solvent_Value> Eco_Solvent_Value;


    public Eco_Solvent(String Eco_Solvent_Name, List<Eco_Solvent_Value> Eco_Solvent_Value) {

        this.Eco_Solvent_Name = Eco_Solvent_Name;
        this.Eco_Solvent_Value = Eco_Solvent_Value;


    }

    public Eco_Solvent() {


    }


    public String getEco_Solvent_Name() {
        return Eco_Solvent_Name;
    }

    public void setEco_Solvent_Name(String eco_Solvent_Name) {
        Eco_Solvent_Name = eco_Solvent_Name;
    }

    public List<Eco_Solvent_Value> getEco_Solvent_value() {
        return Eco_Solvent_Value;
    }

    public void setEco_Solvent_value(List<Eco_Solvent_Value> eco_Solvent_value) {
        Eco_Solvent_Value = eco_Solvent_value;
    }
}

