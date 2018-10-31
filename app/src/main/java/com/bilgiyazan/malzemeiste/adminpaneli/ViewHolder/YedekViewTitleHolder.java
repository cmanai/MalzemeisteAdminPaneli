package com.bilgiyazan.malzemeiste.adminpaneli.ViewHolder;

/**
 * Created by Toshiba on 20/12/2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bilgiyazan.malzemeiste.adminpaneli.R;

/**
 * Simple view holder for a single text view.
 */
public class YedekViewTitleHolder extends RecyclerView.ViewHolder {

    private TextView mTextView;
    private TextView mTextView1;

    public YedekViewTitleHolder(View view) {
        super(view);

        mTextView = (TextView) view.findViewById(R.id.text_title);
        mTextView1 = (TextView) view.findViewById(R.id.see_all);
    }

    public void bindItem(String text, int position, String see_all, final String from) {
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

                mTextView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (from.equals("makinalar_roland")) {

                        }
                    }
                });
                mTextView1.setVisibility(View.VISIBLE);
            }


        }


    }

    @Override
    public String toString() {
        return mTextView.getText().toString();
    }
}