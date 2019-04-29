package com.example.administrator.myframe.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hq
 * @ClassName: CrashHandler
 * @Description UncaughtException处理类, 当程序发生Uncaught异常的时候, 有该类来接管程序, 并记录发送错误报告.
 * 需要在Application中注册，为了要在程序启动器就监控整个程序。
 * @date 2015年1月20日 下午4:58:57
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    public static final String TAG = "fengzi";
    //CrashHandler实例
    private static CrashHandler instance;
    //自定义线程池
    private static ExecutorService FULL_TASK_EXECUTOR;
    //要发送的服务器地址
    private static String ServerAddress = "http://192.168.1.121:8080/YiSaPushServer/collections.jsp";

    static {
        FULL_TASK_EXECUTOR = (ExecutorService) Executors.newCachedThreadPool();
    }

    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //程序的Context对象
    private Context mContext;
    //用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();
    //用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    ;

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        if (instance == null)
            instance = new CrashHandler();
        return instance;
    }

    /**
     * 初始化
     */
    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            //退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        //收集设备参数信息
        collectDeviceInfo(mContext);
        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉,程序开小差了,即将退出.", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        //保存日志文件
        saveCatchInfo2File(ex);
        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
//                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }


    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private String saveCatchInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        sb.append("Android -" + AppUtils.getDate() + "\r\n");
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\r\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
//            //提交到服务器
//            OkHttpClientManager.postAsyn(common.BASEURL3, new OkHttpClientManager.ResultCallback<String>() {
//                @Override
//                public void onError(Request request, Exception e) {
//                    LogUtils.i_fengzi("错误" + e.toString());
//                }
//
//                @Override
//                public void onResponse(String response) {
//                    LogUtils.i_fengzi("response" + response);
//
//                }
//            }, new OkHttpClientManager.Param("count", sb.toString()));
            //保存到本地
            String fileName = "log5.txt";
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Sucai";
            File file = new File(path);
            if (!file.isFile()) {
                file.mkdirs();
            }
            OutputStream out = new FileOutputStream(file.getPath() + "/" + fileName, true);
            PrintStream ps = new PrintStream(out);
            ps.println(sb.toString());
            ps.close();
            Intent service = new Intent(mContext, LogService.class);
            mContext.startService(service);
            return fileName;
        } catch (Exception e) {
//            Log.e(TAG, "an error occured while writing file...", e);
            LogUtils.i_fengzi("","e" + e.toString());
        }
        return null;
    }
}
