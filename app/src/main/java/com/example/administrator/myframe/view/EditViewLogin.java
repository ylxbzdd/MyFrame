package com.example.administrator.myframe.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.myframe.R;


/**
 * Created by admin on 2017/5/11.
 */

public class EditViewLogin extends android.support.v7.widget.AppCompatEditText {

    private Drawable drawable;
    private boolean isShow;

    public EditViewLogin(Context context) {
        super(context);
        inti();
    }

    public EditViewLogin(Context context, AttributeSet attrs) {
        super(context, attrs);
        inti();
    }

    public EditViewLogin(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inti();
    }

    private void inti() {
        drawable = getResources().getDrawable(R.drawable.ic_visibility);
        //设置图标的位置以及大小,getIntrinsicWidth()获取显示出来的大小而不是原图片的带小
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isShow(length(), isShow,drawable);
            }
        });

        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                isShow = hasFocus;
                isShow(length(), isShow,drawable);
            }
        });
    }

    private void isShow(int length, boolean isShow,Drawable d) {
        if (length > 0 && isShow) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Drawable dra = getCompoundDrawables()[2];
        if (event.getAction() == MotionEvent.ACTION_DOWN && dra != null) {
            boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                    && (event.getX() < ((getWidth() - getPaddingRight())));
            if (touchable) {
                drawable = getResources().getDrawable(R.drawable.ic_visibility_off);
                //设置为可见
                this.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }else {
                //设置为密码模式
                this.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }

        }
         //手势离开时
        if (event.getAction()==MotionEvent.ACTION_UP){
            drawable = getResources().getDrawable(R.drawable.ic_visibility);
            this.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        return super.onTouchEvent(event);
    }
}
