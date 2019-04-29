package com.example.administrator.myframe.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myframe.R;
import com.example.administrator.myframe.base.BaseFragment;


/**
 * Created by Administrator on 13:46.
 */
public class TwoFragment extends BaseFragment {

    @Override
    public View initview(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_two, null);
        return view;
    }

    @Override
    public void initdata() {

    }
}
