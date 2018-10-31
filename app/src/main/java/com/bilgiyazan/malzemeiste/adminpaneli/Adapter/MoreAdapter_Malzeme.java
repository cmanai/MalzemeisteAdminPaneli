package com.bilgiyazan.malzemeiste.adminpaneli.Adapter;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter.MoreAdapterModelMalzeme;
import com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter.MoreDetailsAdapterModelMalzeme;
import com.bilgiyazan.malzemeiste.adminpaneli.R;
import com.bilgiyazan.malzemeiste.adminpaneli.ViewHolder.MoreDetailsViewHolder_Malzeme;
import com.bilgiyazan.malzemeiste.adminpaneli.ViewHolder.MoreViewHolder_Malzeme;

import java.util.List;

public class MoreAdapter_Malzeme extends ExpandableRecyclerAdapter<MoreAdapterModelMalzeme, MoreDetailsAdapterModelMalzeme, MoreViewHolder_Malzeme, MoreDetailsViewHolder_Malzeme> {

    private static final int PARENT_NORMAL = 0;
    private static final int CHILD_NORMAL = 1;
    Activity activity;
    private LayoutInflater mInflater;
    private List<MoreAdapterModelMalzeme> moreAdapterModelList;

    public MoreAdapter_Malzeme(Activity activity, Context context, @NonNull List<MoreAdapterModelMalzeme> moreAdapterModelList) {
        super(moreAdapterModelList);
        this.moreAdapterModelList = moreAdapterModelList;
        mInflater = LayoutInflater.from(context);
        this.activity = activity;
    }

    @UiThread
    @NonNull
    @Override
    public MoreViewHolder_Malzeme onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View recipeView;

        recipeView = mInflater.inflate(R.layout.list_item_more, parentViewGroup, false);

        return new MoreViewHolder_Malzeme(recipeView);
    }

    @UiThread
    @NonNull
    @Override
    public MoreDetailsViewHolder_Malzeme onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View ingredientView;

        ingredientView = mInflater.inflate(R.layout.more_item_details_malzeme, childViewGroup, false);

        return new MoreDetailsViewHolder_Malzeme(ingredientView);
    }

    @Override
    public void onBindParentViewHolder(@NonNull MoreViewHolder_Malzeme parentViewHolder, int parentPosition, @NonNull MoreAdapterModelMalzeme parent) {
        parentViewHolder.bind(parent);

    }

    @Override
    public void onBindChildViewHolder(@NonNull MoreDetailsViewHolder_Malzeme childViewHolder, int parentPosition, int childPosition, @NonNull MoreDetailsAdapterModelMalzeme child) {
        childViewHolder.bind(child, activity, child.getType(), moreAdapterModelList.get(parentPosition).getMoreDetailsAdapterModelMalzemes().size(), childPosition);

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
