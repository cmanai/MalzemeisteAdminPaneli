package com.bilgiyazan.malzemeiste.adminpaneli.ViewHolder;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bilgiyazan.malzemeiste.adminpaneli.Activity.EditMalzemeActivity;
import com.bilgiyazan.malzemeiste.adminpaneli.Activity.MalzemeDetailsActivity;
import com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter.MoreDetailsAdapterModelMalzeme;
import com.bilgiyazan.malzemeiste.adminpaneli.R;
import com.bumptech.glide.Glide;

import mehdi.sakout.fancybuttons.FancyButton;

public class MoreDetailsViewHolder_Malzeme extends ChildViewHolder {


    TextView detail_more_text;
    View view_separator_detail_more_item;
    RelativeLayout header;
    FancyButton header_title;
    TextView malzeme_detail_title;
    TextView malzeme_detail_ref;
    TextView malzeme_detail_fiyat;
    TextView more_item_details_malzeme_renk;
    RelativeLayout card_empty;

    ImageView malzeme_detail_image;
    SharedPreferences prefs;
    String Dollar_Rate;
    String Euro_Rate;
    RelativeLayout malzeme_more_detail_layout;
    MaterialDialog Delete_Dialog;


    ImageView more_item_details_malzeme_delete;
    ImageView more_item_details_malzeme_edit;
    private DatabaseReference mDatabase;

    public MoreDetailsViewHolder_Malzeme(@NonNull View itemView) {
        super(itemView);


        header = (RelativeLayout) itemView.findViewById(R.id.card_header);
        header_title = (FancyButton) itemView.findViewById(R.id.header_title_malzeme);
        malzeme_detail_title = (TextView) itemView.findViewById(R.id.more_item_details_malzeme_model);
        malzeme_detail_ref = (TextView) itemView.findViewById(R.id.more_item_details_malzeme_kod);
        malzeme_detail_fiyat = (TextView) itemView.findViewById(R.id.more_item_details_malzeme_fiyat);
        more_item_details_malzeme_renk = (TextView) itemView.findViewById(R.id.more_item_details_malzeme_renk);
        card_empty = (RelativeLayout) itemView.findViewById(R.id.card_empty);

        malzeme_detail_image = (ImageView) itemView.findViewById(R.id.malzeme_detail_image);
        malzeme_more_detail_layout = (RelativeLayout) itemView.findViewById(R.id.malzeme_more_detail_layout);

        more_item_details_malzeme_delete = (ImageView) itemView.findViewById(R.id.more_item_details_malzeme_delete);
        more_item_details_malzeme_edit = (ImageView) itemView.findViewById(R.id.more_item_details_malzeme_edit);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Malzeme");

    }

    public void bind(@NonNull final MoreDetailsAdapterModelMalzeme moreDetailsAdapterModelmalzeme, final Activity activity, String Type, int size, int position) {
        prefs = activity.getSharedPreferences("malzemeiste", Context.MODE_PRIVATE);
        Euro_Rate = prefs.getString("Euro_Rate", "");
        Dollar_Rate = prefs.getString("Dollar_Rate", "");

        if (moreDetailsAdapterModelmalzeme.getType().equals("Header")) {
            header.setVisibility(View.VISIBLE);
            malzeme_more_detail_layout.setVisibility(View.GONE);
            card_empty.setVisibility(View.GONE);

            header_title.setText(moreDetailsAdapterModelmalzeme.getModel());
        } else if (moreDetailsAdapterModelmalzeme.getType().equals("Empty")) {

            card_empty.setVisibility(View.VISIBLE);
            header.setVisibility(View.GONE);
            malzeme_more_detail_layout.setVisibility(View.GONE);

        } else {


            more_item_details_malzeme_renk.setText(moreDetailsAdapterModelmalzeme.getrenk());


            more_item_details_malzeme_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, EditMalzemeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    intent.putExtra("Model", moreDetailsAdapterModelmalzeme.getModel());
                    intent.putExtra("Image", moreDetailsAdapterModelmalzeme.getURL());
                    intent.putExtra("Kod", moreDetailsAdapterModelmalzeme.getKod());
                    intent.putExtra("Marka", moreDetailsAdapterModelmalzeme.getMarka());
                    intent.putExtra("Fiyat", malzeme_detail_fiyat.getText().toString());
                    intent.putExtra("Renk", moreDetailsAdapterModelmalzeme.getRenk());
                    intent.putExtra("Miktari", moreDetailsAdapterModelmalzeme.getMiktari());
                    intent.putExtra("Share_Link", moreDetailsAdapterModelmalzeme.getShare_Link());
                    intent.putExtra("Rate", moreDetailsAdapterModelmalzeme.getRate());
                    activity.startActivity(intent);
                }
            });
            more_item_details_malzeme_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Delete_Dialog = new MaterialDialog.Builder(activity)
                            .title("Bu ürünü silinsin mi?")
                            .icon(activity.getResources().getDrawable(R.drawable.icon_malzemeiste))
                            .limitIconToDefaultSize()
                            .content("Bu ürünü sildikten sonra geri alınamaz.")
                            .backgroundColor(activity.getResources().getColor(R.color.md_white_1000))
                            .titleColor(activity.getResources().getColor(R.color.md_black_1000))
                            .contentColor(Color.parseColor("#80000000"))

                            .positiveText(activity.getResources().getString(R.string.agree))
                            .negativeText(activity.getResources().getString(R.string.cancel))

                            .positiveColor(activity.getResources().getColor(R.color.primary))
                            .negativeColor(activity.getResources().getColor(R.color.primary))
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                    Delete_Dialog.dismiss();
                                    mDatabase.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot dataSnapshot0 : dataSnapshot.getChildren()) {

                                                for (DataSnapshot dataSnapshot2 : dataSnapshot0.getChildren()) {


                                                    for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {
                                                        for (DataSnapshot dataSnapshot4 : dataSnapshot3.getChildren()) {
                                                            Log.e("result", dataSnapshot4.getValue() + "");

                                                            Query ObjectKod1 = dataSnapshot4.getRef().orderByChild("Kod").equalTo(moreDetailsAdapterModelmalzeme.getKod());
                                                            ObjectKod1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(DataSnapshot tasksSnapshot) {
                                                                    for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                                                                        //snapshot.getRef().child("Model").setValue("xyzsdsd");
                                                                        snapshot.getRef().removeValue();
                                                                        Toast.makeText(activity, "Ürün Silindi", Toast.LENGTH_SHORT).show();

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


            malzeme_more_detail_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, MalzemeDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    intent.putExtra("Model", moreDetailsAdapterModelmalzeme.getModel());
                    intent.putExtra("Image", moreDetailsAdapterModelmalzeme.getURL());
                    intent.putExtra("Kod", moreDetailsAdapterModelmalzeme.getKod());
                    intent.putExtra("Marka", moreDetailsAdapterModelmalzeme.getMarka());
                    intent.putExtra("Fiyat", malzeme_detail_fiyat.getText().toString());
                    intent.putExtra("Renk", moreDetailsAdapterModelmalzeme.getRenk());
                    intent.putExtra("Miktari", moreDetailsAdapterModelmalzeme.getMiktari());
                    intent.putExtra("Share_Link", moreDetailsAdapterModelmalzeme.getShare_Link());
                    intent.putExtra("Rate", moreDetailsAdapterModelmalzeme.getRate());
                    activity.startActivity(intent);
                }
            });
            if (!moreDetailsAdapterModelmalzeme.getURL().equals("------"))
                Glide.with(activity).load(moreDetailsAdapterModelmalzeme.getURL()).into(malzeme_detail_image);

            header.setVisibility(View.GONE);
            malzeme_more_detail_layout.setVisibility(View.VISIBLE);

            malzeme_detail_title.setText(moreDetailsAdapterModelmalzeme.getModel());
            malzeme_detail_ref.setText(moreDetailsAdapterModelmalzeme.getKod());

         /*   if (moreDetailsAdapterModelmalzeme.getRate().equals("Dollar")&&!Dollar_Rate.isEmpty()) {
                double result = Double.valueOf(Dollar_Rate) * Double.valueOf(moreDetailsAdapterModelmalzeme.getFiyat().substring(0, moreDetailsAdapterModelmalzeme.getFiyat().length() - 1));
                Locale Turkish = new Locale("tr", "TR");
                NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                String string_result = String.valueOf(numberFormatDutch.format(result));
                malzeme_detail_fiyat.setText(string_result);
            } else if (moreDetailsAdapterModelmalzeme.getRate().equals("Euro")&&!Euro_Rate.isEmpty()) {
                double result = Double.valueOf(Euro_Rate) * Double.valueOf(moreDetailsAdapterModelmalzeme.getFiyat().substring(0, moreDetailsAdapterModelmalzeme.getFiyat().length() - 1));
                Locale Turkish = new Locale("tr", "TR");
                NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                String string_result = String.valueOf(numberFormatDutch.format(result));
                malzeme_detail_fiyat.setText(string_result);


            } else {*/
            malzeme_detail_fiyat.setText(moreDetailsAdapterModelmalzeme.getFiyat());

            // }


        }

        /*    if(position==size-1){
                view_separator_detail_more_item.setVisibility(View.GONE);

            }*/
//            detail_more_text.setText(moreDetailsAdapterModel.getInfo());


    }


}


