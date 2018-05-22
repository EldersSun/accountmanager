package com.accountingmanager.Sys.Widgets.GroupRecyclerView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 分组导航栏
 * Created by Home-Pc on 2017/5/4.
 */

public class LatterView extends LinearLayout {

    private Context context;

    private CharacterClickListener mListener;


    public LatterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        setOrientation(VERTICAL);

        initWidgets();
    }

    private void initWidgets(){

        addView(buildTextLayout("HOT"));

        for (char i = 'A'; i <= 'Z'; i++) {
            final String character = i + "";
            TextView tv = buildTextLayout(character);

            addView(tv);
        }

        addView(buildTextLayout("#"));
    }

    private TextView buildTextLayout(final String character) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1);

        TextView tv = new TextView(context);
        tv.setLayoutParams(layoutParams);
        tv.setGravity(Gravity.CENTER);
        tv.setClickable(true);

        tv.setText(character);

        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.clickCharacter(character);
                }
            }
        });
        return tv;
    }

//    private ImageView buildImageLayout() {
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1);
//
//        ImageView iv = new ImageView(context);
//        iv.setLayoutParams(layoutParams);
//
//        iv.setBackgroundResource(R.mipmap.arrow);
//
//        iv.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mListener != null) {
//                    mListener.clickArrow();
//                }
//            }
//        });
//        return iv;
//    }

    public void setCharacterListener(CharacterClickListener listener) {
        mListener = listener;
    }

    public interface CharacterClickListener {
        void clickCharacter(String character);

        void clickArrow();
    }
}
