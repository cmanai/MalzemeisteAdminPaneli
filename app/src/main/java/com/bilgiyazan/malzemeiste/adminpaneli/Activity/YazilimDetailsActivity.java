package com.bilgiyazan.malzemeiste.adminpaneli.Activity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bilgiyazan.malzemeiste.adminpaneli.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

public class YazilimDetailsActivity extends AppCompatActivity {
    private static final int ITEM_COUNT = 100;
    public static boolean GRID_LAYOUT = false;
    Toolbar toolbar;
    TextView Details_fiat_name;
    TextView Details_Marka_value;
    TextView Details_Kod_value;

    ImageView header_details;
    SharedPreferences prefs;
    String Dollar_Rate;
    String Euro_Rate;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FancyButton delete_button;
    FancyButton edit_button;
    MaterialDialog Delete_Dialog;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Object> mContentItems = new ArrayList<>();
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yazilim_details_layout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar_details);
        Details_fiat_name = (TextView) findViewById(R.id.Details_fiat_name);
        Details_Marka_value = (TextView) findViewById(R.id.Details_Marka_value);
        Details_Kod_value = (TextView) findViewById(R.id.Details_Kod_value);
        delete_button = (FancyButton) findViewById(R.id.delete_button);
        edit_button = (FancyButton) findViewById(R.id.edit_button);


        header_details = (ImageView) findViewById(R.id.header_details);

        setSupportActionBar(toolbar);
        prefs = YazilimDetailsActivity.this.getSharedPreferences("malzemeiste", Context.MODE_PRIVATE);

        Euro_Rate = prefs.getString("Euro_Rate", "");
        Dollar_Rate = prefs.getString("Dollar_Rate", "");
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
        toolbar.setTitle(" ");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Yazilim");

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(YazilimDetailsActivity.this, EditYazilimlarActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                intent.putExtra("Model", getIntent().getStringExtra("Model"));
                intent.putExtra("Image", getIntent().getStringExtra("Image"));
                intent.putExtra("Kod", getIntent().getStringExtra("Kod"));
                intent.putExtra("Marka", getIntent().getStringExtra("Marka"));
                intent.putExtra("Fiyat", getIntent().getStringExtra("Fiyat"));
                intent.putExtra("Share_Link", getIntent().getStringExtra("Share_Link"));
                startActivityForResult(intent, 0);


            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete_Dialog = new MaterialDialog.Builder(YazilimDetailsActivity.this)
                        .title("Bu ürünü silinsin mi?")
                        .icon(getResources().getDrawable(R.drawable.icon_malzemeiste))
                        .limitIconToDefaultSize()
                        .content("Bu ürünü sildikten sonra geri alınamaz.")
                        .backgroundColor(getResources().getColor(R.color.md_white_1000))
                        .titleColor(getResources().getColor(R.color.md_black_1000))
                        .contentColor(Color.parseColor("#80000000"))

                        .positiveText(getResources().getString(R.string.agree))
                        .negativeText(getResources().getString(R.string.cancel))

                        .positiveColor(getResources().getColor(R.color.primary))
                        .negativeColor(getResources().getColor(R.color.primary))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                final String Kod = getIntent().getStringExtra("Kod");

                                Toast.makeText(YazilimDetailsActivity.this, "Ürün Silindi", Toast.LENGTH_SHORT).show();

                                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot postSnapshot) {
                                        for (final DataSnapshot dataSnapshot : postSnapshot.getChildren()) {


                                            Query ObjectKod1 = dataSnapshot.getRef().orderByChild("Kod").equalTo(Kod);
                                            ObjectKod1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot tasksSnapshot) {
                                                    for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                                                        snapshot.getRef().removeValue();
                                                        Toast.makeText(YazilimDetailsActivity.this, "Ürün Silindi", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                        Log.e("result", snapshot.getValue() + "");
                                                    }
                                                }


                                                @Override
                                                public void onCancelled(DatabaseError firebaseError) {
                                                    System.out.println("The read failed: " + firebaseError.getMessage());
                                                }
                                            });


                                        }


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                            }
                        })


                        .build();

                Delete_Dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {

                    }
                });
                Delete_Dialog.show();


            }
        });


        // Details_description_value.setText(getIntent().getStringExtra("Description"));
        if (getIntent().getStringExtra("Model").length() > 25) {

            collapsingToolbarLayout.setTitle(getIntent().getStringExtra("Model").substring(0, 24) + "...");
        } else {
            collapsingToolbarLayout.setTitle(getIntent().getStringExtra("Model"));
        }

        Details_fiat_name.setText(getIntent().getStringExtra("Fiyat"));


        Details_Marka_value.setText(getIntent().getStringExtra("Marka"));

        Details_Kod_value.setText(getIntent().getStringExtra("Kod"));


        if (!getIntent().getStringExtra("Image").equals("------"))
            Glide.with(YazilimDetailsActivity.this).load(getIntent().getStringExtra("Image")).into(header_details);

        RecyclerView.LayoutManager layoutManager;

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data.getStringExtra("Model").length() > 25) {

                collapsingToolbarLayout.setTitle(data.getStringExtra("Model").substring(0, 24) + "...");
            } else {
                collapsingToolbarLayout.setTitle(data.getStringExtra("Model"));
            }

            Details_fiat_name.setText(data.getStringExtra("Fiyat"));



            Details_Kod_value.setText(data.getStringExtra("Kod"));



    }

}

}