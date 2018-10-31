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
import com.bilgiyazan.malzemeiste.adminpaneli.Activity.KampanyalarDetailsActivity;
import com.bilgiyazan.malzemeiste.adminpaneli.Activity.MakinalarDetailsActivity;
import com.bilgiyazan.malzemeiste.adminpaneli.Activity.MalzemeDetailsActivity;
import com.bilgiyazan.malzemeiste.adminpaneli.Activity.YazilimDetailsActivity;
import com.bilgiyazan.malzemeiste.adminpaneli.Activity.YedekDetailsActivity;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.SearchModel;
import com.bilgiyazan.malzemeiste.adminpaneli.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class NormalItemAdapter_Search extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    List<SearchModel> SearchModel;
    int Size;
    CallbackInterface mCallback;

    SharedPreferences prefs;
    String Dollar_Rate;
    String Euro_Rate;
    TextView fiyat;
    String fiyat_intent;
    ImageView image;
    TextView title;
    TextView ref;
    RelativeLayout layout;
    CardView cardView;
    ImageView normal_item_details_search_edit;
    ImageView normal_item_details_search_delete;
    MaterialDialog Delete_Dialog;
    private int lastPosition = -1;
    private Activity activity;
    private DatabaseReference mDatabase;

    public NormalItemAdapter_Search(List<SearchModel> SearchModel, Activity activity) {

        try {
            mCallback = (CallbackInterface) activity;
        } catch (ClassCastException ex) {
            //.. should log the error or throw and exception
            Log.e("MyAdapter", "Must implement the CallbackInterface in the Activity", ex);
        }
        prefs = activity.getSharedPreferences("malzemeiste", Context.MODE_PRIVATE);
        Euro_Rate = prefs.getString("Euro_Rate", "");
        Dollar_Rate = prefs.getString("Dollar_Rate", "");

        this.SearchModel = SearchModel;
        if (SearchModel != null) {

            // this.contentsRolands = contentsRolands;
            Log.e("roland size", SearchModel.size() + "");
            Size = SearchModel.size();

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
                .inflate(R.layout.normal_item_details_search, parent, false);

        return new RecyclerView.ViewHolder(view) {
        };

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        cardView = (CardView) holder.itemView.findViewById(R.id.normal_item_details_cardview);
        layout = (RelativeLayout) holder.itemView.findViewById(R.id.normal_item_details_layout);
        fiyat = (TextView) holder.itemView.findViewById(R.id.normal_item_details_fiyat);
        image = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_image);
        title = (TextView) holder.itemView.findViewById(R.id.normal_item_details_title);
        ref = (TextView) holder.itemView.findViewById(R.id.normal_item_details_ref);
        normal_item_details_search_delete = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_search_delete);
        normal_item_details_search_edit = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_search_edit);

        fiyat.setText(SearchModel.get(holder.getAdapterPosition()).getFiyat());

        // }
        normal_item_details_search_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fiyat_intent = SearchModel.get(holder.getAdapterPosition()).getFiyat();

                if (SearchModel.get(holder.getAdapterPosition()).getFrom().equals("Makinalar")) {
                    if (mCallback != null) {
                        mCallback.Update(position, "Makinalar");
                    }

                } else if (SearchModel.get(holder.getAdapterPosition()).getFrom().equals("Boyalar")) {
                    if (mCallback != null) {
                        mCallback.Update(position, "Boyalar");
                    }
                } else if (SearchModel.get(holder.getAdapterPosition()).getFrom().equals("Yedek")) {
                    if (mCallback != null) {
                        mCallback.Update(position, "Yedek");
                    }

                } else if (SearchModel.get(holder.getAdapterPosition()).getFrom().equals("Malzeme")) {

                    if (mCallback != null) {
                        mCallback.Update(position, "Malzeme");
                    }
                } else if (SearchModel.get(holder.getAdapterPosition()).getFrom().equals("Yazilim")) {

                    if (mCallback != null) {
                        mCallback.Update(position, "Yazilim");
                    }
                } else if (SearchModel.get(holder.getAdapterPosition()).getFrom().equals("Kampanyalar")) {

                    if (mCallback != null) {
                        mCallback.Update(position, "Kampanyalar");
                    }
                }

            }
        });
        normal_item_details_search_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SearchModel.get(holder.getAdapterPosition()).getFrom().equals("Makinalar")) {

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Makinalar");

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

                                    final String Kod = SearchModel.get(position).getKod();

                                    Toast.makeText(activity, "Ürün Silindi", Toast.LENGTH_SHORT).show();
                                    if (mCallback != null)
                                        mCallback.refresh();
                                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot postSnapshot) {
                                            for (final DataSnapshot dataSnapshot : postSnapshot.getChildren()) {

                                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                                                    Query ObjectKod1 = dataSnapshot1.getRef().orderByChild("Kod").equalTo(Kod);
                                                    ObjectKod1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot tasksSnapshot) {
                                                            for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                                                                snapshot.getRef().removeValue();

                                                                Log.e("result", snapshot.getValue() + "");
                                                            }
                                                        }


                                                        @Override
                                                        public void onCancelled(DatabaseError firebaseError) {
                                                            System.out.println("The read failed: " + firebaseError.getMessage());
                                                        }
                                                    });


                                                }
                                                Query ObjectKod1 = dataSnapshot.getRef().orderByChild("Kod").equalTo(Kod);
                                                ObjectKod1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot tasksSnapshot) {
                                                        for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                                                            snapshot.getRef().removeValue();

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

                } else if (SearchModel.get(holder.getAdapterPosition()).getFrom().equals("Boyalar")) {

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Boyalar");

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

                                    final String Kod = SearchModel.get(position).getKod();
                                    Toast.makeText(activity, "Ürün Silindi", Toast.LENGTH_SHORT).show();
                                    if (mCallback != null)
                                        mCallback.refresh();
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
                                                Query ObjectKod1 = dataSnapshot.getRef().orderByChild("Kod").equalTo(Kod);
                                                ObjectKod1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot tasksSnapshot) {
                                                        for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
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

                } else if (SearchModel.get(holder.getAdapterPosition()).getFrom().equals("Yazilim")) {

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Yazilim");

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

                                    final String Kod = SearchModel.get(position).getKod();

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
                                                            snapshot.getRef().removeValue();
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

                } else if (SearchModel.get(holder.getAdapterPosition()).getFrom().equals("Yedek")) {

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Yedek");

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

                                    final String Kod = SearchModel.get(position).getKod();
                                    Toast.makeText(activity, "Ürün Silindi", Toast.LENGTH_SHORT).show();
                                    if (mCallback != null)
                                        mCallback.refresh();
                                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot postSnapshot) {
                                            for (final DataSnapshot dataSnapshot : postSnapshot.getChildren()) {

                                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                                                    Query ObjectKod1 = dataSnapshot1.getRef().orderByChild("Kod").equalTo(Kod);
                                                    ObjectKod1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot tasksSnapshot) {
                                                            for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
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

                } else if (SearchModel.get(holder.getAdapterPosition()).getFrom().equals("Malzeme")) {

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Malzeme");

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

                                    final String Kod = SearchModel.get(position).getKod();
                                    Toast.makeText(activity, "Ürün Silindi", Toast.LENGTH_SHORT).show();
                                    if (mCallback != null)
                                        mCallback.refresh();
                                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot postSnapshot) {
                                            for (final DataSnapshot dataSnapshot : postSnapshot.getChildren()) {

                                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                                                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                                        for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {

                                                            Query ObjectKod1 = dataSnapshot3.getRef().orderByChild("Kod").equalTo(Kod);
                                                            ObjectKod1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(DataSnapshot tasksSnapshot) {
                                                                    for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
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

                } else if (SearchModel.get(holder.getAdapterPosition()).getFrom().equals("Kampanyalar")) {

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Malzemeiste_Category").child("Kampanyalar");

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

                                    final String Kod = SearchModel.get(position).getKod();
                                    Toast.makeText(activity, "Ürün Silindi", Toast.LENGTH_SHORT).show();
                                    if (mCallback != null)
                                        mCallback.refresh();
                                    Delete_Dialog.dismiss();
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
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("what am touching", SearchModel.get(holder.getAdapterPosition()).getFrom());
            /*    if (SearchModel.get(holder.getAdapterPosition()).getRate().equals("Dollar")) {
                    double result = Double.valueOf(Dollar_Rate) * Double.valueOf(SearchModel.get(holder.getAdapterPosition()).getFiyat().substring(0, SearchModel.get(holder.getAdapterPosition()).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    fiyat_intent = string_result;
                } else if (SearchModel.get(holder.getAdapterPosition()).getRate().equals("Euro")) {
                    double result = Double.valueOf(Euro_Rate) * Double.valueOf(SearchModel.get(holder.getAdapterPosition()).getFiyat().substring(0, SearchModel.get(holder.getAdapterPosition()).getFiyat().length() - 1));
                    Locale Turkish = new Locale("tr", "TR");
                    NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                    String string_result = String.valueOf(numberFormatDutch.format(result));
                    fiyat_intent = string_result;

                } else {*/
                fiyat_intent = SearchModel.get(holder.getAdapterPosition()).getFiyat();

                // }
                Log.e("to see2", SearchModel.get(holder.getAdapterPosition()).getFiyat());
                if (SearchModel.get(holder.getAdapterPosition()).getFrom().equals("Makinalar")) {

                    Intent intent = new Intent(activity, MakinalarDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Model", SearchModel.get(holder.getAdapterPosition()).getModel());
                    intent.putExtra("Image", SearchModel.get(holder.getAdapterPosition()).getURL());
                    intent.putExtra("Kod", SearchModel.get(holder.getAdapterPosition()).getKod());
                    intent.putExtra("Description", SearchModel.get(holder.getAdapterPosition()).getDescription());
                    intent.putExtra("Marka", SearchModel.get(holder.getAdapterPosition()).getMarka());
                    intent.putExtra("Fiyat", fiyat_intent);
                    intent.putExtra("Share_Link", SearchModel.get(holder.getAdapterPosition()).getShare_Link());
                    intent.putExtra("Rate", SearchModel.get(holder.getAdapterPosition()).getRate());
                    activity.startActivity(intent);
                } else if (SearchModel.get(holder.getAdapterPosition()).getFrom().equals("Boyalar")) {

                    Intent intent = new Intent(activity, BoyalarDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Model", SearchModel.get(holder.getAdapterPosition()).getModel());
                    intent.putExtra("Image", SearchModel.get(holder.getAdapterPosition()).getURL());
                    intent.putExtra("Kod", SearchModel.get(holder.getAdapterPosition()).getKod());

                    intent.putExtra("Renk", SearchModel.get(holder.getAdapterPosition()).getRenk());


                    intent.putExtra("Miktari", SearchModel.get(holder.getAdapterPosition()).getMiktari());
                    intent.putExtra("Ambalaj", SearchModel.get(holder.getAdapterPosition()).getAmbalaj_sekli());

                    intent.putExtra("Marka", SearchModel.get(holder.getAdapterPosition()).getMarka());
                    intent.putExtra("Fiyat", fiyat_intent);
                    intent.putExtra("Share_Link", SearchModel.get(holder.getAdapterPosition()).getShare_Link());
                    intent.putExtra("Rate", SearchModel.get(holder.getAdapterPosition()).getRate());
                    activity.startActivity(intent);
                } else if (SearchModel.get(holder.getAdapterPosition()).getFrom().equals("Yedek")) {
                    Intent intent = new Intent(activity, YedekDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Model", SearchModel.get(holder.getAdapterPosition()).getModel());
                    intent.putExtra("Image", SearchModel.get(holder.getAdapterPosition()).getURL());
                    intent.putExtra("Kod", SearchModel.get(holder.getAdapterPosition()).getKod());

                    intent.putExtra("Uyumlu", SearchModel.get(holder.getAdapterPosition()).getUyumlu());

                    intent.putExtra("Marka", SearchModel.get(holder.getAdapterPosition()).getMarka());
                    intent.putExtra("Fiyat", fiyat_intent);
                    intent.putExtra("Share_Link", SearchModel.get(holder.getAdapterPosition()).getShare_Link());
                    intent.putExtra("Rate", SearchModel.get(holder.getAdapterPosition()).getRate());
                    activity.startActivity(intent);

                } else if (SearchModel.get(holder.getAdapterPosition()).getFrom().equals("Malzeme")) {

                    Intent intent = new Intent(activity, MalzemeDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    intent.putExtra("Model", SearchModel.get(holder.getAdapterPosition()).getModel());
                    intent.putExtra("Image", SearchModel.get(holder.getAdapterPosition()).getURL());
                    intent.putExtra("Kod", SearchModel.get(holder.getAdapterPosition()).getKod());
                    intent.putExtra("Marka", SearchModel.get(holder.getAdapterPosition()).getMarka());
                    intent.putExtra("Fiyat", fiyat_intent);
                    intent.putExtra("Renk", SearchModel.get(holder.getAdapterPosition()).getRenk());
                    intent.putExtra("Miktari", SearchModel.get(holder.getAdapterPosition()).getMiktari());
                    intent.putExtra("Share_Link", SearchModel.get(holder.getAdapterPosition()).getShare_Link());
                    intent.putExtra("Rate", SearchModel.get(holder.getAdapterPosition()).getRate());
                    activity.startActivity(intent);
                } else if (SearchModel.get(holder.getAdapterPosition()).getFrom().equals("Yazilim")) {

                    Intent intent = new Intent(activity, YazilimDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Model", SearchModel.get(holder.getAdapterPosition()).getModel());
                    intent.putExtra("Image", SearchModel.get(holder.getAdapterPosition()).getURL());
                    intent.putExtra("Kod", SearchModel.get(holder.getAdapterPosition()).getKod());
                    intent.putExtra("Marka", SearchModel.get(holder.getAdapterPosition()).getMarka());
                    intent.putExtra("Fiyat", fiyat_intent);
                    intent.putExtra("Share_Link", SearchModel.get(holder.getAdapterPosition()).getShare_Link());
                    activity.startActivity(intent);
                } else if (SearchModel.get(holder.getAdapterPosition()).getFrom().equals("Kampanyalar")) {

                    Intent intent = new Intent(activity, KampanyalarDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("Model", SearchModel.get(holder.getAdapterPosition()).getModel());
                    intent.putExtra("Image", SearchModel.get(holder.getAdapterPosition()).getURL());
                    intent.putExtra("Kod", SearchModel.get(holder.getAdapterPosition()).getKod());
                    intent.putExtra("Fiyat", fiyat_intent);
                    intent.putExtra("Share_Link", SearchModel.get(holder.getAdapterPosition()).getShare_Link());
                    activity.startActivity(intent);
                }
            }
        });

        if (!SearchModel.get(holder.getAdapterPosition()).getURL().equals("------"))
            Glide.with(activity).load(SearchModel.get(holder.getAdapterPosition()).getURL()).into(image);
        title.setText(SearchModel.get(holder.getAdapterPosition()).getModel());
        ref.setText(SearchModel.get(holder.getAdapterPosition()).getKod());


    }


    public interface CallbackInterface {

        void Update(int position, String from);
        void refresh();

    }


}