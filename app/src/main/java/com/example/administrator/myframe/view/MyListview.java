package com.example.administrator.myframe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 *
 * Created by admin on 2017/3/20.
 */

public class MyListview extends ListView {
    public MyListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int i = Integer.MAX_VALUE >> 2;
//        int ms = MeasureSpec.makeMeasureSpec(i, MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, ms);
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }

}
