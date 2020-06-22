package org.cocos2dx.lib;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.IOException;

// 通过上下文拦截 为cocos的localStorage根据appid生成隔离
public class CocosContext extends ContextWrapper {
    private Context mContext;
    private String appid;

    public CocosContext(Context base, String appid) {
        super(base);
        this.mContext = base;
        this.appid = appid;
    }

    /**
     * 获得数据库路径，如果不存在，则创建对象对象
     *
     * @param name
     */
    @Override
    public File getDatabasePath(String name) {
        // 获取sd卡路径
        String dbDir = Utils.mingameDBPath(SharedVisit.gameActivity).getAbsolutePath();
        String dbPath = dbDir + "/" + name;// 数据库路径

        // 数据库文件是否创建成功
        boolean isFileCreateSuccess = false;
        Log.i("MyCocosContextWrapper", "getDatabasePath: " + dbPath);
        // 判断文件是否存在，不存在则创建该文件
        File dbFile = new File(dbPath);
        if (!dbFile.exists()) {
            try {
                isFileCreateSuccess = dbFile.createNewFile();// 创建文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            isFileCreateSuccess = true;

        // 返回数据库文件对象
        if (isFileCreateSuccess)
            return dbFile;
        else
            return null;
    }

    /**
     * 重载这个方法，是用来打开SD卡上的数据库的，android 2.3及以下会调用这个方法。
     *
     * @param name
     * @param mode
     * @param factory
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode,
                                               SQLiteDatabase.CursorFactory factory) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
        return result;
    }

    /**
     * Android 4.0会调用此方法获取数据库。
     *
     * @see android.content.ContextWrapper#openOrCreateDatabase(java.lang.String,
     *	  int, android.database.sqlite.SQLiteDatabase.CursorFactory,
     *	  android.database.DatabaseErrorHandler)
     * @param name
     * @param mode
     * @param factory
     * @param errorHandler
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode,
                                               SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
        return result;
    }
}