package com.example.administrator.myframe.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myframe.R;
import com.example.administrator.myframe.base.BaseFragment;


/**
 * Created by Administrator on 13:46.
 */
public class OneFragment extends BaseFragment {


    @Override
    public View initview(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_one, null);
        TextView textView = new TextView(context);
        textView.setText("是是是");
        return view;
    }

    @Override
    public void initdata() {

    }
}
