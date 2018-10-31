package com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter;


import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

public class MoreAdapterModelYedek implements Parent<MoreDetailsAdapterModelYedek> {

    private String Name;
    private String Type;

    private List<MoreDetailsAdapterModelYedek> moreDetailsAdapterModelYedeks;

    public MoreAdapterModelYedek(String Name, List<MoreDetailsAdapterModelYedek> moreDetailsAdapterModelMakinalars, String Type) {
        this.Name = Name;
        this.Type = Type;
        this.moreDetailsAdapterModelYedeks = moreDetailsAdapterModelMakinalars;
    }


    @Override
    public List<MoreDetailsAdapterModelYedek> getChildList() {
        return moreDetailsAdapterModelYedeks;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<MoreDetailsAdapterModelYedek> getMoreDetailsAdapterModelYedeks() {
        return moreDetailsAdapterModelYedeks;
    }

    public void setMoreDetailsAdapterModelYedeks(List<MoreDetailsAdapterModelYedek> moreDetailsAdapterModelMakinalars) {
        this.moreDetailsAdapterModelYedeks = moreDetailsAdapterModelMakinalars;
    }


    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
