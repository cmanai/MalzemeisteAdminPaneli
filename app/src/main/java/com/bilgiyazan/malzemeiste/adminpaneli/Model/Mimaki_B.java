package com.bilgiyazan.malzemeiste.adminpaneli.Model;


import java.util.List;

/**
 * Created by Toshiba on 23/12/2016.
 */

public class Mimaki_B {

    private List<Eco_Solvent> eco_solventList;
    private List<Uv_Boyalar> uv_boyalarList;
    private List<Temizleme> temizlemeList;


    public Mimaki_B(List<Eco_Solvent> eco_solventList, List<Uv_Boyalar> uv_boyalarList, List<Temizleme> temizlemeList) {
        this.eco_solventList = eco_solventList;
        this.uv_boyalarList = uv_boyalarList;
        this.temizlemeList = temizlemeList;
    }

    public Mimaki_B() {


    }

    public List<Eco_Solvent> getEco_solventList() {
        return eco_solventList;
    }

    public void setEco_solventList(List<Eco_Solvent> eco_solventList) {
        this.eco_solventList = eco_solventList;
    }

    public List<Uv_Boyalar> getUv_boyalarList() {
        return uv_boyalarList;
    }

    public void setUv_boyalarList(List<Uv_Boyalar> uv_boyalarList) {
        this.uv_boyalarList = uv_boyalarList;
    }

    public List<Temizleme> getTemizlemeList() {
        return temizlemeList;
    }

    public void setTemizlemeList(List<Temizleme> temizlemeList) {
        this.temizlemeList = temizlemeList;
    }
}
