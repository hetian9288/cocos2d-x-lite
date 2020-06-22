package org.cocos2dx.lib;

import android.app.Application;
import android.content.pm.ApplicationInfo;

public class SharedVisit {
    public static Application application;
    public static Cocos2dxActivity gameActivity;

    private static int sIsDebugMode;
    public static boolean isDebugMode() {
        if (sIsDebugMode == -1) {
            boolean isDebug = application.getApplicationInfo() != null
                    && (application.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            sIsDebugMode = isDebug ? 1 : 0;
        }
        return sIsDebugMode == 1;
    }
}
