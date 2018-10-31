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
import com.bilgiyazan.malzemeiste.adminpaneli.Activity.MakinalarDetailsActivity;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Emerald;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.MLD;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Pro_ink;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Roland;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Triangle;
import com.bilgiyazan.malzemeiste.adminpaneli.R;
import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class NormalItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    List<Roland> rolandList;
    List<Emerald> emeraldList;
    List<MLD> mldList;
    List<Pro_ink> pro_inkList;
    List<Triangle> triangleList;
    CallbackInterface mCallback;

    int Size;
    SharedPreferences prefs;
    String Dollar_Rate;
    String Euro_Rate;
    String From;
    ImageView normal_item_details_makinalar_delete;
    ImageView normal_item_details_makinalar_edit;
    RelativeLayout card_empty;
    MaterialDialog Delete_Dialog;
    ImageView normal_item_details_boyalar_delete;
    ImageView normal_item_details_boyalar_edit;
    private int lastPosition = -1;
    private Activity activity;
    private DatabaseReference mDatabase;

    public NormalItemAdapter(List<Roland> rolandList, List<Emerald> emeraldList, List<MLD> mldList, List<Pro_ink> pro_inkList, List<Triangle> triangleList, String From, Activity activity) {
        try {
            mCallback = (CallbackInterface) activity;
        } catch (ClassCastException ex) {
            //.. should log the error or throw and exception
            Log.e("MyAdapter", "Must implement the CallbackInterface in the Activity", ex);
        }

        prefs = activity.getSharedPreferences("malzemeiste", Context.MODE_PRIVATE);
        Euro_Rate = prefs.getString("Euro_Rate", "");
        Dollar_Rate = prefs.getString("Dollar_Rate", "");
        this.From = From;
        if (rolandList != null) {

            this.rolandList = rolandList;
            Size = rolandList.size();

        }
        if (emeraldList != null) {

            this.emeraldList = emeraldList;
            Size = emeraldList.size();
            Log.e("see all size", Size + "");

        }
        if (mldList != null) {

            this.mldList = mldList;
            Size = mldList.size();

        }
        if (pro_inkList != null) {

            this.pro_inkList = pro_inkList;
            Size = pro_inkList.size();

        }
        if (triangleList != null) {

            this.triangleList = triangleList;
            Size = triangleList.size();

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
        if (From.equals("Boyalar")) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.normal_item_details_boyalar, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.normal_item_details_makinalar, parent, false);
        }


        return new RecyclerView.ViewHolder(view) {
        };

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {


        if (From.equals("Boyalar")) {
            CardView cardView = (CardView) holder.itemView.findViewById(R.id.normal_item_details_boyalar_cardview);
            RelativeLayout boyalar_layout = (RelativeLayout) holder.itemView.findViewById(R.id.normal_item_details_boyalar_layout);
            TextView boyalar_fiyat = (TextView) holder.itemView.findViewById(R.id.normal_item_details_boyalar_fiyat);
            ImageView boyalar_image = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_boyalar_image);
            TextView boyalar_title = (TextView) holder.itemView.findViewById(R.id.normal_item_details_boyalar_title);
            TextView boyalar_ref = (TextView) holder.itemView.findViewById(R.id.normal_item_details_boyalar_ref);
            TextView normal_item_details_boyalar_ambalaj = (TextView) holder.itemView.findViewById(R.id.normal_item_details_boyalar_ambalaj);
            normal_item_details_boyalar_delete = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_boyalar_delete);
            normal_item_details_boyalar_edit = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_boyalar_edit);
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Boyalar");
            card_empty = (RelativeLayout) holder.itemView.findViewById(R.id.card_empty);
            TextView boyalar_detail_share;


            if (emeraldList != null) {

                normal_item_details_boyalar_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mCallback != null) {
                            mCallback.Update(position, "Emerald", "Boyalar");
                        }

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

                                        final String Kod = emeraldList.get(position).getKod();
                                        Toast.makeText(activity, "Ürün Silindi", Toast.LENGTH_SHORT).show();
                                        if (mCallback != null)
                                        mCallback.refresh();


                                        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot postSnapshot) {
                                                for (final DataSnapshot dataSnapshot : postSnapshot.getChildren()) {


                                                    Query ObjectKod1 = dataSnapshot.getRef().orderByChild("Kod").equalTo(Kod);
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
                normal_item_details_boyalar_ambalaj.setText(emeraldList.get(position).getAmbalaj_sekli());


                boyalar_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, BoyalarDetailsActivity.class);
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
                        activity.startActivity(intent);
                    }
                });


                boyalar_title.setText(emeraldList.get(position).getModel());
                boyalar_ref.setText(emeraldList.get(position).getKod());
                Glide.with(activity).load(emeraldList.get(position).getURL()).into(boyalar_image);

                boyalar_fiyat.setText(emeraldList.get(position).getFiyat());




            } else if (mldList != null) {

                normal_item_details_boyalar_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mCallback != null) {
                            mCallback.Update(position, "MLD", "Boyalar");
                        }

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

                                        final String Kod = mldList.get(position).getKod();
                                        Toast.makeText(activity, "Ürün Silindi", Toast.LENGTH_SHORT).show();
                                        if (mCallback != null)
                                        mCallback.refresh();
                                        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot postSnapshot) {
                                                for (final DataSnapshot dataSnapshot : postSnapshot.getChildren()) {


                                                    Query ObjectKod1 = dataSnapshot.getRef().orderByChild("Kod").equalTo(Kod);
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

                normal_item_details_boyalar_ambalaj.setText(mldList.get(position).getAmbalaj_sekli());


                boyalar_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, BoyalarDetailsActivity.class);
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
                        activity.startActivity(intent);
                    }
                });
                Glide.with(activity).load(mldList.get(position).getURL()).into(boyalar_image);
                boyalar_title.setText(mldList.get(position).getModel());
                boyalar_ref.setText(mldList.get(position).getKod());
                boyalar_fiyat.setText(mldList.get(position).getFiyat());

                boyalar_fiyat.setText(mldList.get(position).getFiyat());



            } else if (pro_inkList != null) {
                normal_item_details_boyalar_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (mCallback != null) {
                            mCallback.Update(position, "Pro ink", "Boyalar");
                        }
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

                                        final String Kod = pro_inkList.get(position).getKod();
                                        Toast.makeText(activity, "Ürün Silindi", Toast.LENGTH_SHORT).show();
                                        if (mCallback != null)
                                            mCallback.refresh();


                                            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot postSnapshot) {
                                                for (final DataSnapshot dataSnapshot : postSnapshot.getChildren()) {


                                                    Query ObjectKod1 = dataSnapshot.getRef().orderByChild("Kod").equalTo(Kod);
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

                normal_item_details_boyalar_ambalaj.setText(pro_inkList.get(position).getAmbalaj_sekli());


                boyalar_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, BoyalarDetailsActivity.class);
                        intent.putExtra("Model", pro_inkList.get(position).getModel());
                        intent.putExtra("Image", pro_inkList.get(position).getURL());
                        intent.putExtra("Kod", pro_inkList.get(position).getKod());
                        intent.putExtra("Ambalaj", pro_inkList.get(position).getAmbalaj_sekli());
                        intent.putExtra("Miktari", pro_inkList.get(position).getMiktari());
                        intent.putExtra("Marka", "Pro ink");
                        intent.putExtra("Fiyat", pro_inkList.get(position).getFiyat());
                        intent.putExtra("Renk", pro_inkList.get(position).getRenk());
                        intent.putExtra("Share_Link", pro_inkList.get(position).getShare_Link());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        activity.startActivity(intent);
                    }
                });
                Glide.with(activity).load(pro_inkList.get(position).getURL()).into(boyalar_image);

                boyalar_title.setText(pro_inkList.get(position).getModel());
                boyalar_ref.setText(pro_inkList.get(position).getKod());
                boyalar_fiyat.setText(pro_inkList.get(position).getFiyat());
                if (pro_inkList.get(position).getRate().equals("Dollar")) {
                    double result = Double.valueOf(Dollar_Rate) * Double.valueOf(pro_inkList.get(position).getFiyat().substring(0, pro_inkList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    boyalar_fiyat.setText(string_result);


                } else if (pro_inkList.get(position).getRate().equals("Euro")) {
                    double result = Double.valueOf(Euro_Rate) * Double.valueOf(pro_inkList.get(position).getFiyat().substring(0, pro_inkList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    boyalar_fiyat.setText(string_result);


                } else {
                    boyalar_fiyat.setText(pro_inkList.get(position).getFiyat());

                }

            } else if (triangleList != null) {


                boyalar_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, MakinalarDetailsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        activity.startActivity(intent);
                    }
                });
                Glide.with(activity).load(triangleList.get(position).getURL()).into(boyalar_image);

                boyalar_title.setText(triangleList.get(position).getModel());
                boyalar_ref.setText(triangleList.get(position).getKod());
                boyalar_fiyat.setText(triangleList.get(position).getFiyat());
                if (triangleList.get(position).getRate().equals("Dollar")) {
                    double result = Double.valueOf(Dollar_Rate) * Double.valueOf(triangleList.get(position).getFiyat().substring(0, triangleList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    boyalar_fiyat.setText(string_result);
                } else if (triangleList.get(position).getRate().equals("Euro")) {
                    double result = Double.valueOf(Euro_Rate) * Double.valueOf(triangleList.get(position).getFiyat().substring(0, triangleList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    boyalar_fiyat.setText(string_result);

                } else {
                    boyalar_fiyat.setText(triangleList.get(position).getFiyat());
                }

            }


        } else {
            CardView cardView = (CardView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_cardview);
            RelativeLayout makinalar_layout = (RelativeLayout) holder.itemView.findViewById(R.id.normal_item_details_makinalar_layout);
            TextView makinalar_fiyat = (TextView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_fiyat);
            ImageView makinalar_image = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_image);
            TextView makinalar_title = (TextView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_title);
            TextView makinalar_ref = (TextView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_ref);
            normal_item_details_makinalar_delete = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_delete);
            normal_item_details_makinalar_edit = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_edit);
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Makinalar");
            card_empty = (RelativeLayout) holder.itemView.findViewById(R.id.card_empty);
            if (rolandList != null) {

                normal_item_details_makinalar_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (mCallback != null) {
                            mCallback.Update(position, "Roland", "Makinalar");
                        }


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

                                        final String Kod = rolandList.get(position).getKod();
                                        Toast.makeText(activity, "Ürün Silindi", Toast.LENGTH_SHORT).show();
                                        if (mCallback != null)
                                            mCallback.refresh();


                                            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot postSnapshot) {
                                                for (final DataSnapshot dataSnapshot : postSnapshot.getChildren()) {


                                                    Query ObjectKod1 = dataSnapshot.getRef().orderByChild("Kod").equalTo(Kod);
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

                if (!rolandList.get(position).getURL().equals("------"))
                    Glide.with(activity).load(rolandList.get(position).getURL()).into(makinalar_image);
                makinalar_title.setText(rolandList.get(position).getModel());
                makinalar_ref.setText(rolandList.get(position).getKod());
               /* if (rolandList.get(position).getRate().equals("Dollar")) {
                    double result = Double.valueOf(Dollar_Rate) * Double.valueOf(rolandList.get(position).getFiyat().substring(0, rolandList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    makinalar_fiyat.setText(string_result);
                } else if (rolandList.get(position).getRate().equals("Euro")) {
                    double result = Double.valueOf(Euro_Rate) * Double.valueOf(rolandList.get(position).getFiyat().substring(0, rolandList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    makinalar_fiyat.setText(string_result);

                } else {*/
                makinalar_fiyat.setText(rolandList.get(position).getFiyat());

                //  }


                makinalar_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, MakinalarDetailsActivity.class);
                        intent.putExtra("Model", rolandList.get(position).getModel());
                        intent.putExtra("Image", rolandList.get(position).getURL());
                        intent.putExtra("Kod", rolandList.get(position).getKod());
                        intent.putExtra("Description", rolandList.get(position).getDescription());
                        intent.putExtra("Marka", "Roland");
                        intent.putExtra("Fiyat", rolandList.get(position).getFiyat());
                        intent.putExtra("Share_Link", rolandList.get(position).getShare_Link());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        activity.startActivity(intent);
                    }
                });

            }
            if (emeraldList != null) {

                if (!emeraldList.get(position).getURL().equals("------"))
                    Glide.with(activity).load(emeraldList.get(position).getURL()).into(makinalar_image);
                makinalar_title.setText(emeraldList.get(position).getModel());
                makinalar_ref.setText(emeraldList.get(position).getKod());
                if (emeraldList.get(position).getRate().equals("Dollar")) {
                    double result = Double.valueOf(Dollar_Rate) * Double.valueOf(emeraldList.get(position).getFiyat().substring(0, emeraldList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    makinalar_fiyat.setText(string_result);
                } else if (emeraldList.get(position).getRate().equals("Euro")) {
                    double result = Double.valueOf(Euro_Rate) * Double.valueOf(emeraldList.get(position).getFiyat().substring(0, emeraldList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    makinalar_fiyat.setText(string_result);

                } else {
                    makinalar_fiyat.setText(rolandList.get(position).getFiyat());

                }

            }
            if (mldList != null) {

                if (!mldList.get(position).getURL().equals("------"))
                    Glide.with(activity).load(mldList.get(position).getURL()).into(makinalar_image);
                makinalar_title.setText(mldList.get(position).getModel());
                makinalar_ref.setText(mldList.get(position).getKod());
                if (mldList.get(position).getRate().equals("Dollar")) {
                    double result = Double.valueOf(Dollar_Rate) * Double.valueOf(mldList.get(position).getFiyat().substring(0, mldList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    makinalar_fiyat.setText(string_result);
                } else if (mldList.get(position).getRate().equals("Euro")) {
                    double result = Double.valueOf(Euro_Rate) * Double.valueOf(mldList.get(position).getFiyat().substring(0, mldList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    makinalar_fiyat.setText(string_result);

                } else {
                    makinalar_fiyat.setText(mldList.get(position).getFiyat());

                }

            }
            if (pro_inkList != null) {

                if (!pro_inkList.get(position).getURL().equals("------"))
                    Glide.with(activity).load(pro_inkList.get(position).getURL()).into(makinalar_image);
                makinalar_title.setText(pro_inkList.get(position).getModel());
                makinalar_ref.setText(pro_inkList.get(position).getKod());
                if (pro_inkList.get(position).getRate().equals("Dollar")) {
                    double result = Double.valueOf(Dollar_Rate) * Double.valueOf(pro_inkList.get(position).getFiyat().substring(0, pro_inkList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    makinalar_fiyat.setText(string_result);
                } else if (pro_inkList.get(position).getRate().equals("Euro")) {
                    double result = Double.valueOf(Euro_Rate) * Double.valueOf(pro_inkList.get(position).getFiyat().substring(0, pro_inkList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    makinalar_fiyat.setText(string_result);

                } else {
                    makinalar_fiyat.setText(pro_inkList.get(position).getFiyat());

                }

            }
          /*  if (triangleList != null) {
                makinalar_detail_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, triangleList.get(position).getShare_Link());
                        sendIntent.setType("text/plain");
                        activity.startActivity(Intent.createChooser(sendIntent, "İle paylaş"));
                    }
                });
                if (!triangleList.get(position).getURL().equals("------"))
                    Glide.with(activity).load(triangleList.get(position).getURL()).into(makinalar_image);
                makinalar_title.setText(triangleList.get(position).getModel());
                makinalar_ref.setText(triangleList.get(position).getKod());
                if (triangleList.get(position).getRate().equals("Dollar")) {
                    double result = Double.valueOf(Dollar_Rate) * Double.valueOf(triangleList.get(position).getFiyat().substring(0, triangleList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    makinalar_fiyat.setText(string_result);
                } else if (triangleList.get(position).getRate().equals("Euro")) {
                    double result = Double.valueOf(Euro_Rate) * Double.valueOf(triangleList.get(position).getFiyat().substring(0, triangleList.get(position).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    makinalar_fiyat.setText(string_result);

                } else {
                    makinalar_fiyat.setText(triangleList.get(position).getFiyat());

                }

            }*/


            // makinalar_title.setText(contents.get(position));
            // makinalar_ref.setText(contents.get());

        }

    }


    public interface CallbackInterface {

        void Update(int position, String brand, String from);
        void refresh();

    }


}