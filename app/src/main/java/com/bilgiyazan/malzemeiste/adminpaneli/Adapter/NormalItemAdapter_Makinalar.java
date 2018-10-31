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
import com.bilgiyazan.malzemeiste.adminpaneli.Activity.EditMakinalarActivity;
import com.bilgiyazan.malzemeiste.adminpaneli.Activity.MakinalarDetailsActivity;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.A_Starjet;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.OKI;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Roland;
import com.bilgiyazan.malzemeiste.adminpaneli.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class NormalItemAdapter_Makinalar extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    List<OKI> contentsOkis;
    List<Roland> contentsRolands;
    List<A_Starjet> contentsA_starjets;
    int Size;
    SharedPreferences prefs;
    String Dollar_Rate;
    String Euro_Rate;
    TextView makinalar_fiyat;
    ImageView normal_item_details_makinalar_delete;
    ImageView normal_item_details_makinalar_edit;
    RelativeLayout card_empty;
    private int lastPosition = -1;
    private Activity activity;
    private DatabaseReference mDatabase;
    MaterialDialog Delete_Dialog;

    public NormalItemAdapter_Makinalar(List<OKI> contentsOkis, List<Roland> contentsRolands, List<A_Starjet> contentsA_starjets, Activity activity) {
        prefs = activity.getSharedPreferences("malzemeiste", Context.MODE_PRIVATE);
        Euro_Rate = prefs.getString("Euro_Rate", "");
        Dollar_Rate = prefs.getString("Dollar_Rate", "");

        this.contentsRolands = contentsRolands;
        Log.e("roland size", contentsRolands.size() + "");
        Size = contentsRolands.size();


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
                .inflate(R.layout.normal_item_details_makinalar, parent, false);

        return new RecyclerView.ViewHolder(view) {
        };

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        CardView cardView = (CardView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_cardview);
        RelativeLayout makinalar_layout = (RelativeLayout) holder.itemView.findViewById(R.id.normal_item_details_makinalar_layout);
        makinalar_fiyat = (TextView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_fiyat);
        ImageView makinalar_image = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_image);
        TextView makinalar_title = (TextView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_title);
        TextView makinalar_ref = (TextView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_ref);
        normal_item_details_makinalar_delete = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_delete);
        normal_item_details_makinalar_edit = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_edit);
        card_empty = (RelativeLayout) holder.itemView.findViewById(R.id.card_empty);

        TextView more_item_details_yedek_kdv = (TextView) holder.itemView.findViewById(R.id.more_item_details_makinalar_kdv);

        if (!contentsRolands.isEmpty()) {


            if (contentsRolands.get(position).getModel().equals("yok")) {
                Log.e("yok", "var");
                card_empty.setVisibility(View.VISIBLE);
                makinalar_layout.setVisibility(View.GONE);
                cardView.setCardElevation(0);
            } else {
                Log.e("yok", "yok");

                mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Makinalar");

                normal_item_details_makinalar_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, EditMakinalarActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        intent.putExtra("Model", contentsRolands.get(position).getModel());
                        intent.putExtra("Image", contentsRolands.get(position).getURL());
                        intent.putExtra("Kod", contentsRolands.get(position).getKod());
                        intent.putExtra("Description", contentsRolands.get(position).getDescription());
                        intent.putExtra("Marka", "Roland");
                        intent.putExtra("Fiyat", contentsRolands.get(position).getFiyat());
                        intent.putExtra("Share_Link", contentsRolands.get(position).getShare_Link());
                        intent.putExtra("from", "normal_makinalar");
                        activity.startActivity(intent);

                    }
                });
                normal_item_details_makinalar_delete.setOnClickListener(new View.OnClickListener() {
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


                                                    Query ObjectKod1 = dataSnapshot.getRef().orderByChild("Kod").equalTo(contentsRolands.get(position).getKod());
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
                more_item_details_yedek_kdv.setText("+ KDV ");

                makinalar_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, MakinalarDetailsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        intent.putExtra("Model", contentsRolands.get(position).getModel());
                        intent.putExtra("Image", contentsRolands.get(position).getURL());
                        intent.putExtra("Kod", contentsRolands.get(position).getKod());
                        intent.putExtra("Description", contentsRolands.get(position).getDescription());
                        intent.putExtra("Marka", "Roland");
                        intent.putExtra("Fiyat", contentsRolands.get(position).getFiyat());
                        intent.putExtra("Share_Link", contentsRolands.get(position).getShare_Link());
                        activity.startActivity(intent);
                    }
                });

                if (!contentsRolands.get(position).getURL().equals("------"))
                    Glide.with(activity).load(contentsRolands.get(position).getURL()).into(makinalar_image);
                makinalar_title.setText(contentsRolands.get(position).getModel());
                makinalar_ref.setText(contentsRolands.get(position).getKod());
        /*    if (contentsRolands.get(position).getRate().equals("Dollar")&&!Dollar_Rate.isEmpty()) {
                double result;

                    result = Double.valueOf(Dollar_Rate) * Double.valueOf(contentsRolands.get(position).getFiyat().substring(0, contentsRolands.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    makinalar_fiyat.setText(string_result);



            } else if (contentsRolands.get(position).getRate().equals("Euro")&&!Euro_Rate.isEmpty()) {

                double result;

                    result = Double.valueOf(Euro_Rate) * Double.valueOf(contentsRolands.get(position).getFiyat().substring(0, contentsRolands.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    makinalar_fiyat.setText(string_result);


            } else {*/
                makinalar_fiyat.setText(contentsRolands.get(position).getFiyat());

                // }

            }
        }


        // makinalar_title.setText(contents.get(position));
        // makinalar_ref.setText(contents.get());

    }


}