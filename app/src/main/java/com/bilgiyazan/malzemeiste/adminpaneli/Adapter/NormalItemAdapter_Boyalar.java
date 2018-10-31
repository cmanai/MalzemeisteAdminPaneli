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
import com.bilgiyazan.malzemeiste.adminpaneli.Activity.BoyalarDetailsActivity;
import com.bilgiyazan.malzemeiste.adminpaneli.Activity.EditBoyalarActivity;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Emerald;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.MLD;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Pro_ink;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Triangle;
import com.bilgiyazan.malzemeiste.adminpaneli.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */

public class NormalItemAdapter_Boyalar extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    MaterialDialog Delete_Dialog;
    ImageView normal_item_details_boyalar_delete;
    ImageView normal_item_details_boyalar_edit;
    RelativeLayout card_empty;
    private List<Emerald> contentsEmerald;
    private List<MLD> contentsMLD;
    private List<Pro_ink> contentsPro_ink;
    private List<Triangle> contentsTriangle;
    private int Size;
    private SharedPreferences prefs;
    private String Dollar_Rate;
    private String Euro_Rate;
    private int lastPosition = -1;
    private Activity activity;
    private int pos;
    private DatabaseReference mDatabase;


    NormalItemAdapter_Boyalar(List<Emerald> contentsEmerald, List<MLD> contentsMLD, List<Pro_ink> contentsPro_ink, List<Triangle> contentsTriangle, Activity activity) {
        prefs = activity.getSharedPreferences("malzemeiste", Context.MODE_PRIVATE);
        Euro_Rate = prefs.getString("Euro_Rate", "");
        Dollar_Rate = prefs.getString("Dollar_Rate", "");
        if (contentsEmerald != null) {
            this.contentsEmerald = contentsEmerald;
            Log.e("Pro_ink size", contentsEmerald.size() + "");
            Size = contentsEmerald.size();
        }
        if (contentsMLD != null) {

            this.contentsMLD = contentsMLD;
            Log.e("MLD size", contentsMLD.size() + "");
            Size = contentsMLD.size();

        }
        if (contentsPro_ink != null) {
            this.contentsPro_ink = contentsPro_ink;
            Log.e("Pro_ink size", contentsPro_ink.size() + "");
            Size = contentsPro_ink.size();


        }

        if (contentsTriangle != null) {
            this.contentsTriangle = contentsTriangle;
            Log.e("Triangle size", contentsTriangle.size() + "");
            Size = contentsTriangle.size();


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
                .inflate(R.layout.normal_item_details_boyalar, parent, false);

        return new RecyclerView.ViewHolder(view) {
        };

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        CardView cardView = (CardView) holder.itemView.findViewById(R.id.normal_item_details_boyalar_cardview);
        RelativeLayout boyalar_layout = (RelativeLayout) holder.itemView.findViewById(R.id.normal_item_details_boyalar_layout);
        final TextView boyalar_fiyat = (TextView) holder.itemView.findViewById(R.id.normal_item_details_boyalar_fiyat);
        ImageView boyalar_image = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_boyalar_image);
        TextView boyalar_title = (TextView) holder.itemView.findViewById(R.id.normal_item_details_boyalar_title);
        TextView boyalar_ref = (TextView) holder.itemView.findViewById(R.id.normal_item_details_boyalar_ref);
        TextView normal_item_details_boyalar_ambalaj = (TextView) holder.itemView.findViewById(R.id.normal_item_details_boyalar_ambalaj);
        normal_item_details_boyalar_delete = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_boyalar_delete);
        normal_item_details_boyalar_edit = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_boyalar_edit);
        card_empty = (RelativeLayout) holder.itemView.findViewById(R.id.card_empty);
        TextView boyalar_detail_share;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Boyalar");

        pos = holder.getAdapterPosition();

        if (contentsEmerald != null) {

            if (contentsEmerald.get(position).getModel().equals("yok")) {
                Log.e("yok", "var");
                card_empty.setVisibility(View.VISIBLE);
                boyalar_layout.setVisibility(View.GONE);
                cardView.setCardElevation(0);
            } else {


                normal_item_details_boyalar_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, EditBoyalarActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("Model", contentsEmerald.get(position).getModel());
                        intent.putExtra("Image", contentsEmerald.get(position).getURL());
                        intent.putExtra("Kod", contentsEmerald.get(position).getKod());
                        intent.putExtra("Renk", contentsEmerald.get(position).getRenk());
                        intent.putExtra("Miktari", contentsEmerald.get(position).getMiktari());
                        intent.putExtra("Ambalaj", contentsEmerald.get(position).getAmbalaj_sekli());

                        // intent.putExtra("Description",contentsMLD.get(position).getDescription());
                        intent.putExtra("Marka", "MLD");
                        intent.putExtra("Fiyat", boyalar_fiyat.getText().toString());
                        intent.putExtra("Share_Link", contentsEmerald.get(position).getShare_Link());
                        intent.putExtra("from", "normal_boyalar");

                        activity.startActivity(intent);
                    }
                });
                normal_item_details_boyalar_delete.setOnClickListener(new View.OnClickListener() {
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


                                                    Query ObjectKod1 = dataSnapshot.getRef().orderByChild("Kod").equalTo(contentsEmerald.get(position).getKod());
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
                normal_item_details_boyalar_ambalaj.setText(contentsEmerald.get(position).getAmbalaj_sekli());


                boyalar_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, BoyalarDetailsActivity.class);

                        intent.putExtra("Model", contentsEmerald.get(position).getModel());
                        intent.putExtra("Image", contentsEmerald.get(position).getURL());
                        intent.putExtra("Kod", contentsEmerald.get(position).getKod());
                        intent.putExtra("Renk", contentsEmerald.get(position).getRenk());
                        intent.putExtra("Miktari", contentsEmerald.get(position).getMiktari());
                        intent.putExtra("Ambalaj", contentsEmerald.get(position).getAmbalaj_sekli());
                        //  intent.putExtra("Description",contentsEmerald.get(position).getDescription());
                        intent.putExtra("Marka", "Emerald");
                        intent.putExtra("Fiyat", boyalar_fiyat.getText().toString());
                        intent.putExtra("Share_Link", contentsEmerald.get(position).getShare_Link());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        activity.startActivity(intent);
                    }
                });

                boyalar_title.setText(contentsEmerald.get(position).getModel());
                boyalar_ref.setText(contentsEmerald.get(position).getKod());
                Glide.with(activity).load(contentsEmerald.get(position).getURL()).into(boyalar_image);
        /*    if (contentsEmerald.get(position).getRate().equals("Dollar")&&!Dollar_Rate.isEmpty()) {

                double result;


                     result = Double.valueOf(Dollar_Rate) * Double.valueOf(contentsEmerald.get(position).getFiyat().substring(0, contentsEmerald.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    boyalar_fiyat.setText(string_result);


        } else if (contentsEmerald.get(position).getRate().equals("Euro")&&!Euro_Rate.isEmpty()) {
                double result;



                    result = Double.valueOf(Euro_Rate) * Double.valueOf(contentsEmerald.get(position).getFiyat().substring(0, contentsEmerald.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    boyalar_fiyat.setText(string_result);



            } else {*/
                boyalar_fiyat.setText(contentsEmerald.get(position).getFiyat());

                // }

            }
        } else if (contentsMLD != null) {


            if (contentsMLD.get(position).getModel().equals("yok")) {
                Log.e("yok", "var");
                card_empty.setVisibility(View.VISIBLE);
                boyalar_layout.setVisibility(View.GONE);
                cardView.setCardElevation(0);
            } else {


                normal_item_details_boyalar_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, EditBoyalarActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("Model", contentsMLD.get(position).getModel());
                        intent.putExtra("Image", contentsMLD.get(position).getURL());
                        intent.putExtra("Kod", contentsMLD.get(position).getKod());
                        intent.putExtra("Renk", contentsMLD.get(position).getRenk());
                        intent.putExtra("Miktari", contentsMLD.get(position).getMiktari());
                        intent.putExtra("Ambalaj", contentsMLD.get(position).getAmbalaj_sekli());

                        // intent.putExtra("Description",contentsMLD.get(position).getDescription());
                        intent.putExtra("Marka", "MLD");
                        intent.putExtra("Fiyat", boyalar_fiyat.getText().toString());
                        intent.putExtra("Share_Link", contentsMLD.get(position).getShare_Link());
                        intent.putExtra("from", "normal_boyalar");

                        activity.startActivity(intent);
                    }
                });
                normal_item_details_boyalar_delete.setOnClickListener(new View.OnClickListener() {
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


                                                    Query ObjectKod1 = dataSnapshot.getRef().orderByChild("Kod").equalTo(contentsMLD.get(position).getKod());
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
                normal_item_details_boyalar_ambalaj.setText(contentsMLD.get(position).getAmbalaj_sekli());


                boyalar_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, BoyalarDetailsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        intent.putExtra("Model", contentsMLD.get(position).getModel());
                        intent.putExtra("Image", contentsMLD.get(position).getURL());
                        intent.putExtra("Kod", contentsMLD.get(position).getKod());
                        intent.putExtra("Renk", contentsMLD.get(position).getRenk());
                        intent.putExtra("Miktari", contentsMLD.get(position).getMiktari());
                        intent.putExtra("Ambalaj", contentsMLD.get(position).getAmbalaj_sekli());

                        // intent.putExtra("Description",contentsMLD.get(position).getDescription());
                        intent.putExtra("Marka", "MLD");
                        intent.putExtra("Fiyat", boyalar_fiyat.getText().toString());
                        intent.putExtra("Share_Link", contentsMLD.get(position).getShare_Link());
                        activity.startActivity(intent);
                    }
                });
                Glide.with(activity).load(contentsMLD.get(position).getURL()).into(boyalar_image);
                boyalar_title.setText(contentsMLD.get(position).getModel());
                boyalar_ref.setText(contentsMLD.get(position).getKod());
                boyalar_fiyat.setText(contentsMLD.get(position).getFiyat());
         /*   if (contentsMLD.get(position).getRate().equals("Dollar")) {
                double result = Double.valueOf(Dollar_Rate) * Double.valueOf(contentsMLD.get(position).getFiyat().substring(0, contentsMLD.get(position).getFiyat().length() - 1));
                String string_result = String.valueOf(result);
                boyalar_fiyat.setText(string_result);


            } else if (contentsMLD.get(position).getRate().equals("Euro")) {
                double result = Double.valueOf(Euro_Rate) * Double.valueOf(contentsMLD.get(position).getFiyat().substring(0, contentsMLD.get(position).getFiyat().length() - 1));
                Locale Turkish = new Locale("tr", "TR");
                NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                String string_result = String.valueOf(numberFormatDutch.format(result));
                boyalar_fiyat.setText(string_result);


            } else {*/
                boyalar_fiyat.setText(contentsMLD.get(position).getFiyat());

                // }
            }
        } else if (contentsPro_ink != null) {
            if (contentsPro_ink.get(position).getModel().equals("yok")) {
                Log.e("yok", "var");
                card_empty.setVisibility(View.VISIBLE);
                boyalar_layout.setVisibility(View.GONE);
                cardView.setCardElevation(0);
            } else {


                normal_item_details_boyalar_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, EditBoyalarActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("Model", contentsPro_ink.get(position).getModel());
                        intent.putExtra("Image", contentsPro_ink.get(position).getURL());
                        intent.putExtra("Kod", contentsPro_ink.get(position).getKod());
                        intent.putExtra("Renk", contentsPro_ink.get(position).getRenk());
                        intent.putExtra("Miktari", contentsPro_ink.get(position).getMiktari());
                        intent.putExtra("Ambalaj", contentsPro_ink.get(position).getAmbalaj_sekli());

                        // intent.putExtra("Description",contentsMLD.get(position).getDescription());
                        intent.putExtra("Marka", "MLD");
                        intent.putExtra("Fiyat", boyalar_fiyat.getText().toString());
                        intent.putExtra("Share_Link", contentsPro_ink.get(position).getShare_Link());
                        intent.putExtra("from", "normal_boyalar");

                        activity.startActivity(intent);
                    }
                });
                normal_item_details_boyalar_delete.setOnClickListener(new View.OnClickListener() {
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


                                                    Query ObjectKod1 = dataSnapshot.getRef().orderByChild("Kod").equalTo(contentsPro_ink.get(position).getKod());
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
                normal_item_details_boyalar_ambalaj.setText(contentsPro_ink.get(position).getAmbalaj_sekli());


                boyalar_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, BoyalarDetailsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        intent.putExtra("Model", contentsPro_ink.get(position).getModel());
                        intent.putExtra("Image", contentsPro_ink.get(position).getURL());
                        intent.putExtra("Kod", contentsPro_ink.get(position).getKod());
                        intent.putExtra("Renk", contentsPro_ink.get(position).getRenk());
                        intent.putExtra("Miktari", contentsPro_ink.get(position).getMiktari());
                        intent.putExtra("Ambalaj", contentsPro_ink.get(position).getAmbalaj_sekli());

                        //  intent.putExtra("Description",contentsPro_ink.get(position).getDescription());
                        intent.putExtra("Marka", "Pro Ink");
                        intent.putExtra("Fiyat", boyalar_fiyat.getText().toString());
                        intent.putExtra("Share_Link", contentsPro_ink.get(position).getShare_Link());
                        activity.startActivity(intent);
                    }
                });
                Glide.with(activity).load(contentsPro_ink.get(position).getURL()).into(boyalar_image);

                boyalar_title.setText(contentsPro_ink.get(position).getModel());
                boyalar_ref.setText(contentsPro_ink.get(position).getKod());
                boyalar_fiyat.setText(contentsPro_ink.get(position).getFiyat());
       /*     if (contentsPro_ink.get(position).getRate().equals("Dollar")) {
                double result = Double.valueOf(Dollar_Rate) * Double.valueOf(contentsPro_ink.get(position).getFiyat().substring(0, contentsPro_ink.get(position).getFiyat().length() - 1));
                Locale Turkish = new Locale("tr", "TR");
                NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                String string_result = String.valueOf(numberFormatDutch.format(result));
                boyalar_fiyat.setText(string_result);


            } else if (contentsPro_ink.get(position).getRate().equals("Euro")) {
                double result = Double.valueOf(Euro_Rate) * Double.valueOf(contentsPro_ink.get(position).getFiyat().substring(0, contentsPro_ink.get(position).getFiyat().length() - 1));
                Locale Turkish = new Locale("tr", "TR");
                NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                String string_result = String.valueOf(numberFormatDutch.format(result));
                boyalar_fiyat.setText(string_result);


            } else {*/
                boyalar_fiyat.setText(contentsPro_ink.get(position).getFiyat());

                //  }
            }
        } else if (contentsTriangle != null) {
            if (contentsTriangle.get(position).getModel().equals("yok")) {
                Log.e("yok", "var");
                card_empty.setVisibility(View.VISIBLE);
                boyalar_layout.setVisibility(View.GONE);
                cardView.setCardElevation(0);
            } else {


                normal_item_details_boyalar_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                normal_item_details_boyalar_delete.setOnClickListener(new View.OnClickListener() {
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


                                                    Query ObjectKod1 = dataSnapshot.getRef().orderByChild("Kod").equalTo(contentsTriangle.get(position).getKod());
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

                boyalar_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, BoyalarDetailsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("Model", contentsTriangle.get(position).getModel());
                        intent.putExtra("Image", contentsTriangle.get(position).getURL());
                        intent.putExtra("Kod", contentsTriangle.get(position).getKod());
                        intent.putExtra("Ambalaj", contentsTriangle.get(position).getAmbalaj_sekli());

                        intent.putExtra("Renk", contentsTriangle.get(position).getRenk());
                        intent.putExtra("Miktari", contentsTriangle.get(position).getMiktari());
                        // intent.putExtra("Description",contentsTriangle.get(position).getDescription());
                        intent.putExtra("Marka", "Triangle");
                        intent.putExtra("Fiyat", boyalar_fiyat.getText().toString());
                        intent.putExtra("Share_Link", contentsTriangle.get(position).getShare_Link());
                        activity.startActivity(intent);
                    }
                });
                Glide.with(activity).load(contentsTriangle.get(position).getURL()).into(boyalar_image);

                boyalar_title.setText(contentsTriangle.get(position).getModel());
                boyalar_ref.setText(contentsTriangle.get(position).getKod());
                boyalar_fiyat.setText(contentsTriangle.get(position).getFiyat());
          /*  if (contentsTriangle.get(position).getRate().equals("Dollar")) {
                double result = Double.valueOf(Dollar_Rate) * Double.valueOf(contentsTriangle.get(position).getFiyat().substring(0, contentsTriangle.get(position).getFiyat().length() - 1));
                Locale Turkish = new Locale("tr", "TR");
                NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                String string_result = String.valueOf(numberFormatDutch.format(result));
                boyalar_fiyat.setText(string_result);
            } else if (contentsTriangle.get(position).getRate().equals("Euro")) {
                double result = Double.valueOf(Euro_Rate) * Double.valueOf(contentsTriangle.get(position).getFiyat().substring(0, contentsTriangle.get(position).getFiyat().length() - 1));
                Locale Turkish = new Locale("tr", "TR");
                NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                String string_result = String.valueOf(numberFormatDutch.format(result));
                boyalar_fiyat.setText(string_result);

            } else {*/
                boyalar_fiyat.setText(contentsTriangle.get(position).getFiyat());
                // }

            }


            // boyalar_title.setText(contents.get(position));
            // boyalar_ref.setText(contents.get());

        }
    }


}