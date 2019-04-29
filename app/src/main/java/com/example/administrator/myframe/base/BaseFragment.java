package com.example.administrator.myframe.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 14:04.
 */
public abstract class BaseFragment extends Fragment {

    public Activity context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();
        View view =  initview(inflater,container);
        initdata();
        return view;
    }

    public abstract View initview(LayoutInflater inflater,  ViewGroup container);

    public abstract void initdata();

}
