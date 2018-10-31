package com.bilgiyazan.malzemeiste.adminpaneli.ViewHolder;

/**
 * Created by Toshiba on 20/12/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bilgiyazan.malzemeiste.adminpaneli.Activity.SeeAllActivity;
import com.bilgiyazan.malzemeiste.adminpaneli.R;

/**
 * Simple view holder for a single text view.
 */
public class BoyalarViewTitleHolder extends RecyclerView.ViewHolder {

    private TextView mTextView;
    private TextView mTextView1;

    public BoyalarViewTitleHolder(View view) {
        super(view);

        mTextView = (TextView) view.findViewById(R.id.text_title);
        mTextView1 = (TextView) view.findViewById(R.id.see_all);
    }

    public void bindItem(String text, int position, String see_all, final Activity activity, final String from) {
        mTextView.setText(text);
        if (position == 0) {
            mTextView1.setVisibility(View.GONE);
            mTextView1.setText("");

        } else {
            if (see_all.equals("no")) {
                mTextView1.setVisibility(View.GONE);
                mTextView1.setText("");
            } else {
                mTextView1.setText("hepsini gör");
                mTextView1.setVisibility(View.VISIBLE);

                mTextView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, SeeAllActivity.class);
                        intent.putExtra("from", from);
                        activity.startActivity(intent);


                    }
                });
            }

        }
    }

    @Override
    public String toString() {
        return mTextView.getText().toString();
    }
}