package com.bilgiyazan.malzemeiste.adminpaneli.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bilgiyazan.malzemeiste.adminpaneli.Model.A_Starjet;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.OKI;
import com.bilgiyazan.malzemeiste.adminpaneli.Model.Roland;
import com.bilgiyazan.malzemeiste.adminpaneli.R;

import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class NormalItemAdapter_Yedek extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    List<OKI> contentsOkis;
    List<Roland> contentsRolands;
    List<A_Starjet> contentsA_starjets;
    int Size;
    SharedPreferences prefs;
    String Dollar_Rate;
    String Euro_Rate;
    private int lastPosition = -1;
    private Activity activity;

    public NormalItemAdapter_Yedek(List<OKI> contentsOkis, List<Roland> contentsRolands, List<A_Starjet> contentsA_starjets, Activity activity) {
        prefs = activity.getSharedPreferences("malzemeiste", Context.MODE_PRIVATE);
        Euro_Rate = prefs.getString("Euro_Rate", "");
        Dollar_Rate = prefs.getString("Dollar_Rate", "");

        if (contentsRolands != null) {

            this.contentsRolands = contentsRolands;
            Log.e("roland size", contentsRolands.size() + "");
            Size = contentsRolands.size();

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
                .inflate(R.layout.normal_item_details_yedek, parent, false);

        return new RecyclerView.ViewHolder(view) {
        };

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

      /*  CardView cardView = (CardView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_cardview);
        RelativeLayout makinalar_layout = (RelativeLayout) holder.itemView.findViewById(R.id.normal_item_details_makinalar_layout);
        TextView makinalar_fiyat = (TextView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_fiyat);
        ImageView makinalar_image = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_image);
        TextView makinalar_title = (TextView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_title);
        TextView makinalar_ref = (TextView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_ref);
        ImageView makinalar_detail_call;
        ImageView makinalar_detail_email;
        ImageView makinalar_detail_share;
        makinalar_detail_call = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_call);
        makinalar_detail_email = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_email);
        makinalar_detail_share = (ImageView) holder.itemView.findViewById(R.id.normal_item_details_makinalar_share);
        if (contentsRolands != null) {
            if (!contentsRolands.get(position).getURL().equals("------"))
                Glide.with(activity).load(contentsRolands.get(position).getURL()).into(makinalar_image);
            makinalar_title.setText(contentsRolands.get(position).getModel());
            makinalar_ref.setText(contentsRolands.get(position).getKod());
            if (contentsRolands.get(position).getRate().equals("Dollar")) {
                double result = Double.valueOf(Dollar_Rate) * Double.valueOf(contentsRolands.get(position).getFiyat().substring(0, contentsRolands.get(position).getFiyat().length() - 1));
                Locale Turkish = new Locale("tr", "TR");
                NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                String string_result = String.valueOf(numberFormatDutch.format(result));
                makinalar_fiyat.setText(string_result);
            } else if (contentsRolands.get(position).getRate().equals("Euro")) {
                double result = Double.valueOf(Euro_Rate) * Double.valueOf(contentsRolands.get(position).getFiyat().substring(0, contentsRolands.get(position).getFiyat().length() - 1));
                Locale Turkish = new Locale("tr", "TR");
                NumberFormat numberFormatDutch = NumberFormat.getCurrencyInstance(Turkish);
                String string_result = String.valueOf(numberFormatDutch.format(result));
                makinalar_fiyat.setText(string_result);

            } else {
                makinalar_fiyat.setText(contentsRolands.get(position).getFiyat());

            }

        }


        makinalar_detail_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "0(212) 438 48 29"));
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.CALL_PHONE},
                            1);
                } else {

                    activity.startActivity(callIntent);

                }
            }
        });
        makinalar_detail_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"bilgi@bilgiyazan.com"});
                try {

                    activity.startActivity(Intent.createChooser(intent, "E-posta ile Via ..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(activity, "Hiçbir e-posta istemcisi yüklü değil.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // makinalar_title.setText(contents.get(position));
        // makinalar_ref.setText(contents.get());*/

    }


}