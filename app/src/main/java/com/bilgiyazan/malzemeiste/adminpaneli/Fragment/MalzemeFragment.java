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

import com.bilgiyazan.malzemeiste.adminpaneli.Adapter.MalzemeAdapter;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Eco_Solvent_Value;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Folyo;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Malzeme;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Unifol;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Unifol_Value;
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


public class MalzemeFragment extends Fragment {


    private static final String KEY_HEADER_POSITIONING = "key_header_mode";
    private static final String KEY_MARGINS_FIXED = "key_margins_fixed";

    List<Unifol> unifolList;
    Folyo folyo;
    Malzeme malzeme;
    List<Unifol_Value> unifol_valueList;
    Boolean isInternetPresent;
    ConnectionDetector connectionDetector;
    SharedPreferences prefs;
    private DatabaseReference mDatabase;
    private ViewHolder mViews;
    private MalzemeAdapter mAdapter;
    private int mHeaderDisplay;
    private boolean mAreMarginsFixed;
    private Random mRng = new Random();
    private Toast mToast = null;
    private SwipeRefreshLayout swipeMalzeme;

    public static MalzemeFragment newInstance() {
        return new MalzemeFragment();
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
        View view = inflater.inflate(R.layout.malzeme_fragment, container, false);
        swipeMalzeme = (SwipeRefreshLayout) view.findViewById(R.id.swipeMalzeme);
        prefs = getActivity().getSharedPreferences("malzemeiste", Context.MODE_PRIVATE);

        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        connectionDetector = new ConnectionDetector(getActivity());
        isInternetPresent = connectionDetector.isConnectingToInternet();


/*        if (!isInternetPresent) {
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

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Malzeme");
        mDatabase.keepSynced(true);
        mViews = new ViewHolder(view);


        // Setup refresh listener which triggers new data loading
        swipeMalzeme.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    public void run() {


                        RefreshData(mViews);
                    }
                }, 1000);

            }
        });
        swipeMalzeme.setColorSchemeResources(R.color.colorPrimary);


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot postSnapshot) {

                new GetEuroTate().execute();
                new GetDollarRate().execute();

                unifolList = new ArrayList<>();


                for (DataSnapshot dataSnapshot : postSnapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                        if (dataSnapshot1.getKey().equals("Unifol")) {
                            for (DataSnapshot dataSnapshot0 : dataSnapshot1.getChildren()) {

                                unifol_valueList = new ArrayList<Unifol_Value>();
                                Unifol unifol = new Unifol();

                                for (DataSnapshot dataSnapshot2 : dataSnapshot0.getChildren()) {

                                    if (dataSnapshot2.getKey().equals("Unifol_Value")) {


                                        for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {

                                            unifol_valueList.add(

                                                    new Unifol_Value(dataSnapshot3.getValue(Unifol_Value.class).getModel(), dataSnapshot3.getValue(Unifol_Value.class).getKod(),
                                                            dataSnapshot3.getValue(Unifol_Value.class).getRenk(), dataSnapshot3.getValue(Unifol_Value.class).getMiktari(),
                                                            dataSnapshot3.getValue(Unifol_Value.class).getFiyat(),
                                                            dataSnapshot3.getValue(Unifol_Value.class).getURL(), dataSnapshot3.getValue(Unifol_Value.class).getDescription(),
                                                            dataSnapshot3.getValue(Unifol_Value.class).getRate(), dataSnapshot3.getValue(Unifol_Value.class).getShare_Link()));

                                            Log.e("Unifol_Value", dataSnapshot3.getValue(Eco_Solvent_Value.class).getModel() + "");
                                        }
                                        unifol.setUnifol_Value(unifol_valueList);


                                    } else {
                                        Log.e("Unifol_Name", dataSnapshot2.getValue(String.class) + "");

                                        unifol.setUnifol_Name(dataSnapshot2.getValue(String.class));

                                    }

                                    // eco_solvent.setEco_Solvent_value(new RealmList<Eco_Solvent_Value>(ecoSolventValueList.toArray(new Eco_Solvent_Value[ecoSolventValueList.size()])));
                                }
                                unifolList.add(unifol);

                                //
                                //


                            }
                        }

                    }

                }

                folyo = new Folyo();
                folyo.setUnifolList(unifolList);
                malzeme = new Malzeme();
                malzeme.setFolyo(folyo);
                // mimaki_m.setEndustriyelList(endustriyelList);


                mViews = new ViewHolder(view);
                mViews.initViews(new LayoutManager(getActivity()));
                mAdapter = new MalzemeAdapter(getActivity(), mHeaderDisplay, getActivity(), malzeme);
                mAdapter.setMarginsFixed(mAreMarginsFixed);
                mAdapter.setHeaderDisplay(mHeaderDisplay);
                mViews.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                swipeMalzeme.setRefreshing(false);


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

                    unifolList = new ArrayList<>();


                    for (DataSnapshot dataSnapshot : postSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                            if (dataSnapshot1.getKey().equals("Unifol")) {
                                for (DataSnapshot dataSnapshot0 : dataSnapshot1.getChildren()) {

                                    unifol_valueList = new ArrayList<Unifol_Value>();
                                    Unifol unifol = new Unifol();

                                    for (DataSnapshot dataSnapshot2 : dataSnapshot0.getChildren()) {

                                        if (dataSnapshot2.getKey().equals("Unifol_Value")) {


                                            for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {

                                                unifol_valueList.add(

                                                        new Unifol_Value(dataSnapshot3.getValue(Unifol_Value.class).getModel(), dataSnapshot3.getValue(Unifol_Value.class).getKod(),
                                                                dataSnapshot3.getValue(Unifol_Value.class).getRenk(), dataSnapshot3.getValue(Unifol_Value.class).getMiktari(),
                                                                dataSnapshot3.getValue(Unifol_Value.class).getFiyat(),
                                                                dataSnapshot3.getValue(Unifol_Value.class).getURL(), dataSnapshot3.getValue(Unifol_Value.class).getDescription(),
                                                                dataSnapshot3.getValue(Unifol_Value.class).getRate(), dataSnapshot3.getValue(Unifol_Value.class).getShare_Link()));

                                                Log.e("Unifol_Value Miktari", dataSnapshot3.getValue(Unifol_Value.class).getMiktari() + "");
                                            }
                                            unifol.setUnifol_Value(unifol_valueList);


                                        } else {
                                            Log.e("Unifol_Name", dataSnapshot2.getValue(String.class) + "");

                                            unifol.setUnifol_Name(dataSnapshot2.getValue(String.class));

                                        }

                                        // eco_solvent.setEco_Solvent_value(new RealmList<Eco_Solvent_Value>(ecoSolventValueList.toArray(new Eco_Solvent_Value[ecoSolventValueList.size()])));
                                    }
                                    unifolList.add(unifol);

                                    //
                                    //


                                }
                            }

                        }

                    }

                    folyo = new Folyo();
                    folyo.setUnifolList(unifolList);
                    malzeme = new Malzeme();
                    malzeme.setFolyo(folyo);
                    // mimaki_m.setEndustriyelList(endustriyelList);


                    view.initViews(new LayoutManager(getActivity()));
                    mAdapter = new MalzemeAdapter(getActivity(), mHeaderDisplay, getActivity(), malzeme);
                    mAdapter.setMarginsFixed(mAreMarginsFixed);
                    mAdapter.setHeaderDisplay(mHeaderDisplay);
                    mViews.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    swipeMalzeme.setRefreshing(false);


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
            swipeMalzeme.setRefreshing(false);

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
            mRecyclerView = (RecyclerView) view.findViewById(R.id.malzeme_recyclerView);

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