/****************************************************************************
 Copyright (c) 2018 Xiamen Yaji Software Co., Ltd.

 http://www.cocos2d-x.org

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
 ****************************************************************************/
package org.cocos2dx.lib;

import android.app.Activity;
import android.os.Build;
import android.view.View;

public class Utils {
    private static Activity sActivity = null;

    public static void setActivity(final Activity activity) {
        Utils.sActivity = activity;
    }

    public static void hideVirtualButton() {
        if (Build.VERSION.SDK_INT >= 19 &&
                null != Utils.sActivity) {
            // use reflection to remove dependence of API level

            Class viewClass = View.class;
            final int SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION = Cocos2dxReflectionHelper.<Integer>getConstantValue(viewClass, "SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION");
            final int SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN = Cocos2dxReflectionHelper.<Integer>getConstantValue(viewClass, "SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN");
            final int SYSTEM_UI_FLAG_HIDE_NAVIGATION = Cocos2dxReflectionHelper.<Integer>getConstantValue(viewClass, "SYSTEM_UI_FLAG_HIDE_NAVIGATION");
            final int SYSTEM_UI_FLAG_FULLSCREEN = Cocos2dxReflectionHelper.<Integer>getConstantValue(viewClass, "SYSTEM_UI_FLAG_FULLSCREEN");
            final int SYSTEM_UI_FLAG_IMMERSIVE_STICKY = Cocos2dxReflectionHelper.<Integer>getConstantValue(viewClass, "SYSTEM_UI_FLAG_IMMERSIVE_STICKY");
            final int SYSTEM_UI_FLAG_LAYOUT_STABLE = Cocos2dxReflectionHelper.<Integer>getConstantValue(viewClass, "SYSTEM_UI_FLAG_LAYOUT_STABLE");

            // getWindow().getDecorView().setSystemUiVisibility();
            final Object[] parameters = new Object[]{SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    | SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    | SYSTEM_UI_FLAG_IMMERSIVE_STICKY};
            Cocos2dxReflectionHelper.<Void>invokeInstanceMethod(Utils.sActivity.getWindow().getDecorView(),
                    "setSystemUiVisibility",
                    new Class[]{Integer.TYPE},
                    parameters);
        }
    }

    // 生成小游戏的根目录路径
    public static File getMingameRootPath(Cocos2dxActivity activity) {
        return getMingameRootPath(activity, activity.getAppid());
    }

    // 生成小游戏的根目录路径
    public static File getMingameRootPath(Context context, String appid) {
        String path = context.getFilesDir().getAbsolutePath();
        int lastSlash = path.lastIndexOf("/");
        String rootPath = path.substring(0, lastSlash + 1);
        File rootDir = new File(rootPath + "/plugins/mingame/" + appid);
        if (!rootDir.exists()) {
            rootDir.mkdirs();
        }
        return rootDir;
    }

    // 在小游戏的根目录下生成一个目录
    public static File mingamePathMkdir(Cocos2dxActivity context, String dirName) {
        String appid = context.getAppid();
        return mingamePathMkdir(context, appid, dirName);
    }

    // 在小游戏的根目录下生成一个目录
    public static File mingamePathMkdir(Cocos2dxActivity context, String appid, String dirName) {
        if (FileUtil.isDiskAvailable()) {
            File rootPath = getMingameRootPath(context, appid);
            rootPath = new File(rootPath, dirName);
            if (rootPath.exists() || rootPath.mkdirs()) {
                return rootPath;
            }
        }

        return null;
    }

    // 小游戏源码目录
    public static File mingameSourcePath(Cocos2dxActivity context) {
        return mingamePathMkdir(context, "source");
    }

    public static File mingameSourcePath(Cocos2dxActivity context, String appid) {
        return mingamePathMkdir(context, appid, "source");
    }

    public static String mingameSourceJoinPath(Cocos2dxActivity context, String path) {
        String sourcePath = mingamePathMkdir(context, "source").getAbsolutePath();
        if (path.startsWith("/")) {
            return sourcePath + path;
        }
        return sourcePath + "/" + path;
    }

    // 源码目录创建文件夹
    public static File mingameSourceMkdir(Cocos2dxActivity context, String dirName) {
        File root = mingameSourcePath(context);
        File newDir = new File(root, dirName);
        if (newDir.exists() || newDir.mkdirs()) {
            return newDir;
        }
        return null;
    }

    public static File mingameSourceMkdir(Cocos2dxActivity context, String appid, String dirName) {
        File root = mingameSourcePath(context, appid);
        File newDir = new File(root, dirName);
        if (newDir.exists() || newDir.mkdirs()) {
            return newDir;
        }
        return null;
    }

    // 小游戏缓存目录
    public static File mingameCachePath(Cocos2dxActivity context) {
        return mingamePathMkdir(context, "caches");
    }

    public static File mingameCachePath(Cocos2dxActivity context, String appid) {
        return mingamePathMkdir(context, appid, "caches");
    }

    // 缓存目录创建文件夹
    public static File mingameCacheMkdir(Cocos2dxActivity context, String dirName) {
        File root = mingameCachePath(context);
        File newDir = new File(root, dirName);
        if (newDir.exists() || newDir.mkdirs()) {
            return newDir;
        }
        return null;
    }

    public static File mingameCacheMkdir(Cocos2dxActivity context, String appid, String dirName) {
        File root = mingameCachePath(context, appid);
        File newDir = new File(root, dirName);
        if (newDir.exists() || newDir.mkdirs()) {
            return newDir;
        }
        return null;
    }

    public static String mingameCacheJoinPath(Cocos2dxActivity context, String path) {
        String cachePath = mingameCachePath(context).getAbsolutePath();
        if (path.startsWith("/")) {
            return cachePath + path;
        }
        return cachePath + "/" + path;
    }

    // 小程序数据目录
    public static File mingameDBPath(Cocos2dxActivity context) {
        return mingamePathMkdir(context, "databases");
    }

    public static File mingameDBPath(Cocos2dxActivity context, String appid) {
        return mingamePathMkdir(context, appid, "databases");
    }

    // 数据目录创建目录
    public static File mimappDBMkdir(Cocos2dxActivity context, String dirName) {
        File root = mingameDBPath(context);
        File newDir = new File(root, dirName);
        if (newDir.exists() || newDir.mkdirs()) {
            return newDir;
        }
        return null;
    }

    public static File mingameDBMkdir(Cocos2dxActivity context, String appid, String dirName) {
        File root = mingameDBPath(context, appid);
        File newDir = new File(root, dirName);
        if (newDir.exists() || newDir.mkdirs()) {
            return newDir;
        }
        return null;
    }
}