package com.bilgiyazan.malzemeiste.adminpaneli.Model;


/**
 * Created by Toshiba on 23/12/2016.
 */

public class SearchModel {

    private String Model;
    private String Kod;
    private String Renk;
    private String Miktari;
    private String Ambalaj_sekli;
    private String Fiyat;
    private String URL;
    private String Description;
    private String Rate;
    private String Share_Link;
    private String From;
    private String Uyumlu;
    private String KDV;
    private String Marka;

    public SearchModel(String Model, String Kod, String Fiyat, String URL, String Rate, String From, String Share_Link, String Renk, String Miktari, String Ambalaj_sekli, String Uyumlu, String Description, String KDV, String Marka) {

        this.Model = Model;
        this.Kod = Kod;
        this.Renk = Renk;
        this.Miktari = Miktari;
        this.Ambalaj_sekli = Ambalaj_sekli;
        this.Fiyat = Fiyat;
        this.URL = URL;
        this.Description = Description;
        this.Rate = Rate;
        this.Share_Link = Share_Link;
        this.From = From;
        this.Uyumlu = Uyumlu;
        this.KDV = KDV;
        this.Marka = Marka;
    }

    public SearchModel() {


    }


    public String getMarka() {
        return Marka;
    }

    public void setMarka(String marka) {
        Marka = marka;
    }

    public String getUyumlu() {
        return Uyumlu;
    }

    public void setUyumlu(String uyumlu) {
        Uyumlu = uyumlu;
    }

    public String getKDV() {
        return KDV;
    }

    public void setKDV(String KDV) {
        this.KDV = KDV;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getShare_Link() {
        return Share_Link;
    }

    public void setShare_Link(String share_Link) {
        Share_Link = share_Link;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getRenk() {
        return Renk;
    }

    public void setRenk(String renk) {
        Renk = renk;
    }

    public String getMiktari() {
        return Miktari;
    }

    public void setMiktari(String miktari) {
        Miktari = miktari;
    }

    public String getAmbalaj_sekli() {
        return Ambalaj_sekli;
    }

    public void setAmbalaj_sekli(String ambalaj_sekli) {
        Ambalaj_sekli = ambalaj_sekli;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getKod() {
        return Kod;
    }

    public void setKod(String kod) {
        Kod = kod;
    }

    public String getFiyat() {
        return Fiyat;
    }

    public void setFiyat(String fiyat) {
        Fiyat = fiyat;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

}

