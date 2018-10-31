package com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter;

public class MoreDetailsAdapterModelMalzeme {

    private String Model;
    private String Kod;
    private String Fiyat;
    private String Type;
    private String Rate;
    private String URL;
    private String Share_Link;
    private String renk;
    private String Marka;
    private String Miktari;

    public MoreDetailsAdapterModelMalzeme(String Model, String Kod, String Fiyat, String Type, String Rate, String URL, String Share_Link, String renk, String Marka, String Miktari) {
        this.Model = Model;
        this.Kod = Kod;
        this.Fiyat = Fiyat;
        this.Type = Type;
        this.Rate = Rate;
        this.URL = URL;
        this.Share_Link = Share_Link;
        this.renk = renk;
        this.Marka = Marka;
        this.Miktari = Miktari;
    }


    public String getMiktari() {
        return Miktari;
    }

    public void setMiktari(String miktari) {
        Miktari = miktari;
    }

    public String getRenk() {
        return renk;
    }

    public void setRenk(String renk) {
        this.renk = renk;
    }

    public String getMarka() {
        return Marka;
    }

    public void setMarka(String marka) {
        Marka = marka;
    }

    public String getrenk() {
        return renk;
    }

    public void setrenk(String renk) {
        renk = renk;
    }

    public String getShare_Link() {
        return Share_Link;
    }

    public void setShare_Link(String share_Link) {
        Share_Link = share_Link;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
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

    public String getModel() {
        return Model;
    }

    public void setModel(String Model) {
        Model = Model;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
