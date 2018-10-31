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

public class BoyalarDetailsActivity extends AppCompatActivity {
    private static final int ITEM_COUNT = 100;
    public static boolean GRID_LAYOUT = false;
    Toolbar toolbar;
    TextView Details_description_value;
    TextView Details_fiat_name;
    TextView Details_Marka_value;
    TextView Details_Kod_value;
    TextView Details_Miktari_value;
    TextView Details_Renk_value;
    TextView Details_Ambalaj_value;
    TextView Details_Renk_name;

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
        setContentView(R.layout.boyalar_details_layout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar_details);
        Details_description_value = (TextView) findViewById(R.id.Details_description_value);
        Details_fiat_name = (TextView) findViewById(R.id.Details_fiat_name);
        Details_Marka_value = (TextView) findViewById(R.id.Details_Marka_value);
        Details_Kod_value = (TextView) findViewById(R.id.Details_Kod_value);
        Details_Renk_value = (TextView) findViewById(R.id.Details_Renk_value);
        Details_Ambalaj_value = (TextView) findViewById(R.id.Details_Ambalaj_value);
        Details_Miktari_value = (TextView) findViewById(R.id.Details_Miktari_value);
        Details_Renk_name = (TextView) findViewById(R.id.Details_Renk_name);
        header_details = (ImageView) findViewById(R.id.header_details);
        delete_button = (FancyButton) findViewById(R.id.delete_button);
        edit_button = (FancyButton) findViewById(R.id.edit_button);
        setSupportActionBar(toolbar);
        prefs = BoyalarDetailsActivity.this.getSharedPreferences("malzemeiste", Context.MODE_PRIVATE);

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


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Boyalar");

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BoyalarDetailsActivity.this, EditBoyalarActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Model", getIntent().getStringExtra("Model"));
                intent.putExtra("Image", getIntent().getStringExtra("Image"));
                intent.putExtra("Kod", getIntent().getStringExtra("Kod"));
                intent.putExtra("Renk", getIntent().getStringExtra("Renk"));
                intent.putExtra("Miktari", getIntent().getStringExtra("Miktari"));
                intent.putExtra("Ambalaj", getIntent().getStringExtra("Ambalaj"));

                // intent.putExtra("Description",contentsMLD.get(position).getDescription());
                intent.putExtra("Marka", getIntent().getStringExtra("Marka"));
                intent.putExtra("Fiyat", getIntent().getStringExtra("Fiyat"));
                intent.putExtra("Share_Link", getIntent().getStringExtra("Share_Link"));

                startActivityForResult(intent, 0);

            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete_Dialog = new MaterialDialog.Builder(BoyalarDetailsActivity.this)
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

                                Toast.makeText(BoyalarDetailsActivity.this, "Ürün Silindi", Toast.LENGTH_SHORT).show();

                                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot postSnapshot) {
                                        for (final DataSnapshot dataSnapshot : postSnapshot.getChildren()) {


                                            for (DataSnapshot dataSnapshot0 : dataSnapshot.getChildren()) {

                                                for (DataSnapshot dataSnapshot2 : dataSnapshot0.getChildren()) {


                                                    for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {

                                                        Query ObjectKod1 = dataSnapshot3.getRef().orderByChild("Kod").equalTo(Kod);
                                                        ObjectKod1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot tasksSnapshot) {
                                                                for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                                                                    snapshot.getRef().removeValue();
                                                                    Toast.makeText(BoyalarDetailsActivity.this, "Ürün Silindi", Toast.LENGTH_SHORT).show();
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
                                            }
                                            Query ObjectKod1 = dataSnapshot.getRef().orderByChild("Kod").equalTo(Kod);
                                            ObjectKod1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot tasksSnapshot) {
                                                    for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                                                        snapshot.getRef().removeValue();
                                                        Toast.makeText(BoyalarDetailsActivity.this, "Ürün Silindi", Toast.LENGTH_SHORT).show();
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

        if (getIntent().getStringExtra("Renk").equals("")) {
            Details_Renk_name.setVisibility(View.INVISIBLE);
            Details_Renk_value.setVisibility(View.INVISIBLE);
        } else {
            Details_Renk_name.setVisibility(View.VISIBLE);
            Details_Renk_value.setVisibility(View.VISIBLE);
            Details_Renk_value.setText(getIntent().getStringExtra("Renk"));
        }

        Details_Miktari_value.setText(getIntent().getStringExtra("Miktari"));
        Details_Ambalaj_value.setText(getIntent().getStringExtra("Ambalaj"));

        Details_fiat_name.setText(getIntent().getStringExtra("Fiyat"));


        Details_Marka_value.setText(getIntent().getStringExtra("Marka"));
        Details_Kod_value.setText(getIntent().getStringExtra("Kod"));


        if (!getIntent().getStringExtra("Image").equals("------"))
            Glide.with(BoyalarDetailsActivity.this).load(getIntent().getStringExtra("Image")).into(header_details);

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

            if (getIntent().getStringExtra("Renk").equals("")) {
                Details_Renk_name.setVisibility(View.INVISIBLE);
                Details_Renk_value.setVisibility(View.INVISIBLE);
            } else {
                Details_Renk_name.setVisibility(View.VISIBLE);
                Details_Renk_value.setVisibility(View.VISIBLE);
                Details_Renk_value.setText(data.getStringExtra("Renk"));
            }

//            Details_Miktari_value.setText(data.getStringExtra("Miktari"));
//            Details_Miktari_value.setText(getIntent().getStringExtra("Miktari"));


            Details_Ambalaj_value.setText(data.getStringExtra("Ambalaj_sekli"));

            Details_fiat_name.setText(data.getStringExtra("Fiyat"));


            Details_Kod_value.setText(data.getStringExtra("Kod"));


        }
    }

}