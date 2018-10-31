package com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter;

public class MoreDetailsAdapterModelMakinalar {

    private String Model;
    private String Kod;
    private String Fiyat;
    private String Type;
    private String Rate;
    private String URL;
    private String Share_Link;
    private String KDV;
    private String Marka;
    private String Description;


    public MoreDetailsAdapterModelMakinalar(String Model, String Kod, String Fiyat, String Rate, String Type, String URL, String Share_Link, String KDV, String Marka, String Description) {
        this.Model = Model;
        this.Kod = Kod;
        this.Fiyat = Fiyat;
        this.Type = Type;
        this.Rate = Rate;
        this.URL = URL;
        this.Share_Link = Share_Link;
        this.KDV = KDV;
        this.Marka = Marka;
        this.Description = Description;


    }

    public String getMarka() {
        return Marka;
    }

    public void setMarka(String marka) {
        Marka = marka;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getKDV() {
        return KDV;
    }

    public void setKDV(String KDV) {
        this.KDV = KDV;
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
