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
import com.bilgiyazan.malzemeiste.adminpaneli.Activity.EditMakinalarActivity;
import com.bilgiyazan.malzemeiste.adminpaneli.Activity.MakinalarDetailsActivity;
import com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter.MoreDetailsAdapterModelMakinalar;
import com.bilgiyazan.malzemeiste.adminpaneli.R;
import com.bumptech.glide.Glide;

public class MoreDetailsViewHolder_Makinalar extends ChildViewHolder {


    TextView makinalar_detail_title;
    TextView makinalar_detail_ref;
    TextView makinalar_detail_fiyat;
    RelativeLayout card_empty;
    MaterialDialog Delete_Dialog;
    ImageView makinalar_detail_image;
    SharedPreferences prefs;
    String Dollar_Rate;
    String Euro_Rate;
    RelativeLayout makinalar_more_detail_layout;
    ImageView more_item_details_makinalar_delete;
    ImageView more_item_details_makinalar_edit;
    private DatabaseReference mDatabase;

    public MoreDetailsViewHolder_Makinalar(@NonNull View itemView) {
        super(itemView);

        makinalar_detail_title = (TextView) itemView.findViewById(R.id.more_item_details_makinalar_model);
        makinalar_detail_ref = (TextView) itemView.findViewById(R.id.more_item_details_makinalar_kod);
        makinalar_detail_fiyat = (TextView) itemView.findViewById(R.id.more_item_details_makinalar_fiyat);
        more_item_details_makinalar_delete = (ImageView) itemView.findViewById(R.id.more_item_details_makinalar_delete);
        more_item_details_makinalar_edit = (ImageView) itemView.findViewById(R.id.more_item_details_makinalar_edit);
        card_empty = (RelativeLayout) itemView.findViewById(R.id.card_empty);

        makinalar_detail_image = (ImageView) itemView.findViewById(R.id.makinalar_detail_image);
        makinalar_more_detail_layout = (RelativeLayout) itemView.findViewById(R.id.makinalar_more_detail_layout);

    }

    public void bind(@NonNull final MoreDetailsAdapterModelMakinalar moreDetailsAdapterModelMakinalar, final Activity activity, String Type, int size, int position) {


        Log.e("Makinalar type", moreDetailsAdapterModelMakinalar.getType());
        if (moreDetailsAdapterModelMakinalar.getType().equals("Empty")) {

            card_empty.setVisibility(View.VISIBLE);
            makinalar_more_detail_layout.setVisibility(View.GONE);

        } else {


            makinalar_more_detail_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, MakinalarDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Model", moreDetailsAdapterModelMakinalar.getModel());
                    intent.putExtra("Image", moreDetailsAdapterModelMakinalar.getURL());
                    intent.putExtra("Kod", moreDetailsAdapterModelMakinalar.getKod());
                    intent.putExtra("Description", moreDetailsAdapterModelMakinalar.getDescription());
                    intent.putExtra("Marka", moreDetailsAdapterModelMakinalar.getMarka());
                    intent.putExtra("Fiyat", makinalar_detail_fiyat.getText().toString());
                    intent.putExtra("Share_Link", moreDetailsAdapterModelMakinalar.getShare_Link());
                    intent.putExtra("Rate", moreDetailsAdapterModelMakinalar.getRate());
                    activity.startActivity(intent);
                }
            });
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Makinalar");

            more_item_details_makinalar_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, EditMakinalarActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Model", moreDetailsAdapterModelMakinalar.getModel());
                    intent.putExtra("Image", moreDetailsAdapterModelMakinalar.getURL());
                    intent.putExtra("Kod", moreDetailsAdapterModelMakinalar.getKod());
                    intent.putExtra("Description", moreDetailsAdapterModelMakinalar.getDescription());
                    intent.putExtra("Marka", moreDetailsAdapterModelMakinalar.getMarka());
                    intent.putExtra("Fiyat", makinalar_detail_fiyat.getText().toString());
                    intent.putExtra("Share_Link", moreDetailsAdapterModelMakinalar.getShare_Link());
                    intent.putExtra("Rate", moreDetailsAdapterModelMakinalar.getRate());
                    intent.putExtra("from", "more_makinalar");

                    activity.startActivity(intent);
                }
            });
            more_item_details_makinalar_delete.setOnClickListener(new View.OnClickListener() {
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


                                                    Query ObjectKod1 = dataSnapshot1.getRef().orderByChild("Kod").equalTo(moreDetailsAdapterModelMakinalar.getKod());
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



                           /* Query ObjectKod = dataSnapshot.getRef().orderByChild("Kod").equalTo(moreDetailsAdapterModelMakinalar.getKod());
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
            prefs = activity.getSharedPreferences("malzemeiste", Context.MODE_PRIVATE);
            Euro_Rate = prefs.getString("Euro_Rate", "");
            Dollar_Rate = prefs.getString("Dollar_Rate", "");

            if (!moreDetailsAdapterModelMakinalar.getURL().equals("------"))
                Glide.with(activity).load(moreDetailsAdapterModelMakinalar.getURL()).into(makinalar_detail_image);
            Log.e("URL", moreDetailsAdapterModelMakinalar.getURL());


            makinalar_detail_title.setText(moreDetailsAdapterModelMakinalar.getModel());
            makinalar_detail_ref.setText(moreDetailsAdapterModelMakinalar.getKod());


      /*  if (moreDetailsAdapterModelMakinalar.getRate().equals("Dollar")&&!Dollar_Rate.isEmpty()) {
            double result = Double.valueOf(Dollar_Rate) * Double.valueOf(moreDetailsAdapterModelMakinalar.getFiyat().substring(0, moreDetailsAdapterModelMakinalar.getFiyat().length() - 1));
            Locale Turkish = new Locale("tr", "TR");
            NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
            String string_result = String.valueOf(numberFormatDutch.format(result));
            makinalar_detail_fiyat.setText(string_result);
        } else if (moreDetailsAdapterModelMakinalar.getRate().equals("Euro")&&!Euro_Rate.isEmpty()) {
            double result = Double.valueOf(Euro_Rate) * Double.valueOf(moreDetailsAdapterModelMakinalar.getFiyat().substring(0, moreDetailsAdapterModelMakinalar.getFiyat().length() - 1));
            Locale Turkish = new Locale("tr", "TR");
            NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
            String string_result = String.valueOf(numberFormatDutch.format(result));
            makinalar_detail_fiyat.setText(string_result);

        } else {*/
            makinalar_detail_fiyat.setText(moreDetailsAdapterModelMakinalar.getFiyat());

            //}


        }

    }
}


