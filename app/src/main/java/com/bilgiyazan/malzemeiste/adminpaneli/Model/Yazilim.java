package com.bilgiyazan.malzemeiste.adminpaneli.Model;


import java.util.List;

/**
 * Created by Toshiba on 23/12/2016.
 */

public class Yazilim {


    private List<CorelDraw> corelDrawList;


    public Yazilim(List<CorelDraw> corelDrawList) {


        this.corelDrawList = corelDrawList;

    }

    public Yazilim() {

    }


    public List<CorelDraw> getCorelDrawList() {
        return corelDrawList;
    }

    public void setCorelDrawList(List<CorelDraw> corelDrawList) {
        this.corelDrawList = corelDrawList;
    }
}
