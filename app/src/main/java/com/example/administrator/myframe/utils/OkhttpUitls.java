package com.example.administrator.myframe.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * Created by FengZi on 2017/6/9 15:58.
 */

public class OkhttpUitls implements java.io.Serializable {

    public OkHttpClient mOkHttpClient;
    private volatile static OkhttpUitls mInstance = null;
    private static ArrayList<String> cookie = new ArrayList<>();//跨站点操作时需要对自行对不同cookie进行保存，访问时设置cookie以延续会话
    private static HashMap<String, String> mapcookie = new HashMap<>();
    //获取UI线程
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private OkhttpUitls() {
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(6000, TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(6000, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(6000, TimeUnit.SECONDS);
    }

    public static void goout() {
        if (mInstance != null) {
            mInstance.cookie.clear();
            mInstance = null;
        }
    }

    private Object readResolve() {
        return mInstance;
    }

    private static Class getClass(String classname)
            throws ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        if (classLoader == null)
            classLoader = OkhttpUitls.class.getClassLoader();

        return (classLoader.loadClass(classname));
    }

    //回调接口
    public interface MOkCallBack {
        void onSuccess(String str);

        void onError();
    }

    //获取实例
    public static OkhttpUitls getInstance() {
        if (mInstance == null) {
            synchronized (OkhttpUitls.class) {
                if (mInstance == null) {
                    mInstance = new OkhttpUitls();
                }
            }
        }
        return mInstance;
    }

    /**
     * get方式进行网络访问携带自定义请求头
     *
     * @param url       地址
     * @param mCallBack 回调
     */
    public void get(final String url, final MOkCallBack mCallBack, Header[] header) {
        if (header == null) header = new Header[]{};
        Request.Builder url1 = new Request.Builder().url(url);
        addcookie(url1);
        for (Header q : header) {
            url1.addHeader(q.key, q.value);
        }
        final Request request = url1
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onFailure(Request request, IOException e) {
                outError(e, url);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallBack.onError();
                    }
                });
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                savecookie(response);
                final String re = response.body().string();
                mHandler.post(new Runnable() {
                    public void run() {
                        mCallBack.onSuccess(re);
                    }
                });
            }
        });
    }//get

    /**
     * get方式进行网络访问
     *
     * @param url       地址
     * @param mCallBack 回调
     */
    public void get(boolean b, final String url, final MOkCallBack mCallBack) {
        Request.Builder url1 = new Request.Builder()
                .url(url);
        addcookie(url1);
        final Request request = url1
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onFailure(Request request, IOException e) {
                outError(e, url);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallBack.onError();
                    }
                });
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                savecookie1(response);
                final String re = response.body().string();
                mHandler.post(new Runnable() {
                    public void run() {
                        mCallBack.onSuccess(re);
                    }
                });
            }
        });
    }//get

    /**
     * get方式进行网络访问
     *
     * @param url       地址
     * @param mCallBack 回调
     */
    public void get(String url, final MOkCallBack mCallBack) {
        get(url, mCallBack, null);
    }//get


    /**
     * get方式进行图片展示
     *
     * @param url       地址
     * @param mCallBack 回调
     */
    public void get(final ImageView view, final String url, final MOkCallBack mCallBack, Header[] header) {
        if (header == null) header = new Header[]{};
        Request.Builder url1 = new Request.Builder()
                .url(url);
        addcookie(url1);
        for (Header q : header) {
            url1.addHeader(q.key, q.value);
        }
        final Request request = url1
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onFailure(Request request, IOException e) {
                outError(e, url);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallBack.onError();
                    }
                });
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                savecookie(response);
                InputStream is = null;
                try {
                    is = response.body().byteStream();
                    final Bitmap bitmap = BitmapFactory.decodeStream(is);
//                    view.setImageBitmap(bitmap);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.setImageBitmap(bitmap);
                        }
                    });
                    mHandler.post(new Runnable() {
                        public void run() {
                            mCallBack.onSuccess("");
                        }
                    });
                } catch (Exception e) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mCallBack.onError();
                        }
                    });

                } finally {
                    if (is != null) try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }//get

    /**
     * get方式进行图片展示
     *
     * @param url       地址
     * @param mCallBack 回调
     */
    public void get(boolean b, final ImageView view, final String url, final MOkCallBack mCallBack, Header[] header) {
        if (header == null) header = new Header[]{};
        Request.Builder url1 = new Request.Builder()
                .url(url);
        addcookie(url1);
        for (Header q : header) {
            url1.addHeader(q.key, q.value);
        }
        final Request request = url1
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onFailure(Request request, IOException e) {
                outError(e, url);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallBack.onError();
                    }
                });
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                savecookie(response);
                InputStream is = null;
                try {
                    is = response.body().byteStream();
                    final Bitmap bitmap = BitmapFactory.decodeStream(is);
                    mHandler.post(new Runnable() {
                        public void run() {
                            view.setImageBitmap(bitmap);
                            mCallBack.onSuccess("");
                        }
                    });
                } catch (Exception e) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mCallBack.onError();
                        }
                    });

                } finally {
                    if (is != null) try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }//get

    /**
     * post方式进行网络访问
     *
     * @param url       地址
     * @param
     * @param mCallBack 接口
     */
    public void post(String url, final MOkCallBack mCallBack, Param... params) {
        post(url, mCallBack, null, params);
    }

    public void post(String url, final MOkCallBack mCallBack) {
        post(url, mCallBack, new Param[]{});
    }


    public void post(final String url, final MOkCallBack mCallBack, Header[] header, Param... params) {
        if (header == null) header = new Header[]{};
        if (params == null) params = new Param[]{};
        Request.Builder url1 = new Request.Builder()
                .url(url);
        addcookie(url1);
        for (Header q : header) {
            url1.addHeader(q.key, q.value);
        }
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Param param : params) {
            if (TextUtils.isEmpty(param.key) || TextUtils.isEmpty(param.value)) return;
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        final Request request = url1
                .post(requestBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onFailure(Request request, IOException e) {
                LogUtils.i_fengzi("","onError--->" + url + "<--->" + e.toString());
                outError(e, url);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallBack.onError();
                    }
                });
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                savecookie(response);
                final String re = response.body().string();
                mHandler.post(new Runnable() {
                    public void run() {
                        mCallBack.onSuccess(re);
                    }
                });
            }

        });

    }//post

    public void post(boolean b, final String url, final MOkCallBack mCallBack, Header[] header, Param... params) {
        if (header == null) header = new Header[]{};
        if (params == null) params = new Param[]{};
        Request.Builder url1 = new Request.Builder()
                .url(url);
        addcookie(url1);
        for (Header q : header) {
            url1.addHeader(q.key, q.value);
        }
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Param param : params) {
            if (TextUtils.isEmpty(param.key) || TextUtils.isEmpty(param.value)) return;
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        final Request request = url1
                .post(requestBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onFailure(Request request, IOException e) {
                LogUtils.i_fengzi("","onError--->" + url + "<--->" + e.toString());
                outError(e, url);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallBack.onError();
                    }
                });
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                savecookie1(response);
                final String re = response.body().string();
                mHandler.post(new Runnable() {
                    public void run() {
                        mCallBack.onSuccess(re);
                    }
                });
            }

        });

    }//post

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void outError(IOException e, String url) {
        StringBuffer sb = new StringBuffer();
        ArrayList<String> cookie = getCookie();
        for (String s : cookie) {
            sb.append(s);
        }
        AppUtils.out("onError", url + "\r\n" + e.toString() + "\r\n" + "coocookie:" + sb.toString());
    }

//    public void post(final String url, final MOkCallBack mCallBack, Header[] header, String json, Param... params) {
//        if (header == null) header = new Header[]{};
//        if (json == null) json = "";
//        if (params == null) params = new Param[]{};
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
//        //创建一个请求对象
////        final Request request = new Request.Builder()
////                .url(url)
////                .post(requestBody)
////                .build();
//        Request.Builder url1 = new Request.Builder()
//                .url(url);
//        addcookie(url1);
//        for (Header q : header) {
//            url1.addHeader(q.key, q.value);
//        }
////        FormEncodingBuilder builder = new FormEncodingBuilder();
////        for (Param param : params) {
////            builder.add(param.key, param.value);
////        }
//        final Request request = url1
//                .post(requestBody)
//                .build();
//        Call call = mOkHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                outError(e, url);
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mCallBack.onError();
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(final Response response) throws IOException {
//
//                savecookie(response);
//                final String re = response.body().string();
//                mHandler.post(new Runnable() {
//                    public void run() {
//                        mCallBack.onSuccess(re);
//                    }
//                });
//            }
//
//        });
//
//    }//post json

    private synchronized void addcookie(Request.Builder url1) {
        StringBuffer sb = new StringBuffer();
        Set<String> strings = mapcookie.keySet();
        for (String s : strings) {
            sb.append(s).append("=").append(mapcookie.get(s)).append(";");
        }
//        for (String s : cookie) {
//            sb.append(s + ";");
//        }

//        LogUtils.i_zzz("cookie:            " + sb.toString());
        String s = sb.toString();
        url1.addHeader("cookie", s.substring(0, s.length() > 1 ? s.length() - 1 : s.length()));
    }

    /**
     * 其他页面cookie
     *
     * @param response
     */
    private void savecookie(Response response) {
        savecookie1(response);
//        List<String> headers = response.headers("set-cookie");
//        Set<String> strings = mapcookie.keySet();
//        StringBuilder sb = new StringBuilder();
//        String ssss = "";
//        for (int i = 0; i < headers.size(); i++) {
//            String s = headers.get(i);
//            String[] split = s.split("=");
//            for (String ss : strings) {
//                if (split[0].equals(ss)) {
//                    for (int j = 1; j < split.length; j++) {
////                        sb.append(split[j]);
//                        ssss += split[j] + "=";
//                        if (j == split.length - 1) {
//                            ssss = ssss.substring(0, ssss.length() - 1);
//                        }
//                    }
//                    mapcookie.put(split[0], ssss);
//                    ssss = "";
//                } else {
//                    String aa = "";
//                    for (int j = 1; j < split.length; j++) {
//                        aa += split[j] + "=";
//                        if (j == split.length - 1) {
//                            aa = aa.substring(0, aa.length() - 1);
//                        }
//                    }
//                    mapcookie.put(split[0], aa);
//                }
//            }
//        }

    }

    /**
     * 登录页面cookie
     *
     * @param response
     */
    private void savecookie1(Response response) {
        List<String> headers = response.headers("set-cookie");
        HashMap<String, String> lsmap = new HashMap<>();
        fengzi:
        for (String header : headers) {
//            Set<String> strings = mapcookie.keySet();
            String[] split = header.trim().split("=");
//            if (mapcookie.size() <= 0) {
            String aa = "";
            for (int j = 1; j < split.length; j++) {
                aa += split[j] + "=";
                if (j == split.length - 1) {
                    aa = aa.substring(0, aa.length() - 1);
                }
            }
            lsmap.put(split[0], aa);
//            } else {
//                for (String s : strings) {
//                    if (s.equals(split[0])) {
//                        String aa = "";
//                        for (int j = 1; j < split.length; j++) {
//                            aa += split[j] + "=";
//                            if (j == split.length - 1) {
//                                aa = aa.substring(0, aa.length() - 1);
//                            }
//                        }
//                        lsmap.put(split[0], aa);
//                    } else {
//                        String aa = "";
//                        for (int j = 1; j < split.length; j++) {
//                            aa += split[j] + "=";
//                            if (j == split.length - 1) {
//                                aa = aa.substring(0, aa.length() - 1);
//                            }
//                        }
//                        lsmap.put(split[0], aa);
//                    }
//                }
//
//            }

            mapcookie.putAll(lsmap);


//            }
//            headers1.add(header);
        }
//        cookie.addAll(headers1);
    }

    /**
     * 获取当前使用的cookie
     *
     * @return cookie
     */
    public ArrayList<String> getCookie() {
        StringBuffer sb = new StringBuffer();
        HashMap<String, String> ls = new HashMap<>();
        ls.putAll(mapcookie);
        Set<String> keySet = ls.keySet();
        for (String s : keySet) {
            sb.append(s + "=" + ls.get(s));
        }
        ArrayList<String> strings = new ArrayList<>();
        strings.add(sb.toString());
        return strings;
    }

    /**
     * 获取当前使用的cookie
     *
     * @return cookie
     */
    public HashMap<String, String> getCookie2() {
        return mapcookie;
    }

    /**
     * 设置下次访问网络的cookie,设置为""空文本则清除cookie
     *
     * @param cookie 要设置的cookie
     */
    public void setCookie(HashMap<String, String> cookie) {
        this.mapcookie = cookie;
    }

    /**
     * 设置下次访问网络的cookie,设置为""空文本则清除cookie
     *
     * @param cookie 要设置的cookie
     */
    public void setCookie(ArrayList<String> cookie) {
        this.cookie = cookie;
    }

    /**
     * 设置要不要自动跟随302重定向，默认为真
     *
     * @param followRedirects 是否自动跳转
     */
    public void setFollowRedirects(boolean followRedirects) {
        mOkHttpClient.setFollowRedirects(followRedirects);
    }

    public static class Param {
        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }

        String key;
        String value;
    }

    public static class Header {
        String key;
        String value;

        public Header() {
        }

        public Header(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
