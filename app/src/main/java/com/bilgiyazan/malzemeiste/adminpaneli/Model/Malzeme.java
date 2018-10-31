package com.bilgiyazan.malzemeiste.adminpaneli.Model;


/**
 * Created by Toshiba on 23/12/2016.
 */

public class Malzeme {

    private Folyo folyo;


    public Malzeme(Folyo folyo) {
        this.folyo = folyo;

    }

    public Malzeme() {

    }

    public Folyo getFolyo() {
        return folyo;
    }

    public void setFolyo(Folyo folyo) {
        this.folyo = folyo;
    }
}
