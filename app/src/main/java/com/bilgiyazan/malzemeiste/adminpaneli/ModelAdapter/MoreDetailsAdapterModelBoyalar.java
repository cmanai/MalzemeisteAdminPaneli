package com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter;

public class MoreDetailsAdapterModelBoyalar {

    private String Model;
    private String Kod;
    private String Fiyat;
    private String Type;
    private String Rate;
    private String URL;
    private String Share_Link;
    private String Marka;
    private String Ambalaj_sekli;
    private String Renk;
    private String Miktari;

    public MoreDetailsAdapterModelBoyalar(String Model, String Kod, String Fiyat, String Type, String Rate, String URL, String Share_Link, String Ambalaj_sekli, String Marka, String Renk, String Miktari) {
        this.Model = Model;
        this.Kod = Kod;
        this.Fiyat = Fiyat;
        this.Type = Type;
        this.Rate = Rate;
        this.URL = URL;
        this.Share_Link = Share_Link;
        this.Ambalaj_sekli = Ambalaj_sekli;
        this.Marka = Marka;
        this.Renk = Renk;
        this.Miktari = Miktari;
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

    public String getMarka() {
        return Marka;
    }

    public void setMarka(String marka) {
        Marka = marka;
    }

    public String getAmbalaj_sekli() {
        return Ambalaj_sekli;
    }

    public void setAmbalaj_sekli(String ambalaj_sekli) {
        Ambalaj_sekli = ambalaj_sekli;
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
