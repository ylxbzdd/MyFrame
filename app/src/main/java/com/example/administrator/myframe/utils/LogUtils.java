package com.example.administrator.myframe.utils;

import com.example.administrator.myframe.base.common;
import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2016/12/6.
 */

public class LogUtils {

    public static void i_fengzi(String s, String msg) {
        if (common.kaifa) {
//            new Thread() {
//                @Override
//                public void run() {
            Logger.t("fengzi" + s).wtf(msg);
//                }
//            }.start();
        }
    }

    public static void i_zz(String msg) {
        if (common.kaifa) {
            Logger.t("zz").json(msg);
        }
    }

    public static void i_zzz(final String msg) {
        if (common.kaifa) {
            new Thread() {
                @Override
                public void run() {
                    Logger.t("zzz").wtf(msg);
                }
            }.start();
        }
    }


    public static void i_wzz(final String s, final String msg) {
        if (common.kaifa) {
            new Thread() {
                @Override
                public void run() {
                    Logger.t("wzz" + s).wtf(msg);
                }
            }.start();

        }
    }
}
