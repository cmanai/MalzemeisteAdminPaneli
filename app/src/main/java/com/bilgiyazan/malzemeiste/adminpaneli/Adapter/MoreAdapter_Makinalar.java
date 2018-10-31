package com.bilgiyazan.malzemeiste.adminpaneli.Adapter;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter.MoreAdapterModelMakinalar;
import com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter.MoreDetailsAdapterModelMakinalar;
import com.bilgiyazan.malzemeiste.adminpaneli.R;
import com.bilgiyazan.malzemeiste.adminpaneli.ViewHolder.MoreDetailsViewHolder_Makinalar;
import com.bilgiyazan.malzemeiste.adminpaneli.ViewHolder.MoreViewHolder_Makinalar;

import java.util.List;

public class MoreAdapter_Makinalar extends ExpandableRecyclerAdapter<MoreAdapterModelMakinalar, MoreDetailsAdapterModelMakinalar, MoreViewHolder_Makinalar, MoreDetailsViewHolder_Makinalar> {

    private static final int PARENT_NORMAL = 0;
    private static final int CHILD_NORMAL = 1;
    Activity activity;
    private LayoutInflater mInflater;
    private List<MoreAdapterModelMakinalar> moreAdapterModelList;

    public MoreAdapter_Makinalar(Activity activity, Context context, @NonNull List<MoreAdapterModelMakinalar> moreAdapterModelList) {
        super(moreAdapterModelList);
        this.moreAdapterModelList = moreAdapterModelList;
        mInflater = LayoutInflater.from(context);
        this.activity = activity;
    }

    @UiThread
    @NonNull
    @Override
    public MoreViewHolder_Makinalar onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View recipeView;

        recipeView = mInflater.inflate(R.layout.list_item_more, parentViewGroup, false);

        return new MoreViewHolder_Makinalar(recipeView);
    }

    @UiThread
    @NonNull
    @Override
    public MoreDetailsViewHolder_Makinalar onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View ingredientView;

        ingredientView = mInflater.inflate(R.layout.more_item_details_makinalar, childViewGroup, false);

        return new MoreDetailsViewHolder_Makinalar(ingredientView);
    }

    @Override
    public void onBindParentViewHolder(@NonNull MoreViewHolder_Makinalar parentViewHolder, int parentPosition, @NonNull MoreAdapterModelMakinalar parent) {
        parentViewHolder.bind(parent);

    }

    @Override
    public void onBindChildViewHolder(@NonNull MoreDetailsViewHolder_Makinalar childViewHolder, int parentPosition, int childPosition, @NonNull MoreDetailsAdapterModelMakinalar child) {
        childViewHolder.bind(child, activity, child.getType(), moreAdapterModelList.get(parentPosition).getMoreDetailsAdapterModelMakinalars().size(), childPosition);

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
