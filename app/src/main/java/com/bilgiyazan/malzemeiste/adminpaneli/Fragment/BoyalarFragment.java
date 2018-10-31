package com.bilgiyazan.malzemeiste.adminpaneli.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bilgiyazan.malzemeiste.adminpaneli.Adapter.BoyalarAdapter;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Boyalar;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Eco_Solvent;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Eco_Solvent_Value;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Emerald;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.MLD;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Mimaki_B;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Pro_ink;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Temizleme;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Temizleme_Value;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Triangle;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Uv_Boyalar;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Uv_Boyalar_Value;
import com.bilgiyazan.malzemeiste.adminpaneli.R;
import com.bilgiyazan.malzemeiste.adminpaneli.Utils.ConnectionDetector;
import com.bilgiyazan.malzemeiste.adminpaneli.Utils.HttpHandler;
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.tonicartos.superslim.LayoutManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;


public class BoyalarFragment extends Fragment {


    private static final String KEY_HEADER_POSITIONING = "key_header_mode";
    private static final String KEY_MARGINS_FIXED = "key_margins_fixed";
    List<Eco_Solvent> eco_solventList;
    List<Uv_Boyalar> uv_boyalarList;
    List<Temizleme> temizlemeList;
    Mimaki_B mimaki_b;
    Boyalar boyalar;
    List<Uv_Boyalar_Value> uv_boyalar_valueList;
    List<Temizleme_Value> temizleme_valueList;
    List<Emerald> emeraldList;
    List<MLD> mldList;
    List<Pro_ink> pro_inkList;
    List<Triangle> triangleList;
    CircularProgressBar circularProgressBar;
    Boolean isInternetPresent;
    ConnectionDetector connectionDetector;
    List<Eco_Solvent_Value> ecoSolventValueList;
    SharedPreferences prefs;
    private DatabaseReference mDatabase;
    private ViewHolder mViews;
    private BoyalarAdapter mAdapter;
    private int mHeaderDisplay;
    private boolean mAreMarginsFixed;
    private Random mRng = new Random();
    private Toast mToast = null;
    private SwipeRefreshLayout swipeBoyalar;

    public static BoyalarFragment newInstance() {
        return new BoyalarFragment();
    }

    public static final int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.boyalar_fragment, container, false);
        swipeBoyalar = (SwipeRefreshLayout) view.findViewById(R.id.swipeBoyalar);
        prefs = getActivity().getSharedPreferences("malzemeiste", Context.MODE_PRIVATE);

        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        connectionDetector = new ConnectionDetector(getActivity());
        isInternetPresent = connectionDetector.isConnectingToInternet();
      /*
        if (!isInternetPresent) {
            SuperActivityToast.create(getActivity(), new Style(), Style.TYPE_BUTTON)
                    .setButtonText("Yeniden dene")
                    .setOnButtonClickListener("", null, new SuperActivityToast.OnButtonClickListener() {
                        @Override
                        public void onClick(View view, Parcelable token) {

                            RefreshData(mViews);


                        }
                    })
                    .setButtonTextColor(getColor(getActivity(),R.color.colorPrimary))
                    .setText("Şimdi bağlanamıyor")
                    .setDuration(Style.DURATION_VERY_LONG)
                    .setFrame(Style.FRAME_LOLLIPOP)
                    .setColor(getColor(getActivity(),R.color.colorText))
                    .setAnimations(Style.ANIMATIONS_POP).show();
        }*/
        if (savedInstanceState != null) {
            mHeaderDisplay = savedInstanceState
                    .getInt(KEY_HEADER_POSITIONING,
                            getResources().getInteger(R.integer.default_header_display));
            mAreMarginsFixed = savedInstanceState
                    .getBoolean(KEY_MARGINS_FIXED,
                            getResources().getBoolean(R.bool.default_margins_fixed));
        } else {
            mHeaderDisplay = getResources().getInteger(R.integer.default_header_display);
            mAreMarginsFixed = getResources().getBoolean(R.bool.default_margins_fixed);
        }

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Boyalar");
        mDatabase.keepSynced(true);

        mViews = new ViewHolder(view);


        swipeBoyalar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {

                        RefreshData(mViews);

                    }
                }, 1000);

            }
        });
        swipeBoyalar.setColorSchemeResources(R.color.colorPrimary);


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot postSnapshot) {


                new GetEuroTate().execute();
                new GetDollarRate().execute();
                eco_solventList = new ArrayList<>();
                uv_boyalarList = new ArrayList<>();
                temizlemeList = new ArrayList<>();
                emeraldList = new ArrayList<>();
                mldList = new ArrayList<>();
                pro_inkList = new ArrayList<>();
                triangleList = new ArrayList<>();
                ecoSolventValueList = new ArrayList<>();
                uv_boyalar_valueList = new ArrayList<>();
                temizleme_valueList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : postSnapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        if (dataSnapshot.getKey().equals("Emerald")) {

                            emeraldList.add(dataSnapshot1.getValue(Emerald.class));
                            //    Log.e("data Emerald", dataSnapshot1.getValue() + "");
                        }


                        if (dataSnapshot.getKey().equals("MLD")) {
                            mldList.add(dataSnapshot1.getValue(MLD.class));
                            //   Log.e("data MLD", dataSnapshot1.getValue() + "");


                        }
                        if (dataSnapshot.getKey().equals("Pro_ink")) {
                            pro_inkList.add(dataSnapshot1.getValue(Pro_ink.class));

                            //   Log.e("data Pro_ink", dataSnapshot1.getValue() + "");

                        }
                        if (dataSnapshot.getKey().equals("Triangle")) {
                            triangleList.add(dataSnapshot1.getValue(Triangle.class));

                            //  Log.e("data Triangle", dataSnapshot1.getValue() + "");

                        }

                        if (dataSnapshot1.getKey().equals("Eco_Solvent")) {
                            for (DataSnapshot dataSnapshot0 : dataSnapshot1.getChildren()) {

                                ecoSolventValueList = new ArrayList<>();
                                Eco_Solvent eco_solvent = new Eco_Solvent();

                                for (DataSnapshot dataSnapshot2 : dataSnapshot0.getChildren()) {

                                    if (dataSnapshot2.getKey().equals("Eco_Solvent_Value")) {


                                        for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {

                                            ecoSolventValueList.add(

                                                    new Eco_Solvent_Value(dataSnapshot3.getValue(Eco_Solvent_Value.class).getModel(), dataSnapshot3.getValue(Eco_Solvent_Value.class).getKod(),
                                                            dataSnapshot3.getValue(Eco_Solvent_Value.class).getRenk(), dataSnapshot3.getValue(Eco_Solvent_Value.class).getMiktari(),
                                                            dataSnapshot3.getValue(Eco_Solvent_Value.class).getAmbalaj_sekli(), dataSnapshot3.getValue(Eco_Solvent_Value.class).getFiyat(),
                                                            dataSnapshot3.getValue(Eco_Solvent_Value.class).getURL(), dataSnapshot3.getValue(Eco_Solvent_Value.class).getDescription(),
                                                            dataSnapshot3.getValue(Eco_Solvent_Value.class).getRate(), dataSnapshot3.getValue(Eco_Solvent_Value.class).getShare_Link()));

                                            Log.e("Ecosolvent Value", dataSnapshot3.getValue(Eco_Solvent_Value.class).getModel() + "");
                                        }
                                        eco_solvent.setEco_Solvent_value(ecoSolventValueList);


                                    } else {
                                        Log.e("Ecosolvent Name", dataSnapshot2.getValue(String.class) + "");

                                        eco_solvent.setEco_Solvent_Name(dataSnapshot2.getValue(String.class));

                                    }

                                    // eco_solvent.setEco_Solvent_value(new ArrayList<com.bilgiyazan.malzemeiste.adminpaneli.Model.Eco_Solvent>()<Eco_Solvent_Value>(ecoSolventValueList.toArray(new Eco_Solvent_Value[ecoSolventValueList.size()])));
                                }
                                eco_solventList.add(eco_solvent);

                                //
                                //


                            }

                            Log.e("Eco_Solvent size", eco_solventList.size() + "");
                        }
                        if (dataSnapshot1.getKey().equals("Temizleme")) {
                            for (DataSnapshot dataSnapshot0 : dataSnapshot1.getChildren()) {

                                temizleme_valueList = new ArrayList<>();
                                Temizleme temizleme = new Temizleme();

                                for (DataSnapshot dataSnapshot2 : dataSnapshot0.getChildren()) {

                                    if (dataSnapshot2.getKey().equals("Temizleme_Value")) {


                                        for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {

                                            temizleme_valueList.add(

                                                    new Temizleme_Value(dataSnapshot3.getValue(Temizleme_Value.class).getModel(), dataSnapshot3.getValue(Temizleme_Value.class).getKod(),
                                                            dataSnapshot3.getValue(Temizleme_Value.class).getRenk(), dataSnapshot3.getValue(Temizleme_Value.class).getMiktari(),
                                                            dataSnapshot3.getValue(Temizleme_Value.class).getAmbalaj_sekli(), dataSnapshot3.getValue(Temizleme_Value.class).getFiyat(),
                                                            dataSnapshot3.getValue(Temizleme_Value.class).getURL(), dataSnapshot3.getValue(Temizleme_Value.class).getDescription(),
                                                            dataSnapshot3.getValue(Temizleme_Value.class).getRate(), dataSnapshot3.getValue(Temizleme_Value.class).getShare_Link()));

                                            Log.e("Temizleme Value", dataSnapshot3.getValue(Temizleme_Value.class).getModel() + "");
                                        }
                                        temizleme.setTemizleme_Value(temizleme_valueList);


                                    } else {
                                        Log.e("Temizleme Name", dataSnapshot2.getValue(String.class) + "");

                                        temizleme.setTemizleme_Name(dataSnapshot2.getValue(String.class));

                                    }

                                    // eco_solvent.setEco_Solvent_value(new ArrayList<com.bilgiyazan.malzemeiste.adminpaneli.Model.Eco_Solvent>()<Eco_Solvent_Value>(ecoSolventValueList.toArray(new Eco_Solvent_Value[ecoSolventValueList.size()])));
                                }
                                temizlemeList.add(temizleme);

                                //
                                //


                            }


                            Log.e("Temizleme size", temizlemeList.size() + "");

                        }
                        if (dataSnapshot1.getKey().equals("Uv_Boyalar")) {
                            for (DataSnapshot dataSnapshot0 : dataSnapshot1.getChildren()) {

                                uv_boyalar_valueList = new ArrayList<>();
                                Uv_Boyalar uv_boyalar = new Uv_Boyalar();

                                for (DataSnapshot dataSnapshot2 : dataSnapshot0.getChildren()) {

                                    if (dataSnapshot2.getKey().equals("Uv_Boyalar_Value")) {


                                        for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {

                                            uv_boyalar_valueList.add(

                                                    new Uv_Boyalar_Value(dataSnapshot3.getValue(Uv_Boyalar_Value.class).getModel(), dataSnapshot3.getValue(Uv_Boyalar_Value.class).getKod(),
                                                            dataSnapshot3.getValue(Uv_Boyalar_Value.class).getRenk(), dataSnapshot3.getValue(Uv_Boyalar_Value.class).getMiktari(),
                                                            dataSnapshot3.getValue(Uv_Boyalar_Value.class).getAmbalaj_sekli(), dataSnapshot3.getValue(Uv_Boyalar_Value.class).getFiyat(),
                                                            dataSnapshot3.getValue(Uv_Boyalar_Value.class).getURL(), dataSnapshot3.getValue(Uv_Boyalar_Value.class).getDescription(),
                                                            dataSnapshot3.getValue(Uv_Boyalar_Value.class).getRate(), dataSnapshot3.getValue(Uv_Boyalar_Value.class).getShare_Link()));

                                            Log.e("Uv_Boyalar Value", dataSnapshot3.getValue(Eco_Solvent_Value.class).getModel() + "");
                                        }
                                        uv_boyalar.setUv_boyalar_Value(uv_boyalar_valueList);


                                    } else {
                                        Log.e("Uv_Boyalar Name", dataSnapshot2.getValue(String.class) + "");

                                        uv_boyalar.setUv_boyalar_Name(dataSnapshot2.getValue(String.class));

                                    }

                                    // eco_solvent.setEco_Solvent_value(new ArrayList<com.bilgiyazan.malzemeiste.adminpaneli.Model.Eco_Solvent>()<Eco_Solvent_Value>(ecoSolventValueList.toArray(new Eco_Solvent_Value[ecoSolventValueList.size()])));
                                }
                                uv_boyalarList.add(uv_boyalar);

                                //
                                //


                            }


                            Log.e("Uv_Boyalar size", uv_boyalarList.size() + "");
                        }
                    }

                }
                //    Log.e("solvent_value size", eco_solventList.get(0).getEco_Solvent_value().size() + "");
                //    Log.e("boyalar_value size", uv_boyalarList.get(0).getUv_boyalar_Value().size() + "");
                //   Log.e("temizleme_value size", temizlemeList.get(0).getTemizleme_Value().size() + "");

                mimaki_b = new Mimaki_B();
                mimaki_b.setEco_solventList(eco_solventList);
                mimaki_b.setUv_boyalarList(uv_boyalarList);
                mimaki_b.setTemizlemeList(temizlemeList);
                boyalar = new Boyalar();
                boyalar.setEmeraldList(emeraldList);
                boyalar.setMldList(mldList);
                boyalar.setProInkList(pro_inkList);
                boyalar.setTriangleList(triangleList);
                boyalar.setMimaki_b(mimaki_b);
                mViews = new ViewHolder(view);
                mViews.initViews(new LayoutManager(getActivity()));
                mAdapter = new BoyalarAdapter(getActivity(), mHeaderDisplay, getActivity(), boyalar);
                mAdapter.setMarginsFixed(mAreMarginsFixed);
                mAdapter.setHeaderDisplay(mHeaderDisplay);
                mViews.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });


    }

    public void RefreshData(final ViewHolder view) {
        connectionDetector = new ConnectionDetector(getActivity());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        if (isInternetPresent) {
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot postSnapshot) {


                    eco_solventList = new ArrayList<>();
                    uv_boyalarList = new ArrayList<>();
                    temizlemeList = new ArrayList<>();
                    emeraldList = new ArrayList<>();
                    mldList = new ArrayList<>();
                    pro_inkList = new ArrayList<>();
                    triangleList = new ArrayList<>();
                    ecoSolventValueList = new ArrayList<>();
                    uv_boyalar_valueList = new ArrayList<>();
                    temizleme_valueList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : postSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            if (dataSnapshot.getKey().equals("Emerald")) {

                                emeraldList.add(dataSnapshot1.getValue(Emerald.class));
                                //    Log.e("data Emerald", dataSnapshot1.getValue() + "");
                            }


                            if (dataSnapshot.getKey().equals("MLD")) {
                                mldList.add(dataSnapshot1.getValue(MLD.class));
                                //   Log.e("data MLD", dataSnapshot1.getValue() + "");


                            }
                            if (dataSnapshot.getKey().equals("Pro_ink")) {
                                pro_inkList.add(dataSnapshot1.getValue(Pro_ink.class));

                                //   Log.e("data Pro_ink", dataSnapshot1.getValue() + "");

                            }
                            if (dataSnapshot.getKey().equals("Triangle")) {
                                triangleList.add(dataSnapshot1.getValue(Triangle.class));

                                //  Log.e("data Triangle", dataSnapshot1.getValue() + "");

                            }

                            if (dataSnapshot1.getKey().equals("Eco_Solvent")) {
                                for (DataSnapshot dataSnapshot0 : dataSnapshot1.getChildren()) {

                                    ecoSolventValueList = new ArrayList<>();
                                    Eco_Solvent eco_solvent = new Eco_Solvent();

                                    for (DataSnapshot dataSnapshot2 : dataSnapshot0.getChildren()) {

                                        if (dataSnapshot2.getKey().equals("Eco_Solvent_Value")) {


                                            for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {

                                                ecoSolventValueList.add(

                                                        new Eco_Solvent_Value(dataSnapshot3.getValue(Eco_Solvent_Value.class).getModel(), dataSnapshot3.getValue(Eco_Solvent_Value.class).getKod(),
                                                                dataSnapshot3.getValue(Eco_Solvent_Value.class).getRenk(), dataSnapshot3.getValue(Eco_Solvent_Value.class).getMiktari(),
                                                                dataSnapshot3.getValue(Eco_Solvent_Value.class).getAmbalaj_sekli(), dataSnapshot3.getValue(Eco_Solvent_Value.class).getFiyat(),
                                                                dataSnapshot3.getValue(Eco_Solvent_Value.class).getURL(), dataSnapshot3.getValue(Eco_Solvent_Value.class).getDescription(),
                                                                dataSnapshot3.getValue(Eco_Solvent_Value.class).getRate(), dataSnapshot3.getValue(Eco_Solvent_Value.class).getShare_Link()));

                                                Log.e("Ecosolvent Value", dataSnapshot3.getValue(Eco_Solvent_Value.class).getModel() + "");
                                            }
                                            eco_solvent.setEco_Solvent_value(ecoSolventValueList);


                                        } else {
                                            Log.e("Ecosolvent Name", dataSnapshot2.getValue(String.class) + "");

                                            eco_solvent.setEco_Solvent_Name(dataSnapshot2.getValue(String.class));

                                        }

                                        // eco_solvent.setEco_Solvent_value(new ArrayList<com.bilgiyazan.malzemeiste.adminpaneli.Model.Eco_Solvent>()<Eco_Solvent_Value>(ecoSolventValueList.toArray(new Eco_Solvent_Value[ecoSolventValueList.size()])));
                                    }
                                    eco_solventList.add(eco_solvent);

                                    //
                                    //


                                }

                                Log.e("Eco_Solvent size", eco_solventList.size() + "");
                            }
                            if (dataSnapshot1.getKey().equals("Temizleme")) {
                                for (DataSnapshot dataSnapshot0 : dataSnapshot1.getChildren()) {

                                    temizleme_valueList = new ArrayList<>();
                                    Temizleme temizleme = new Temizleme();

                                    for (DataSnapshot dataSnapshot2 : dataSnapshot0.getChildren()) {

                                        if (dataSnapshot2.getKey().equals("Temizleme_Value")) {


                                            for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {

                                                temizleme_valueList.add(

                                                        new Temizleme_Value(dataSnapshot3.getValue(Temizleme_Value.class).getModel(), dataSnapshot3.getValue(Temizleme_Value.class).getKod(),
                                                                dataSnapshot3.getValue(Temizleme_Value.class).getRenk(), dataSnapshot3.getValue(Temizleme_Value.class).getMiktari(),
                                                                dataSnapshot3.getValue(Temizleme_Value.class).getAmbalaj_sekli(), dataSnapshot3.getValue(Temizleme_Value.class).getFiyat(),
                                                                dataSnapshot3.getValue(Temizleme_Value.class).getURL(), dataSnapshot3.getValue(Temizleme_Value.class).getDescription(),
                                                                dataSnapshot3.getValue(Temizleme_Value.class).getRate(), dataSnapshot3.getValue(Temizleme_Value.class).getShare_Link()));

                                                Log.e("Temizleme Value", dataSnapshot3.getValue(Temizleme_Value.class).getModel() + "");
                                            }
                                            temizleme.setTemizleme_Value(temizleme_valueList);


                                        } else {
                                            Log.e("Temizleme Name", dataSnapshot2.getValue(String.class) + "");

                                            temizleme.setTemizleme_Name(dataSnapshot2.getValue(String.class));

                                        }

                                        // eco_solvent.setEco_Solvent_value(new ArrayList<com.bilgiyazan.malzemeiste.adminpaneli.Model.Eco_Solvent>()<Eco_Solvent_Value>(ecoSolventValueList.toArray(new Eco_Solvent_Value[ecoSolventValueList.size()])));
                                    }
                                    temizlemeList.add(temizleme);

                                    //
                                    //


                                }


                                Log.e("Temizleme size", temizlemeList.size() + "");

                            }
                            if (dataSnapshot1.getKey().equals("Uv_Boyalar")) {
                                for (DataSnapshot dataSnapshot0 : dataSnapshot1.getChildren()) {

                                    uv_boyalar_valueList = new ArrayList<>();
                                    Uv_Boyalar uv_boyalar = new Uv_Boyalar();

                                    for (DataSnapshot dataSnapshot2 : dataSnapshot0.getChildren()) {

                                        if (dataSnapshot2.getKey().equals("Uv_Boyalar_Value")) {


                                            for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {

                                                uv_boyalar_valueList.add(

                                                        new Uv_Boyalar_Value(dataSnapshot3.getValue(Uv_Boyalar_Value.class).getModel(), dataSnapshot3.getValue(Uv_Boyalar_Value.class).getKod(),
                                                                dataSnapshot3.getValue(Uv_Boyalar_Value.class).getRenk(), dataSnapshot3.getValue(Uv_Boyalar_Value.class).getMiktari(),
                                                                dataSnapshot3.getValue(Uv_Boyalar_Value.class).getAmbalaj_sekli(), dataSnapshot3.getValue(Uv_Boyalar_Value.class).getFiyat(),
                                                                dataSnapshot3.getValue(Uv_Boyalar_Value.class).getURL(), dataSnapshot3.getValue(Uv_Boyalar_Value.class).getDescription(),
                                                                dataSnapshot3.getValue(Uv_Boyalar_Value.class).getRate(), dataSnapshot3.getValue(Uv_Boyalar_Value.class).getShare_Link()));

                                                Log.e("Uv_Boyalar Value", dataSnapshot3.getValue(Eco_Solvent_Value.class).getModel() + "");
                                            }
                                            uv_boyalar.setUv_boyalar_Value(uv_boyalar_valueList);


                                        } else {
                                            Log.e("Uv_Boyalar Name", dataSnapshot2.getValue(String.class) + "");

                                            uv_boyalar.setUv_boyalar_Name(dataSnapshot2.getValue(String.class));

                                        }

                                        // eco_solvent.setEco_Solvent_value(new ArrayList<com.bilgiyazan.malzemeiste.adminpaneli.Model.Eco_Solvent>()<Eco_Solvent_Value>(ecoSolventValueList.toArray(new Eco_Solvent_Value[ecoSolventValueList.size()])));
                                    }
                                    uv_boyalarList.add(uv_boyalar);

                                    //
                                    //


                                }


                                Log.e("Uv_Boyalar size", uv_boyalarList.size() + "");
                            }
                        }

                    }
                    //    Log.e("solvent_value size", eco_solventList.get(0).getEco_Solvent_value().size() + "");
                    //    Log.e("boyalar_value size", uv_boyalarList.get(0).getUv_boyalar_Value().size() + "");
                    //   Log.e("temizleme_value size", temizlemeList.get(0).getTemizleme_Value().size() + "");

                    mimaki_b = new Mimaki_B();
                    mimaki_b.setEco_solventList(eco_solventList);
                    mimaki_b.setUv_boyalarList(uv_boyalarList);
                    mimaki_b.setTemizlemeList(temizlemeList);
                    boyalar = new Boyalar();
                    boyalar.setEmeraldList(emeraldList);
                    boyalar.setMldList(mldList);
                    boyalar.setProInkList(pro_inkList);
                    boyalar.setTriangleList(triangleList);
                    boyalar.setMimaki_b(mimaki_b);
                    view.initViews(new LayoutManager(getActivity()));
                    mAdapter = new BoyalarAdapter(getActivity(), mHeaderDisplay, getActivity(), boyalar);
                    mAdapter.setMarginsFixed(mAreMarginsFixed);
                    mAdapter.setHeaderDisplay(mHeaderDisplay);
                    mViews.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    swipeBoyalar.setRefreshing(false);


                }

                @Override
                public void onCancelled(DatabaseError firebaseError) {

                }
            });

        } else {
            SuperActivityToast.create(getActivity(), new Style(), Style.TYPE_BUTTON)
                    .setButtonText("Yeniden dene")
                    .setOnButtonClickListener("", null, new SuperActivityToast.OnButtonClickListener() {
                        @Override
                        public void onClick(View view, Parcelable token) {

                            RefreshData(mViews);


                        }
                    })
                    .setButtonTextColor(getColor(getActivity(), R.color.colorPrimary))
                    .setText("Şimdi bağlanamıyor")
                    .setDuration(Style.DURATION_VERY_LONG)
                    .setFrame(Style.FRAME_LOLLIPOP)
                    .setColor(getColor(getActivity(), R.color.colorText))
                    .setAnimations(Style.ANIMATIONS_POP).show();
            swipeBoyalar.setRefreshing(false);

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(KEY_HEADER_POSITIONING, mHeaderDisplay);
        outState.putBoolean(KEY_MARGINS_FIXED, mAreMarginsFixed);
    }

    public void scrollToRandomPosition() {
        int position = mRng.nextInt(mAdapter.getItemCount());
        String s = "Scroll to position " + position
                + (mAdapter.isItemHeader(position) ? ", header " : ", item ")
                + mAdapter.itemToString(position) + ".";
        if (mToast != null) {
            mToast.setText(s);
        } else {
            mToast = Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT);
        }
        mToast.show();
        mViews.scrollToPosition(position);
    }

    public void setHeadersOverlaid(boolean areHeadersOverlaid) {
        mHeaderDisplay = areHeadersOverlaid ? mHeaderDisplay
                | LayoutManager.LayoutParams.HEADER_OVERLAY
                : mHeaderDisplay & ~LayoutManager.LayoutParams.HEADER_OVERLAY;
        mAdapter.setHeaderDisplay(mHeaderDisplay);
    }

    public void setHeadersSticky(boolean areHeadersSticky) {
        mHeaderDisplay = areHeadersSticky ? mHeaderDisplay
                | LayoutManager.LayoutParams.HEADER_STICKY
                : mHeaderDisplay & ~LayoutManager.LayoutParams.HEADER_STICKY;
        mAdapter.setHeaderDisplay(mHeaderDisplay);
    }

    public void setMarginsFixed(boolean areMarginsFixed) {
        mAreMarginsFixed = areMarginsFixed;
        mAdapter.setMarginsFixed(areMarginsFixed);
    }

    public void smoothScrollToRandomPosition() {
        int position = mRng.nextInt(mAdapter.getItemCount());
        String s = "Smooth scroll to position " + position
                + (mAdapter.isItemHeader(position) ? ", header " : ", item ")
                + mAdapter.itemToString(position) + ".";
        if (mToast != null) {
            mToast.setText(s);
        } else {
            mToast = Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT);
        }
        mToast.show();
        mViews.smoothScrollToPosition(position);
    }

    private static class ViewHolder {

        private final RecyclerView mRecyclerView;


        public ViewHolder(View view) {
            mRecyclerView = (RecyclerView) view.findViewById(R.id.boyalar_recyclerView);

        }

        public void initViews(LayoutManager lm) {
            mRecyclerView.setLayoutManager(lm);
            mRecyclerView.setHasFixedSize(false);
            mRecyclerView.setNestedScrollingEnabled(true);
        }

        public void scrollToPosition(int position) {
            mRecyclerView.scrollToPosition(position);
        }

        public void setAdapter(RecyclerView.Adapter<?> adapter) {
            mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }

        public void smoothScrollToPosition(int position) {
            mRecyclerView.smoothScrollToPosition(position);
        }
    }

    private class GetEuroTate extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response

            String jsonStr = sh.makeServiceCall("http://free.currencyconverterapi.com/api/v3/convert?q=EUR_TRY");

            Log.e("", "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);


                    JSONObject Resultobj = jsonObj.getJSONObject("results");
                    JSONObject Rateobj = Resultobj.getJSONObject("EUR_TRY");
                    String rateString = String.valueOf(Rateobj.get("val"));
                    Log.e("resuult euro", rateString + "");
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("Euro_Rate", rateString);
                    editor.apply();

                } catch (final JSONException e) {
                    Log.e("", "Json parsing error: " + e.getMessage());


                }
            } else {
                Log.e("", "Couldn't get json from server.");


            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }

    }

    private class GetDollarRate extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall("http://free.currencyconverterapi.com/api/v3/convert?q=USD_TRY");

            Log.e("", "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject Resultobj = jsonObj.getJSONObject("results");
                    JSONObject Rateobj = Resultobj.getJSONObject("USD_TRY");
                    String rateString = String.valueOf(Rateobj.get("val"));
                    Log.e("resuult dollar", rateString + "");
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("Dollar_Rate", rateString);
                    editor.apply();

                } catch (final JSONException e) {
                    Log.e("", "Json parsing error: " + e.getMessage());


                }
            } else {
                Log.e("", "Couldn't get json from server.");


            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }

    }
}