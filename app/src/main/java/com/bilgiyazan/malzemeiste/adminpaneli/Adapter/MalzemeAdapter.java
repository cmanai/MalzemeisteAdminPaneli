package com.bilgiyazan.malzemeiste.adminpaneli.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bilgiyazan.malzemeiste.adminpaneli.Model.Malzeme;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Unifol;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Unifol_Value;
import com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter.MoreAdapterModelMalzeme;
import com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter.MoreDetailsAdapterModelMalzeme;
import com.bilgiyazan.malzemeiste.adminpaneli.R;
import com.bilgiyazan.malzemeiste.adminpaneli.ViewHolder.MalzemeViewHeaderHolder;
import com.bilgiyazan.malzemeiste.adminpaneli.ViewHolder.MalzemeViewTitleHolder;
import com.bilgiyazan.malzemeiste.adminpaneli.ViewHolder.MoreViewHolder_Malzeme;
import com.tonicartos.superslim.GridSLM;

import java.util.ArrayList;
import java.util.List;


public class MalzemeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0x00;
    private static final int VIEW_TYPE_NORMAL_CONTENT = 0x01;
    private static final int VIEW_TYPE_TITLE = 0x02;
    private static final int VIEW_TYPE_COLLAPSING = 0x03;
    public final String[] boyalar;
    private final List<String> collapsinglist;
    private final String[] sub_items_name;
    private final String[] items_name;
    private final ArrayList<LineItem> mItems;
    public Activity activity;
    private List<MoreDetailsAdapterModelMalzeme> DetailsList;
    private List<MoreAdapterModelMalzeme> ItemList;
    private List<Unifol> unifolList;

    private List<Unifol_Value> unifol_valueList;

    private int mHeaderDisplay;
    private boolean mMarginsFixed;
    private int lastPosition = -1;

    public MalzemeAdapter(Context context, int headerMode, Activity activity, Malzeme malzeme) {

        this.unifolList = new ArrayList<>();
        unifolList = malzeme.getFolyo().getUnifolList();


        Log.e("unifolList size", unifolList.size() + "");


        this.activity = activity;
        boyalar = context.getResources().getStringArray(R.array.malzeme);
        sub_items_name = context.getResources().getStringArray(R.array.sub_items_name4);
        items_name = context.getResources().getStringArray(R.array.items_name);

        mHeaderDisplay = headerMode;

        mItems = new ArrayList<>();

        collapsinglist = new ArrayList<>();

        //Insert headers into list of items.
        String lastHeader = "";
        int sectionManager = -1;
        int headerCount = 0;
        int sectionFirstPosition = 0;
        for (int i = 0; i < boyalar.length; i++) {
            String header = boyalar[i].substring(0, 1);
            if (!TextUtils.equals(lastHeader, header)) {
                // Insert new header view and update section data.
                sectionManager = (sectionManager + 1) % 2;
                sectionFirstPosition = i + headerCount;
                lastHeader = header;
                headerCount += 1;
                mItems.add(new MalzemeAdapter.LineItem(header, true, false, false, false, sectionManager, sectionFirstPosition));
            }

            mItems.add(new MalzemeAdapter.LineItem(boyalar[i], false, false, false, true, sectionManager, sectionFirstPosition));
            if (i == 0) {
                for (int j = 0; j < sub_items_name.length; j++) {
                    mItems.add(new MalzemeAdapter.LineItem(sub_items_name[j], false, true, false, false, sectionManager, sectionFirstPosition));
                }
            } else {

                mItems.add(new MalzemeAdapter.LineItem(items_name[0], false, false, true, false, sectionManager, sectionFirstPosition));

            }


        }

    }

    public boolean isItemHeader(int position) {
        return mItems.get(position).isHeader;
    }


    public String itemToString(int position) {
        return mItems.get(position).text;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {


            case VIEW_TYPE_HEADER:

                return new MalzemeViewHeaderHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.header_item, parent, false));

            case VIEW_TYPE_COLLAPSING:
                return new MoreViewHolder_Malzeme(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.sub_item_layout, parent, false));
            case VIEW_TYPE_TITLE:
                return new MalzemeViewTitleHolder(LayoutInflater.from(parent.getContext())

                        .inflate(R.layout.text_line_item, parent, false));
            case VIEW_TYPE_NORMAL_CONTENT:
                return new MalzemeViewHeaderHolder(LayoutInflater.from(parent.getContext())

                        .inflate(R.layout.normal_item_layout, parent, false));
            default:
                return new MalzemeViewHeaderHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.header_item, parent, false));

        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        setAnimation(holder.itemView, position);


        if (getItemViewType(position) == VIEW_TYPE_HEADER) {


            final View itemView = holder.itemView;
            final GridSLM.LayoutParams lp = GridSLM.LayoutParams.from(itemView.getLayoutParams());
            // Overrides xml attrs, could use different layouts too.
            final MalzemeAdapter.LineItem item = mItems.get(position);
            MalzemeViewHeaderHolder malzemeViewHeaderHolder = (MalzemeViewHeaderHolder) holder;
            malzemeViewHeaderHolder.bindItem(item.text);
            lp.headerDisplay = mHeaderDisplay;
            if (lp.isHeaderInline() || (mMarginsFixed && !lp.isHeaderOverlay())) {
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            } else {
                lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            }

            lp.headerEndMarginIsAuto = !mMarginsFixed;
            lp.headerStartMarginIsAuto = !mMarginsFixed;
            lp.setSlm(GridSLM.ID);
            lp.setFirstPosition(position);
            itemView.setLayoutParams(lp);


        } else if (getItemViewType(position) == VIEW_TYPE_COLLAPSING) {


            final View itemView = holder.itemView;
            final GridSLM.LayoutParams lp = GridSLM.LayoutParams.from(itemView.getLayoutParams());
            // Overrides xml attrs, could use different layouts too.
            final MalzemeAdapter.LineItem item = mItems.get(position);
            MoreViewHolder_Malzeme moreViewHolder = (MoreViewHolder_Malzeme) holder;


            if (item.text.equals("UNIFOL")) {

                DetailsList = new ArrayList<>();
                ItemList = new ArrayList<>();
                unifol_valueList = new ArrayList<>();
                for (int i = 0; i < unifolList.size(); i++) {
                    DetailsList.add(new MoreDetailsAdapterModelMalzeme(unifolList.get(i).getUnifol_Name(), "", "", "Header", "", "", "", "", "", ""));
                    unifol_valueList = unifolList.get(i).getUnifol_Value();
                    if (unifol_valueList != null) {

                        for (int v = 0; v < unifol_valueList.size(); v++) {
                            DetailsList.add(new MoreDetailsAdapterModelMalzeme(unifol_valueList.get(v).getModel(), unifol_valueList.get(v).getKod(), unifol_valueList.get(v).getFiyat(), "Child", unifol_valueList.get(v).getRate(), unifol_valueList.get(v).getURL(), unifol_valueList.get(v).getShare_Link(), unifol_valueList.get(v).getRenk(), "Unifol", unifol_valueList.get(v).getMiktari()));

                        }
                    } else {
                        DetailsList.add(new MoreDetailsAdapterModelMalzeme("", "", "", "Empty", "", "", "", "", "", ""));

                    }


                }

                ItemList.add((new MoreAdapterModelMalzeme(item.text, DetailsList, "Parent")));

            }


            if (ItemList.size() != 0) {

                MoreAdapter_Malzeme moreAdapter_malzeme = new MoreAdapter_Malzeme(activity, holder.itemView.getContext(), ItemList);
                RecyclerView mRecyclerView;
                mRecyclerView = (RecyclerView) moreViewHolder.itemView.findViewById(R.id.sub_recyclerView);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));

                mRecyclerView.setNestedScrollingEnabled(false);
                mRecyclerView.setHasFixedSize(false);

                mRecyclerView.setAdapter(moreAdapter_malzeme);
              /*  if(ItemList.size()==1){
                    moreAdapter_malzeme.expandParent(0);
                }*/
                moreAdapter_malzeme.notifyDataSetChanged();


            }


            lp.setSlm(GridSLM.ID);

            lp.setFirstPosition(position);

            itemView.setLayoutParams(lp);


        } else if (getItemViewType(position) == VIEW_TYPE_NORMAL_CONTENT) {


            final View itemView = holder.itemView;
            final GridSLM.LayoutParams lp = GridSLM.LayoutParams.from(itemView.getLayoutParams());
            // Overrides xml attrs, could use different layouts too.
            final MalzemeAdapter.LineItem item = mItems.get(position);


            lp.setSlm(GridSLM.ID);

            lp.setFirstPosition(position);

            itemView.setLayoutParams(lp);
        } else if (getItemViewType(position) == VIEW_TYPE_TITLE) {

            final View itemView = holder.itemView;
            final GridSLM.LayoutParams lp = GridSLM.LayoutParams.from(itemView.getLayoutParams());
            // Overrides xml attrs, could use different layouts too.
            final MalzemeAdapter.LineItem item = mItems.get(position);
            MalzemeViewTitleHolder malzemeViewTitleHolder = (MalzemeViewTitleHolder) holder;
            malzemeViewTitleHolder.bindItem(item.text, position - 1, "no", activity, "");

            lp.setSlm(GridSLM.ID);
            // lp.setColumnWidth(mContext.getResources().getDimensionPixelSize(R.dimen.grid_column_width));

            lp.setFirstPosition(position - 1);
            itemView.setLayoutParams(lp);


        }

    }

    @Override
    public int getItemViewType(int position) {

        if (mItems.get(position).isHeader) {
            return VIEW_TYPE_HEADER;
        } else if (mItems.get(position).isTittle) {
            return VIEW_TYPE_TITLE;
        } else if (mItems.get(position).isCollapsing) {
            return VIEW_TYPE_COLLAPSING;
        } else if (mItems.get(position).isNormal) {
            return VIEW_TYPE_NORMAL_CONTENT;
        }
        return -1;

    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setHeaderDisplay(int headerDisplay) {
        mHeaderDisplay = headerDisplay;
        notifyHeaderChanges();
    }

    public void setMarginsFixed(boolean marginsFixed) {
        mMarginsFixed = marginsFixed;
        notifyHeaderChanges();
    }

    private void notifyHeaderChanges() {
        for (int i = 0; i < mItems.size(); i++) {
            MalzemeAdapter.LineItem item = mItems.get(i);
            if (item.isHeader) {
                notifyItemChanged(i);
            }
        }
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(activity, android.R.anim.fade_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

        holder.itemView.clearAnimation();
    }

    private static class LineItem {

        public int sectionManager;

        public int sectionFirstPosition;

        public boolean isHeader;
        public String text;
        boolean isCollapsing;
        boolean isNormal;
        boolean isTittle;

        public LineItem(String text, boolean isHeader, boolean isCollapsing, boolean isNormal, boolean isTittle, int sectionManager,
                        int sectionFirstPosition) {
            this.isHeader = isHeader;
            this.isCollapsing = isCollapsing;
            this.isNormal = isNormal;
            this.isTittle = isTittle;
            this.text = text;
            this.sectionManager = sectionManager;
            this.sectionFirstPosition = sectionFirstPosition;
        }
    }
}