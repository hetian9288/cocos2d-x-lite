package org.cocos2dx.lib;

import android.app.Application;
import android.content.pm.ApplicationInfo;

import java.io.File;

public class SharedVisit {
    public static Application application;
    public static Cocos2dxActivity gameActivity;
    private static String rootPath;

    private static int sIsDebugMode;
    public static boolean isDebugMode() {
        if (sIsDebugMode == -1) {
            boolean isDebug = application.getApplicationInfo() != null
                    && (application.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            sIsDebugMode = isDebug ? 1 : 0;
        }
        return sIsDebugMode == 1;
    }

    public static void setRootPath(String path) {
        rootPath = path;
        File dir = new File(rootPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static String getRootPath(String appid) {
        if (rootPath == null) {
            String path = application.getFilesDir().getAbsolutePath();
            int lastSlash = path.lastIndexOf("/");
            String appPath = path.substring(0, lastSlash + 1);
            File rootDir = new File(appPath + "/plugins/mingame");
            if (!rootDir.exists()) {
                rootDir.mkdirs();
            }
            rootPath = rootDir.getAbsolutePath();
        }
        File dir = new File(rootPath, appid);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getAbsolutePath();
    }
}
