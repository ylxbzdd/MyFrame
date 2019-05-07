package com.example.administrator.myframe.fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myframe.R;
import com.example.administrator.myframe.base.BaseFragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
