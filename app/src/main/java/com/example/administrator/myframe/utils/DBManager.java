package com.example.administrator.myframe.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.administrator.myframe.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class DBManager {
    private final int BUFFER_SIZE = 1024;
    public static String DB_NAME;
    public static final String PACKAGE_NAME = "com.newapp.newapp";
    public static final String DB_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + PACKAGE_NAME;
    private SQLiteDatabase database;
    private Context context;
    private File file = null;

    public DBManager(Context context, String tablename) {
//        Log.e("fz", "DBManager" + tablename);
        this.context = context;
        DB_NAME = tablename;
    }

    public void openDatabase() {
//        Log.i("fengzi", getClass().getName() + ";openDatabase");
        this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);
    }

    public SQLiteDatabase getDatabase() {
//        Log.i("fengzi", getClass().getName() + ";getDatabase");
        return this.database;
    }

    private SQLiteDatabase openDatabase(String dbfile) {
//        Log.i("fengzi", getClass().getName() + ";openDatabase");
        try {
            file = new File(dbfile);
            if (!file.exists()) {
//                Log.i("fengzi", getClass().getName() + ";!file.exists()");
                InputStream is = null;

                    is = context.getResources().openRawResource(R.raw.data);

                    if (is != null) {

                    } else {
                    }
                    FileOutputStream fos = new FileOutputStream(dbfile);
                    if (is != null) {

                    } else {
                    }
                    byte[] buffer = new byte[BUFFER_SIZE];
                    int count = 0;
                    while ((count = is.read(buffer)) > 0) {
                        fos.write(buffer, 0, count);

                        fos.flush();
                    }
                    fos.close();
                    is.close();

            }else{
//                Log.i("fengzi", getClass().getName() + ";file.exists()");
            }
            database = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
            return database;
        } catch (FileNotFoundException e) {
//            Log.i("fengzi", getClass().getName() + ";e.toString():"+e.toString());
            e.printStackTrace();
        } catch (IOException e) {
//            Log.i("fengzi", getClass().getName() + ";e.toString():"+e.toString());
            e.printStackTrace();
        } catch (Exception e) {
//            Log.i("fengzi", getClass().getName() + ";e.toString():"+e.toString());
        }
        return null;
    }

    public void closeDatabase() {
//        Log.i("fengzi", getClass().getName() + ";closeDatabase");
        if (this.database != null)
            this.database.close();
    }
}