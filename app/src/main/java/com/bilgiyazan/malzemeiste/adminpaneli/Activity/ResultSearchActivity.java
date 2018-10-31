package com.bilgiyazan.malzemeiste.adminpaneli.Activity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.bilgiyazan.malzemeiste.adminpaneli.Adapter.NormalItemAdapter_Search;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Bicaklar;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Boyalar;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.CorelDraw;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Eco_Solvent;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Eco_Solvent_Value;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Emerald;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Folyo;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Head_Board;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Kablo;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Kampanyalar;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Kesim;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Lateks;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.MLD;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Main_Board;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Makinalar;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Malzeme;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Mimaki_B;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Mimaki_M;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Motor;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Pro_ink;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Roland;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Roland_Y;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.SearchModel;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Solvent_Uv_Yazicilar;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Solvent_Yazici;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Temizleme;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Temizleme_Value;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Triangle;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Unifol;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Unifol_Value;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Uv_Boyalar;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Uv_Boyalar_Value;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Uv_Yazicilar;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Yazilim;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.YedekParca;
import com.bilgiyazan.malzemeiste.adminpaneli.R;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnBackPressListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

public class ResultSearchActivity extends AppCompatActivity implements NormalItemAdapter_Search.CallbackInterface {
    FloatingSearchView mSearchView;
    DatabaseReference mDatabase;
    DatabaseReference mDatabase1;
    DatabaseReference mDatabase2;
    DatabaseReference mDatabase3;
    DatabaseReference mDatabase4;
    DatabaseReference mDatabase5;
    DatabaseReference mDatabase6;
    DialogPlus SortDialog;

    Mimaki_M mimaki_m;
    Makinalar makinalar;
    String Concat;

    List<Kesim> kesimList;
    List<Solvent_Yazici> solvent_yaziciList;
    List<Solvent_Uv_Yazicilar> solvent_uv_yazicilarList;
    List<Uv_Yazicilar> uv_yazicilarList;
    List<Lateks> lateksList;
    List<Roland> rolandList;
    TextView nothing_result_text;
    ImageView nothing_result;

    List<SearchModel> testlist;
    List<Eco_Solvent> eco_solventList;
    List<Eco_Solvent_Value> ecoSolventValueList;
    List<Uv_Boyalar> uv_boyalarList;
    List<Uv_Boyalar_Value> uv_boyalar_valueList;
    List<Temizleme> temizlemeList;
    List<Temizleme_Value> temizleme_valueList;
    Mimaki_B mimaki_b;
    Boyalar boyalar;
    List<Emerald> emeraldList;
    List<MLD> mldList;
    List<Pro_ink> pro_inkList;
    List<Triangle> triangleList;


    Roland_Y roland_y;
    YedekParca yedekParca;

    List<Bicaklar> bicaklarList;
    List<Head_Board> head_boardList;
    List<Kablo> kabloList;
    List<Main_Board> main_boardList;
    List<Motor> motorList;
    List<Kampanyalar> kampanyalars;


    List<Unifol> unifolList;
    Folyo folyo;
    Malzeme malzeme;
    List<Unifol_Value> unifol_valueList;
    SharedPreferences prefs;

    RecyclerView mRecyclerView;
    Yazilim yazilim;
    String SearchString;
    CircularProgressBar progress_search;
    NormalItemAdapter_Search mAdapter;
    private List<CorelDraw> corelDrawList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = this.getSharedPreferences("malzemeiste_admin_panel", Context.MODE_PRIVATE);

        setContentView(R.layout.result_search_layout);

        mRecyclerView = (RecyclerView) findViewById(R.id.search_recyclerView);
       /* RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ResultSearchActivity.this, 2);*/
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, 1);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setNestedScrollingEnabled(false);
        mSearchView = (FloatingSearchView) findViewById(R.id.floating_search_view);
        progress_search = (CircularProgressBar) findViewById(R.id.progress_search);
        nothing_result_text = (TextView) findViewById(R.id.nothing_result_text);
        nothing_result = (ImageView) findViewById(R.id.nothing_result);
        mSearchView.setSearchText(getIntent().getStringExtra("search"));
        mSearchView.clearFocus();
        SearchString = getIntent().getStringExtra("search").toUpperCase();

        SortDialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.sort_dialog_layout))
                .setGravity(Gravity.BOTTOM)
                .setContentWidth(ViewGroup.LayoutParams.MATCH_PARENT)  // or any custom width ie: 300
                .setContentHeight(ViewGroup.LayoutParams.MATCH_PARENT)

                .setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogPlus dialog) {
                    }
                })
                .setOnBackPressListener(new OnBackPressListener() {
                    @Override
                    public void onBackPressed(DialogPlus dialogPlus) {
                        SortDialog.dismiss();
                    }
                })

                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {

                    }
                })
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {


                        if (view.getId() == R.id.close_sort) {
                            SortDialog.dismiss();
                        }


                    }
                })

                .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)

                .create();


        View view = SortDialog.getHolderView();

        CheckBox sort_by_code = (CheckBox) view.findViewById(R.id.sort_by_code);


        if (prefs.getString("SortByKod", "false").equals("true")) {

            sort_by_code.setChecked(true);


        }

        sort_by_code.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putString("SortByKod", "true");


                    editor.apply();
                } else {
                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putString("SortByKod", "false");


                    editor.apply();

                }
            }
        });
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category");
        mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Makinalar");
        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Boyalar");
        mDatabase3 = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Yedek_Parca");
        mDatabase4 = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Malzeme");
        mDatabase5 = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Yazilim");
        mDatabase6 = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Kampanyalar");


        mSearchView.setOnHomeActionClickListener(
                new FloatingSearchView.OnHomeActionClickListener() {
                    @Override
                    public void onHomeClicked() {
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();

                    }

                });
        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.action_sort) {


                    SortDialog.show();
                }


            }
        });

        progress_search.setVisibility(View.VISIBLE);
        nothing_result_text.setVisibility(View.INVISIBLE);
        nothing_result.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                GetFromFirebase();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //GetFromFirebase();
        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {


            }

            @Override
            public void onSearchAction(String currentQuery) {
/*
                !currentQuery.toUpperCase().equals(SearchString) &&
*/

                if (!currentQuery.equals("")) {
                    SearchString = currentQuery.toUpperCase();
                    progress_search.setVisibility(View.VISIBLE);
                    nothing_result_text.setVisibility(View.INVISIBLE);
                    nothing_result.setVisibility(View.INVISIBLE);
                    mRecyclerView.setVisibility(View.INVISIBLE);

                    testlist = new ArrayList<SearchModel>();
                    mRecyclerView.removeAllViews();

                    GetFromFirebase();
                }

            }
        });


    }


    public void GetFromFirebase() {

        testlist = new ArrayList<SearchModel>();
        testlist.clear();
        mRecyclerView.removeAllViews();
        mAdapter = new NormalItemAdapter_Search(testlist, ResultSearchActivity.this);


        mRecyclerView.setAdapter(mAdapter);


        mAdapter.notifyDataSetChanged();
        progress_search.setVisibility(View.VISIBLE);
        mDatabase1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                      @Override
                                                      public void onDataChange(DataSnapshot postSnapshot) {
                                                          progress_search.setVisibility(View.VISIBLE);
                                                          testlist = new ArrayList<SearchModel>();

                                                          kesimList = new ArrayList<>();
                                                          rolandList = new ArrayList<>();
                                                          solvent_yaziciList = new ArrayList<>();
                                                          solvent_uv_yazicilarList = new ArrayList<>();
                                                          uv_yazicilarList = new ArrayList<>();
                                                          lateksList = new ArrayList<>();
                                                          Log.e("sizzzeee of test0", testlist.size() + "");


                                                          for (DataSnapshot dataSnapshot : postSnapshot.getChildren()) {
                                                              for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                                                                  if (dataSnapshot.getKey().equals("Roland")) {
                                                                      rolandList.add(dataSnapshot1.getValue(Roland.class));
                                                                      // Log.e("data roland", dataSnapshot1.getValue() + "");


                                                                  }
                                                                  if (dataSnapshot1.getKey().equals("Solvent_Yazici")) {
                                                                      for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                                                          solvent_yaziciList.add(dataSnapshot2.getValue(Solvent_Yazici.class));
                                                                          // Log.e("data roland", dataSnapshot1.getValue() + "");
                                                                      }


                                                                  }
                                                                  if (dataSnapshot1.getKey().equals("Solvent_Uv_Yazicilar")) {
                                                                      for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                                                          solvent_uv_yazicilarList.add(dataSnapshot2.getValue(Solvent_Uv_Yazicilar.class));
                                                                          // Log.e("data roland", dataSnapshot1.getValue() + "");
                                                                      }

                                                                  }
                                                                  if (dataSnapshot1.getKey().equals("Uv_Yazicilar")) {
                                                                      for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                                                          uv_yazicilarList.add(dataSnapshot2.getValue(Uv_Yazicilar.class));
                                                                          // Log.e("data roland", dataSnapshot1.getValue() + "");
                                                                      }


                                                                  }
                                                                  if (dataSnapshot1.getKey().equals("Lateks")) {
                                                                      for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                                                          lateksList.add(dataSnapshot2.getValue(Lateks.class));
                                                                          // Log.e("data roland", dataSnapshot1.getValue() + "");
                                                                      }


                                                                  }
                                                                  if (dataSnapshot1.getKey().equals("Kesim")) {
                                                                      for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                                                          kesimList.add(dataSnapshot2.getValue(Kesim.class));

                                                                      }

                                                                  }

                                                              }

                                                          }

                                                          mimaki_m = new Mimaki_M();

                                                          mimaki_m.setKesimList(kesimList);
                                                          mimaki_m.setLateksList(lateksList);
                                                          mimaki_m.setSolvent_uv_yazicilarList(solvent_uv_yazicilarList);
                                                          mimaki_m.setSolvent_yaziciList(solvent_yaziciList);
                                                          mimaki_m.setUv_yazicilarList(uv_yazicilarList);
                                                          makinalar = new Makinalar();
                                                          makinalar.setMimaki_m(mimaki_m);
                                                          makinalar.setRolandList(rolandList);


                                                          for (int i = 0; i < rolandList.size(); i++) {
                                                              if (prefs.getString("SortByKod", "false").equals("true")) {
                                                                  if (rolandList.get(i).getModel().toUpperCase().contains(SearchString) || rolandList.get(i).getKod().equals(SearchString))
                                                                      testlist.add(new SearchModel(rolandList.get(i).getModel(), rolandList.get(i).getKod(), rolandList.get(i).getFiyat(), rolandList.get(i).getURL(), rolandList.get(i).getRate(), "Makinalar", rolandList.get(i).getShare_Link(), "", "", "", "", rolandList.get(i).getDescription(), "", "Roland"));
                                                              } else {
                                                                  if (rolandList.get(i).getModel().toUpperCase().contains(SearchString))
                                                                      testlist.add(new SearchModel(rolandList.get(i).getModel(), rolandList.get(i).getKod(), rolandList.get(i).getFiyat(), rolandList.get(i).getURL(), rolandList.get(i).getRate(), "Makinalar", rolandList.get(i).getShare_Link(), "", "", "", "", rolandList.get(i).getDescription(), "", "Roland"));

                                                              }
                                                          }
                                                          for (int i = 0; i < kesimList.size(); i++) {
                                                              if (prefs.getString("SortByKod", "false").equals("true")) {
                                                                  if (kesimList.get(i).getModel().toUpperCase().contains(SearchString) || kesimList.get(i).getKod().equals(SearchString))
                                                                      testlist.add(new SearchModel(kesimList.get(i).getModel(), kesimList.get(i).getKod(), kesimList.get(i).getFiyat(), kesimList.get(i).getURL(), kesimList.get(i).getRate(), "Makinalar", "", kesimList.get(i).getShare_Link(), "", "", "", kesimList.get(i).getDescription(), "", "Mimaki"));
                                                              } else {
                                                                  if (kesimList.get(i).getModel().toUpperCase().contains(SearchString))
                                                                      testlist.add(new SearchModel(kesimList.get(i).getModel(), kesimList.get(i).getKod(), kesimList.get(i).getFiyat(), kesimList.get(i).getURL(), kesimList.get(i).getRate(), "Makinalar", kesimList.get(i).getShare_Link(), "", "", "", "", kesimList.get(i).getDescription(), "", "Mimaki"));

                                                              }
                                                          }
                                                          for (int i = 0; i < lateksList.size(); i++) {
                                                              if (prefs.getString("SortByKod", "false").equals("true")) {
                                                                  if (lateksList.get(i).getModel().toUpperCase().contains(SearchString) || lateksList.get(i).getKod().equals(SearchString))
                                                                      testlist.add(new SearchModel(lateksList.get(i).getModel(), lateksList.get(i).getKod(), lateksList.get(i).getFiyat(), lateksList.get(i).getURL(), lateksList.get(i).getRate(), "Makinalar", lateksList.get(i).getShare_Link(), "", "", "", "", lateksList.get(i).getDescription(), "", "Mimaki"));
                                                              } else {
                                                                  if (lateksList.get(i).getModel().toUpperCase().contains(SearchString))
                                                                      testlist.add(new SearchModel(lateksList.get(i).getModel(), lateksList.get(i).getKod(), lateksList.get(i).getFiyat(), lateksList.get(i).getURL(), lateksList.get(i).getRate(), "Makinalar", lateksList.get(i).getShare_Link(), "", "", "", "", lateksList.get(i).getDescription(), "", "Mimaki"));

                                                              }
                                                          }
                                                          for (int i = 0; i < solvent_uv_yazicilarList.size(); i++) {
                                                              if (prefs.getString("SortByKod", "false").equals("true")) {
                                                                  if (solvent_uv_yazicilarList.get(i).getModel().toUpperCase().contains(SearchString) || solvent_uv_yazicilarList.get(i).getKod().equals(SearchString))
                                                                      testlist.add(new SearchModel(solvent_uv_yazicilarList.get(i).getModel(), solvent_uv_yazicilarList.get(i).getKod(), solvent_uv_yazicilarList.get(i).getFiyat(), solvent_uv_yazicilarList.get(i).getURL(), solvent_uv_yazicilarList.get(i).getRate(), "Makinalar", solvent_uv_yazicilarList.get(i).getShare_Link(), "", "", "", "", solvent_uv_yazicilarList.get(i).getDescription(), "", "Mimaki"));
                                                              } else {
                                                                  if (solvent_uv_yazicilarList.get(i).getModel().toUpperCase().contains(SearchString))
                                                                      testlist.add(new SearchModel(solvent_uv_yazicilarList.get(i).getModel(), solvent_uv_yazicilarList.get(i).getKod(), solvent_uv_yazicilarList.get(i).getFiyat(), solvent_uv_yazicilarList.get(i).getURL(), solvent_uv_yazicilarList.get(i).getRate(), "Makinalar", solvent_uv_yazicilarList.get(i).getShare_Link(), "", "", "", "", solvent_uv_yazicilarList.get(i).getDescription(), "", "Mimaki"));

                                                              }
                                                          }
                                                          for (int i = 0; i < solvent_yaziciList.size(); i++) {
                                                              if (prefs.getString("SortByKod", "false").equals("true")) {
                                                                  if (solvent_yaziciList.get(i).getModel().toUpperCase().contains(SearchString) || solvent_yaziciList.get(i).getKod().equals(SearchString))
                                                                      testlist.add(new SearchModel(solvent_yaziciList.get(i).getModel(), solvent_yaziciList.get(i).getKod(), solvent_yaziciList.get(i).getFiyat(), solvent_yaziciList.get(i).getURL(), solvent_yaziciList.get(i).getRate(), "Makinalar", solvent_yaziciList.get(i).getShare_Link(), "", "", "", "", solvent_yaziciList.get(i).getDescription(), "", "Mimaki"));
                                                              } else {
                                                                  if (solvent_yaziciList.get(i).getModel().toUpperCase().contains(SearchString))
                                                                      testlist.add(new SearchModel(solvent_yaziciList.get(i).getModel(), solvent_yaziciList.get(i).getKod(), solvent_yaziciList.get(i).getFiyat(), solvent_yaziciList.get(i).getURL(), solvent_yaziciList.get(i).getRate(), "Makinalar", solvent_yaziciList.get(i).getShare_Link(), "", "", "", "", solvent_yaziciList.get(i).getDescription(), "", "Mimaki"));

                                                              }
                                                          }
                                                          for (int i = 0; i < uv_yazicilarList.size(); i++) {
                                                              if (prefs.getString("SortByKod", "false").equals("true")) {
                                                                  if (uv_yazicilarList.get(i).getModel().toUpperCase().contains(SearchString) || uv_yazicilarList.get(i).getKod().equals(SearchString))
                                                                      testlist.add(new SearchModel(uv_yazicilarList.get(i).getModel(), uv_yazicilarList.get(i).getKod(), uv_yazicilarList.get(i).getFiyat(), uv_yazicilarList.get(i).getURL(), uv_yazicilarList.get(i).getRate(), "Makinalar", uv_yazicilarList.get(i).getShare_Link(), "", "", "", "", uv_yazicilarList.get(i).getDescription(), "", "Mimaki"));
                                                              } else {
                                                                  if (uv_yazicilarList.get(i).getModel().toUpperCase().contains(SearchString))
                                                                      testlist.add(new SearchModel(uv_yazicilarList.get(i).getModel(), uv_yazicilarList.get(i).getKod(), uv_yazicilarList.get(i).getFiyat(), uv_yazicilarList.get(i).getURL(), uv_yazicilarList.get(i).getRate(), "Makinalar", uv_yazicilarList.get(i).getShare_Link(), "", "", "", "", uv_yazicilarList.get(i).getDescription(), "", "Mimaki"));

                                                              }
                                                          }
                                                          Log.e("sizzzeee of test", testlist.size() + "");

                                                          mDatabase2.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                        @Override
                                                                                                        public void onDataChange(DataSnapshot postSnapshot) {
                                                                                                            progress_search.setVisibility(View.VISIBLE);


                                                                                                            eco_solventList = new ArrayList<Eco_Solvent>();
                                                                                                            uv_boyalarList = new ArrayList<>();
                                                                                                            temizlemeList = new ArrayList<>();
                                                                                                            emeraldList = new ArrayList<>();
                                                                                                            mldList = new ArrayList<>();
                                                                                                            pro_inkList = new ArrayList<>();
                                                                                                            triangleList = new ArrayList<>();
                                                                                                            ecoSolventValueList = new ArrayList<Eco_Solvent_Value>();
                                                                                                            uv_boyalar_valueList = new ArrayList<Uv_Boyalar_Value>();
                                                                                                            temizleme_valueList = new ArrayList<Temizleme_Value>();
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

/*
                                                                                                          ecoSolventValueList = new RealmList<Eco_Solvent_Value>();
*/
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

                                                                                                                                // eco_solvent.setEco_Solvent_value(new RealmList<Eco_Solvent_Value>(ecoSolventValueList.toArray(new Eco_Solvent_Value[ecoSolventValueList.size()])));
                                                                                                                            }
                                                                                                                            eco_solventList.add(eco_solvent);

                                                                                                                            //
                                                                                                                            //


                                                                                                                        }

                                                                                                                        Log.e("Eco_Solvent size", eco_solventList.size() + "");
                                                                                                                    }
                                                                                                                    if (dataSnapshot1.getKey().equals("Temizleme")) {
                                                                                                                        for (DataSnapshot dataSnapshot0 : dataSnapshot1.getChildren()) {

/*
                                                                                                          temizleme_valueList = new RealmList<Temizleme_Value>();
*/
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

                                                                                                                                // eco_solvent.setEco_Solvent_value(new RealmList<Eco_Solvent_Value>(ecoSolventValueList.toArray(new Eco_Solvent_Value[ecoSolventValueList.size()])));
                                                                                                                            }
                                                                                                                            temizlemeList.add(temizleme);

                                                                                                                            //
                                                                                                                            //


                                                                                                                        }


                                                                                                                        Log.e("Temizleme size", temizlemeList.size() + "");

                                                                                                                    }
                                                                                                                    if (dataSnapshot1.getKey().equals("Uv_Boyalar")) {
                                                                                                                        for (DataSnapshot dataSnapshot0 : dataSnapshot1.getChildren()) {

/*
                                                                                                          uv_boyalar_valueList = new RealmList<Uv_Boyalar_Value>();
*/
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

                                                                                                                                    }
                                                                                                                                    uv_boyalar.setUv_boyalar_Value(uv_boyalar_valueList);


                                                                                                                                } else {
                                                                                                                                    Log.e("Uv_Boyalar Name", dataSnapshot2.getValue(String.class) + "");

                                                                                                                                    uv_boyalar.setUv_boyalar_Name(dataSnapshot2.getValue(String.class));

                                                                                                                                }

                                                                                                                                // eco_solvent.setEco_Solvent_value(new RealmList<Eco_Solvent_Value>(ecoSolventValueList.toArray(new Eco_Solvent_Value[ecoSolventValueList.size()])));
                                                                                                                            }
                                                                                                                            uv_boyalarList.add(uv_boyalar);

                                                                                                                            //
                                                                                                                            //


                                                                                                                        }


                                                                                                                        Log.e("Uv_Boyalar size", uv_boyalarList.size() + "");
                                                                                                                    }
                                                                                                                }

                                                                                                            }


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


                                                                                                            for (int i = 0; i < ecoSolventValueList.size(); i++) {
                                                                                                                if (prefs.getString("SortByKod", "false").equals("true")) {
                                                                                                                    if (ecoSolventValueList.get(i).getModel().toUpperCase().contains(SearchString) || ecoSolventValueList.get(i).getKod().equals(SearchString))

                                                                                                                        testlist.add(new SearchModel(ecoSolventValueList.get(i).getModel(), ecoSolventValueList.get(i).getKod(), ecoSolventValueList.get(i).getFiyat(), ecoSolventValueList.get(i).getURL(), ecoSolventValueList.get(i).getRate(), "Boyalar", ecoSolventValueList.get(i).getShare_Link(), ecoSolventValueList.get(i).getRenk(), ecoSolventValueList.get(i).getMiktari(), ecoSolventValueList.get(i).getAmbalaj_sekli(), "", "", "", "Mimaki"));
                                                                                                                } else {
                                                                                                                    if (ecoSolventValueList.get(i).getModel().toUpperCase().contains(SearchString))
                                                                                                                        testlist.add(new SearchModel(ecoSolventValueList.get(i).getModel(), ecoSolventValueList.get(i).getKod(), ecoSolventValueList.get(i).getFiyat(), ecoSolventValueList.get(i).getURL(), ecoSolventValueList.get(i).getRate(), "Boyalar", ecoSolventValueList.get(i).getShare_Link(), ecoSolventValueList.get(i).getRenk(), ecoSolventValueList.get(i).getMiktari(), ecoSolventValueList.get(i).getAmbalaj_sekli(), "", "", "", "Mimaki"));

                                                                                                                }
                                                                                                            }
                                                                                                            for (int i = 0; i < uv_boyalar_valueList.size(); i++) {


                                                                                                                if (prefs.getString("SortByKod", "false").equals("true")) {
                                                                                                                    if (uv_boyalar_valueList.get(i).getModel().toUpperCase().contains(SearchString) || uv_boyalar_valueList.get(i).getKod().equals(SearchString))
                                                                                                                        testlist.add(new SearchModel(uv_boyalar_valueList.get(i).getModel(), uv_boyalar_valueList.get(i).getKod(), uv_boyalar_valueList.get(i).getFiyat(), uv_boyalar_valueList.get(i).getURL(), uv_boyalar_valueList.get(i).getRate(), "Boyalar", uv_boyalar_valueList.get(i).getShare_Link(), uv_boyalar_valueList.get(i).getRenk(), uv_boyalar_valueList.get(i).getMiktari(), uv_boyalar_valueList.get(i).getAmbalaj_sekli(), "", "", "", "Mimaki"));
                                                                                                                    ;
                                                                                                                } else {
                                                                                                                    if (uv_boyalar_valueList.get(i).getModel().toUpperCase().contains(SearchString))
                                                                                                                        testlist.add(new SearchModel(uv_boyalar_valueList.get(i).getModel(), uv_boyalar_valueList.get(i).getKod(), uv_boyalar_valueList.get(i).getFiyat(), uv_boyalar_valueList.get(i).getURL(), uv_boyalar_valueList.get(i).getRate(), "Boyalar", uv_boyalar_valueList.get(i).getShare_Link(), uv_boyalar_valueList.get(i).getRenk(), uv_boyalar_valueList.get(i).getMiktari(), uv_boyalar_valueList.get(i).getAmbalaj_sekli(), "", "", "", "Mimaki"));

                                                                                                                }
                                                                                                            }
                                                                                                            for (int i = 0; i < temizleme_valueList.size(); i++) {
                                                                                                                if (prefs.getString("SortByKod", "false").equals("true")) {
                                                                                                                    if (temizleme_valueList.get(i).getModel().toUpperCase().contains(SearchString) || temizleme_valueList.get(i).getKod().equals(SearchString))
                                                                                                                        testlist.add(new SearchModel(temizleme_valueList.get(i).getModel(), temizleme_valueList.get(i).getKod(), temizleme_valueList.get(i).getFiyat(), temizleme_valueList.get(i).getURL(), temizleme_valueList.get(i).getRate(), "Boyalar", temizleme_valueList.get(i).getShare_Link(), "", temizleme_valueList.get(i).getMiktari(), temizleme_valueList.get(i).getAmbalaj_sekli(), "", "", "", "Mimaki"));
                                                                                                                } else {
                                                                                                                    if (temizleme_valueList.get(i).getModel().toUpperCase().contains(SearchString))
                                                                                                                        testlist.add(new SearchModel(temizleme_valueList.get(i).getModel(), temizleme_valueList.get(i).getKod(), temizleme_valueList.get(i).getFiyat(), temizleme_valueList.get(i).getURL(), temizleme_valueList.get(i).getRate(), "Boyalar", temizleme_valueList.get(i).getShare_Link(), "", temizleme_valueList.get(i).getMiktari(), temizleme_valueList.get(i).getAmbalaj_sekli(), "", "", "", "Mimaki"));

                                                                                                                }
                                                                                                            }


                                                                                                            for (int i = 0; i < emeraldList.size(); i++) {
                                                                                                                if (prefs.getString("SortByKod", "false").equals("true")) {
                                                                                                                    if (emeraldList.get(i).getModel().toUpperCase().contains(SearchString) || emeraldList.get(i).getKod().equals(SearchString))
                                                                                                                        testlist.add(new SearchModel(emeraldList.get(i).getModel(), emeraldList.get(i).getKod(), emeraldList.get(i).getFiyat(), emeraldList.get(i).getURL(), emeraldList.get(i).getRate(), "Boyalar", emeraldList.get(i).getShare_Link(), emeraldList.get(i).getRenk(), emeraldList.get(i).getMiktari(), emeraldList.get(i).getAmbalaj_sekli(), "", "", "", "Emerald"));
                                                                                                                } else {
                                                                                                                    if (emeraldList.get(i).getModel().toUpperCase().contains(SearchString))
                                                                                                                        testlist.add(new SearchModel(emeraldList.get(i).getModel(), emeraldList.get(i).getKod(), emeraldList.get(i).getFiyat(), emeraldList.get(i).getURL(), emeraldList.get(i).getRate(), "Boyalar", emeraldList.get(i).getShare_Link(), emeraldList.get(i).getRenk(), emeraldList.get(i).getMiktari(), emeraldList.get(i).getAmbalaj_sekli(), "", "", "", "Emerald"));

                                                                                                                }
                                                                                                            }
                                                                                                            for (int i = 0; i < mldList.size(); i++) {
                                                                                                                if (prefs.getString("SortByKod", "false").equals("true")) {
                                                                                                                    if (mldList.get(i).getModel().toUpperCase().contains(SearchString) || mldList.get(i).getKod().equals(SearchString))
                                                                                                                        testlist.add(new SearchModel(mldList.get(i).getModel(), mldList.get(i).getKod(), mldList.get(i).getFiyat(), mldList.get(i).getURL(), mldList.get(i).getRate(), "Boyalar", mldList.get(i).getShare_Link(), mldList.get(i).getRenk(), mldList.get(i).getMiktari(), mldList.get(i).getAmbalaj_sekli(), "", "", "", "MLD"));
                                                                                                                } else {
                                                                                                                    if (mldList.get(i).getModel().toUpperCase().contains(SearchString))
                                                                                                                        testlist.add(new SearchModel(mldList.get(i).getModel(), mldList.get(i).getKod(), mldList.get(i).getFiyat(), mldList.get(i).getURL(), mldList.get(i).getRate(), "Boyalar", mldList.get(i).getShare_Link(), mldList.get(i).getRenk(), mldList.get(i).getMiktari(), mldList.get(i).getAmbalaj_sekli(), "", "", "", "MLD"));

                                                                                                                }
                                                                                                            }
                                                                                                            for (int i = 0; i < pro_inkList.size(); i++) {
                                                                                                                if (prefs.getString("SortByKod", "false").equals("true")) {
                                                                                                                    if (pro_inkList.get(i).getModel().toUpperCase().contains(SearchString) || pro_inkList.get(i).getKod().equals(SearchString))
                                                                                                                        testlist.add(new SearchModel(pro_inkList.get(i).getModel(), pro_inkList.get(i).getKod(), pro_inkList.get(i).getFiyat(), pro_inkList.get(i).getURL(), pro_inkList.get(i).getRate(), "Boyalar", pro_inkList.get(i).getShare_Link(), pro_inkList.get(i).getRenk(), pro_inkList.get(i).getMiktari(), pro_inkList.get(i).getAmbalaj_sekli(), "", "", "", "Pro Ink"));
                                                                                                                } else {
                                                                                                                    if (pro_inkList.get(i).getModel().toUpperCase().contains(SearchString))
                                                                                                                        testlist.add(new SearchModel(pro_inkList.get(i).getModel(), pro_inkList.get(i).getKod(), pro_inkList.get(i).getFiyat(), pro_inkList.get(i).getURL(), pro_inkList.get(i).getRate(), "Boyalar", pro_inkList.get(i).getShare_Link(), pro_inkList.get(i).getRenk(), pro_inkList.get(i).getMiktari(), pro_inkList.get(i).getAmbalaj_sekli(), "", "", "", "Pro Ink"));

                                                                                                                }
                                                                                                            }
                                                                                                            Log.e("sizzzeee of test", testlist.size() + "");

                                                                                                            mDatabase3.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                                                          @Override
                                                                                                                                                          public void onDataChange(DataSnapshot postSnapshot) {
                                                                                                                                                              progress_search.setVisibility(View.VISIBLE);

                                                                                                                                                              bicaklarList = new ArrayList<>();
                                                                                                                                                              head_boardList = new ArrayList<>();
                                                                                                                                                              kabloList = new ArrayList<>();
                                                                                                                                                              main_boardList = new ArrayList<>();
                                                                                                                                                              motorList = new ArrayList<>();
                                                                                                                                                              // reklamList = new ArrayList<>();


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


                                                                                                                                                                      if (dataSnapshot1.getKey().equals("Kesim")) {
                                                                                                                                                                          for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                                                                                                                                                              kesimList.add(dataSnapshot2.getValue(Kesim.class));

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


                                                                                                                                                              for (int i = 0; i < bicaklarList.size(); i++) {
                                                                                                                                                                  if (prefs.getString("SortByKod", "false").equals("true")) {
                                                                                                                                                                      if (bicaklarList.get(i).getModel().toUpperCase().contains(SearchString) || bicaklarList.get(i).getKod().equals(SearchString))
                                                                                                                                                                          testlist.add(new SearchModel(bicaklarList.get(i).getModel(), bicaklarList.get(i).getKod(), bicaklarList.get(i).getFiyat(), bicaklarList.get(i).getURL(), bicaklarList.get(i).getRate(), "Yedek", bicaklarList.get(i).getShare_Link(), "", "", "", bicaklarList.get(i).getUyumlu(), "", "", "Roland"));
                                                                                                                                                                  } else {
                                                                                                                                                                      if (bicaklarList.get(i).getModel().toUpperCase().contains(SearchString))
                                                                                                                                                                          testlist.add(new SearchModel(bicaklarList.get(i).getModel(), bicaklarList.get(i).getKod(), bicaklarList.get(i).getFiyat(), bicaklarList.get(i).getURL(), bicaklarList.get(i).getRate(), "Yedek", bicaklarList.get(i).getShare_Link(), "", "", "", bicaklarList.get(i).getUyumlu(), "", "", "Roland"));

                                                                                                                                                                  }
                                                                                                                                                              }
                                                                                                                                                              for (int i = 0; i < head_boardList.size(); i++) {
                                                                                                                                                                  if (prefs.getString("SortByKod", "false").equals("true")) {
                                                                                                                                                                      if (head_boardList.get(i).getModel().toUpperCase().contains(SearchString) || head_boardList.get(i).getKod().equals(SearchString))
                                                                                                                                                                          testlist.add(new SearchModel(head_boardList.get(i).getModel(), head_boardList.get(i).getKod(), head_boardList.get(i).getFiyat(), head_boardList.get(i).getURL(), head_boardList.get(i).getRate(), "Yedek", head_boardList.get(i).getShare_Link(), "", "", "", head_boardList.get(i).getUyumlu(), "", "", "Roland"));
                                                                                                                                                                  } else {
                                                                                                                                                                      if (head_boardList.get(i).getModel().toUpperCase().contains(SearchString))
                                                                                                                                                                          testlist.add(new SearchModel(head_boardList.get(i).getModel(), head_boardList.get(i).getKod(), head_boardList.get(i).getFiyat(), head_boardList.get(i).getURL(), head_boardList.get(i).getRate(), "Yedek", head_boardList.get(i).getShare_Link(), "", "", "", head_boardList.get(i).getUyumlu(), "", "", "Roland"));

                                                                                                                                                                  }
                                                                                                                                                              }
                                                                                                                                                              for (int i = 0; i < kabloList.size(); i++) {
                                                                                                                                                                  if (prefs.getString("SortByKod", "false").equals("true")) {
                                                                                                                                                                      if (kabloList.get(i).getModel().toUpperCase().contains(SearchString) || kabloList.get(i).getKod().equals(SearchString))
                                                                                                                                                                          testlist.add(new SearchModel(kabloList.get(i).getModel(), kabloList.get(i).getKod(), kabloList.get(i).getFiyat(), kabloList.get(i).getURL(), kabloList.get(i).getRate(), "Yedek", kabloList.get(i).getShare_Link(), "", "", "", kabloList.get(i).getUyumlu(), "", "", "Roland"));
                                                                                                                                                                  } else {
                                                                                                                                                                      if (kabloList.get(i).getModel().toUpperCase().contains(SearchString))
                                                                                                                                                                          testlist.add(new SearchModel(kabloList.get(i).getModel(), kabloList.get(i).getKod(), kabloList.get(i).getFiyat(), kabloList.get(i).getURL(), kabloList.get(i).getRate(), "Yedek", kabloList.get(i).getShare_Link(), "", "", "", kabloList.get(i).getUyumlu(), "", "", "Roland"));

                                                                                                                                                                  }
                                                                                                                                                              }
                                                                                                                                                              for (int i = 0; i < main_boardList.size(); i++) {
                                                                                                                                                                  if (prefs.getString("SortByKod", "false").equals("true")) {
                                                                                                                                                                      if (main_boardList.get(i).getModel().toUpperCase().contains(SearchString) || main_boardList.get(i).getKod().equals(SearchString))
                                                                                                                                                                          testlist.add(new SearchModel(main_boardList.get(i).getModel(), main_boardList.get(i).getKod(), main_boardList.get(i).getFiyat(), main_boardList.get(i).getURL(), main_boardList.get(i).getRate(), "Yedek", main_boardList.get(i).getShare_Link(), "", "", "", main_boardList.get(i).getUyumlu(), "", "", "Roland"));
                                                                                                                                                                  } else {
                                                                                                                                                                      if (main_boardList.get(i).getModel().toUpperCase().contains(SearchString))
                                                                                                                                                                          testlist.add(new SearchModel(main_boardList.get(i).getModel(), main_boardList.get(i).getKod(), main_boardList.get(i).getFiyat(), main_boardList.get(i).getURL(), main_boardList.get(i).getRate(), "Yedek", main_boardList.get(i).getShare_Link(), "", "", "", main_boardList.get(i).getUyumlu(), "", "", "Roland"));

                                                                                                                                                                  }
                                                                                                                                                              }
                                                                                                                                                              for (int i = 0; i < motorList.size(); i++) {
                                                                                                                                                                  if (prefs.getString("SortByKod", "false").equals("true")) {
                                                                                                                                                                      if (motorList.get(i).getModel().toUpperCase().contains(SearchString) || motorList.get(i).getKod().equals(SearchString))
                                                                                                                                                                          testlist.add(new SearchModel(motorList.get(i).getModel(), motorList.get(i).getKod(), motorList.get(i).getFiyat(), motorList.get(i).getURL(), motorList.get(i).getRate(), "Yedek", motorList.get(i).getShare_Link(), "", "", "", motorList.get(i).getUyumlu(), "", "", "Roland"));
                                                                                                                                                                  } else {
                                                                                                                                                                      if (motorList.get(i).getModel().toUpperCase().contains(SearchString))
                                                                                                                                                                          testlist.add(new SearchModel(motorList.get(i).getModel(), motorList.get(i).getKod(), motorList.get(i).getFiyat(), motorList.get(i).getURL(), motorList.get(i).getRate(), "Yedek", motorList.get(i).getShare_Link(), "", "", "", motorList.get(i).getUyumlu(), "", "", "Roland"));

                                                                                                                                                                  }
                                                                                                                                                              }

                                                                                                                                                              Log.e("sizzzeee of test", testlist.size() + "");

                                                                                                                                                              mDatabase4.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                                                                                                            @Override
                                                                                                                                                                                                            public void onDataChange(DataSnapshot postSnapshot) {
                                                                                                                                                                                                                progress_search.setVisibility(View.VISIBLE);


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
                                                                                                                                                                                                                                                            dataSnapshot3.getValue(Unifol_Value.class).getRenk(),
                                                                                                                                                                                                                                                            dataSnapshot3.getValue(Unifol_Value.class).getMiktari(),
                                                                                                                                                                                                                                                            dataSnapshot3.getValue(Unifol_Value.class).getFiyat(),
                                                                                                                                                                                                                                                            dataSnapshot3.getValue(Unifol_Value.class).getURL(),
                                                                                                                                                                                                                                                            dataSnapshot3.getValue(Unifol_Value.class).getDescription(),
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
                                                                                                                                                                                                                for (int i = 0; i < unifol_valueList.size(); i++) {

                                                                                                                                                                                                                    if (prefs.getString("SortByKod", "false").equals("true")) {
                                                                                                                                                                                                                        if (unifol_valueList.get(i).getModel().toUpperCase().contains(SearchString) || unifol_valueList.get(i).getKod().equals(SearchString))
                                                                                                                                                                                                                            testlist.add(new SearchModel(unifol_valueList.get(i).getModel(), unifol_valueList.get(i).getKod(), unifol_valueList.get(i).getFiyat(), unifol_valueList.get(i).getURL(), unifol_valueList.get(i).getRate(), "Malzeme", unifol_valueList.get(i).getShare_Link(), unifol_valueList.get(i).getRenk(), unifol_valueList.get(i).getMiktari(), "", "", "", "", "Folyo"));
                                                                                                                                                                                                                    } else {
                                                                                                                                                                                                                        if (unifol_valueList.get(i).getModel().toUpperCase().contains(SearchString))
                                                                                                                                                                                                                            testlist.add(new SearchModel(unifol_valueList.get(i).getModel(), unifol_valueList.get(i).getKod(), unifol_valueList.get(i).getFiyat(), unifol_valueList.get(i).getURL(), unifol_valueList.get(i).getRate(), "Malzeme", unifol_valueList.get(i).getShare_Link(), unifol_valueList.get(i).getRenk(), unifol_valueList.get(i).getMiktari(), "", "", "", "", "Folyo"));

                                                                                                                                                                                                                    }

                                                                                                                                                                                                                }
                                                                                                                                                                                                                Log.e("sizzzeee of test1", testlist.size() + "");


                                                                                                                                                                                                                mDatabase5.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                                                                                                                                                              @Override
                                                                                                                                                                                                                                                              public void onDataChange(DataSnapshot postSnapshot) {
                                                                                                                                                                                                                                                                  corelDrawList = new ArrayList<>();


                                                                                                                                                                                                                                                                  for (DataSnapshot dataSnapshot : postSnapshot.getChildren()) {
                                                                                                                                                                                                                                                                      for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                                                                                                                                                                                                                                                                          if (dataSnapshot.getKey().equals("Corel_Draw")) {
                                                                                                                                                                                                                                                                              corelDrawList.add(dataSnapshot1.getValue(CorelDraw.class));
                                                                                                                                                                                                                                                                              Log.e("corel drawwwwwwww", dataSnapshot1.getValue() + "");


                                                                                                                                                                                                                                                                          }


                                                                                                                                                                                                                                                                      }

                                                                                                                                                                                                                                                                  }

                                                                                                                                                                                                                                                                  Log.e("sizzzeee of test2", testlist.size() + "");
                                                                                                                                                                                                                                                                  Log.e("sizzzeee corelDrawList2", corelDrawList.size() + "");
                                                                                                                                                                                                                                                                  yazilim = new Yazilim();
                                                                                                                                                                                                                                                                  yazilim.setCorelDrawList(corelDrawList);
                                                                                                                                                                                                                                                                  for (int i = 0; i < corelDrawList.size(); i++) {

                                                                                                                                                                                                                                                                      if (prefs.getString("SortByKod", "false").equals("true")) {
                                                                                                                                                                                                                                                                          if (corelDrawList.get(i).getModel().toUpperCase().contains(SearchString) || corelDrawList.get(i).getKod().equals(SearchString))
                                                                                                                                                                                                                                                                              testlist.add(new SearchModel(corelDrawList.get(i).getModel(), corelDrawList.get(i).getKod(), corelDrawList.get(i).getFiyat(), corelDrawList.get(i).getURL(), corelDrawList.get(i).getRate(), "Yazilim", corelDrawList.get(i).getShare_Link(), "", "", "", "", "", "", "Corel Draw"));
                                                                                                                                                                                                                                                                      } else {
                                                                                                                                                                                                                                                                          if (corelDrawList.get(i).getModel().toUpperCase().contains(SearchString))
                                                                                                                                                                                                                                                                              testlist.add(new SearchModel(corelDrawList.get(i).getModel(), corelDrawList.get(i).getKod(), corelDrawList.get(i).getFiyat(), corelDrawList.get(i).getURL(), corelDrawList.get(i).getRate(), "Yazilim", corelDrawList.get(i).getShare_Link(), "", "", "", "", "", "", "Corel Draw"));

                                                                                                                                                                                                                                                                      }

                                                                                                                                                                                                                                                                  }
                                                                                                                                                                                                                                                                  Log.e("sizzzeee of test3", testlist.size() + "");
                                                                                                                                                                                                                                                                  Log.e("sizzzeee corelDrawList3", corelDrawList.size() + "");


                                                                                                                                                                                                                                                                  mDatabase6.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                                                                                                                                                                      @Override
                                                                                                                                                                                                                                                                      public void onDataChange(DataSnapshot postSnapshot) {


                                                                                                                                                                                                                                                                          kampanyalars = new ArrayList<>();


                                                                                                                                                                                                                                                                          for (DataSnapshot dataSnapshot : postSnapshot.getChildren()) {
                                                                                                                                                                                                                                                                              for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                                                                                                                                                                                                                                                  Log.e("data kampanyalar", dataSnapshot1.getValue() + "");
                                                                                                                                                                                                                                                                                  kampanyalars.add(dataSnapshot1.getValue(Kampanyalar.class));
                                                                                                                                                                                                                                                                                  // Log.e("data roland", dataSnapshot1.getValue() + "");


                                                                                                                                                                                                                                                                              }

                                                                                                                                                                                                                                                                          }

                                                                                                                                                                                                                                                                          for (int i = 0; i < kampanyalars.size(); i++) {
                                                                                                                                                                                                                                                                              if (prefs.getString("SortByKod", "false").equals("true")) {
                                                                                                                                                                                                                                                                                  if (kampanyalars.get(i).getModel().toUpperCase().contains(SearchString) || kampanyalars.get(i).getKod().equals(SearchString))
                                                                                                                                                                                                                                                                                      testlist.add(new SearchModel(kampanyalars.get(i).getModel(), kampanyalars.get(i).getKod(), kampanyalars.get(i).getFiyat(), kampanyalars.get(i).getURL(), kampanyalars.get(i).getRate(), "Kampanyalar", kampanyalars.get(i).getShare_Link(), "", "", "", "", "", "", ""));
                                                                                                                                                                                                                                                                              } else {
                                                                                                                                                                                                                                                                                  if (kampanyalars.get(i).getModel().toUpperCase().contains(SearchString))
                                                                                                                                                                                                                                                                                      testlist.add(new SearchModel(kampanyalars.get(i).getModel(), kampanyalars.get(i).getKod(), kampanyalars.get(i).getFiyat(), kampanyalars.get(i).getURL(), kampanyalars.get(i).getRate(), "Kampanyalar", kampanyalars.get(i).getShare_Link(), "", "", "", "", "", "", ""));

                                                                                                                                                                                                                                                                              }
                                                                                                                                                                                                                                                                          }

                                                                                                                                                                                                                                                                          Log.e("sizzzeee of test4", testlist.size() + "");
                                                                                                                                                                                                                                                                          final Handler handler = new Handler();
                                                                                                                                                                                                                                                                          handler.postDelayed(new Runnable() {
                                                                                                                                                                                                                                                                                                  @Override
                                                                                                                                                                                                                                                                                                  public void run() {
                                                                                                                                                                                                                                                                                                      Log.e("sizzzeee of test5", testlist.size() + "");
                                                                                                                                                                                                                                                                                                      if (testlist.size() > 0) {
                                                                                                                                                                                                                                                                                                          mRecyclerView.setVisibility(View.VISIBLE);


                                                                                                                                                                                                                                                                                                          progress_search.setVisibility(View.INVISIBLE);
                                                                                                                                                                                                                                                                                                          nothing_result_text.setVisibility(View.INVISIBLE);
                                                                                                                                                                                                                                                                                                          nothing_result.setVisibility(View.INVISIBLE);


                                                                                                                                                                                                                                                                                                          mAdapter = new NormalItemAdapter_Search(testlist, ResultSearchActivity.this);


                                                                                                                                                                                                                                                                                                          mRecyclerView.setAdapter(mAdapter);
                                                                                                                                                                                                                                                                                                          mRecyclerView.setHasFixedSize(true);

                                                                                                                                                                                                                                                                                                          mAdapter.notifyDataSetChanged();

                                                                                                                                                                                                                                                                                                      } else {
                                                                                                                                                                                                                                                                                                          progress_search.setVisibility(View.INVISIBLE);
                                                                                                                                                                                                                                                                                                          nothing_result_text.setVisibility(View.VISIBLE);
                                                                                                                                                                                                                                                                                                          nothing_result.setVisibility(View.VISIBLE);
                                                                                                                                                                                                                                                                                                          mRecyclerView.setVisibility(View.INVISIBLE);
                                                                                                                                                                                                                                                                                                      }
                                                                                                                                                                                                                                                                                                  }
                                                                                                                                                                                                                                                                                              }

                                                                                                                                                                                                                                                                                  , 1000);


                                                                                                                                                                                                                                                                      }

                                                                                                                                                                                                                                                                      @Override
                                                                                                                                                                                                                                                                      public void onCancelled(DatabaseError firebaseError) {

                                                                                                                                                                                                                                                                      }
                                                                                                                                                                                                                                                                  });


                                                                                                                                                                                                                                                              }

                                                                                                                                                                                                                                                              @Override
                                                                                                                                                                                                                                                              public void onCancelled(DatabaseError
                                                                                                                                                                                                                                                                                              firebaseError) {

                                                                                                                                                                                                                                                              }
                                                                                                                                                                                                                                                          }

                                                                                                                                                                                                                );
                                                                                                                                                                                                            }

                                                                                                                                                                                                            @Override
                                                                                                                                                                                                            public void onCancelled(DatabaseError firebaseError) {

                                                                                                                                                                                                            }
                                                                                                                                                                                                        }

                                                                                                                                                              );

                                                                                                                                                          }

                                                                                                                                                          @Override
                                                                                                                                                          public void onCancelled(DatabaseError firebaseError) {

                                                                                                                                                          }
                                                                                                                                                      }

                                                                                                            );


                                                                                                        }

                                                                                                        @Override
                                                                                                        public void onCancelled(DatabaseError firebaseError) {

                                                                                                        }
                                                                                                    }

                                                          );

                                                      }

                                                      @Override
                                                      public void onCancelled(DatabaseError firebaseError) {

                                                      }
                                                  }

        );


    }


    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();

    }


    @Override
    public void Update(int position, String from) {
        if (from.equals("Makinalar")) {

            Intent intent = new Intent(this, EditMakinalarActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("Model", testlist.get(position).getModel());
            intent.putExtra("Image", testlist.get(position).getURL());
            intent.putExtra("Kod", testlist.get(position).getKod());
            intent.putExtra("Description", testlist.get(position).getDescription());
            intent.putExtra("Marka", testlist.get(position).getMarka());
            intent.putExtra("Fiyat", testlist.get(position).getFiyat());
            intent.putExtra("Share_Link", testlist.get(position).getShare_Link());
            intent.putExtra("Rate", testlist.get(position).getRate());
            startActivityForResult(intent, 0);
        } else if (from.equals("Boyalar")) {

            Intent intent = new Intent(this, EditBoyalarActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("Model", testlist.get(position).getModel());
            intent.putExtra("Image", testlist.get(position).getURL());
            intent.putExtra("Kod", testlist.get(position).getKod());

            intent.putExtra("Renk", testlist.get(position).getRenk());


            intent.putExtra("Miktari", testlist.get(position).getMiktari());
            intent.putExtra("Ambalaj", testlist.get(position).getAmbalaj_sekli());

            intent.putExtra("Marka", testlist.get(position).getMarka());
            intent.putExtra("Fiyat", testlist.get(position).getFiyat());
            intent.putExtra("Share_Link", testlist.get(position).getShare_Link());
            intent.putExtra("Rate", testlist.get(position).getRate());
            startActivityForResult(intent, 0);
        } else if (testlist.get(position).getFrom().equals("Yedek")) {
            Intent intent = new Intent(this, EditYedekActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("Model", testlist.get(position).getModel());
            intent.putExtra("Image", testlist.get(position).getURL());
            intent.putExtra("Kod", testlist.get(position).getKod());

            intent.putExtra("Uyumlu", testlist.get(position).getUyumlu());

            intent.putExtra("Marka", testlist.get(position).getMarka());
            intent.putExtra("Fiyat", testlist.get(position).getFiyat());
            intent.putExtra("Share_Link", testlist.get(position).getShare_Link());
            intent.putExtra("Rate", testlist.get(position).getRate());
            startActivityForResult(intent, 0);

        } else if (from.equals("Malzeme")) {

            Intent intent = new Intent(this, EditMalzemeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            intent.putExtra("Model", testlist.get(position).getModel());
            intent.putExtra("Image", testlist.get(position).getURL());
            intent.putExtra("Kod", testlist.get(position).getKod());
            intent.putExtra("Marka", testlist.get(position).getMarka());
            intent.putExtra("Fiyat", testlist.get(position).getFiyat());
            intent.putExtra("Renk", testlist.get(position).getRenk());
            intent.putExtra("Miktari", testlist.get(position).getMiktari());
            intent.putExtra("Share_Link", testlist.get(position).getShare_Link());
            intent.putExtra("Rate", testlist.get(position).getRate());
            startActivityForResult(intent, 0);
        } else if (from.equals("Yazilim")) {

            Intent intent = new Intent(this, EditYazilimlarActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("Model", testlist.get(position).getModel());
            intent.putExtra("Image", testlist.get(position).getURL());
            intent.putExtra("Kod", testlist.get(position).getKod());
            intent.putExtra("Marka", testlist.get(position).getMarka());
            intent.putExtra("Fiyat", testlist.get(position).getFiyat());
            intent.putExtra("Share_Link", testlist.get(position).getShare_Link());
            startActivityForResult(intent, 0);
        } else if (from.equals("Kampanyalar")) {

            Intent intent = new Intent(this, EditKampanyalarActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("Model", testlist.get(position).getModel());
            intent.putExtra("Image", testlist.get(position).getURL());
            intent.putExtra("Kod", testlist.get(position).getKod());
            intent.putExtra("Fiyat", testlist.get(position).getFiyat());
            intent.putExtra("Share_Link", testlist.get(position).getShare_Link());
            startActivityForResult(intent, 0);
        }

    }

    @Override
    public void refresh() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        testlist.clear();
        mRecyclerView.removeAllViews();
        mAdapter = new NormalItemAdapter_Search(testlist, ResultSearchActivity.this);


        mRecyclerView.setAdapter(mAdapter);


        mAdapter.notifyDataSetChanged();
        progress_search.setVisibility(View.VISIBLE);

    }
}
