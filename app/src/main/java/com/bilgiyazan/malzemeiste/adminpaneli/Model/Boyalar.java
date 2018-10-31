package com.bilgiyazan.malzemeiste.adminpaneli.Model;


import java.util.List;

/**
 * Created by Toshiba on 23/12/2016.
 */

public class Boyalar {

    private Mimaki_B mimaki_b;
    private List<Emerald> emeraldList;
    private List<MLD> mldList;
    private List<Pro_ink> proInkList;
    private List<Triangle> triangleList;


    public Boyalar(Mimaki_B mimaki_b, List<Emerald> emeraldList, List<MLD> mldList, List<Pro_ink> proInkList, List<Triangle> triangleList) {


        this.mimaki_b = mimaki_b;
        this.emeraldList = emeraldList;
        this.mldList = mldList;
        this.proInkList = proInkList;
        this.triangleList = triangleList;

    }

    public Boyalar() {

    }

    public Mimaki_B getMimaki_b() {
        return mimaki_b;
    }

    public void setMimaki_b(Mimaki_B mimaki_b) {
        this.mimaki_b = mimaki_b;
    }

    public List<Emerald> getEmeraldList() {
        return emeraldList;
    }

    public void setEmeraldList(List<Emerald> emeraldList) {
        this.emeraldList = emeraldList;
    }

    public List<MLD> getMldList() {
        return mldList;
    }

    public void setMldList(List<MLD> mldList) {
        this.mldList = mldList;
    }

    public List<Pro_ink> getProInkList() {
        return proInkList;
    }

    public void setProInkList(List<Pro_ink> proInkList) {
        this.proInkList = proInkList;
    }

    public List<Triangle> getTriangleList() {
        return triangleList;
    }

    public void setTriangleList(List<Triangle> triangleList) {
        this.triangleList = triangleList;
    }
}
