package com.bilgiyazan.malzemeiste.adminpaneli.Model;


import java.util.List;

/**
 * Created by Toshiba on 23/12/2016.
 */

public class Makinalar {

    private Mimaki_M mimaki_m;
    private List<Roland> rolandList;


    public Makinalar(Mimaki_M mimaki_m, List<Roland> rolandList) {


        this.mimaki_m = mimaki_m;
        this.rolandList = rolandList;


    }

    public Makinalar() {

    }

    public Mimaki_M getMimaki_m() {
        return mimaki_m;
    }

    public void setMimaki_m(Mimaki_M mimaki_m) {
        this.mimaki_m = mimaki_m;
    }


    public List<Roland> getRolandList() {
        return rolandList;
    }

    public void setRolandList(List<Roland> rolandList) {
        this.rolandList = rolandList;
    }


}
