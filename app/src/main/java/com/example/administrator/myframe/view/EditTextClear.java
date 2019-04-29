package com.example.administrator.myframe.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.myframe.R;


/**
 * Created by admin on 2017/5/11.
 * 自定义 EditText清空
 */

public class EditTextClear extends android.support.v7.widget.AppCompatEditText {


    private Drawable drawable;

    public EditTextClear(Context context) {
        super(context);
        inti();
    }

    public EditTextClear(Context context, AttributeSet attrs) {
        super(context, attrs);
        inti();
    }

    public EditTextClear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inti();
    }

    private void inti() {
        drawable = getResources().getDrawable(R.drawable.ic_clear_default);
        //设置图标的位置以及大小,getIntrinsicWidth()获取显示出来的大小而不是原图片的带小
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        //内容变化监听
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //检测状态，检查是否显示删除按钮
                updateCleanable(length(), true);
            }
        });
        //获取焦点监听
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //检测状态，检查是否显示删除按钮
                updateCleanable(length(), hasFocus);
            }
        });
    }

    /**
     * 当内容不为空，而且获得焦点，才显示右侧删除按钮
     * @param length
     * @param hasFocus
     */
    private void updateCleanable(int length, boolean hasFocus) {
        if (length>0&&hasFocus){
            setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        }else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }

    /**
     * getTotalPaddingRight()图标左边缘至控件右边缘的距离
       getWidth() - getTotalPaddingRight()表示从最左边到图标左边缘的位置
       getWidth() - getPaddingRight()表示最左边到图标右边缘的位置
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //可以获得上下左右四个drawable，右侧排第二。图标没有设置则为空。
        Drawable drawable2 = getCompoundDrawables()[2];
        if (drawable2!=null&&event.getAction()==MotionEvent.ACTION_DOWN){
            //检查点击的位置是否是右侧的删除图标
            boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                    && (event.getX() < ((getWidth() - getPaddingRight())));
            if (touchable){
                setText("");
            }

        }

        return super.onTouchEvent(event);
    }
}
