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
import com.bilgiyazan.malzemeiste.adminpaneli.Activity.EditYedekActivity;
import com.bilgiyazan.malzemeiste.adminpaneli.Activity.YedekDetailsActivity;
import com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter.MoreDetailsAdapterModelYedek;
import com.bilgiyazan.malzemeiste.adminpaneli.R;
import com.bumptech.glide.Glide;

public class MoreDetailsViewHolder_Yedek extends ChildViewHolder {


    TextView yedek_detail_title;
    TextView yedek_detail_ref;
    TextView yedek_detail_fiyat;
    TextView more_item_details_yedek_kdv;
    TextView more_item_details_yedek_uyumlu;
    RelativeLayout card_empty;
    MaterialDialog Delete_Dialog;

    ImageView yedek_detail_image;
    SharedPreferences prefs;
    String Dollar_Rate;
    String Euro_Rate;
    RelativeLayout yedek_more_detail_layout;

    ImageView more_item_details_yedek_delete;
    ImageView more_item_details_yedek_edit;
    private DatabaseReference mDatabase;

    public MoreDetailsViewHolder_Yedek(@NonNull View itemView) {
        super(itemView);

        yedek_detail_title = (TextView) itemView.findViewById(R.id.more_item_details_yedek_model);
        yedek_detail_ref = (TextView) itemView.findViewById(R.id.more_item_details_yedek_kod);
        yedek_detail_fiyat = (TextView) itemView.findViewById(R.id.more_item_details_yedek_fiyat);
        more_item_details_yedek_kdv = (TextView) itemView.findViewById(R.id.more_item_details_yedek_kdv);
        more_item_details_yedek_uyumlu = (TextView) itemView.findViewById(R.id.more_item_details_yedek_uyumlu);
        card_empty = (RelativeLayout) itemView.findViewById(R.id.card_empty);

        yedek_detail_image = (ImageView) itemView.findViewById(R.id.yedek_detail_image);
        yedek_more_detail_layout = (RelativeLayout) itemView.findViewById(R.id.yedek_more_detail_layout);


        more_item_details_yedek_delete = (ImageView) itemView.findViewById(R.id.more_item_details_yedek_delete);
        more_item_details_yedek_edit = (ImageView) itemView.findViewById(R.id.more_item_details_yedek_edit);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Yedek_Parca");


    }

    public void bind(@NonNull final MoreDetailsAdapterModelYedek moreDetailsAdapterModelyedek, final Activity activity, String Type, int size, int position) {

        if (moreDetailsAdapterModelyedek.getType().equals("Empty")) {

            card_empty.setVisibility(View.VISIBLE);
            yedek_more_detail_layout.setVisibility(View.GONE);

        } else {

            prefs = activity.getSharedPreferences("malzemeiste", Context.MODE_PRIVATE);
            Euro_Rate = prefs.getString("Euro_Rate", "");
            Dollar_Rate = prefs.getString("Dollar_Rate", "");

            if (!moreDetailsAdapterModelyedek.getURL().equals("------"))
                Glide.with(activity).load(moreDetailsAdapterModelyedek.getURL()).into(yedek_detail_image);
            Log.e("URL", moreDetailsAdapterModelyedek.getURL());


            more_item_details_yedek_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, EditYedekActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Model", moreDetailsAdapterModelyedek.getModel());
                    intent.putExtra("Image", moreDetailsAdapterModelyedek.getURL());
                    intent.putExtra("Kod", moreDetailsAdapterModelyedek.getKod());

                    intent.putExtra("Uyumlu", moreDetailsAdapterModelyedek.getUyumlu());

                    intent.putExtra("Marka", moreDetailsAdapterModelyedek.getMarka());
                    intent.putExtra("Fiyat", yedek_detail_fiyat.getText().toString());
                    intent.putExtra("Share_Link", moreDetailsAdapterModelyedek.getShare_Link());
                    intent.putExtra("Rate", moreDetailsAdapterModelyedek.getRate());
                    activity.startActivity(intent);
                }
            });
            more_item_details_yedek_delete.setOnClickListener(new View.OnClickListener() {
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


                                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                                                    Query ObjectKod1 = dataSnapshot1.getRef().orderByChild("Kod").equalTo(moreDetailsAdapterModelyedek.getKod());
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

            yedek_more_detail_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, YedekDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Model", moreDetailsAdapterModelyedek.getModel());
                    intent.putExtra("Image", moreDetailsAdapterModelyedek.getURL());
                    intent.putExtra("Kod", moreDetailsAdapterModelyedek.getKod());

                    intent.putExtra("Uyumlu", moreDetailsAdapterModelyedek.getUyumlu());

                    intent.putExtra("Marka", moreDetailsAdapterModelyedek.getMarka());
                    intent.putExtra("Fiyat", yedek_detail_fiyat.getText().toString());
                    intent.putExtra("Share_Link", moreDetailsAdapterModelyedek.getShare_Link());
                    intent.putExtra("Rate", moreDetailsAdapterModelyedek.getRate());
                    activity.startActivity(intent);
                }
            });
            yedek_detail_title.setText(moreDetailsAdapterModelyedek.getModel());
            more_item_details_yedek_uyumlu.setText(moreDetailsAdapterModelyedek.getUyumlu());
            yedek_detail_ref.setText(moreDetailsAdapterModelyedek.getKod());


     /*   if (moreDetailsAdapterModelyedek.getRate().equals("Dollar")&&!Dollar_Rate.isEmpty()) {
            double result = Double.valueOf(Dollar_Rate) * Double.valueOf(moreDetailsAdapterModelyedek.getFiyat().substring(0, moreDetailsAdapterModelyedek.getFiyat().length() - 1));
            Locale Turkish = new Locale("tr", "TR");
            NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
            String string_result = String.valueOf(numberFormatDutch.format(result));
            yedek_detail_fiyat.setText(string_result);
        } else if (moreDetailsAdapterModelyedek.getRate().equals("Euro")&&!Euro_Rate.isEmpty()) {
            double result = Double.valueOf(Euro_Rate) * Double.valueOf(moreDetailsAdapterModelyedek.getFiyat().substring(0, moreDetailsAdapterModelyedek.getFiyat().length() - 1));
            Locale Turkish = new Locale("tr", "TR");
            NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
            String string_result = String.valueOf(numberFormatDutch.format(result));
            yedek_detail_fiyat.setText(string_result);

        } else {*/
            yedek_detail_fiyat.setText(moreDetailsAdapterModelyedek.getFiyat());

            //}


        }
    }


}


