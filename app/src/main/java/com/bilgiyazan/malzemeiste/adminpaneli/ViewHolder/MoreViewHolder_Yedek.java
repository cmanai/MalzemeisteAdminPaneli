package com.bilgiyazan.malzemeiste.adminpaneli.ViewHolder;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.bilgiyazan.malzemeiste.adminpaneli.ModelAdapter.MoreAdapterModelYedek;
import com.bilgiyazan.malzemeiste.adminpaneli.R;

import mehdi.sakout.fancybuttons.FancyButton;


public class MoreViewHolder_Yedek extends ParentViewHolder {

    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 180f;

    @NonNull
    private final ImageView mArrowExpandImageView;
    FancyButton name_more;
    boolean Exp = true;

    public MoreViewHolder_Yedek(@NonNull View itemView) {
        super(itemView);
        name_more = (FancyButton) itemView.findViewById(R.id.name_more);

        mArrowExpandImageView = (ImageView) itemView.findViewById(R.id.arrow_expand_imageview);
    }

    public void bind(@NonNull MoreAdapterModelYedek moreAdapterModel) {

     /*   mArrowExpandImageView.bringToFront();
        mArrowExpandImageView.setClipToOutline(true);
        ViewCompat.setElevation(mArrowExpandImageView, 10);*/
        name_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MoreViewHolder_Yedek.super.isExpanded()) {
                    MoreViewHolder_Yedek.super.collapseView();
                } else {
                    MoreViewHolder_Yedek.super.expandView();

                }


            }
        });
        name_more.setText(moreAdapterModel.getName());


    }

    @SuppressLint("NewApi")
    @Override
    public void setExpanded(boolean expanded) {
        super.setExpanded(expanded);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (expanded) {

                mArrowExpandImageView.setRotation(ROTATED_POSITION);
            } else {

                mArrowExpandImageView.setRotation(INITIAL_POSITION);
            }
        }
    }

    @Override
    public void onExpansionToggled(boolean expanded) {
        super.onExpansionToggled(expanded);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            RotateAnimation rotateAnimation;
            if (expanded) { // rotate clockwise
                rotateAnimation = new RotateAnimation(ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                Exp = !Exp;

            } else { // rotate counterclockwise
                rotateAnimation = new RotateAnimation(-1 * ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                Exp = !Exp;
            }

            rotateAnimation.setDuration(200);
            rotateAnimation.setFillAfter(true);
            mArrowExpandImageView.startAnimation(rotateAnimation);
        }
    }

  /*  @Override
    protected void collapseView() {
        super.collapseView();
        setExpanded(false);
        onExpansionToggled(true);
        Exp = true;
        if (mParentViewHolderExpandCollapseListener != null) {
            mParentViewHolderExpandCollapseListener.onParentCollapsed(getAdapterPosition());
        }
    }*/
}


