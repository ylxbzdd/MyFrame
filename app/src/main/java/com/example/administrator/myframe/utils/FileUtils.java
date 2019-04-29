package com.example.administrator.myframe.utils;

import android.content.Context;

import java.io.IOException;


/**
 * //
 * Created by FengZi on 2017/6/6 17:47.
 */

public class FileUtils {
    public static void out(Context context, String file_name, String con) throws IOException {
//        FileOutputStream outputStream = context.openFileOutput(file_name, Context.MODE_PRIVATE);
//        outputStream.write(con.getBytes("UTF-8"));
//        outputStream.close();
         AppUtils.savesp(context, file_name, con);
    }

    public static String in(Context context, String file_name) throws IOException {
//        FileInputStream inputStream = context.openFileInput( file_name);
//        byte[] bytes = new byte[1024 * 2];
//        StringBuffer sb = new StringBuffer();
//        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
//        while (inputStream.read(bytes) != -1) {
//            arrayOutputStream.write(bytes, 0, bytes.length);
//        }
//        inputStream.close();
//        arrayOutputStream.close();
//        String content = new String(arrayOutputStream.toByteArray());
//        return content;
        return AppUtils.getsp(context, file_name, "");
    }
}
