package com.example.administrator.myframe.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.myframe.R;
import com.example.administrator.myframe.base.common;

import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.CONNECTIVITY_SERVICE;

/*** Created by Administrator on 15:07.*/
public class AppUtils {
    private static Gson gson;
    private static JSONObject jsonCity;
    private static Toast toast;
    private static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
    private static String srr = "asdugfjadslygjlsayhg156464ashgadfshgjadshggfjasdhgoyasdhogyhasdodgiyhasidogyasdig1654yadogiydasogyadsogyhadsoiyghoasdyghadsogyhhasdoigyhadsoigyasdoigyadsgioydsagoiadsygioadsy[gowyq[gtqo]asoigybashvhmHPODSHgAGO[";
    private static ConnectivityManager mConnectivityManager;

    /*** 判断包是否安装某个app** @param context* @param packageName* @return*/
    public static boolean isInstalled(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        try {
            manager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*** 安装应用程序** @param context* @param apkFile*/
    public static void installApp(Context context, File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /*** 打开应用程序** @param context* @param packageName*/
    public static void openApp(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        context.startActivity(intent);
    }

    /*** 判断当前是否是wifi** @param mContext* @return*/
    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    //判断连接网络是否可用
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
            //如果仅仅是用来判断网络连接则可以使用
            cm.getActiveNetworkInfo().isAvailable();
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }//判断 GPS是否打开

    public static boolean isGpsEnabled(Context context) {
        LocationManager lm = ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
        List<String> accessibleProviders = lm.getProviders(true);
        return accessibleProviders != null && accessibleProviders.size() > 0;
    }

    //判断WIFI是否打开
    public static boolean isWifiEnabled(Context context) {
        ConnectivityManager mgrConn = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        TelephonyManager mgrTel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return ((mgrConn.getActiveNetworkInfo() != null && mgrConn.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
    }

    //判断是否是3G网络
    public static boolean is3rd(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        if (networkINfo != null && networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    //获取今天的日期
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getTodaydata() {
        long timeMillis = System.currentTimeMillis();
        Date date = new Date(timeMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String today = dateFormat.format(date);
        return today;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getDate() {
        return getDate(System.currentTimeMillis() / 1000, "yyyy-MM-dd  HH:mm:ss");
    }

    public static double get_liangwei2(double d, int s) {
        try {
            //BigDecimal.ROUND_HALF_UP 遇到.5的情况时往上近似 ,ROUND_HALF_DOWN 遇到.5的情况时往
            BigDecimal bigDecimal = new BigDecimal(d).setScale(s, BigDecimal.ROUND_HALF_UP);
            return bigDecimal.doubleValue();
        } catch (Exception ss) {
            return 0;
        }
    }

    public static double get_liangwei2(double d) {
        return get_liangwei2(d, 3);
    }

    /*** 对double数据进行取精度.** @param value double数据.* @param scale 精度位数(保留的小数位数).* @param roundingMode 精度取值方式.* @return 精度计算后的数据.*/
    public static double round(double value, int scale, int roundingMode) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, roundingMode);
        double d = bd.doubleValue();
        bd = null;
        return d;
    }

    /*** double 相加** @param d1* @param d2* @return*/
    public static double sum(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.add(bd2).doubleValue();
    }

    /*** double 相减** @param d1* @param d2* @return*/
    public double sub(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.subtract(bd2).doubleValue();
    }

    /*** double 乘法** @param d1* @param d2* @return*/
    public static double mul(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.multiply(bd2).doubleValue();
    }

    /*** double 除法** @param d1* @param d2* @param scale 四舍五入 小数点位数* @return*/
    public static double div(double d1, double d2, int scale) {// 当然在此之前，你要判断分母是否为0，// 为0你可以根据实际需求做相应的处理
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    //将json数据转换成字符串
    public static String getJson(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    //添加空格
    public static String addkongge(String context) {
        StringBuffer sb = new StringBuffer();
        char[] chars = context.toCharArray();
        for (char aChar : chars) {
            sb.append(aChar + " ");
        }
        return sb.toString().trim();
    }

    //替换特殊符号
    public static String getS(String trim, String s) {
        trim = trim.replace(",", s);
        trim = trim.replace("*", s);
        trim = trim.replace("，", s);
        trim = trim.replace(".", s);
        trim = trim.replace(" ", s);
        trim = trim.replace("|", s);
        trim = trim.replace("-", s);
        trim = trim.replace(s + s, s);
        trim = trim.replace(s + s + s, s);
        trim = trim.replace(s + " " + s, s);
        return trim.trim();
    }

    //拼接空格
    public static String addkongge1(String trim) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i + 2 <= trim.length(); i = i + 2) {
            String substring = trim.substring(i, i + 2);
            sb.append(substring + " ");
        }
        return sb.toString().trim();
    }

    public static String getnum(String str) {
        str = str.trim();
        String str2 = "";
        if (str != null && !"".equals(str)) {
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) >= 43 && str.charAt(i) <= 57) {
                    str2 += str.charAt(i);
                }
            }
        }
        return str2;
    }

    /*** 获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行 getClass()* .getCanonicalName()*/
    public static PowerManager.WakeLock acquireWakeLock(Context context, String CanonicalName) {
        PowerManager.WakeLock wakeLock = null;
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, CanonicalName);
        if (null != wakeLock) {
            wakeLock.acquire();
        }
        return wakeLock;
    }

    // 释放设备电源锁
    public static void releaseWakeLock(PowerManager.WakeLock wakeLock) {
        if (null != wakeLock && wakeLock.isHeld()) {
            wakeLock.release();
        }
    }

    public static String get_liangwei2(String d, int s) {
        // BigDecimal bigDecimal = new BigDecimal(d).setScale(s, BigDecimal.ROUND_HALF_UP);ROUND_FLOOR BigDecimal.ROUND_DOWN保留几位小数 ex:保留三位就会把多余的截掉 BigDecimal.ROUND_UP 如果不够则添加最小单位，ex：1.26654保留三位则是1.266+0.001 = 1.267；两位则加0.01；
        BigDecimal bigDecimal = new BigDecimal(d).setScale(s, BigDecimal.ROUND_DOWN);
        return bigDecimal.toString();
    }

    //服务是否运行
    public static boolean isServiceRunning(Context context, String serviceName) {
        boolean isRunning = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> lists = am.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo info : lists) {
            // 获取运行服务再启动
            if (info.service.getClassName().equals(serviceName)) {
                return true;
            }
        }
        return isRunning;
    }

    /*** 判断当前日期是星期几** @param pTime 时间戳* @return dayForWeek 判断结果* @Exception 发生异常*/
    String pTime = "2012-03-12";

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getWeek(String pTime) {
        String Week = "星期";
        String date = timeStamp2Date(pTime, "yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(date));
        } catch (Exception e) {

        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            Week += "天";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            Week += "六";
        }
        return Week;
    }


    // unicode转utf-8
    public static String decode(String unicodeStr) {
        byte[] converttoBytes = new byte[0];
        String s2 = "tttt";
        try {
            converttoBytes = unicodeStr.getBytes("UTF-8");
            s2 = new String(converttoBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s2;
    }

    //判断是否是网址
    public static boolean isUrl1(String url) {
        Pattern pattern = Pattern.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://|[Rr][Tt][Ss][Pp]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
        return pattern.matcher(url).matches();
    }

    /*** 判断url是否是网址** @param url* @return*/
    public static boolean isUrl(String url) {
        return Linkify.addLinks(new SpannableString(url), Linkify.WEB_URLS);
    }

    //获取手机屏幕宽高
    public static int[] getWH(Activity context) {
        WindowManager wm = context.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        return new int[]{width, height};
    }

    //px转换为dp
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    //获取手机唯一标识
    public static String getsimi(Context context) {
        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String szImei = "";
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
// TODO: Consider calling
// ActivityCompat#requestPermissions
// here to request the missing permissions, and then overriding
// public void onRequestPermissionsResult(int requestCode, String[] permissions,
// int[] grantResults)
// to handle the case where the user grants the permission. See the documentation
// for ActivityCompat#requestPermissions for more details.
        } else {
            szImei = TelephonyMgr.getDeviceId();//1、获取手机唯一识别码
        }
//2、1无法获取获取2的识别码
        if (TextUtils.isEmpty(szImei) || szImei.length() != 15) {
            szImei = "35" +
                    Build.BOARD.length() % 10 +
                    Build.BRAND.length() % 10 +
                    Build.CPU_ABI.length() % 10 +
                    Build.DEVICE.length() % 10 +
                    Build.DISPLAY.length() % 10 +
                    Build.HOST.length() % 10 +
                    Build.ID.length() % 10 +
                    Build.MANUFACTURER.length() % 10 +
                    Build.MODEL.length() % 10 +
                    Build.PRODUCT.length() % 10 +
                    Build.TAGS.length() % 10 +
                    Build.TYPE.length() % 10 +
                    Build.USER.length() % 10; //13 digits
//3、1和2都无法获取产生随机数
            if (TextUtils.isEmpty(szImei) || szImei.length() != 15) {
                szImei = AppUtils.getsp(context, "szImei", "");
                if (TextUtils.isEmpty(szImei) || szImei.length() != 15) {
                    for (int i = 0; i < 15; i++) {
                        szImei += srr.charAt(AppUtils.getint(srr.length()));
                    }
                    AppUtils.savesp(context, "szImei", szImei);
                }
            }
        }
//MD5加密
        return MD5Util.encrypt(szImei);
    }

    /**
     * 返回一个一定值内的随机数
     *
     * @param i 定值
     * @return
     */
    public static int getint(int i) {
        Random ran = new Random();
        return ran.nextInt(i);
    }


    /*** 开启浏览器** @param context* @param url*/
    public static void startWEB(Context context, String url) {
        if (TextUtils.isEmpty(url)) {
            AppUtils.toast(context, "网址不能为空");
            return;
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);
    }

    /**
     * 吐司
     *
     * @param context
     * @param msg
     */
    public static void toast(Context context, String msg) {
        if (TextUtils.isEmpty(msg)) return;
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 去xml里面标签
     *
     * @param msg
     * @return
     */
    public static String getStringfoXML(String msg) {
        StringBuffer ssss = new StringBuffer();
        String[] split = msg.split(">");
        for (int k = 0; k < split.length; k++) {
            if (!split[k].startsWith("<")) {
                int end = split[k].indexOf("<");
                String substring = "";
                if (end == -1) {
                    substring = split[k];
                } else {
                    substring = split[k].substring(0, end);
                }
                ssss.append(substring);
            }
// LogUtils.i_fengzi(k+"修改后：" + ssss.toString());
        }
// LogUtils.i_fengzi("修改后：" + ssss.toString());
        return ssss.toString();
    }

    /*** 设置状态栏的颜色** @param activity* @param colorResId*/
    public static void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorResId));
                //底部导航栏
                window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //gson转换为bean对象
    public synchronized static Gson getgson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @return
     * @paramseconds精确到秒的字符串
     * @paramformatStr
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) format = "yyyy-MM-dd HH:m";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }


    /*** 判断是否是正确的省份证号码* * @param cid** @return*/
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean isRightForID
    (String cid) {
        if (!cid.matches("^\\d{17}(\\d|x|X)$")) {
            return false;
        }
        String aCity = "{'11':'北京','12':'天津','13':'河北','14':'山西','15':'内蒙古','21':'辽宁','22':'吉林','23':'黑龙江','31':'上海','32':'江苏','33':'浙江','34':'安徽','35':'福建','36':'江西','37':'山东','41':'河南','42':'湖北'," + "'43':'湖南','44':'广东','45':'广西','46':'海南','50':'重庆','51':'四川','52':'贵州','53':'云南','54':'西藏','61':'陕西','62':'甘肃','63':'青海','64':'宁夏','65':'新疆','71':'台湾','81':'香港','82':'澳门','91':'国外'}";
        if (jsonCity == null) {
            try {
                jsonCity = new JSONObject(aCity);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        int[] arrExp = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        // 加权因子
        Object[] arrValid = new Object[]{1, 0, "X", 9, 8, 7, 6, 5, 4, 3, 2};
        // 校验码
        int iSum = 0, idx;
        String sBirthday = cid.substring(6, 10) + "-" + Integer.parseInt(cid.substring(10, 12)) + "-" + Integer.parseInt(cid.substring(12, 14));
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = df.parse(sBirthday);
            calendar.setTime(d);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        try {
            Log.i("fengzi", ";js" + jsonCity + "");
            if (jsonCity.get(cid.substring(0, 2)) == null) {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (!sBirthday.equals((calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH)))) {
            return false;
        }
        for (int i = 0; i < cid.length() - 1; i++) {// 对前17位数字与权值乘积求和
            iSum += Integer.parseInt(cid.substring(i, i + 1), 10) * arrExp[i];
        }
        // 计算模（固定算法）
        idx = iSum % 11;
        // 检验第18为是否与校验码相等
        return cid.substring(17, 18).toUpperCase().equals(arrValid[idx] + "");
    }

    /*** 获得全角字符串 避免自动换行右边不对起** @param input* @return*/
    public static String getAligningString(String input) {
        if (TextUtils.isEmpty(input)) {
            return "";
        }
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375) c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /*** 设置粘贴版内容** @param context* @param content*/
    public static void setCopyContent(Context context, String content) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    /*** 获取粘贴版内容** @param context*/
    public static String getCopyContent(Context
                                                context) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        CharSequence text = cmb.getText();
        if (TextUtils.isEmpty(text)) return "";
        return text.toString().trim() + "";
    }

    /*** 打开指定包名的应用** @param packageName* @param context*/
    public static void openCLD
    (String packageName, Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
        }
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(pi.packageName);
        List<ResolveInfo> apps = packageManager.queryIntentActivities(resolveIntent, 0);
        ResolveInfo ri = apps.iterator().next();
        if (ri != null) {
            String className = ri.activityInfo.name;
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName(packageName, className);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cn);
            context.startActivity(intent);
        }
    }

    /*** 隐藏手机号中间4位** @param phone 需要隐藏的手机号* @return*/
    public static String getphone
    (String phone) {
        if (isMobileNO(phone)) {
            String newphone = "";
            newphone = phone.substring(0, 3) + "****";
            newphone += phone.substring(7, 11);
            return newphone;
        }
        return "该手机号格式错误";
    }

    /*** 获取所有的非系统app的名字** @param context* @return*/
    public static List<String> getAllAppName(Context context) {
        List<String> appList = new ArrayList<>();
        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
            String packageName = packageInfo.packageName;
            // packageInfo.versionCode;//版本号
            // packageInfo.versionName;//版本名称
            packageInfo.applicationInfo.loadIcon(context.getPackageManager());//判断是否系统应用
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                //非系统应用
                appList.add(packageName);
            } else {
                //系统应用
            }
        }
        return appList;
    }

    //从时间提取 时分秒
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getTimeFromMillisecond(Long millisecond, String dateforma) {
        if (dateforma == null) {
            dateforma = "HH:mm:ss";
        }
// SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateforma);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateforma, Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        Date date = new Date(millisecond);
        String timeStr = simpleDateFormat.format(date);
        return timeStr;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getDate(long time, String DateForma) {
        if (DateForma == null || DateForma.equals(""))
            DateForma = "yyyy-MM-dd HH:mm";
        SimpleDateFormat format = new SimpleDateFormat(DateForma);
        return format.format(new Date(time * 1000));
    }

    /*** 日期格式字符串转换成时间戳** @return* @paramdate字符串日期* @paramformat如：yyyy-MM-ddHH:mm:ss*/
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static long getTime(String date_str, String format) {
        if (format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date_str).getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    //dp转换成px
    public static float dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (dipValue * scale + 0.5f);
    }

    /*** 显示键盘** @param acticvity*/
    public static void showinput(Activity acticvity) {
        if (!(acticvity.getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)) {
            ((InputMethodManager) acticvity.getSystemService(acticvity.INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /*** 隐藏键盘** @param acticvity*/
    public static void hideinput(Activity acticvity) {
        if (acticvity == null || acticvity instanceof Context)
            return;
        if ((acticvity.getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)) {
            acticvity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }
        ((InputMethodManager) acticvity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(acticvity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        ((InputMethodManager) acticvity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(acticvity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /*** 数据加载** @param context 上下文* @return AlertDialog对象*/
    public static AlertDialog DialogDing(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.loading_dialog);//设置AlertDialog样式
        View v = View.inflate(context, R.layout.spinkitview_layout, null);
        ProgressBar bar = (ProgressBar) v.findViewById(R.id.spin_kit);
        DoubleBounce doubleBounce = new DoubleBounce();
        bar.setIndeterminateDrawable(doubleBounce);
        builder.setView(v);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    /*** 数据加载 需要添加依赖 compile 'com.github.ybq:Android-SpinKit:1.0.1'* 在项目的build中添加：** @param context 上下文* @return AlertDialog对象*/
    public static AlertDialog DialogDing(Context context, String con) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.loading_dialog2);
        //设置AlertDialog样式
        View v = View.inflate(context, R.layout.spinkitview_layout, null);
        ProgressBar bar = (ProgressBar) v.findViewById(R.id.spin_kit);
        TextView t = (TextView) v.findViewById(R.id.t);
        t.setText(con + "");
        t.setTextColor(Color.WHITE);
        DoubleBounce doubleBounce = new DoubleBounce();
        bar.setIndeterminateDrawable(doubleBounce);
        builder.setView(v);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    //dialog 需要的style/*<style name="loading_dialog2" parent="android:style/Theme.Dialog"><item name="android:windowFrame">@null</item><item name="android:windowNoTitle">true</item><!--设置AlertDialog背景色--><item name="android:windowBackground">@color/touming</item><!--是否依附于activity--><item name="android:windowIsFloating">true</item><!--AlertDialog昏暗色背景是否启用--><item name="android:backgroundDimEnabled">true</item><item name="android:windowContentOverlay">@null</item></style>*//*** 验证是否是电话号码** @param mobiles 需要验证的号码* @return 是否是电话号码*/
    public static boolean isMobileNO(String mobiles) {
        String telRegex = "[1][34578]\\d{9}";
        //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    /*** 验证是否是邮箱** @param email 需要验证的号码* @return 是否是邮箱*/
    public static boolean isEmail
    (String email) {
        if (null == email || "".equals(email))
            return false;
//        Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}");
        //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static void savesp(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences("XHB", Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
        sp = null;
    }

    public static void savesp(Context context, String key, Long value) {
        SharedPreferences sp = context.getSharedPreferences("XHB", Context.MODE_PRIVATE);
        sp.edit().putLong(key, value).commit();
        sp = null;
    }

    public static void savesp(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("XHB", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
        sp = null;
    }

    public static void savesp(Context context, String key, Integer value) {
        SharedPreferences sp = context.getSharedPreferences("XHB", Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
        sp = null;
    }

    public static String getsp(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences("XHB", Context.MODE_PRIVATE);
        return sp.getString(key, value);
    }

    public static boolean getsp(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("XHB", Context.MODE_PRIVATE);
        return sp.getBoolean(key, value);
    }

    public static int getsp(Context context, String key, int s) {
        SharedPreferences sp = context.getSharedPreferences("XHB", Context.MODE_PRIVATE);
        return sp.getInt(key, s);
    }

    public static long getsp(Context context, String key, long s) {
        SharedPreferences sp = context.getSharedPreferences("XHB", Context.MODE_PRIVATE);
        return sp.getLong(key, s);
    }

    /*** 判断网络是否连接** @param context* @return*/
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        if (null != connectivity) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void displayImage(Context context, final ImageView view, String url) {
        if (TextUtils.isEmpty(url)) {
            AppUtils.toast(context, "网址为空");
            return;
        }
        Picasso.with(context).load(url).fit().into(view);
    }


    /*** 数据截取** @param map* @param response 请求返回数据* @param value 传入的值* @return 返回key*/
    public static String getSplitData(LinkedHashMap<String, String> map, String
            response, String value) {
        String[] split = response.split("\\|");
        for (int i = 1; i < split.length; i++) {
            String s = split[i].toString().trim();
            String[] split1 = s.split(",");
            map.put(split1[0], split1[1]);
        }
        Set<String> strings = map.keySet();
        for (String s : strings) {
            String s1 = map.get(s);
            if (value.equals(s1)) {
                return s;
            }
        }
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void out(String key, String vl) {

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+common.appname;
        File file = new File(path);
        if (!file.isFile()) {
            file.mkdirs();
        }
        try {
            OutputStream out = new FileOutputStream(file.getPath() + "/message5.txt", true);
            PrintStream ps = new PrintStream(out);
            Class<?> clazz = Class.forName("android.os.Build");
            Field[] fields = clazz.getFields();
            for (Field field : fields) {
                ps.println(field.getName() + " : " + field.get(null));
            }
            ps.println(common.bendicode + "时间 : " + AppUtils.getDate());
            ps.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            ps.println(key + ":" + vl);
            ps.println("===================================分割线===================================");
            ps.println();
            ps.println();
            ps.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Process.killProcess(Process.myPid());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void out1(String key, String vl) {

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + common.appname;
        File file = new File(path);
        if (!file.isFile()) {
            file.mkdirs();
        }
        try {
            OutputStream out = new FileOutputStream(file.getPath() + "/Betting.txt", true);
            PrintStream ps = new PrintStream(out);
            ps.println(common.bendicode + "时间 : " + AppUtils.getDate());
            ps.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            ps.println(key + ":" + vl);
            ps.println("===================================分割线===================================");
            ps.println();
            ps.println();
            ps.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Process.killProcess(Process.myPid());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void out2(String key, String vl) {

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + common.appname;
        File file = new File(path);
        if (!file.isFile()) {
            file.mkdirs();
        }
        try {
            OutputStream out = new FileOutputStream(file.getPath() + "/duanqi5.txt", true);
            PrintStream ps = new PrintStream(out);
            ps.println(common.bendicode + "时间 : " + AppUtils.getDate());
            ps.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            ps.println(key + ":" + vl);
            ps.println("===================================分割线===================================");
            ps.println();
            ps.println();
            ps.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Process.killProcess(Process.myPid());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void out4(String key, String vl) {

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + common.appname;
        File file = new File(path);
        if (!file.isFile()) {
            file.mkdirs();
        }
        try {
            OutputStream out = new FileOutputStream(file.getPath() + "/jinlin.txt", true);
            PrintStream ps = new PrintStream(out);
            ps.println(common.bendicode + "时间 : " + AppUtils.getDate());
            ps.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            ps.println(key + ":" + vl);
            ps.println("===================================分割线===================================");
            ps.println();
            ps.println();
            ps.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Process.killProcess(Process.myPid());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void outlog(String key, String vl) {

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + common.appname;
        File file = new File(path);
        if (!file.isFile()) {
            file.mkdirs();
        }
        try {
            OutputStream out = new FileOutputStream(file.getPath() + "/login.txt", true);
            PrintStream ps = new PrintStream(out);
            ps.println(common.bendicode + "时间 : " + AppUtils.getDate());
            ps.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            ps.println(key + ":" + vl);
            ps.println("===================================分割线===================================");
            ps.println();
            ps.println();
            ps.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Process.killProcess(Process.myPid());
    }

    //判断是否安装微信
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    //判断是否安装支付宝
    public static boolean checkAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }


    //获取手机网络ip地址
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
            return "null";
        } catch (SocketException ex) {
            Log.e("Error", ex.toString());
        }
        return null;
    }

    //显示IP信息 ,获取手机连接网络的ip信息
    public static String setUpInfo(Activity context) {
        //获取系统的连接服务
        mConnectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        //实例化mActiveNetInfo对象
        NetworkInfo mActiveNetInfo = mConnectivityManager.getActiveNetworkInfo();//获取网络连接的信息
        // 如果是WIFI网络
        if (mActiveNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return getLocalIpAddress() + ">>wifi";
        }
        //如果是手机网络
        else if (mActiveNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return getLocalIpAddress() + ">>手机";
        } else {
            return getLocalIpAddress() + ">>未知";
        }
    }

    //将网络图片转换为bitmap
    public static Bitmap urlPicChangeBitmap(final String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            return null;
        }
    }

    //将图片保存到手机中
    public static boolean saveImg(Bitmap bitmap, String name, Context context) {
        try {
            String sdcardPath = System.getenv("EXTERNAL_STORAGE"); //获得sd卡路径
            String dir = sdcardPath + "/JIETU/"; //图片保存的文件夹名
            File file = new File(dir); //已File来构建
            if (!file.exists()) { //如果不存在 就mkdirs()创建此文件夹
                file.mkdirs();
            }
            File mFile = new File(dir + name); //将要保存的图片文件
            if (mFile.exists()) {
                AppUtils.toast(context, "该图片已存在");
                return false;
            }

            FileOutputStream outputStream = new FileOutputStream(mFile); //构建输出流
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream); //compress到输出outputStream
            Uri uri = Uri.fromFile(mFile); //获得图片的uri
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri)); //发送广播通知更新图库，这样系统图库可以找到这张图片
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    // 功能：字符串半角转换为全角// 说明：半角空格为32,全角空格为12288.// 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248// 输入参数：input -- 需要转换的字符串// 输出参数：无：// 返回值: 转换后的字符串

    public static String halfToFull(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32)
            //半角空格
            {
                c[i] = (char) 12288;
                continue;
            }
            //根据实际情况，过滤不需要转换的符号
            if (c[i] == 46) //半角点号，不转换
                continue;
            if (c[i] > 32 && c[i] < 127)
                //其他符号都转换为全角
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Date getDate4(String date_str) {
        long time = getTime(date_str, "yyyy/MM/dd HH:mm");
        return new Date(time * 1000);
    }

    // 功能：字符串全角转换为半角// 说明：全角空格为12288，半角空格为32// 其他字符全角(65281-65374)与半角(33-126)的对应关系是：均相差65248// 输入参数：input -- 需要转换的字符串// 输出参数：无：// 返回值: 转换后的字符串
    public static String fullToHalf(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288)
            //全角空格
            {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375) c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

//    //显示在界面底部，中部，头部
//    public static void showDiaplusDialog(String[] copyContent, Context context,
//                                         int gravity, AdapterView.OnItemClickListener listener) {
//        String[] titles = copyContent;
//        List<Map<String, String>> datas = new ArrayList<>();
//        for (int i = 0; i < titles.length; i++) {
//            Map<String, String> itemData = new HashMap<>();
//            itemData.put("name", titles[i]);
//            datas.add(itemData);
//        }
//        SimpleAdapter adapter = new SimpleAdapter(context, datas, R.layout.simple_text_list_item, new String[]{"name"}, new int[]{R.id.simple_text_name});
//        DialogPlus dialogPlus = DialogPlus.newDialog(context).setAdapter(adapter).setGravity(gravity).setOnItemClickListener(listener).create();
//        dialogPlus.show();
//    }
//
//    //在调用showListPopup方法之前先设置adapter和数据源
//    ArrayAdapter adapter = new ArrayAdapter<>(context, R.layout.simple_list_item, copyContent);
//    final myQMUIListPopup mListPopup = new myQMUIListPopup(context, QMUIPopup.DIRECTION_NONE, adapter);//显示在控件下方
//
//    public static void showListPopup(final myQMUIListPopup mListPopup, Context context, View v,
//                                     int preferredDirection, AdapterView.OnItemClickListener listener) {
//        mListPopup.create(QMUIDisplayHelper.dp2px(context, 100), QMUIDisplayHelper.dp2px(context, 200), listener);
//        mListPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Overridepublic
//            void onDismiss() {
//                mListPopup.dismiss();
//            }
//        });
//        mListPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_CENTER);
//        mListPopup.setPreferredDirection(preferredDirection);
//        mListPopup.show(v);
//    }
}

