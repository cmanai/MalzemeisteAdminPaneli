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

import com.bilgiyazan.malzemeiste.adminpaneli.Adapter.YedekParcaAdapter;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Bicaklar;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Head_Board;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Kablo;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Main_Board;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Motor;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Roland_Y;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.YedekParca;
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


public class YedekParcaFragment extends Fragment {


    private static final String KEY_HEADER_POSITIONING = "key_header_mode";
    private static final String KEY_MARGINS_FIXED = "key_margins_fixed";
    Roland_Y roland_y;
    YedekParca yedekParca;
    Boolean isInternetPresent;
    ConnectionDetector connectionDetector;
    SharedPreferences prefs;
    private List<Bicaklar> bicaklarList;
    private List<Head_Board> head_boardList;
    private List<Kablo> kabloList;
    private List<Main_Board> main_boardList;
    private List<Motor> motorList;
    private DatabaseReference mDatabase;
    private ViewHolder mViews;
    private YedekParcaAdapter mAdapter;
    private int mHeaderDisplay;
    private boolean mAreMarginsFixed;
    private Random mRng = new Random();
    private Toast mToast = null;
    private SwipeRefreshLayout swipeYedek;

    public static YedekParcaFragment newInstance() {
        return new YedekParcaFragment();
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
        View view = inflater.inflate(R.layout.yedek_fragment, container, false);
        swipeYedek = (SwipeRefreshLayout) view.findViewById(R.id.swipeYedek);
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
            new Handler().postDelayed(new Runnable() {
                public void run() {
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
                }
            }, 500);
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

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Yedek_Parca");
        mDatabase.keepSynced(true);
        mViews = new ViewHolder(view);


        // Setup refresh listener which triggers new data loading
        swipeYedek.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    public void run() {


                        RefreshData(mViews);
                    }
                }, 1000);

            }
        });
        swipeYedek.setColorSchemeResources(R.color.colorPrimary);


        mDatabase.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot postSnapshot) {

                                                new GetEuroTate().execute();
                                                new GetDollarRate().execute();
                                                bicaklarList = new ArrayList<>();
                                                head_boardList = new ArrayList<>();
                                                kabloList = new ArrayList<>();
                                                main_boardList = new ArrayList<>();
                                                motorList = new ArrayList<>();

                                                for (DataSnapshot dataSnapshot : postSnapshot.getChildren()) {
                                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                                                        if (dataSnapshot1.getKey().equals("Bicaklar")) {
                                                            for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {


                                                                bicaklarList.add(dataSnapshot2.getValue(Bicaklar.class));
                                                                // Log.e("data roland", dataSnapshot1.getValue() + "");

                                                            }
                                                        }
                                                        if (dataSnapshot1.getKey().equals("Head_Board")) {
                                                            for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {


                                                                head_boardList.add(dataSnapshot2.getValue(Head_Board.class));
                                                                // Log.e("data roland", dataSnapshot1.getValue() + "");
                                                            }

                                                        }
                                                        if (dataSnapshot1.getKey().equals("Kablo")) {
                                                            for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                                                kabloList.add(dataSnapshot2.getValue(Kablo.class));

                                                            }
                                                            // Log.e("data roland", dataSnapshot1.getValue() + "");


                                                        }
                                                        if (dataSnapshot1.getKey().equals("Main_Board")) {

                                                            for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {

                                                                main_boardList.add(dataSnapshot2.getValue(Main_Board.class));
                                                                // Log.e("data roland", dataSnapshot1.getValue() + "");


                                                            }
                                                        }
                                                        if (dataSnapshot1.getKey().equals("Motor")) {
                                                            for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                                                motorList.add(dataSnapshot2.getValue(Motor.class));

                                                            }

                                                        }


                                                    }

                                                }

                                                roland_y = new Roland_Y();

                                                roland_y.setBicaklarList(bicaklarList);
                                                roland_y.setHead_boardList(head_boardList);
                                                roland_y.setKabloList(kabloList);
                                                roland_y.setMain_boardList(main_boardList);
                                                roland_y.setMotorList(motorList);
                                                yedekParca = new YedekParca();
                                                yedekParca.setRoland_y(roland_y);


                                                mViews = new ViewHolder(view);
                                                mViews.initViews(new LayoutManager(getActivity()));
                                                mAdapter = new YedekParcaAdapter(getActivity(), mHeaderDisplay, getActivity(), yedekParca);
                                                mAdapter.setMarginsFixed(mAreMarginsFixed);
                                                mAdapter.setHeaderDisplay(mHeaderDisplay);
                                                mViews.setAdapter(mAdapter);
                                                mAdapter.notifyDataSetChanged();


                                            }

                                            @Override
                                            public void onCancelled(DatabaseError firebaseError) {

                                            }
                                        }

        );


    }

    public void RefreshData(final ViewHolder view) {
        connectionDetector = new ConnectionDetector(getActivity());
        isInternetPresent = connectionDetector.isConnectingToInternet();
        if (isInternetPresent) {
            mDatabase.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot postSnapshot) {


                                                    bicaklarList = new ArrayList<>();
                                                    head_boardList = new ArrayList<>();
                                                    kabloList = new ArrayList<>();
                                                    main_boardList = new ArrayList<>();
                                                    motorList = new ArrayList<>();
                                                    // reklamList = new ArrayList<>();
             /*   endustriyelList = new ArrayList<>();
                okiList = new ArrayList<>();

                a_starjetList = new ArrayList<>();*/

                                                    for (DataSnapshot dataSnapshot : postSnapshot.getChildren()) {
                                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                       /* if (dataSnapshot.getKey().equals("OKI")) {

                            okiList.add(dataSnapshot1.getValue(OKI.class));
                            // Log.e("data oki", dataSnapshot1.getValue() + "");
                        }*/


                                                            if (dataSnapshot1.getKey().equals("Bicaklar")) {
                                                                for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {


                                                                    bicaklarList.add(dataSnapshot2.getValue(Bicaklar.class));
                                                                    // Log.e("data roland", dataSnapshot1.getValue() + "");

                                                                }
                                                            }
                                                            if (dataSnapshot1.getKey().equals("Head_Board")) {
                                                                for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {


                                                                    head_boardList.add(dataSnapshot2.getValue(Head_Board.class));
                                                                    // Log.e("data roland", dataSnapshot1.getValue() + "");
                                                                }

                                                            }
                                                            if (dataSnapshot1.getKey().equals("Kablo")) {
                                                                for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                                                    kabloList.add(dataSnapshot2.getValue(Kablo.class));

                                                                }
                                                                // Log.e("data roland", dataSnapshot1.getValue() + "");


                                                            }
                                                            if (dataSnapshot1.getKey().equals("Main_Board")) {

                                                                for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {

                                                                    main_boardList.add(dataSnapshot2.getValue(Main_Board.class));
                                                                    // Log.e("data roland", dataSnapshot1.getValue() + "");


                                                                }
                                                            }
                                                            if (dataSnapshot1.getKey().equals("Motor")) {
                                                                for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                                                    motorList.add(dataSnapshot2.getValue(Motor.class));

                                                                }

                                                            }

                     /*   if (dataSnapshot.getKey().equals("A_Starjet")) {
                            a_starjetList.add(dataSnapshot1.getValue(A_Starjet.class));

                            // Log.e("data astarjet", dataSnapshot1.getValue() + "");

                        }*/
                  /*      if (dataSnapshot1.getKey().equals("Kesim")) {
                            for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                kesimList.add(dataSnapshot2.getValue(Kesim.class));

                            }

                        }
                        if (dataSnapshot1.getKey().equals("Reklam")) {
                            for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                reklamList.add(dataSnapshot2.getValue(Reklam.class));

                            }


                        }
                        if (dataSnapshot1.getKey().equals("Endustriyel")) {
                            for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                endustriyelList.add(dataSnapshot2.getValue(Endustriyel.class));

                            }


                        }*/
                      /*  for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            if (dataSnapshot.getKey().equals("Mimaki_M")) {
                                endustriyelList.add(dataSnapshot2.getValue(Endustriyel.class));
                                kesimList.add(dataSnapshot2.getValue(Kesim.class));
                                reklamList.add(dataSnapshot2.getValue(Reklam.class));


                            }
                        }*/
                                                        }

                                                    }

                                                    roland_y = new Roland_Y();
                                                    // mimaki_m.setEndustriyelList(endustriyelList);

                                                    roland_y.setBicaklarList(bicaklarList);
                                                    roland_y.setHead_boardList(head_boardList);
                                                    roland_y.setKabloList(kabloList);
                                                    roland_y.setMain_boardList(main_boardList);
                                                    roland_y.setMotorList(motorList);
                                                    // mimaki_m.setReklamList(reklamList);
                                                    yedekParca = new YedekParca();
                                                    // Yedek.setAstarjetList(a_starjetList);
                                                    yedekParca.setRoland_y(roland_y);
                                                    // Yedek.setOkiList(okiList);


                                                    view.initViews(new LayoutManager(getActivity()));
                                                    mAdapter = new YedekParcaAdapter(getActivity(), mHeaderDisplay, getActivity(), yedekParca);
                                                    mAdapter.setMarginsFixed(mAreMarginsFixed);
                                                    mAdapter.setHeaderDisplay(mHeaderDisplay);
                                                    mViews.setAdapter(mAdapter);
                                                    mAdapter.notifyDataSetChanged();
                                                    swipeYedek.setRefreshing(false);


                                                }

                                                @Override
                                                public void onCancelled(DatabaseError firebaseError) {


                                                }
                                            }

            );


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
            swipeYedek.setRefreshing(false);

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
            mRecyclerView = (RecyclerView) view.findViewById(R.id.yedek_recyclerView);

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