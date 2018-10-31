package com.bilgiyazan.malzemeiste.adminpaneli.Activity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.bilgiyazan.malzemeiste.adminpaneli.Adapter.NormalItemAdapter;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Emerald;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.MLD;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Pro_ink;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Roland;
import com.bilgiyazan.malzemeiste.adminpaneli.R;

import java.util.ArrayList;
import java.util.List;


public class SeeAllActivity extends AppCompatActivity implements NormalItemAdapter.CallbackInterface {

    NormalItemAdapter mAdapter;
    List<Roland> rolandList = new ArrayList<>();
    List<Pro_ink> proInkList = new ArrayList<>();
    List<Emerald> emeraldList = new ArrayList<>();
    List<MLD> mldList = new ArrayList<>();
    RecyclerView mRecyclerView;
    String From_intent;
    private DatabaseReference mDatabase1;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_all_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.see_all_recyclerView);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, 1);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);


        From_intent = getIntent().getStringExtra("from");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_see_all);
        setSupportActionBar(toolbar);
        if (toolbar != null) {

            setSupportActionBar(toolbar);

            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);

            }

        }


        if (From_intent.equals("Boyalar_EMERALD")) {

            emeraldList = new ArrayList<>();

            mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Boyalar").child("Emerald");
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot postSnapshot) {
                    mRecyclerView.removeAllViews();
                    emeraldList.clear();
                    mAdapter = new NormalItemAdapter(null, emeraldList, null, null, null, "Boyalar", SeeAllActivity.this);


                    mRecyclerView.setAdapter(mAdapter);


                    mAdapter.notifyDataSetChanged();

                    for (DataSnapshot dataSnapshot : postSnapshot.getChildren()) {

                        emeraldList.add(dataSnapshot.getValue(Emerald.class));


                    }

                    List<Emerald> mContentItems = new ArrayList<>();
                    if (emeraldList.isEmpty()) {
                        finish();
                    } else {
                        for (int i = 0; i < emeraldList.size(); i++) {

                            mContentItems.add(new Emerald(emeraldList.get(i).getModel(), emeraldList.get(i).getKod(), emeraldList.get(i).getRenk(), emeraldList.get(i).getMiktari(), emeraldList.get(i).getAmbalaj_sekli(), emeraldList.get(i).getFiyat(), emeraldList.get(i).getURL(), emeraldList.get(i).getDescription(), emeraldList.get(i).getRate(), emeraldList.get(i).getShare_Link()));


                        }
                    }


                    mAdapter = new NormalItemAdapter(null, mContentItems, null, null, null, "Boyalar", SeeAllActivity.this);


                    mRecyclerView.setAdapter(mAdapter);


                    mAdapter.notifyDataSetChanged();


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            toolbar.setTitle("BOYALAR / EMERALD");

        }
        if (From_intent.equals("Boyalar_MLD")) {
            toolbar.setTitle("BOYALAR / MLD");
            mldList = new ArrayList<>();


            mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Boyalar").child("MLD");
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot postSnapshot) {
                    mRecyclerView.removeAllViews();

                    mldList.clear();
                    mAdapter = new NormalItemAdapter(null, null, mldList, null, null, "Boyalar", SeeAllActivity.this);


                    mRecyclerView.setAdapter(mAdapter);


                    mAdapter.notifyDataSetChanged();
                    for (DataSnapshot dataSnapshot : postSnapshot.getChildren()) {

                        mldList.add(dataSnapshot.getValue(MLD.class));


                    }

                    List<MLD> mContentItems = new ArrayList<>();
                    if (mldList.isEmpty()) {
                        finish();
                    } else {
                        for (int i = 0; i < mldList.size(); i++) {

                            mContentItems.add(new MLD(mldList.get(i).getModel(), mldList.get(i).getKod(), mldList.get(i).getRenk(), mldList.get(i).getMiktari(), mldList.get(i).getAmbalaj_sekli(), mldList.get(i).getFiyat(), mldList.get(i).getURL(), mldList.get(i).getDescription(), mldList.get(i).getRate(), mldList.get(i).getShare_Link()));


                        }
                    }


                    mAdapter = new NormalItemAdapter(null, null, mContentItems, null, null, "Boyalar", SeeAllActivity.this);


                    mRecyclerView.setAdapter(mAdapter);


                    mAdapter.notifyDataSetChanged();


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
        if (From_intent.equals("Boyalar_PRO_INK")) {
            toolbar.setTitle("BOYALAR / PRO_INK");
            proInkList = new ArrayList<>();


            mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Boyalar").child("Pro_ink");
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot postSnapshot) {
                    mRecyclerView.removeAllViews();
                    proInkList.clear();
                    mAdapter = new NormalItemAdapter(null, null, null, proInkList, null, "Boyalar", SeeAllActivity.this);


                    mRecyclerView.setAdapter(mAdapter);


                    mAdapter.notifyDataSetChanged();

                    for (DataSnapshot dataSnapshot : postSnapshot.getChildren()) {

                        proInkList.add(dataSnapshot.getValue(Pro_ink.class));


                    }

                    List<Pro_ink> mContentItems = new ArrayList<>();
                    if (proInkList.isEmpty()) {
                        finish();
                    } else {
                        for (int i = 0; i < proInkList.size(); i++) {

                            mContentItems.add(new Pro_ink(proInkList.get(i).getModel(), proInkList.get(i).getKod(), proInkList.get(i).getRenk(), proInkList.get(i).getMiktari(), proInkList.get(i).getAmbalaj_sekli(), proInkList.get(i).getFiyat(), proInkList.get(i).getURL(), proInkList.get(i).getDescription(), proInkList.get(i).getRate(), proInkList.get(i).getShare_Link()));

                        }
                    }


                    mAdapter = new NormalItemAdapter(null, null, null, mContentItems, null, "Boyalar", SeeAllActivity.this);


                    mRecyclerView.setAdapter(mAdapter);


                    mAdapter.notifyDataSetChanged();


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
       /* if (getIntent().getStringExtra("from").equals("Boyalar_TRIANGLE")) {
            toolbar.setTitle("BOYALAR / TRIANGLE");
            boyalarRealmResults = realm.where(Boyalar.class).findAll();
            if (boyalarRealmResults.isLoaded()) {


                Log.e("Realm", "Loaded");
                List<Triangle> mContentItems = new ArrayList<>();
                List<Triangle> triangleList = boyalarRealmResults.get(0).getTriangleList();
                for (int i = 0; i < triangleList.size(); i++) {

                    mContentItems.add(new Triangle(triangleList.get(i).getModel(), triangleList.get(i).getKod(), "", "", triangleList.get(i).getAmbalaj_sekli(), triangleList.get(i).getFiyat(), triangleList.get(i).getURL(), triangleList.get(i).getDescription(), triangleList.get(i).getRate(), triangleList.get(i).getShare_Link()));


                }

                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(SeeAllActivity.this, 2);
                mRecyclerView.setLayoutManager(layoutManager);

                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setNestedScrollingEnabled(false);

                NormalItemAdapter mAdapter;
                mAdapter = new NormalItemAdapter(null, null, null, null, mContentItems, "Boyalar", SeeAllActivity.this);


                mRecyclerView.setAdapter(mAdapter);


                mAdapter.notifyDataSetChanged();


            } else {
                Log.e("Realm", "Not Loaded");

            }

        }*/
        if (From_intent.equals("Makinalar_ROLAND")) {
            toolbar.setTitle("BASKI MAKİNALARI / ROLAND");
            rolandList = new ArrayList<>();


            mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Makinalar").child("Roland");
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot postSnapshot) {
                    mRecyclerView.removeAllViews();
                    rolandList.clear();
                    mAdapter = new NormalItemAdapter(rolandList, null, null, null, null, "Makinalar", SeeAllActivity.this);


                    mRecyclerView.setAdapter(mAdapter);


                    mAdapter.notifyDataSetChanged();

                    for (DataSnapshot dataSnapshot : postSnapshot.getChildren()) {

                        rolandList.add(dataSnapshot.getValue(Roland.class));


                    }

                    List<Roland> mContentItems = new ArrayList<>();
                    if (rolandList.isEmpty()) {
                        finish();
                    } else {
                        for (int i = 0; i < rolandList.size(); i++) {

                            mContentItems.add(new Roland(rolandList.get(i).getModel(), rolandList.get(i).getKod(), rolandList.get(i).getFiyat(), rolandList.get(i).getURL(), rolandList.get(i).getDescription(), rolandList.get(i).getRate(), rolandList.get(i).getShare_Link(), rolandList.get(i).getKDV()));


                        }
                    }

                    mAdapter = new NormalItemAdapter(mContentItems, null, null, null, null, "Makinalar", SeeAllActivity.this);


                    mRecyclerView.setAdapter(mAdapter);


                    mAdapter.notifyDataSetChanged();


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle the click on the back arrow click
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity

        super.onBackPressed();

    }


    @Override
    public void Update(int position, String brand, String from) {

        if (from.equals("Boyalar")) {

            if (brand.equals("Emerald")) {
                Intent intent = new Intent(SeeAllActivity.this, EditBoyalarActivity.class);
                intent.putExtra("Model", emeraldList.get(position).getModel());
                intent.putExtra("Image", emeraldList.get(position).getURL());
                intent.putExtra("Kod", emeraldList.get(position).getKod());
                intent.putExtra("Ambalaj", emeraldList.get(position).getAmbalaj_sekli());
                intent.putExtra("Miktari", emeraldList.get(position).getMiktari());
                intent.putExtra("Marka", "Emerald");
                intent.putExtra("Fiyat", emeraldList.get(position).getFiyat());
                intent.putExtra("Renk", emeraldList.get(position).getRenk());
                intent.putExtra("Share_Link", emeraldList.get(position).getShare_Link());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 0);
            } else if (brand.equals("MLD")) {
                Intent intent = new Intent(SeeAllActivity.this, EditBoyalarActivity.class);
                intent.putExtra("Model", mldList.get(position).getModel());
                intent.putExtra("Image", mldList.get(position).getURL());
                intent.putExtra("Kod", mldList.get(position).getKod());
                intent.putExtra("Ambalaj", mldList.get(position).getAmbalaj_sekli());
                intent.putExtra("Miktari", mldList.get(position).getMiktari());
                intent.putExtra("Marka", "MLD");
                intent.putExtra("Fiyat", mldList.get(position).getFiyat());
                intent.putExtra("Renk", mldList.get(position).getRenk());
                intent.putExtra("Share_Link", mldList.get(position).getShare_Link());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 1);

            } else if (brand.equals("Pro ink")) {
                Intent intent = new Intent(SeeAllActivity.this, EditBoyalarActivity.class);
                intent.putExtra("Model", proInkList.get(position).getModel());
                intent.putExtra("Image", proInkList.get(position).getURL());
                intent.putExtra("Kod", proInkList.get(position).getKod());
                intent.putExtra("Ambalaj", proInkList.get(position).getAmbalaj_sekli());
                intent.putExtra("Miktari", proInkList.get(position).getMiktari());
                intent.putExtra("Marka", "Pro ink");
                intent.putExtra("Fiyat", proInkList.get(position).getFiyat());
                intent.putExtra("Renk", proInkList.get(position).getRenk());
                intent.putExtra("Share_Link", proInkList.get(position).getShare_Link());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 2);
            }

        } else if (from.equals("Makinalar")) {
            if (brand.equals("Roland")) {
                Intent intent = new Intent(SeeAllActivity.this, EditMakinalarActivity.class);
                intent.putExtra("Model", rolandList.get(position).getModel());
                intent.putExtra("Image", rolandList.get(position).getURL());
                intent.putExtra("Kod", rolandList.get(position).getKod());
                intent.putExtra("Description", rolandList.get(position).getDescription());
                intent.putExtra("Marka", "Roland");
                intent.putExtra("Fiyat", rolandList.get(position).getFiyat());
                intent.putExtra("Share_Link", rolandList.get(position).getShare_Link());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 3);
            }

        }

    }

    @Override
    public void refresh() {
      /*  if (From_intent.equals("Boyalar_EMERALD")) {
            emeraldList.clear();
            mAdapter = new NormalItemAdapter(null, emeraldList, null, null, null, "Boyalar", SeeAllActivity.this);


            mRecyclerView.setAdapter(mAdapter);


            mAdapter.notifyDataSetChanged();
        }

        if (From_intent.equals("Boyalar_MLD")) {
            mldList.clear();
            mAdapter = new NormalItemAdapter(null, null, mldList, null, null, "Boyalar", SeeAllActivity.this);


            mRecyclerView.setAdapter(mAdapter);


            mAdapter.notifyDataSetChanged();
        }


        if (From_intent.equals("Boyalar_PRO_INK")) {
            proInkList.clear();
            mAdapter = new NormalItemAdapter(null, null, null, proInkList, null, "Boyalar", SeeAllActivity.this);


            mRecyclerView.setAdapter(mAdapter);


            mAdapter.notifyDataSetChanged();

        }

        if (From_intent.equals("Makinalar_ROLAND")) {
            rolandList.clear();
            mAdapter = new NormalItemAdapter(rolandList, null, null, null, null, "Makinalar", SeeAllActivity.this);


            mRecyclerView.setAdapter(mAdapter);


            mAdapter.notifyDataSetChanged();
        }
*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (From_intent.equals("Boyalar_EMERALD")) {
            emeraldList.clear();
            mRecyclerView.removeAllViews();
            mAdapter = new NormalItemAdapter(null, emeraldList, null, null, null, "Boyalar", SeeAllActivity.this);


            mRecyclerView.setAdapter(mAdapter);


            mAdapter.notifyDataSetChanged();
        }

        if (From_intent.equals("Boyalar_MLD")) {
            mldList.clear();
            mRecyclerView.removeAllViews();
            mAdapter = new NormalItemAdapter(null, null, mldList, null, null, "Boyalar", SeeAllActivity.this);


            mRecyclerView.setAdapter(mAdapter);


            mAdapter.notifyDataSetChanged();
        }


        if (From_intent.equals("Boyalar_PRO_INK")) {
            proInkList.clear();
            mRecyclerView.removeAllViews();
            mAdapter = new NormalItemAdapter(null, null, null, proInkList, null, "Boyalar", SeeAllActivity.this);


            mRecyclerView.setAdapter(mAdapter);


            mAdapter.notifyDataSetChanged();

        }

        if (From_intent.equals("Makinalar_ROLAND")) {
            rolandList.clear();
            mRecyclerView.removeAllViews();
            mAdapter = new NormalItemAdapter(rolandList, null, null, null, null, "Makinalar", SeeAllActivity.this);


            mRecyclerView.setAdapter(mAdapter);


            mAdapter.notifyDataSetChanged();
        }


    }
}