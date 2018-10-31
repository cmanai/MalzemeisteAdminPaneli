package com.bilgiyazan.malzemeiste.adminpaneli.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
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

import com.bilgiyazan.malzemeiste.adminpaneli.Model.Boyalar;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Eco_Solvent;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Eco_Solvent_Value;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Emerald;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.MLD;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Pro_ink;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Temizleme;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Temizleme_Value;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Triangle;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Uv_Boyalar;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Uv_Boyalar_Value;
import com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter.MoreAdapterModelBoyalar;
import com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter.MoreDetailsAdapterModelBoyalar;
import com.bilgiyazan.malzemeiste.adminpaneli.R;
import com.bilgiyazan.malzemeiste.adminpaneli.ViewHolder.BoyalarViewHeaderHolder;
import com.bilgiyazan.malzemeiste.adminpaneli.ViewHolder.BoyalarViewTitleHolder;
import com.bilgiyazan.malzemeiste.adminpaneli.ViewHolder.MoreViewHolder_Boyalar;
import com.tonicartos.superslim.GridSLM;

import java.util.ArrayList;
import java.util.List;


public class BoyalarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0x00;
    private static final int VIEW_TYPE_NORMAL_CONTENT = 0x01;
    private static final int VIEW_TYPE_TITLE = 0x02;
    private static final int VIEW_TYPE_COLLAPSING = 0x03;


    public final List<String> collapsinglist;
    public final String[] boyalar;
    public final String[] sub_items_name;
    public final String[] items_name;
    private final ArrayList<LineItem> mItems;

    public List<MoreDetailsAdapterModelBoyalar> DetailsList;
    public List<MoreAdapterModelBoyalar> ItemList;
    public Activity activity;
    public List<Eco_Solvent> eco_solventList;
    public List<Uv_Boyalar> uv_boyalarList;
    public List<Temizleme> temizlemeList;
    public List<Emerald> emeraldList;
    public List<MLD> mldList;
    public List<Pro_ink> pro_inkList;
    public List<Triangle> triangleList;
    List<Eco_Solvent_Value> ecoSolventValueList;
    List<Uv_Boyalar_Value> uv_boyalar_valueList;
    List<Temizleme_Value> temizleme_valueList;
    private int mHeaderDisplay;
    private boolean mMarginsFixed;
    private int lastPosition = -1;

    public BoyalarAdapter(Context context, int headerMode, Activity activity, Boyalar boyalar_firebase) {

        eco_solventList = new ArrayList<>();
        eco_solventList = boyalar_firebase.getMimaki_b().getEco_solventList();

        uv_boyalarList = new ArrayList<>();
        uv_boyalarList = boyalar_firebase.getMimaki_b().getUv_boyalarList();


        temizlemeList = new ArrayList<>();
        temizlemeList = boyalar_firebase.getMimaki_b().getTemizlemeList();


        Log.e("eco_solventList size", eco_solventList.size() + "");
        Log.e("uv_boyalarList size", uv_boyalarList.size() + "");
        Log.e("temizlemeList size", temizlemeList.size() + "");


        emeraldList = new ArrayList<>();
        emeraldList = boyalar_firebase.getEmeraldList();


        mldList = new ArrayList<>();
        mldList = boyalar_firebase.getMldList();


        pro_inkList = new ArrayList<>();
        pro_inkList = boyalar_firebase.getProInkList();


        triangleList = new ArrayList<>();
        triangleList = boyalar_firebase.getTriangleList();


        this.activity = activity;
        boyalar = context.getResources().getStringArray(R.array.boyalar);
        sub_items_name = context.getResources().getStringArray(R.array.sub_items_name2);
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
                mItems.add(new BoyalarAdapter.LineItem(header, true, false, false, false, sectionManager, sectionFirstPosition));
            }

            mItems.add(new BoyalarAdapter.LineItem(boyalar[i], false, false, false, true, sectionManager, sectionFirstPosition));
            if (i == 0) {
                for (int j = 0; j < sub_items_name.length; j++) {
                    mItems.add(new BoyalarAdapter.LineItem(sub_items_name[j], false, true, false, false, sectionManager, sectionFirstPosition));
                }
            } else {

                mItems.add(new BoyalarAdapter.LineItem(items_name[0], false, false, true, false, sectionManager, sectionFirstPosition));

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

                return new BoyalarViewHeaderHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.header_item, parent, false));

            case VIEW_TYPE_COLLAPSING:
                return new MoreViewHolder_Boyalar(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.sub_item_layout, parent, false));
            case VIEW_TYPE_TITLE:
                return new BoyalarViewTitleHolder(LayoutInflater.from(parent.getContext())

                        .inflate(R.layout.text_line_item, parent, false));
            case VIEW_TYPE_NORMAL_CONTENT:
                return new BoyalarViewHeaderHolder(LayoutInflater.from(parent.getContext())

                        .inflate(R.layout.normal_item_layout, parent, false));
            default:
                return new BoyalarViewHeaderHolder(LayoutInflater.from(parent.getContext())
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
            final BoyalarAdapter.LineItem item = mItems.get(position);
            BoyalarViewHeaderHolder boyalarViewHeaderHolder = (BoyalarViewHeaderHolder) holder;
            boyalarViewHeaderHolder.bindItem(item.text);
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
            final BoyalarAdapter.LineItem item = mItems.get(position);
            MoreViewHolder_Boyalar moreViewHolder = (MoreViewHolder_Boyalar) holder;


            if (item.text.equals("ECO SOLVENT / SOLVENT BOYALAR")) {

                DetailsList = new ArrayList<>();
                ItemList = new ArrayList<>();
                ecoSolventValueList = new ArrayList<>();
                for (int i = 0; i < eco_solventList.size(); i++) {
                    DetailsList.add(new MoreDetailsAdapterModelBoyalar(eco_solventList.get(i).getEco_Solvent_Name(), "", "", "Header", "", "", "", "", "Mimaki", "", ""));
                    ecoSolventValueList = eco_solventList.get(i).getEco_Solvent_value();
                    if (ecoSolventValueList != null) {
                        for (int v = 0; v < ecoSolventValueList.size(); v++) {
                            DetailsList.add(new MoreDetailsAdapterModelBoyalar(ecoSolventValueList.get(v).getModel(), ecoSolventValueList.get(v).getKod(), ecoSolventValueList.get(v).getFiyat(), "Child", ecoSolventValueList.get(v).getRate(), ecoSolventValueList.get(v).getURL(), ecoSolventValueList.get(v).getShare_Link(), ecoSolventValueList.get(v).getAmbalaj_sekli(), "Mimaki", ecoSolventValueList.get(v).getRenk(), ecoSolventValueList.get(v).getMiktari()));

                        }
                    } else {
                        DetailsList.add(new MoreDetailsAdapterModelBoyalar("", "", "", "Empty", "", "", "", "", "", "", ""));

                    }


                }

                ItemList.add((new MoreAdapterModelBoyalar(item.text, DetailsList, "Parent")));

            } else if (item.text.equals("UV BOYALAR")) {

                DetailsList = new ArrayList<>();
                ItemList = new ArrayList<>();

                uv_boyalar_valueList = new ArrayList<>();
                for (int i = 0; i < uv_boyalarList.size(); i++) {
                    DetailsList.add(new MoreDetailsAdapterModelBoyalar(uv_boyalarList.get(i).getUv_boyalar_Name(), "", "", "Header", "", "", "", "", "Mimaki", "", ""));
                    uv_boyalar_valueList = uv_boyalarList.get(i).getUv_boyalar_Value();
                    if (uv_boyalar_valueList != null) {
                        for (int v = 0; v < uv_boyalar_valueList.size(); v++) {
                            DetailsList.add(new MoreDetailsAdapterModelBoyalar(uv_boyalar_valueList.get(v).getModel(), uv_boyalar_valueList.get(v).getKod(), uv_boyalar_valueList.get(v).getFiyat(), "Child", uv_boyalar_valueList.get(v).getRate(), uv_boyalar_valueList.get(v).getURL(), uv_boyalar_valueList.get(v).getShare_Link(), uv_boyalar_valueList.get(v).getAmbalaj_sekli(), "Mimaki", uv_boyalar_valueList.get(v).getRenk(), uv_boyalar_valueList.get(v).getMiktari()));

                        }
                    } else {
                        DetailsList.add(new MoreDetailsAdapterModelBoyalar("", "", "", "Empty", "", "", "", "", "", "", ""));

                    }


                }

                ItemList.add((new MoreAdapterModelBoyalar(item.text, DetailsList, "Parent")));
            } else if (item.text.equals("TEMİZLEME/BAKIM")) {

                DetailsList = new ArrayList<>();
                ItemList = new ArrayList<>();

                temizleme_valueList = new ArrayList<>();
                for (int i = 0; i < temizlemeList.size(); i++) {
                    DetailsList.add(new MoreDetailsAdapterModelBoyalar(temizlemeList.get(i).getTemizleme_Name(), "", "", "Header", "", "", "", "", "Mimaki", "", ""));
                    temizleme_valueList = temizlemeList.get(i).getTemizleme_Value();
                    if (temizleme_valueList != null) {
                        for (int v = 0; v < temizleme_valueList.size(); v++) {
                            DetailsList.add(new MoreDetailsAdapterModelBoyalar(temizleme_valueList.get(v).getModel(), temizleme_valueList.get(v).getKod(), temizleme_valueList.get(v).getFiyat(), "Child", temizleme_valueList.get(v).getRate(), temizleme_valueList.get(v).getURL(), temizleme_valueList.get(v).getShare_Link(), temizleme_valueList.get(v).getAmbalaj_sekli(), "Mimaki", "", temizleme_valueList.get(v).getMiktari()));

                        }
                    } else {
                        DetailsList.add(new MoreDetailsAdapterModelBoyalar("", "", "", "Empty", "", "", "", "", "", "", ""));

                    }


                }

                ItemList.add((new MoreAdapterModelBoyalar(item.text, DetailsList, "Parent")));
            }


            if (ItemList.size() != 0) {
                MoreAdapter_Boyalar moreAdapter_boyalar = new MoreAdapter_Boyalar(activity, holder.itemView.getContext(), ItemList);
                RecyclerView mRecyclerView;
                mRecyclerView = (RecyclerView) moreViewHolder.itemView.findViewById(R.id.sub_recyclerView);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));

                mRecyclerView.setNestedScrollingEnabled(false);
                mRecyclerView.setHasFixedSize(false);

                mRecyclerView.setAdapter(moreAdapter_boyalar);

                moreAdapter_boyalar.notifyDataSetChanged();


            }


            lp.setSlm(GridSLM.ID);

            lp.setFirstPosition(position);

            itemView.setLayoutParams(lp);


        } else if (getItemViewType(position) == VIEW_TYPE_NORMAL_CONTENT) {


            final View itemView = holder.itemView;
            final GridSLM.LayoutParams lp = GridSLM.LayoutParams.from(itemView.getLayoutParams());
            // Overrides xml attrs, could use different layouts too.
            final BoyalarAdapter.LineItem item = mItems.get(position);

            if (mItems.get(position - 1).text.equals("EMERALD")) {
                List<Emerald> mContentItems = new ArrayList<>();
                if (!emeraldList.isEmpty()) {

                    if (emeraldList.size() >= 4) {
                        for (int i = 0; i < 4; i++) {

                            mContentItems.add(new Emerald(emeraldList.get(i).getModel(), emeraldList.get(i).getKod(), emeraldList.get(i).getRenk(), emeraldList.get(i).getMiktari(), emeraldList.get(i).getAmbalaj_sekli(), emeraldList.get(i).getFiyat(), emeraldList.get(i).getURL(), emeraldList.get(i).getDescription(), emeraldList.get(i).getRate(), emeraldList.get(i).getShare_Link()));


                        }
                    } else {
                        for (int i = 0; i < emeraldList.size(); i++) {


                            mContentItems.add(new Emerald(emeraldList.get(i).getModel(), emeraldList.get(i).getKod(), emeraldList.get(i).getRenk(), emeraldList.get(i).getMiktari(), emeraldList.get(i).getAmbalaj_sekli(), emeraldList.get(i).getFiyat(), emeraldList.get(i).getURL(), emeraldList.get(i).getDescription(), emeraldList.get(i).getRate(), emeraldList.get(i).getShare_Link()));


                        }
                    }
                    RecyclerView mRecyclerView = (RecyclerView) holder.itemView.findViewById(R.id.normal_recyclerView);
                    RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, 1);
                    mRecyclerView.setLayoutManager(layoutManager);

                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setNestedScrollingEnabled(false);

                    NormalItemAdapter_Boyalar mAdapter;
                    mAdapter = new NormalItemAdapter_Boyalar(mContentItems, null, null, null, activity);


                    mRecyclerView.setAdapter(mAdapter);


                    mAdapter.notifyDataSetChanged();
                } else {

                    mContentItems.add(new Emerald("yok", "", "", "", "", "", "", "", "", ""));

                    RecyclerView mRecyclerView = (RecyclerView) holder.itemView.findViewById(R.id.normal_recyclerView);
                    RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, 1);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setNestedScrollingEnabled(false);

                    NormalItemAdapter_Boyalar mAdapter;
                    mAdapter = new NormalItemAdapter_Boyalar(mContentItems, null, null, null, activity);


                    mRecyclerView.setAdapter(mAdapter);


                    mAdapter.notifyDataSetChanged();
                }
            }
            if (mItems.get(position - 1).text.equals("MLD")) {
                List<MLD> mContentItems = new ArrayList<>();

                if (!mldList.isEmpty()) {
                    if (mldList.size() >= 4) {
                        for (int i = 0; i < 4; i++) {

                            mContentItems.add(new MLD(mldList.get(i).getModel(), mldList.get(i).getKod(), mldList.get(i).getRenk(), mldList.get(i).getMiktari(), mldList.get(i).getAmbalaj_sekli(), mldList.get(i).getFiyat(), mldList.get(i).getURL(), mldList.get(i).getDescription(), mldList.get(i).getRate(), mldList.get(i).getShare_Link()));


                        }
                    } else {
                        for (int i = 0; i < mldList.size(); i++) {

                            mContentItems.add(new MLD(mldList.get(i).getModel(), mldList.get(i).getKod(), mldList.get(i).getRenk(), mldList.get(i).getMiktari(), mldList.get(i).getAmbalaj_sekli(), mldList.get(i).getFiyat(), mldList.get(i).getURL(), mldList.get(i).getDescription(), mldList.get(i).getRate(), mldList.get(i).getShare_Link()));


                        }
                    }
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(activity, 2);
                    RecyclerView mRecyclerView = (RecyclerView) holder.itemView.findViewById(R.id.normal_recyclerView);
                    mRecyclerView.setLayoutManager(layoutManager);

                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setNestedScrollingEnabled(false);

                    NormalItemAdapter_Boyalar mAdapter;
                    mAdapter = new NormalItemAdapter_Boyalar(null, mContentItems, null, null, activity);


                    mRecyclerView.setAdapter(mAdapter);


                    mAdapter.notifyDataSetChanged();
                } else {

                    mContentItems.add(new MLD("yok", "", "", "", "", "", "", "", "", ""));

                    RecyclerView mRecyclerView = (RecyclerView) holder.itemView.findViewById(R.id.normal_recyclerView);
                    RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, 1);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setNestedScrollingEnabled(false);

                    NormalItemAdapter_Boyalar mAdapter;
                    mAdapter = new NormalItemAdapter_Boyalar(null, mContentItems, null, null, activity);


                    mRecyclerView.setAdapter(mAdapter);


                    mAdapter.notifyDataSetChanged();
                }
            }
            if (mItems.get(position - 1).text.equals("PRO_INK")) {
                List<Pro_ink> mContentItems = new ArrayList<>();

                if (!pro_inkList.isEmpty()) {
                    if (pro_inkList.size() >= 4) {
                        for (int i = 0; i < 4; i++) {

                            mContentItems.add(new Pro_ink(pro_inkList.get(i).getModel(), pro_inkList.get(i).getKod(), pro_inkList.get(i).getRenk(), pro_inkList.get(i).getMiktari(), pro_inkList.get(i).getAmbalaj_sekli(), pro_inkList.get(i).getFiyat(), pro_inkList.get(i).getURL(), pro_inkList.get(i).getDescription(), pro_inkList.get(i).getRate(), pro_inkList.get(i).getShare_Link()));


                        }
                    } else {
                        for (int i = 0; i < pro_inkList.size(); i++) {

                            mContentItems.add(new Pro_ink(pro_inkList.get(i).getModel(), pro_inkList.get(i).getKod(), pro_inkList.get(i).getRenk(), pro_inkList.get(i).getMiktari(), pro_inkList.get(i).getAmbalaj_sekli(), pro_inkList.get(i).getFiyat(), pro_inkList.get(i).getURL(), pro_inkList.get(i).getDescription(), pro_inkList.get(i).getRate(), pro_inkList.get(i).getShare_Link()));


                        }
                    }
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(activity, 2);
                    RecyclerView mRecyclerView = (RecyclerView) holder.itemView.findViewById(R.id.normal_recyclerView);
                    mRecyclerView.setLayoutManager(layoutManager);

                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setNestedScrollingEnabled(false);

                    NormalItemAdapter_Boyalar mAdapter;
                    mAdapter = new NormalItemAdapter_Boyalar(null, null, mContentItems, null, activity);


                    mRecyclerView.setAdapter(mAdapter);


                    mAdapter.notifyDataSetChanged();
                } else {

                    mContentItems.add(new Pro_ink("yok", "", "", "", "", "", "", "", "", ""));

                    RecyclerView mRecyclerView = (RecyclerView) holder.itemView.findViewById(R.id.normal_recyclerView);
                    RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, 1);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setNestedScrollingEnabled(false);

                    NormalItemAdapter_Boyalar mAdapter;
                    mAdapter = new NormalItemAdapter_Boyalar(null, null, mContentItems, null, activity);


                    mRecyclerView.setAdapter(mAdapter);


                    mAdapter.notifyDataSetChanged();
                }

            }
          /*  if (mItems.get(position - 1).text.equals("TRIANGLE")) {
                List<Triangle> mContentItems = new ArrayList<>();

                if (!triangleList.isEmpty()) {
                    if (triangleList.size() >= 4) {
                        for (int i = 0; i < 4; i++) {
                            if (triangleList.get(i).getModel().length() > 25) {

                                mContentItems.add(new Triangle(triangleList.get(i).getModel().substring(0, 24) + "...", triangleList.get(i).getKod(), "", "", "", triangleList.get(i).getFiyat(), triangleList.get(i).getURL(), triangleList.get(i).getDescription(), triangleList.get(i).getRate()));
                            } else {
                                mContentItems.add(new Triangle(triangleList.get(i).getModel(), triangleList.get(i).getKod(), "", "", "", triangleList.get(i).getFiyat(), triangleList.get(i).getURL(), triangleList.get(i).getDescription(), triangleList.get(i).getRate()));

                            }
                        }
                    } else {
                        for (int i = 0; i < triangleList.size(); i++) {
                            if (triangleList.get(i).getModel().length() > 25) {

                                mContentItems.add(new Triangle(triangleList.get(i).getModel().substring(0, 24) + "...", triangleList.get(i).getKod(), "", "", "", triangleList.get(i).getFiyat(), triangleList.get(i).getURL(), triangleList.get(i).getDescription(), triangleList.get(i).getRate()));
                            } else {
                                mContentItems.add(new Triangle(triangleList.get(i).getModel(), triangleList.get(i).getKod(), "", "", "", triangleList.get(i).getFiyat(), triangleList.get(i).getURL(), triangleList.get(i).getDescription(), triangleList.get(i).getRate()));

                            }
                        }
                    }
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(activity, 2);
                    RecyclerView mRecyclerView = (RecyclerView) holder.itemView.findViewById(R.id.normal_recyclerView);
                    mRecyclerView.setLayoutManager(layoutManager);

                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setNestedScrollingEnabled(false);

                    NormalItemAdapter_Boyalar mAdapter;
                    mAdapter = new NormalItemAdapter_Boyalar(null, null, null, mContentItems, activity);


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
            final BoyalarAdapter.LineItem item = mItems.get(position);
            BoyalarViewTitleHolder boyalarViewTitleHolder = (BoyalarViewTitleHolder) holder;
            boyalarViewTitleHolder.bindItem(item.text, position - 1, "no", activity, "");

            if (mItems.get(position).text.equals("EMERALD")) {
                if (emeraldList.size() <= 4) {
                    boyalarViewTitleHolder.bindItem(item.text, position - 1, "no", activity, "Boyalar_EMERALD");
                } else {
                    boyalarViewTitleHolder.bindItem(item.text, position - 1, "yes", activity, "Boyalar_EMERALD");
                }

            } else if (mItems.get(position).text.equals("MLD")) {
                if (mldList.size() <= 4) {
                    boyalarViewTitleHolder.bindItem(item.text, position - 1, "no", activity, "Boyalar_MLD");
                } else {
                    boyalarViewTitleHolder.bindItem(item.text, position - 1, "yes", activity, "Boyalar_MLD");
                }

            } else if (mItems.get(position).text.equals("PRO_INK")) {
                if (pro_inkList.size() <= 4) {
                    boyalarViewTitleHolder.bindItem(item.text, position - 1, "no", activity, "Boyalar_PRO_INK");
                } else {
                    boyalarViewTitleHolder.bindItem(item.text, position - 1, "yes", activity, "Boyalar_PRO_INK");
                }
            } else if (mItems.get(position).text.equals("TRIANGLE")) {
                if (triangleList.size() <= 4) {
                    boyalarViewTitleHolder.bindItem(item.text, position - 1, "no", activity, "Boyalar_TRIANGLE");
                } else {
                    boyalarViewTitleHolder.bindItem(item.text, position - 1, "yes", activity, "Boyalar_TRIANGLE");
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
            BoyalarAdapter.LineItem item = mItems.get(i);
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