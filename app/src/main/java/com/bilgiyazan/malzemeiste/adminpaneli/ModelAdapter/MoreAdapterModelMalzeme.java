package com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter;


import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

public class MoreAdapterModelMalzeme implements Parent<MoreDetailsAdapterModelMalzeme> {

    private String Name;
    private String Type;

    private List<MoreDetailsAdapterModelMalzeme> moreDetailsAdapterModelMalzemes;

    public MoreAdapterModelMalzeme(String Name, List<MoreDetailsAdapterModelMalzeme> moreDetailsAdapterModelMalzemes, String Type) {
        this.Name = Name;
        this.Type = Type;
        this.moreDetailsAdapterModelMalzemes = moreDetailsAdapterModelMalzemes;
    }


    @Override
    public List<MoreDetailsAdapterModelMalzeme> getChildList() {
        return moreDetailsAdapterModelMalzemes;
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

    public List<MoreDetailsAdapterModelMalzeme> getMoreDetailsAdapterModelMalzemes() {
        return moreDetailsAdapterModelMalzemes;
    }

    public void setMoreDetailsAdapterModelMalzemes(List<MoreDetailsAdapterModelMalzeme> moreDetailsAdapterModelMalzemes) {
        this.moreDetailsAdapterModelMalzemes = moreDetailsAdapterModelMalzemes;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
