package com.bilgiyazan.malzemeiste.adminpaneli.Adapter;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter.MoreAdapterModelYedek;
import com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter.MoreDetailsAdapterModelYedek;
import com.bilgiyazan.malzemeiste.adminpaneli.R;
import com.bilgiyazan.malzemeiste.adminpaneli.ViewHolder.MoreDetailsViewHolder_Yedek;
import com.bilgiyazan.malzemeiste.adminpaneli.ViewHolder.MoreViewHolder_Yedek;

import java.util.List;

public class MoreAdapter_Yedek extends ExpandableRecyclerAdapter<MoreAdapterModelYedek, MoreDetailsAdapterModelYedek, MoreViewHolder_Yedek, MoreDetailsViewHolder_Yedek> {

    private static final int PARENT_NORMAL = 0;
    private static final int CHILD_NORMAL = 1;
    Activity activity;
    private LayoutInflater mInflater;
    private List<MoreAdapterModelYedek> moreAdapterModelList;

    public MoreAdapter_Yedek(Activity activity, Context context, @NonNull List<MoreAdapterModelYedek> moreAdapterModelList) {
        super(moreAdapterModelList);
        this.moreAdapterModelList = moreAdapterModelList;
        mInflater = LayoutInflater.from(context);
        this.activity = activity;
    }

    @UiThread
    @NonNull
    @Override
    public MoreViewHolder_Yedek onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View recipeView;

        recipeView = mInflater.inflate(R.layout.list_item_more, parentViewGroup, false);

        return new MoreViewHolder_Yedek(recipeView);
    }

    @UiThread
    @NonNull
    @Override
    public MoreDetailsViewHolder_Yedek onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View ingredientView;

        ingredientView = mInflater.inflate(R.layout.more_item_details_yedek, childViewGroup, false);

        return new MoreDetailsViewHolder_Yedek(ingredientView);
    }

    @Override
    public void onBindParentViewHolder(@NonNull MoreViewHolder_Yedek parentViewHolder, int parentPosition, @NonNull MoreAdapterModelYedek parent) {
        parentViewHolder.bind(parent);

    }

    @Override
    public void onBindChildViewHolder(@NonNull MoreDetailsViewHolder_Yedek childViewHolder, int parentPosition, int childPosition, @NonNull MoreDetailsAdapterModelYedek child) {
        childViewHolder.bind(child, activity, child.getType(), moreAdapterModelList.get(parentPosition).getMoreDetailsAdapterModelYedeks().size(), childPosition);

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
