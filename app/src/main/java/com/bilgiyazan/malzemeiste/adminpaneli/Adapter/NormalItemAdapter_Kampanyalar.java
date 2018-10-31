package com.bilgiyazan.malzemeiste.adminpaneli.Adapter;

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
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bilgiyazan.malzemeiste.adminpaneli.Activity.EditKampanyalarActivity;
import com.bilgiyazan.malzemeiste.adminpaneli.Activity.KampanyalarDetailsActivity;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Kampanyalar;
import com.bilgiyazan.malzemeiste.adminpaneli.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class NormalItemAdapter_Kampanyalar extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    List<Kampanyalar> kampanyalars;
    int Size;
    SharedPreferences prefs;
    String Dollar_Rate;
    String Euro_Rate;
    MaterialDialog Delete_Dialog;
    ImageView normal_item_details_kampanyalar_delete;
    ImageView normal_item_details_kampanyalar_edit;
    RelativeLayout card_empty;
    private int lastPosition = -1;
    private Activity activity;
    private DatabaseReference mDatabase;

    public NormalItemAdapter_Kampanyalar(List<Kampanyalar> kampanyalars, Activity activity) {
        prefs = activity.getSharedPreferences("malzemeiste", Context.MODE_PRIVATE);
        Euro_Rate = prefs.getString("Euro_Rate", "");
        Dollar_Rate = prefs.getString("Dollar_Rate", "");

        if (kampanyalars != null) {

            this.kampanyalars = kampanyalars;

            Size = kampanyalars.size();

        }

        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {


        return TYPE_HEADER;


    }

    @Override
    public int getItemCount() {
        return Size;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.normal_item_details_kampanyalar, parent, false);

        return new RecyclerView.ViewHolder(view) {
        };

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        CardView cardView = (CardView) holder.itemView.findViewById(R.id.normal_item_details_kampanyalar_cardview);
        RelativeLayout kampanyalar_layout = (RelativeLayout) holder.itemView.findViewById(R.id.normal_item_details_kampanyalar_layout);
        final TextView kampanyalar_fiyat = (TextView) holder.itemView.findViewById(R.id.normal_item_details_kampanyalar_fiyat);
        ImageView kampanyalar_image = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_kampanyalar_image);
        TextView kampanyalar_title = (TextView) holder.itemView.findViewById(R.id.normal_item_details_kampanyalar_title);
        TextView kampanyalar_ref = (TextView) holder.itemView.findViewById(R.id.normal_item_details_kampanyalar_ref);
        normal_item_details_kampanyalar_delete = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_kampanyalar_delete);
        normal_item_details_kampanyalar_edit = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_kampanyalar_edit);
        card_empty = (RelativeLayout) holder.itemView.findViewById(R.id.card_empty);


        if (kampanyalars != null) {
            //
            if (kampanyalars.get(position).getModel().equals("yok")) {
                Log.e("yok", "var");
                card_empty.setVisibility(View.VISIBLE);
                kampanyalar_layout.setVisibility(View.GONE);
                cardView.setCardElevation(0);
            } else {
                Log.e("yok", "yok");

                mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Kampanyalar");

                normal_item_details_kampanyalar_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, EditKampanyalarActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("Model", kampanyalars.get(position).getModel());
                        intent.putExtra("Image", kampanyalars.get(position).getURL());
                        intent.putExtra("Kod", kampanyalars.get(position).getKod());
                        intent.putExtra("Fiyat", kampanyalar_fiyat.getText().toString());
                        intent.putExtra("Share_Link", kampanyalars.get(position).getShare_Link());
                        activity.startActivity(intent);
                    }
                });
                normal_item_details_kampanyalar_delete.setOnClickListener(new View.OnClickListener() {
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
                                        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot postSnapshot) {
                                                for (final DataSnapshot dataSnapshot : postSnapshot.getChildren()) {


                                                    Query ObjectKod1 = dataSnapshot.getRef().orderByChild("Kod").equalTo(kampanyalars.get(position).getKod());
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
                kampanyalar_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, KampanyalarDetailsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("Model", kampanyalars.get(position).getModel());
                        intent.putExtra("Image", kampanyalars.get(position).getURL());
                        intent.putExtra("Kod", kampanyalars.get(position).getKod());
                        intent.putExtra("Fiyat", kampanyalar_fiyat.getText().toString());
                        intent.putExtra("Share_Link", kampanyalars.get(position).getShare_Link());
                        activity.startActivity(intent);
                    }
                });
                if (!kampanyalars.get(position).getURL().equals("------"))
                    Glide.with(activity).load(kampanyalars.get(position).getURL()).into(kampanyalar_image);
                kampanyalar_title.setText(kampanyalars.get(position).getModel());
                kampanyalar_ref.setText(kampanyalars.get(position).getKod());
       /*     if (corelDraws.get(position).getRate().equals("Dollar")&&!Dollar_Rate.isEmpty()) {
                double result = Double.valueOf(Dollar_Rate) * Double.valueOf(corelDraws.get(position).getFiyat().substring(0, corelDraws.get(position).getFiyat().length() - 1));
                Locale Turkish = new Locale("tr", "TR");
                NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                String string_result = String.valueOf(numberFormatDutch.format(result));
                kampanyalar_fiyat.setText(string_result);
            } else if (corelDraws.get(position).getRate().equals("Euro")&&!Euro_Rate.isEmpty()) {
                double result = Double.valueOf(Euro_Rate) * Double.valueOf(corelDraws.get(position).getFiyat().substring(0, corelDraws.get(position).getFiyat().length() - 1));
                Locale Turkish = new Locale("tr", "TR");
                NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                String string_result = String.valueOf(numberFormatDutch.format(result));
                kampanyalar_fiyat.setText(string_result);

            } else {*/
                kampanyalar_fiyat.setText(kampanyalars.get(position).getFiyat());

                // }

            }


            // kampanyalar_title.setText(contents.get(position));
            // kampanyalar_ref.setText(contents.get());

        }
    }

}