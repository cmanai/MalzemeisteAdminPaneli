package com.bilgiyazan.malzemeiste.adminpaneli.Model;


import java.util.List;

/**
 * Created by Toshiba on 23/12/2016.
 */

public class Folyo {

    private List<Unifol> unifolList;


    public Folyo(List<Unifol> unifolList) {
        this.unifolList = unifolList;

    }

    public Folyo() {

    }

    public List<Unifol> getUnifolList() {
        return unifolList;
    }

    public void setUnifolList(List<Unifol> unifolList) {
        this.unifolList = unifolList;
    }
}
