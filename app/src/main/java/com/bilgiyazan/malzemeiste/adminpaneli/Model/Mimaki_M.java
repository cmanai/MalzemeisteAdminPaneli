package com.bilgiyazan.malzemeiste.adminpaneli.Model;


import java.util.List;

/**
 * Created by Toshiba on 23/12/2016.
 */

public class Mimaki_M {

    private List<Kesim> kesimList;
    private List<Solvent_Yazici> solvent_yaziciList;
    private List<Solvent_Uv_Yazicilar> solvent_uv_yazicilarList;
    private List<Uv_Yazicilar> uv_yazicilarList;
    private List<Lateks> lateksList;


    public Mimaki_M(List<Kesim> kesimList, List<Solvent_Yazici> solvent_yaziciList, List<Solvent_Uv_Yazicilar> solvent_uv_yazicilarList, List<Uv_Yazicilar> uv_yazicilarList, List<Lateks> lateksList) {
        this.kesimList = kesimList;
        this.solvent_yaziciList = solvent_yaziciList;
        this.solvent_uv_yazicilarList = solvent_uv_yazicilarList;
        this.uv_yazicilarList = uv_yazicilarList;
        this.lateksList = lateksList;
    }

    public Mimaki_M() {

    }

    public List<Solvent_Yazici> getSolvent_yaziciList() {
        return solvent_yaziciList;
    }

    public void setSolvent_yaziciList(List<Solvent_Yazici> solvent_yaziciList) {
        this.solvent_yaziciList = solvent_yaziciList;
    }

    public List<Solvent_Uv_Yazicilar> getSolvent_uv_yazicilarList() {
        return solvent_uv_yazicilarList;
    }

    public void setSolvent_uv_yazicilarList(List<Solvent_Uv_Yazicilar> solvent_uv_yazicilarList) {
        this.solvent_uv_yazicilarList = solvent_uv_yazicilarList;
    }

    public List<Uv_Yazicilar> getUv_yazicilarList() {
        return uv_yazicilarList;
    }

    public void setUv_yazicilarList(List<Uv_Yazicilar> uv_yazicilarList) {
        this.uv_yazicilarList = uv_yazicilarList;
    }

    public List<Lateks> getLateksList() {
        return lateksList;
    }

    public void setLateksList(List<Lateks> lateksList) {
        this.lateksList = lateksList;
    }

    public List<Kesim> getKesimList() {
        return kesimList;
    }

    public void setKesimList(List<Kesim> kesimList) {
        this.kesimList = kesimList;
    }


}
