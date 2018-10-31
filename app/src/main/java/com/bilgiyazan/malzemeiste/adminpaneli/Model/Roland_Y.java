package com.bilgiyazan.malzemeiste.adminpaneli.Model;


import java.util.List;

/**
 * Created by Toshiba on 23/12/2016.
 */

public class Roland_Y {

    private List<Bicaklar> bicaklarList;
    private List<Head_Board> head_boardList;
    private List<Kablo> kabloList;
    private List<Main_Board> main_boardList;
    private List<Motor> motorList;


    public Roland_Y(List<Bicaklar> bicaklarList, List<Head_Board> head_boardList, List<Kablo> kabloList, List<Main_Board> main_boardList, List<Motor> motorList) {
        this.bicaklarList = bicaklarList;
        this.head_boardList = head_boardList;
        this.kabloList = kabloList;
        this.main_boardList = main_boardList;
        this.motorList = motorList;
    }

    public Roland_Y() {


    }

    public List<Bicaklar> getBicaklarList() {
        return bicaklarList;
    }

    public void setBicaklarList(List<Bicaklar> bicaklarList) {
        this.bicaklarList = bicaklarList;
    }

    public List<Head_Board> getHead_boardList() {
        return head_boardList;
    }

    public void setHead_boardList(List<Head_Board> head_boardList) {
        this.head_boardList = head_boardList;
    }

    public List<Kablo> getKabloList() {
        return kabloList;
    }

    public void setKabloList(List<Kablo> kabloList) {
        this.kabloList = kabloList;
    }

    public List<Main_Board> getMain_boardList() {
        return main_boardList;
    }

    public void setMain_boardList(List<Main_Board> main_boardList) {
        this.main_boardList = main_boardList;
    }

    public List<Motor> getMotorList() {
        return motorList;
    }

    public void setMotorList(List<Motor> motorList) {
        this.motorList = motorList;
    }
}
