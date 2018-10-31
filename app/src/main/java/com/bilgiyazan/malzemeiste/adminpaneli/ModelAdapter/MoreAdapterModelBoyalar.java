package com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter;


import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

public class MoreAdapterModelBoyalar implements Parent<MoreDetailsAdapterModelBoyalar> {

    private String Name;
    private String Type;

    private List<MoreDetailsAdapterModelBoyalar> moreDetailsAdapterModelBoyalars;

    public MoreAdapterModelBoyalar(String Name, List<MoreDetailsAdapterModelBoyalar> moreDetailsAdapterModelBoyalars, String Type) {
        this.Name = Name;
        this.Type = Type;
        this.moreDetailsAdapterModelBoyalars = moreDetailsAdapterModelBoyalars;
    }


    @Override
    public List<MoreDetailsAdapterModelBoyalar> getChildList() {
        return moreDetailsAdapterModelBoyalars;
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

    public List<MoreDetailsAdapterModelBoyalar> getMoreDetailsAdapterModelBoyalars() {
        return moreDetailsAdapterModelBoyalars;
    }

    public void setMoreDetailsAdapterModelBoyalars(List<MoreDetailsAdapterModelBoyalar> moreDetailsAdapterModelBoyalars) {
        this.moreDetailsAdapterModelBoyalars = moreDetailsAdapterModelBoyalars;
    }


    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
