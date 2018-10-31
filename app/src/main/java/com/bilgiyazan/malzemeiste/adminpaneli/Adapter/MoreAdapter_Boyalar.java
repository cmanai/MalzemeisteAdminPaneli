package com.bilgiyazan.malzemeiste.adminpaneli.Adapter;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter.MoreAdapterModelBoyalar;
import com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter.MoreDetailsAdapterModelBoyalar;
import com.bilgiyazan.malzemeiste.adminpaneli.R;
import com.bilgiyazan.malzemeiste.adminpaneli.ViewHolder.MoreDetailsViewHolder_Boyalar;
import com.bilgiyazan.malzemeiste.adminpaneli.ViewHolder.MoreViewHolder_Boyalar;

import java.util.List;

public class MoreAdapter_Boyalar extends ExpandableRecyclerAdapter<MoreAdapterModelBoyalar, MoreDetailsAdapterModelBoyalar, MoreViewHolder_Boyalar, MoreDetailsViewHolder_Boyalar> {

    private static final int PARENT_NORMAL = 0;
    private static final int CHILD_NORMAL = 1;
    Activity activity;
    private LayoutInflater mInflater;
    private List<MoreAdapterModelBoyalar> moreAdapterModelList;

    public MoreAdapter_Boyalar(Activity activity, Context context, @NonNull List<MoreAdapterModelBoyalar> moreAdapterModelList) {
        super(moreAdapterModelList);
        this.moreAdapterModelList = moreAdapterModelList;
        mInflater = LayoutInflater.from(context);
        this.activity = activity;
    }

    @UiThread
    @NonNull
    @Override
    public MoreViewHolder_Boyalar onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View recipeView;

        recipeView = mInflater.inflate(R.layout.list_item_more, parentViewGroup, false);

        return new MoreViewHolder_Boyalar(recipeView);
    }

    @UiThread
    @NonNull
    @Override
    public MoreDetailsViewHolder_Boyalar onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View ingredientView;

        ingredientView = mInflater.inflate(R.layout.more_item_details_boyalar, childViewGroup, false);

        return new MoreDetailsViewHolder_Boyalar(ingredientView);
    }

    @Override
    public void onBindParentViewHolder(@NonNull MoreViewHolder_Boyalar parentViewHolder, int parentPosition, @NonNull MoreAdapterModelBoyalar parent) {
        parentViewHolder.bind(parent);

    }

    @Override
    public void onBindChildViewHolder(@NonNull MoreDetailsViewHolder_Boyalar childViewHolder, int parentPosition, int childPosition, @NonNull MoreDetailsAdapterModelBoyalar child) {
        childViewHolder.bind(child, activity, child.getType(), moreAdapterModelList.get(parentPosition).getMoreDetailsAdapterModelBoyalars().size(), childPosition);

    }


    @Override
    public int getParentViewType(int parentPosition) {

        return PARENT_NORMAL;

    }

    @Override
    public int getChildViewType(int parentPosition, int childPosition) {

        return CHILD_NORMAL;

    }

    @Override
    public boolean isParentViewType(int viewType) {
        return viewType == PARENT_NORMAL;
    }

}
