package com.bilgiyazan.malzemeiste.adminpaneli.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bilgiyazan.malzemeiste.adminpaneli.Model.Kesim;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Lateks;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Makinalar;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Roland;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Solvent_Uv_Yazicilar;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Solvent_Yazici;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Uv_Yazicilar;
import com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter.MoreAdapterModelMakinalar;
import com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter.MoreDetailsAdapterModelMakinalar;
import com.bilgiyazan.malzemeiste.adminpaneli.R;
import com.bilgiyazan.malzemeiste.adminpaneli.ViewHolder.MakinalarViewHeaderHolder;
import com.bilgiyazan.malzemeiste.adminpaneli.ViewHolder.MakinalarViewTitleHolder;
import com.bilgiyazan.malzemeiste.adminpaneli.ViewHolder.MoreViewHolder_Makinalar;
import com.tonicartos.superslim.GridSLM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class MakinalarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0x00;
    private static final int VIEW_TYPE_NORMAL_CONTENT = 0x01;
    private static final int VIEW_TYPE_TITLE = 0x02;
    private static final int VIEW_TYPE_COLLAPSING = 0x03;
    private static final int LINEAR = 0;
    final List<String> collapsinglist;
    final String[] makinalar;
    final String[] sub_items_name;
    final String[] items_name;
    private final ArrayList<LineItem> mItems;
    private final ArrayList<LineItem> mItems2;
    private final ArrayList<LineItem> mItems3;
    private final Context mContext;
    List<MoreDetailsAdapterModelMakinalar> DetailsList;
    List<MoreAdapterModelMakinalar> ItemList;
    Activity activity;
    List<Kesim> kesimList;
    List<Roland> rolandList;
    List<Solvent_Yazici> solvent_yaziciList;
    List<Solvent_Uv_Yazicilar> solvent_uv_yazicilarList;
    List<Uv_Yazicilar> uv_yazicilarList;
    List<Lateks> lateksList;
    private int lastPosition = -1;
    private int mHeaderDisplay;
    private boolean mMarginsFixed;

    public MakinalarAdapter(Context context, int headerMode, Activity activity, Makinalar makinalar_firebase) {

        kesimList = new ArrayList<>();
        kesimList = makinalar_firebase.getMimaki_m().getKesimList();

        solvent_yaziciList = new ArrayList<>();
        solvent_yaziciList = makinalar_firebase.getMimaki_m().getSolvent_yaziciList();


        solvent_uv_yazicilarList = new ArrayList<>();
        solvent_uv_yazicilarList = makinalar_firebase.getMimaki_m().getSolvent_uv_yazicilarList();


        uv_yazicilarList = new ArrayList<>();
        uv_yazicilarList = makinalar_firebase.getMimaki_m().getUv_yazicilarList();

        lateksList = new ArrayList<>();
        lateksList = makinalar_firebase.getMimaki_m().getLateksList();

        rolandList = new ArrayList<>();
        rolandList = makinalar_firebase.getRolandList();


        mContext = context;
        this.activity = activity;
        makinalar = context.getResources().getStringArray(R.array.makinalar);
        sub_items_name = context.getResources().getStringArray(R.array.sub_items_name);
        items_name = context.getResources().getStringArray(R.array.items_name);

        mHeaderDisplay = headerMode;

        mItems = new ArrayList<>();
        mItems2 = new ArrayList<>();
        mItems3 = new ArrayList<>();
        collapsinglist = new ArrayList<>();

        //Insert headers into list of items.
        String lastHeader = "";
        int sectionManager = -1;
        int headerCount = 0;
        int sectionFirstPosition = 0;
        for (int i = 0; i < makinalar.length; i++) {
            String header = makinalar[i].substring(0, 1);
            if (!TextUtils.equals(lastHeader, header)) {
                // Insert new header view and update section data.
                sectionManager = (sectionManager + 1) % 2;
                sectionFirstPosition = i + headerCount;
                lastHeader = header;
                headerCount += 1;
                mItems.add(new MakinalarAdapter.LineItem(header, true, false, false, false, sectionManager, sectionFirstPosition));
            }

            mItems.add(new MakinalarAdapter.LineItem(makinalar[i], false, false, false, true, sectionManager, sectionFirstPosition));
            if (i == 0) {
                for (int j = 0; j < sub_items_name.length; j++) {
                    mItems.add(new MakinalarAdapter.LineItem(sub_items_name[j], false, true, false, false, sectionManager, sectionFirstPosition));
                }
            } else {

                mItems.add(new MakinalarAdapter.LineItem(items_name[0], false, false, true, false, sectionManager, sectionFirstPosition));

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

                return new MakinalarViewHeaderHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.header_item, parent, false));

            case VIEW_TYPE_COLLAPSING:
                return new MoreViewHolder_Makinalar(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.sub_item_layout, parent, false));
            case VIEW_TYPE_TITLE:
                return new MakinalarViewTitleHolder(LayoutInflater.from(parent.getContext())

                        .inflate(R.layout.text_line_item, parent, false));
            case VIEW_TYPE_NORMAL_CONTENT:
                return new MakinalarViewHeaderHolder(LayoutInflater.from(parent.getContext())

                        .inflate(R.layout.normal_item_layout, parent, false));
            default:
                return new MakinalarViewHeaderHolder(LayoutInflater.from(parent.getContext())
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
            final MakinalarAdapter.LineItem item = mItems.get(position);
            MakinalarViewHeaderHolder makinalarViewHeaderHolder = (MakinalarViewHeaderHolder) holder;
            makinalarViewHeaderHolder.bindItem(item.text);
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
            final MakinalarAdapter.LineItem item = mItems.get(position);
            MoreViewHolder_Makinalar moreViewHolder = (MoreViewHolder_Makinalar) holder;


         /*   <item>KESİM PLOTTERLERİ</item>
            <item>SOLVENT YAZICI</item>
            <item>SOLVENT UV YAZICILAR</item>
            <item>UV YAZICILAR</item>
            <item>LATEKS</item>*/
            if (item.text.equals("KESİM PLOTTERLERİ")) {

                DetailsList = new ArrayList<>();
                ItemList = new ArrayList<>();
                if (!kesimList.isEmpty()) {
                    for (int i = 0; i < kesimList.size(); i++) {
                        DetailsList.add(new MoreDetailsAdapterModelMakinalar(kesimList.get(i).getModel(), kesimList.get(i).getKod(), kesimList.get(i).getFiyat(), kesimList.get(i).getRate(), "Child", kesimList.get(i).getURL(), kesimList.get(i).getShare_Link(), kesimList.get(i).getKDV(), "Mimaki", kesimList.get(i).getDescription()));
                    }
                } else {
                    DetailsList.add(new MoreDetailsAdapterModelMakinalar("", "", "", "", "Empty", "", "", "", "", ""));

                }
                ItemList.add((new MoreAdapterModelMakinalar(item.text, DetailsList, "Parent")));

            } else if (item.text.equals("SOLVENT YAZICI")) {

                DetailsList = new ArrayList<>();
                ItemList = new ArrayList<>();
                if (!solvent_yaziciList.isEmpty()) {
                    for (int i = 0; i < solvent_yaziciList.size(); i++) {
                        DetailsList.add(new MoreDetailsAdapterModelMakinalar(solvent_yaziciList.get(i).getModel(), solvent_yaziciList.get(i).getKod(), solvent_yaziciList.get(i).getFiyat(), solvent_yaziciList.get(i).getRate(), "Child", solvent_yaziciList.get(i).getURL(), solvent_yaziciList.get(i).getShare_Link(), solvent_yaziciList.get(i).getKDV(), "Mimaki", solvent_yaziciList.get(i).getDescription()));
                    }
                } else {
                    DetailsList.add(new MoreDetailsAdapterModelMakinalar("", "", "", "", "Empty", "", "", "", "", ""));

                }

                ItemList.add((new MoreAdapterModelMakinalar(item.text, DetailsList, "Parent")));
            } else if (item.text.equals("SOLVENT UV YAZICILAR")) {


                DetailsList = new ArrayList<>();
                ItemList = new ArrayList<>();
                if (!solvent_uv_yazicilarList.isEmpty()) {
                    for (int i = 0; i < solvent_uv_yazicilarList.size(); i++) {
                        DetailsList.add(new MoreDetailsAdapterModelMakinalar(solvent_uv_yazicilarList.get(i).getModel(), solvent_uv_yazicilarList.get(i).getKod(), solvent_uv_yazicilarList.get(i).getFiyat(), solvent_uv_yazicilarList.get(i).getRate(), "Child", solvent_uv_yazicilarList.get(i).getURL(), solvent_uv_yazicilarList.get(i).getShare_Link(), solvent_uv_yazicilarList.get(i).getKDV(), "Mimaki", solvent_uv_yazicilarList.get(i).getDescription()));
                    }
                } else {
                    DetailsList.add(new MoreDetailsAdapterModelMakinalar("", "", "", "", "Empty", "", "", "", "", ""));

                }

                ItemList.add((new MoreAdapterModelMakinalar(item.text, DetailsList, "Parent")));
            } else if (item.text.equals("UV YAZICILAR")) {

                DetailsList = new ArrayList<>();
                ItemList = new ArrayList<>();
                if (!uv_yazicilarList.isEmpty()) {
                    for (int i = 0; i < uv_yazicilarList.size(); i++) {
                        DetailsList.add(new MoreDetailsAdapterModelMakinalar(uv_yazicilarList.get(i).getModel(), uv_yazicilarList.get(i).getKod(), uv_yazicilarList.get(i).getFiyat(), uv_yazicilarList.get(i).getRate(), "Child", uv_yazicilarList.get(i).getURL(), uv_yazicilarList.get(i).getShare_Link(), uv_yazicilarList.get(i).getKDV(), "Mimaki", uv_yazicilarList.get(i).getDescription()));
                    }
                } else {
                    DetailsList.add(new MoreDetailsAdapterModelMakinalar("", "", "", "", "Empty", "", "", "", "", ""));

                }
                ItemList.add((new MoreAdapterModelMakinalar(item.text, DetailsList, "Parent")));
            } else if (item.text.equals("LATEKS")) {

                DetailsList = new ArrayList<>();
                ItemList = new ArrayList<>();
                if (!lateksList.isEmpty()) {
                    for (int i = 0; i < lateksList.size(); i++) {
                        DetailsList.add(new MoreDetailsAdapterModelMakinalar(lateksList.get(i).getModel(), lateksList.get(i).getKod(), lateksList.get(i).getFiyat(), lateksList.get(i).getRate(), "Child", lateksList.get(i).getURL(), lateksList.get(i).getShare_Link(), lateksList.get(i).getKDV(), "Mimaki", lateksList.get(i).getDescription()));
                    }
                } else {
                    DetailsList.add(new MoreDetailsAdapterModelMakinalar("", "", "", "", "Empty", "", "", "", "", ""));

                }
                ItemList.add((new MoreAdapterModelMakinalar(item.text, DetailsList, "Parent")));
            }


            if (ItemList.size() != 0) {
                MoreAdapter_Makinalar moreAdapter_makinalar = new MoreAdapter_Makinalar(activity, holder.itemView.getContext(), ItemList);
                RecyclerView mRecyclerView;
                mRecyclerView = (RecyclerView) moreViewHolder.itemView.findViewById(R.id.sub_recyclerView);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));

                mRecyclerView.setNestedScrollingEnabled(false);
                mRecyclerView.setHasFixedSize(false);

                mRecyclerView.setAdapter(moreAdapter_makinalar);

                moreAdapter_makinalar.notifyDataSetChanged();


            }


            lp.setSlm(GridSLM.ID);

            lp.setFirstPosition(position);

            itemView.setLayoutParams(lp);


        } else if (getItemViewType(position) == VIEW_TYPE_NORMAL_CONTENT) {


            final View itemView = holder.itemView;
            final GridSLM.LayoutParams lp = GridSLM.LayoutParams.from(itemView.getLayoutParams());
            // Overrides xml attrs, could use different layouts too.
            final MakinalarAdapter.LineItem item = mItems.get(position);

          /*  if (mItems.get(position - 1).text.equals("OKI")) {
                List<OKI> mContentItems = new ArrayList<>();
                if (!okiList.isEmpty()) {

                    if (okiList.size() >= 4) {
                        for (int i = 0; i < 4; i++) {
                            if (okiList.get(i).getModel().length() > 25) {
                                mContentItems.add(new OKI(okiList.get(i).getModel().substring(0, 24) + "...", okiList.get(i).getKod(), okiList.get(i).getFiyat(), okiList.get(i).getURL(), okiList.get(i).getDescription(), okiList.get(i).getRate()));

                            } else {
                                mContentItems.add(new OKI(okiList.get(i).getModel(), okiList.get(i).getKod(), okiList.get(i).getFiyat(), okiList.get(i).getURL(), okiList.get(i).getDescription(), okiList.get(i).getRate()));

                            }
                        }
                    } else {
                        for (int i = 0; i < okiList.size(); i++) {

                            if (okiList.get(i).getModel().length() > 25) {
                                mContentItems.add(new OKI(okiList.get(i).getModel().substring(0, 24) + "...", okiList.get(i).getKod(), okiList.get(i).getFiyat(), okiList.get(i).getURL(), okiList.get(i).getDescription(), okiList.get(i).getRate()));

                            } else {
                                mContentItems.add(new OKI(okiList.get(i).getModel(), okiList.get(i).getKod(), okiList.get(i).getFiyat(), okiList.get(i).getURL(), okiList.get(i).getDescription(), okiList.get(i).getRate()));

                            }
                        }
                    }
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(activity, 2);
                    RecyclerView mRecyclerView = (RecyclerView) holder.itemView.findViewById(R.id.normal_recyclerView);
                    mRecyclerView.setLayoutManager(layoutManager);

                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setNestedScrollingEnabled(false);

                    NormalItemAdapter_Makinalar mAdapter;
                    mAdapter = new NormalItemAdapter_Makinalar(mContentItems, null, null, activity);


                    mRecyclerView.setAdapter(mAdapter);


                    mAdapter.notifyDataSetChanged();
                }
            }*/
            if (mItems.get(position - 1).text.equals("ROLAND")) {
                List<Roland> mContentItems = new ArrayList<>();

                if (!rolandList.isEmpty()) {
                    Log.e("roland", "not empty");

                    if (rolandList.size() >= 4) {
                        for (int i = 0; i < 4; i++) {

                            mContentItems.add(new Roland(rolandList.get(i).getModel(), rolandList.get(i).getKod(), rolandList.get(i).getFiyat(), rolandList.get(i).getURL(), rolandList.get(i).getDescription(), rolandList.get(i).getRate(), rolandList.get(i).getShare_Link(), rolandList.get(i).getKDV()));


                        }
                    } else {
                        for (int i = 0; i < rolandList.size(); i++) {

                            mContentItems.add(new Roland(rolandList.get(i).getModel(), rolandList.get(i).getKod(), rolandList.get(i).getFiyat(), rolandList.get(i).getURL(), rolandList.get(i).getDescription(), rolandList.get(i).getRate(), rolandList.get(i).getShare_Link(), rolandList.get(i).getKDV()));


                        }
                    }

                    RecyclerView mRecyclerView = (RecyclerView) holder.itemView.findViewById(R.id.normal_recyclerView);
                    RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, 1);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setNestedScrollingEnabled(false);

                    NormalItemAdapter_Makinalar mAdapter;
                    mAdapter = new NormalItemAdapter_Makinalar(null, mContentItems, null, activity);


                    mRecyclerView.setAdapter(mAdapter);


                    mAdapter.notifyDataSetChanged();
                } else {
                    Log.e("roland", "empty");

                    mContentItems.add(new Roland("yok", "", "", "", "", "", "", ""));
                    RecyclerView mRecyclerView = (RecyclerView) holder.itemView.findViewById(R.id.normal_recyclerView);
                    RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, 1);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setNestedScrollingEnabled(false);

                    NormalItemAdapter_Makinalar mAdapter;
                    mAdapter = new NormalItemAdapter_Makinalar(null, mContentItems, null, activity);


                    mRecyclerView.setAdapter(mAdapter);


                    mAdapter.notifyDataSetChanged();
                }
            }
        /*    if (mItems.get(position - 1).text.equals("A-STARTJET")) {
                List<A_Starjet> mContentItems = new ArrayList<>();

                if (!a_starjetList.isEmpty()) {
                    if (a_starjetList.size() >= 4) {
                        for (int i = 0; i < 4; i++) {
                            if (a_starjetList.get(i).getModel().length() > 25) {

                                mContentItems.add(new A_Starjet(a_starjetList.get(i).getModel().substring(0, 24) + "...", a_starjetList.get(i).getKod(), a_starjetList.get(i).getFiyat(), a_starjetList.get(i).getURL(), a_starjetList.get(i).getDescription(), a_starjetList.get(i).getRate()));
                            } else {
                                mContentItems.add(new A_Starjet(a_starjetList.get(i).getModel(), a_starjetList.get(i).getKod(), a_starjetList.get(i).getFiyat(), a_starjetList.get(i).getURL(), a_starjetList.get(i).getDescription(), a_starjetList.get(i).getRate()));

                            }
                        }
                    } else {
                        for (int i = 0; i < a_starjetList.size(); i++) {
                            if (a_starjetList.get(i).getModel().length() > 25) {

                                mContentItems.add(new A_Starjet(a_starjetList.get(i).getModel().substring(0, 24) + "...", a_starjetList.get(i).getKod(), a_starjetList.get(i).getFiyat(), a_starjetList.get(i).getURL(), a_starjetList.get(i).getDescription(), a_starjetList.get(i).getRate()));
                            } else {
                                mContentItems.add(new A_Starjet(a_starjetList.get(i).getModel(), a_starjetList.get(i).getKod(), a_starjetList.get(i).getFiyat(), a_starjetList.get(i).getURL(), a_starjetList.get(i).getDescription(), a_starjetList.get(i).getRate()));

                            }
                        }
                    }
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(activity, 2);
                    RecyclerView mRecyclerView = (RecyclerView) holder.itemView.findViewById(R.id.normal_recyclerView);
                    mRecyclerView.setLayoutManager(layoutManager);

                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setNestedScrollingEnabled(false);

                    NormalItemAdapter_Makinalar mAdapter;
                    mAdapter = new NormalItemAdapter_Makinalar(null, null, mContentItems, activity);


                    mRecyclerView.setAdapter(mAdapter);


                    mAdapter.notifyDataSetChanged();
                }
            }*/


            lp.setSlm(GridSLM.ID);

            lp.setFirstPosition(position);

            itemView.setLayoutParams(lp);
        } else if (getItemViewType(position) == VIEW_TYPE_TITLE) {


            final View itemView = holder.itemView;
            final GridSLM.LayoutParams lp = GridSLM.LayoutParams.from(itemView.getLayoutParams());
            // Overrides xml attrs, could use different layouts too.
            final MakinalarAdapter.LineItem item = mItems.get(position);
            MakinalarViewTitleHolder makinalarViewTitleHolder = (MakinalarViewTitleHolder) holder;
            makinalarViewTitleHolder.bindItem(item.text, position - 1, "no", activity, "");
            if (mItems.get(position).text.equals("ROLAND")) {
                if (rolandList.size() <= 4) {
                    makinalarViewTitleHolder.bindItem(item.text, position - 1, "no", activity, "Makinalar_ROLAND");
                } else {
                    makinalarViewTitleHolder.bindItem(item.text, position - 1, "yes", activity, "Makinalar_ROLAND");
                }

            }
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
            MakinalarAdapter.LineItem item = mItems.get(i);
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