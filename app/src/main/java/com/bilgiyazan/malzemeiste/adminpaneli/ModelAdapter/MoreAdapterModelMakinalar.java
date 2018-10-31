package com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter;


import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

public class MoreAdapterModelMakinalar implements Parent<MoreDetailsAdapterModelMakinalar> {

    private String Name;
    private String Type;

    private List<MoreDetailsAdapterModelMakinalar> moreDetailsAdapterModelMakinalars;

    public MoreAdapterModelMakinalar(String Name, List<MoreDetailsAdapterModelMakinalar> moreDetailsAdapterModelMakinalars, String Type) {
        this.Name = Name;
        this.Type = Type;
        this.moreDetailsAdapterModelMakinalars = moreDetailsAdapterModelMakinalars;
    }


    @Override
    public List<MoreDetailsAdapterModelMakinalar> getChildList() {
        return moreDetailsAdapterModelMakinalars;
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

    public List<MoreDetailsAdapterModelMakinalar> getMoreDetailsAdapterModelMakinalars() {
        return moreDetailsAdapterModelMakinalars;
    }

    public void setMoreDetailsAdapterModelMakinalars(List<MoreDetailsAdapterModelMakinalar> moreDetailsAdapterModelMakinalars) {
        this.moreDetailsAdapterModelMakinalars = moreDetailsAdapterModelMakinalars;
    }


    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
