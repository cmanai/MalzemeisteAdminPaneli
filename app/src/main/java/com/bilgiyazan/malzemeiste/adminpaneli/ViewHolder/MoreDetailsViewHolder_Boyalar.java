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
import com.bilgiyazan.malzemeiste.adminpaneli.Activity.BoyalarDetailsActivity;
import com.bilgiyazan.malzemeiste.adminpaneli.Activity.EditBoyalarActivity;
import com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter.MoreDetailsAdapterModelBoyalar;
import com.bilgiyazan.malzemeiste.adminpaneli.R;
import com.bumptech.glide.Glide;

import mehdi.sakout.fancybuttons.FancyButton;

public class MoreDetailsViewHolder_Boyalar extends ChildViewHolder {


    TextView detail_more_text;
    View view_separator_detail_more_item;
    RelativeLayout header;
    RelativeLayout card_empty;
    FancyButton header_title;
    TextView boyalar_detail_title;
    TextView boyalar_detail_ref;
    TextView boyalar_detail_fiyat;
    TextView more_item_details_boyalar_ambalaj;
    ImageView boyalar_detail_image;
    SharedPreferences prefs;
    String Dollar_Rate;
    String Euro_Rate;
    RelativeLayout boyalar_more_detail_layout;

    MaterialDialog Delete_Dialog;

    ImageView more_item_details_boyalar_delete;
    ImageView more_item_details_boyalar_edit;
    private DatabaseReference mDatabase;

    public MoreDetailsViewHolder_Boyalar(@NonNull View itemView) {
        super(itemView);


        header = (RelativeLayout) itemView.findViewById(R.id.card_header);
        card_empty = (RelativeLayout) itemView.findViewById(R.id.card_empty);
        header_title = (FancyButton) itemView.findViewById(R.id.header_title_boyalar);
        boyalar_detail_title = (TextView) itemView.findViewById(R.id.more_item_details_boyalar_model);
        boyalar_detail_ref = (TextView) itemView.findViewById(R.id.more_item_details_boyalar_kod);
        boyalar_detail_fiyat = (TextView) itemView.findViewById(R.id.more_item_details_boyalar_fiyat);
        more_item_details_boyalar_ambalaj = (TextView) itemView.findViewById(R.id.more_item_details_boyalar_ambalaj);

        boyalar_detail_image = (ImageView) itemView.findViewById(R.id.boyalar_detail_image);
        boyalar_more_detail_layout = (RelativeLayout) itemView.findViewById(R.id.boyalar_more_detail_layout);

        more_item_details_boyalar_delete = (ImageView) itemView.findViewById(R.id.more_item_details_boyalar_delete);
        more_item_details_boyalar_edit = (ImageView) itemView.findViewById(R.id.more_item_details_boyalar_edit);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Boyalar");

    }

    public void bind(@NonNull final MoreDetailsAdapterModelBoyalar moreDetailsAdapterModelBoyalar, final Activity activity, String Type, int size, int position) {
        prefs = activity.getSharedPreferences("malzemeiste", Context.MODE_PRIVATE);
        Euro_Rate = prefs.getString("Euro_Rate", "");
        Dollar_Rate = prefs.getString("Dollar_Rate", "");

        if (moreDetailsAdapterModelBoyalar.getType().equals("Header")) {
            header.setVisibility(View.VISIBLE);
            boyalar_more_detail_layout.setVisibility(View.GONE);
            header_title.setText(moreDetailsAdapterModelBoyalar.getModel());
            card_empty.setVisibility(View.GONE);

        } else if (moreDetailsAdapterModelBoyalar.getType().equals("Empty")) {

            card_empty.setVisibility(View.VISIBLE);
            header.setVisibility(View.GONE);
            boyalar_more_detail_layout.setVisibility(View.GONE);

        } else {


            more_item_details_boyalar_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, EditBoyalarActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Model", moreDetailsAdapterModelBoyalar.getModel());
                    intent.putExtra("Image", moreDetailsAdapterModelBoyalar.getURL());
                    intent.putExtra("Kod", moreDetailsAdapterModelBoyalar.getKod());
                    intent.putExtra("Renk", moreDetailsAdapterModelBoyalar.getRenk());
                    intent.putExtra("Miktari", moreDetailsAdapterModelBoyalar.getMiktari());
                    intent.putExtra("Ambalaj", moreDetailsAdapterModelBoyalar.getAmbalaj_sekli());

                    intent.putExtra("Marka", moreDetailsAdapterModelBoyalar.getMarka());
                    intent.putExtra("Fiyat", boyalar_detail_fiyat.getText().toString());
                    intent.putExtra("Share_Link", moreDetailsAdapterModelBoyalar.getShare_Link());
                    intent.putExtra("Rate", moreDetailsAdapterModelBoyalar.getRate());
                    intent.putExtra("from", "more_boyalar");

                    activity.startActivity(intent);
                }
            });


            more_item_details_boyalar_delete.setOnClickListener(new View.OnClickListener() {
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
                                        public void onDataChange(DataSnapshot postSnapshot) {
                                            for (final DataSnapshot dataSnapshot : postSnapshot.getChildren()) {


                                                for (DataSnapshot dataSnapshot0 : dataSnapshot.getChildren()) {

                                                    for (DataSnapshot dataSnapshot2 : dataSnapshot0.getChildren()) {


                                                        for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {
                                                            Query ObjectKod1 = dataSnapshot3.getRef().orderByChild("Kod").equalTo(moreDetailsAdapterModelBoyalar.getKod());
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



                           /* Query ObjectKod = dataSnapshot.getRef().orderByChild("Kod").equalTo(moreDetailsAdapterModelBoyalar.getKod());
                            ObjectKod.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot tasksSnapshot) {
                                    for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                                      snapshot.getRef().removeValue();
                                        Log.e("result", snapshot.getValue() + "");
                                    }
                                }


                                @Override
                                public void onCancelled(DatabaseError firebaseError) {

                                }
                            });
*/


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


            more_item_details_boyalar_ambalaj.setText(moreDetailsAdapterModelBoyalar.getAmbalaj_sekli());


            boyalar_more_detail_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, BoyalarDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Model", moreDetailsAdapterModelBoyalar.getModel());
                    intent.putExtra("Image", moreDetailsAdapterModelBoyalar.getURL());
                    intent.putExtra("Kod", moreDetailsAdapterModelBoyalar.getKod());
                    intent.putExtra("Renk", moreDetailsAdapterModelBoyalar.getRenk());
                    intent.putExtra("Miktari", moreDetailsAdapterModelBoyalar.getMiktari());
                    intent.putExtra("Ambalaj", moreDetailsAdapterModelBoyalar.getAmbalaj_sekli());

                    intent.putExtra("Marka", moreDetailsAdapterModelBoyalar.getMarka());
                    intent.putExtra("Fiyat", boyalar_detail_fiyat.getText().toString());
                    intent.putExtra("Share_Link", moreDetailsAdapterModelBoyalar.getShare_Link());
                    intent.putExtra("Rate", moreDetailsAdapterModelBoyalar.getRate());
                    activity.startActivity(intent);
                }
            });
            if (!moreDetailsAdapterModelBoyalar.getURL().equals("------"))
                Glide.with(activity).load(moreDetailsAdapterModelBoyalar.getURL()).into(boyalar_detail_image);

            header.setVisibility(View.GONE);
            boyalar_more_detail_layout.setVisibility(View.VISIBLE);

            boyalar_detail_title.setText(moreDetailsAdapterModelBoyalar.getModel());
            boyalar_detail_ref.setText(moreDetailsAdapterModelBoyalar.getKod());

          /*  if (moreDetailsAdapterModelBoyalar.getRate().equals("Dollar")&&!Dollar_Rate.isEmpty()) {
                double result = Double.valueOf(Dollar_Rate) * Double.valueOf(moreDetailsAdapterModelBoyalar.getFiyat().substring(0, moreDetailsAdapterModelBoyalar.getFiyat().length() - 1));
                Locale Turkish = new Locale("tr", "TR");
                NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                String string_result = String.valueOf(numberFormatDutch.format(result));
                boyalar_detail_fiyat.setText(string_result);
            } else if (moreDetailsAdapterModelBoyalar.getRate().equals("Euro")&&!Euro_Rate.isEmpty()) {
                double result = Double.valueOf(Euro_Rate) * Double.valueOf(moreDetailsAdapterModelBoyalar.getFiyat().substring(0, moreDetailsAdapterModelBoyalar.getFiyat().length() - 1));
                Locale Turkish = new Locale("tr", "TR");
                NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                String string_result = String.valueOf(numberFormatDutch.format(result));
                boyalar_detail_fiyat.setText(string_result);


            } else {*/
            boyalar_detail_fiyat.setText(moreDetailsAdapterModelBoyalar.getFiyat());

            //}


        }

        /*    if(position==size-1){
                view_separator_detail_more_item.setVisibility(View.GONE);

            }*/
//            detail_more_text.setText(moreDetailsAdapterModel.getInfo());


    }


}


