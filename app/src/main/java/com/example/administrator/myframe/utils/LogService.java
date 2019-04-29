package com.example.administrator.myframe.utils;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.squareup.okhttp.Request;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by Administrator on 2017/5/4.
 */

public class LogService extends Service {
    int cishu = 0;
    private StringBuffer sb;
    private File file1;
    private String url="http://47.92.5.70/miaozan1/uploads/error_log/error_log.php";
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //提交到服务器
                    OkHttpClientManager.postAsyn(url, new OkHttpClientManager.ResultCallback<String>() {
                        @Override
                        public void onError(Request request, Exception e) {
//                            LogUtils.i_fengzi("错误" + e.toString());
                            cishu++;
                            if (cishu <= 10)
                                handler.sendEmptyMessageDelayed(1, 4000);
                        }

                        @Override
                        public void onResponse(String response) {
//                            LogUtils.i_fengzi(":sssresponse" + response);
                            if (file1 != null)
                                file1.delete();

                        }
                    }, new OkHttpClientManager.Param("count", "fengzi" + sb.toString()));
                    break;
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
//        LogUtils.i_fengzi("sssss");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        String fileName = "yuyu.log";
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/yuyu/log";
        file1 = new File(path + "/" + fileName);
        sb = new StringBuffer();
        try {
            FileInputStream fin = new FileInputStream(file1);
            byte[] buffer = new byte[1024 * 1024];
            int read = 0;
            while ((read = fin.read(buffer)) != -1) {
                sb.append(new String(buffer));
//                LogUtils.i_fengzi("buffer.toString():" + new String(buffer,"utf-8"));
            }
            fin.close();
//            LogUtils.i_fengzi("ssss:" + sb.toString().length());
            handler.sendEmptyMessageDelayed(1, 4000);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
