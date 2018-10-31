package com.bilgiyazan.malzemeiste.adminpaneli.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bilgiyazan.malzemeiste.adminpaneli.Model.Kampanyalar;
import com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter.MoreAdapterModelYedek;
import com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter.MoreDetailsAdapterModelYedek;
import com.bilgiyazan.malzemeiste.adminpaneli.R;
import com.bilgiyazan.malzemeiste.adminpaneli.ViewHolder.MoreViewHolder_Yedek;
import com.bilgiyazan.malzemeiste.adminpaneli.ViewHolder.YedekViewHeaderHolder;
import com.bilgiyazan.malzemeiste.adminpaneli.ViewHolder.YedekViewTitleHolder;
import com.tonicartos.superslim.GridSLM;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by florentchampigny on 24/04/15.
 */
public class KampanyalarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0x00;
    private static final int VIEW_TYPE_NORMAL_CONTENT = 0x01;
    private static final int VIEW_TYPE_TITLE = 0x02;
    private static final int VIEW_TYPE_COLLAPSING = 0x03;
    private static final int LINEAR = 0;
    final List<String> collapsinglist;
    final String[] kampanyalar_array;
    final String[] sub_items_name;
    final String[] items_name;
    private final ArrayList<LineItem> mItems;

    private final Context mContext;
    List<MoreDetailsAdapterModelYedek> DetailsList;
    List<MoreAdapterModelYedek> ItemList;
    Activity activity;
    List<Kampanyalar> kampanyalars;
    private int lastPosition = -1;
    private int mHeaderDisplay;
    private boolean mMarginsFixed;

    public KampanyalarAdapter(Context context, int headerMode, Activity activity, List<Kampanyalar> kampanyalars) {

        this.kampanyalars = new ArrayList<>();
        this.kampanyalars = kampanyalars;
        Log.e("data size kampanyalar", this.kampanyalars.size() + "");
        mContext = context;
        this.activity = activity;
        kampanyalar_array = context.getResources().getStringArray(R.array.kamp);
        sub_items_name = context.getResources().getStringArray(R.array.sub_items_name3);
        items_name = context.getResources().getStringArray(R.array.items_name);

        mHeaderDisplay = headerMode;

        mItems = new ArrayList<>();

        collapsinglist = new ArrayList<>();

        //Insert headers into list of items.
        String lastHeader = "";
        int sectionManager = -1;
        int headerCount = 0;
        int sectionFirstPosition = 0;
        for (int i = 0; i < kampanyalar_array.length; i++) {
            String header = kampanyalar_array[i].substring(0, 1);
            if (!TextUtils.equals(lastHeader, header)) {
                // Insert new header view and update section data.
                sectionManager = (sectionManager + 1) % 2;
                sectionFirstPosition = i + headerCount;
                lastHeader = header;
                headerCount += 1;
                mItems.add(new KampanyalarAdapter.LineItem(header, true, false, false, false, sectionManager, sectionFirstPosition));
            }

            mItems.add(new KampanyalarAdapter.LineItem(kampanyalar_array[i], false, false, false, true, sectionManager, sectionFirstPosition));

            mItems.add(new KampanyalarAdapter.LineItem(items_name[0], false, false, true, false, sectionManager, sectionFirstPosition));
               /* for (int j = 0; j < sub_items_name.length; j++) {
                    mItems.add(new kampanyalarAdapter.LineItem(sub_items_name[j], false, true, false, false, sectionManager, sectionFirstPosition));
                }*/


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

                return new YedekViewHeaderHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.header_item, parent, false));

            case VIEW_TYPE_COLLAPSING:
                return new MoreViewHolder_Yedek(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.sub_item_layout, parent, false));
            case VIEW_TYPE_TITLE:
                return new YedekViewTitleHolder(LayoutInflater.from(parent.getContext())

                        .inflate(R.layout.text_line_item, parent, false));
            case VIEW_TYPE_NORMAL_CONTENT:
                return new YedekViewHeaderHolder(LayoutInflater.from(parent.getContext())

                        .inflate(R.layout.normal_item_layout, parent, false));
            default:
                return new YedekViewTitleHolder(LayoutInflater.from(parent.getContext())
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
            final KampanyalarAdapter.LineItem item = mItems.get(position);
            YedekViewHeaderHolder yedekViewHeaderHolder = (YedekViewHeaderHolder) holder;
            yedekViewHeaderHolder.bindItem(item.text);
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

/*

            final View itemView = holder.itemView;
            final GridSLM.LayoutParams lp = GridSLM.LayoutParams.from(itemView.getLayoutParams());
            // Overrides xml attrs, could use different layouts too.
            final kampanyalarAdapter.LineItem item = mItems.get(position);
            MoreViewHolder_Yedek moreViewHolder = (MoreViewHolder_Yedek) holder;


         */
/*   <item>KESİM PLOTTERLERİ</item>
            <item>SOLVENT YAZICI</item>
            <item>SOLVENT UV YAZICILAR</item>
            <item>UV YAZICILAR</item>
            <item>LATEKS</item>*//*

            if (item.text.equals("MAİN BOARD")) {

                DetailsList = new ArrayList<>();
                ItemList = new ArrayList<>();

                for (int i = 0; i < main_boardList.size(); i++) {
                    DetailsList.add(new MoreDetailsAdapterModelYedek(main_boardList.get(i).getModel(), main_boardList.get(i).getKod(), main_boardList.get(i).getFiyat(), main_boardList.get(i).getRate(), "Child", main_boardList.get(i).getURL(), main_boardList.get(i).getShare_Link(),main_boardList.get(i).getUyumlu(),main_boardList.get(i).getKDV()));
                }

                ItemList.add((new MoreAdapterModelYedek(item.text, DetailsList, "Parent")));

            } else if (item.text.equals("HEAD BOARD")) {

                DetailsList = new ArrayList<>();
                ItemList = new ArrayList<>();

                for (int i = 0; i < head_boardList.size(); i++) {
                    DetailsList.add(new MoreDetailsAdapterModelYedek(head_boardList.get(i).getModel(), head_boardList.get(i).getKod(), head_boardList.get(i).getFiyat(), head_boardList.get(i).getRate(), "Child", head_boardList.get(i).getURL(), head_boardList.get(i).getShare_Link(),head_boardList.get(i).getUyumlu(),head_boardList.get(i).getKDV()));
                }

                ItemList.add((new MoreAdapterModelYedek(item.text, DetailsList, "Parent")));
            } else if (item.text.equals("BIÇAK")) {

                DetailsList = new ArrayList<>();
                ItemList = new ArrayList<>();

                for (int i = 0; i < bicaklarList.size(); i++) {
                    DetailsList.add(new MoreDetailsAdapterModelYedek(bicaklarList.get(i).getModel(), bicaklarList.get(i).getKod(), bicaklarList.get(i).getFiyat(), bicaklarList.get(i).getRate(), "Child", bicaklarList.get(i).getURL(), bicaklarList.get(i).getShare_Link(),bicaklarList.get(i).getUyumlu(),bicaklarList.get(i).getKDV()));
                }

                ItemList.add((new MoreAdapterModelYedek(item.text, DetailsList, "Parent")));
            } else if (item.text.equals("MOTOR")) {

                DetailsList = new ArrayList<>();
                ItemList = new ArrayList<>();

                for (int i = 0; i < motorList.size(); i++) {
                    DetailsList.add(new MoreDetailsAdapterModelYedek(motorList.get(i).getModel(), motorList.get(i).getKod(), motorList.get(i).getFiyat(), motorList.get(i).getRate(), "Child", motorList.get(i).getURL(), motorList.get(i).getShare_Link(),motorList.get(i).getUyumlu(),motorList.get(i).getKDV()));
                }

                ItemList.add((new MoreAdapterModelYedek(item.text, DetailsList, "Parent")));
            } else if (item.text.equals("KABLO")) {

                DetailsList = new ArrayList<>();
                ItemList = new ArrayList<>();

                for (int i = 0; i < kabloList.size(); i++) {
                    DetailsList.add(new MoreDetailsAdapterModelYedek(kabloList.get(i).getModel(), kabloList.get(i).getKod(), kabloList.get(i).getFiyat(), kabloList.get(i).getRate(), "Child", kabloList.get(i).getURL(), kabloList.get(i).getShare_Link(),kabloList.get(i).getUyumlu(),kabloList.get(i).getKDV()));
                }

                ItemList.add((new MoreAdapterModelYedek(item.text, DetailsList, "Parent")));
            }


            if (ItemList.size() != 0) {
                MoreAdapter_Yedek moreAdapter_yedek = new MoreAdapter_Yedek(activity, holder.itemView.getContext(), ItemList);
                RecyclerView mRecyclerView;
                mRecyclerView = (RecyclerView) moreViewHolder.itemView.findViewById(R.id.sub_recyclerView);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));

                mRecyclerView.setNestedScrollingEnabled(false);
                mRecyclerView.setHasFixedSize(false);

                mRecyclerView.setAdapter(moreAdapter_yedek);

                moreAdapter_yedek.notifyDataSetChanged();


            }


            lp.setSlm(GridSLM.ID);

            lp.setFirstPosition(position);

            itemView.setLayoutParams(lp);
*/


        } else if (getItemViewType(position) == VIEW_TYPE_NORMAL_CONTENT) {


            final View itemView = holder.itemView;
            final GridSLM.LayoutParams lp = GridSLM.LayoutParams.from(itemView.getLayoutParams());
            // Overrides xml attrs, could use different layouts too.
            final KampanyalarAdapter.LineItem item = mItems.get(position);
            if (mItems.get(position - 1).text.equals("KAMPANYA ÜRÜNLERİ")) {
                List<Kampanyalar> mContentItems = new ArrayList<>();
                if (!kampanyalars.isEmpty()) {

                 /*   if (kampanyalars.size() >= 4) {*/
                  /*      for (int i = 0; i < 4; i++) {
                            if (kampanyalars.get(i).getModel().length() > 25) {
                                mContentItems.add(new Kampanyalar(kampanyalars.get(i).getModel().substring(0, 24) + "...", kampanyalars.get(i).getKod(), kampanyalars.get(i).getFiyat(), kampanyalars.get(i).getURL(), kampanyalars.get(i).getDescription(), kampanyalars.get(i).getRate(), kampanyalars.get(i).getShare_Link()));

                            } else {
                                mContentItems.add(new Kampanyalar(kampanyalars.get(i).getModel(), kampanyalars.get(i).getKod(), kampanyalars.get(i).getFiyat(), kampanyalars.get(i).getURL(), kampanyalars.get(i).getDescription(), kampanyalars.get(i).getRate(), kampanyalars.get(i).getShare_Link()));

                            }
                        }*/
                   /* } else {*/
                    for (int i = 0; i < kampanyalars.size(); i++) {


                        mContentItems.add(new Kampanyalar(kampanyalars.get(i).getModel(), kampanyalars.get(i).getKod(), kampanyalars.get(i).getFiyat(), kampanyalars.get(i).getURL(), kampanyalars.get(i).getDescription(), kampanyalars.get(i).getRate(), kampanyalars.get(i).getShare_Link()));


                    }
                    // }

                    Log.e("data size mContentItems", mContentItems.size() + "");

                    RecyclerView mRecyclerView = (RecyclerView) holder.itemView.findViewById(R.id.normal_recyclerView);
                    RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, 1);
                    mRecyclerView.setLayoutManager(layoutManager);

                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setNestedScrollingEnabled(false);

                    NormalItemAdapter_Kampanyalar mAdapter;
                    mAdapter = new NormalItemAdapter_Kampanyalar(mContentItems, activity);


                    mRecyclerView.setAdapter(mAdapter);


                    mAdapter.notifyDataSetChanged();
                } else {

                    mContentItems.add(new Kampanyalar("yok", "", "", "", "", "", ""));
                    RecyclerView mRecyclerView = (RecyclerView) holder.itemView.findViewById(R.id.normal_recyclerView);
                    RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, 1);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setNestedScrollingEnabled(false);

                    NormalItemAdapter_Kampanyalar mAdapter;
                    mAdapter = new NormalItemAdapter_Kampanyalar(mContentItems, activity);


                    mRecyclerView.setAdapter(mAdapter);


                    mAdapter.notifyDataSetChanged();
                }
            }


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
           /* if (mItems.get(position - 1).text.equals("ROLAND")) {
                List<Roland> mContentItems = new ArrayList<>();

                if (!rolandList.isEmpty()) {
                    if (rolandList.size() >= 4) {
                        for (int i = 0; i < 4; i++) {
                            if (rolandList.get(i).getModel().length() > 25) {

                                mContentItems.add(new Roland(rolandList.get(i).getModel().substring(0, 24) + "...", rolandList.get(i).getKod(), rolandList.get(i).getFiyat(), rolandList.get(i).getURL(), rolandList.get(i).getDescription(), rolandList.get(i).getRate()));
                            } else {
                                mContentItems.add(new Roland(rolandList.get(i).getModel(), rolandList.get(i).getKod(), rolandList.get(i).getFiyat(), rolandList.get(i).getURL(), rolandList.get(i).getDescription(), rolandList.get(i).getRate()));

                            }
                        }
                    } else {
                        for (int i = 0; i < rolandList.size(); i++) {
                            if (rolandList.get(i).getModel().length() > 25) {

                                mContentItems.add(new Roland(rolandList.get(i).getModel().substring(0, 24) + "...", rolandList.get(i).getKod(), rolandList.get(i).getFiyat(), rolandList.get(i).getURL(), rolandList.get(i).getDescription(), rolandList.get(i).getRate()));
                            } else {
                                mContentItems.add(new Roland(rolandList.get(i).getModel(), rolandList.get(i).getKod(), rolandList.get(i).getFiyat(), rolandList.get(i).getURL(), rolandList.get(i).getDescription(), rolandList.get(i).getRate()));

                            }
                        }
                    }
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(activity, 2);
                    RecyclerView mRecyclerView = (RecyclerView) holder.itemView.findViewById(R.id.normal_recyclerView);
                    mRecyclerView.setLayoutManager(layoutManager);

                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setNestedScrollingEnabled(false);

                    NormalItemAdapter_Makinalar mAdapter;
                    mAdapter = new NormalItemAdapter_Makinalar(null, mContentItems, null, activity);


                    mRecyclerView.setAdapter(mAdapter);


                    mAdapter.notifyDataSetChanged();
                }
            }*/
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
            final KampanyalarAdapter.LineItem item = mItems.get(position);
            YedekViewTitleHolder yedekViewTitleHolder = (YedekViewTitleHolder) holder;
            yedekViewTitleHolder.bindItem(item.text, position - 1, "no", "");
         /*   if (mItems.get(position).text.equals("ROLAND")) {
                if (rolandList.size() <= 4) {
                    makinalarViewTitleHolder.bindItem(item.text, position - 1, "no");
                } else {
                    makinalarViewTitleHolder.bindItem(item.text, position - 1, "yes");
                }

            }*/
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
            KampanyalarAdapter.LineItem item = mItems.get(i);
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