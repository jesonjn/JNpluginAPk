package net.jeson.jnpluginlibrary.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by jiangneng on 6/28/16.
 */
public class AppUtils {
    /**
     * 从apk文件获得程序的包名
     *
     * @param context
     * @param apkPath
     * @return
     */
    public static String getPackageNameFromApk(Context context, String apkPath) {

        PackageInfo apkInfo = context.getPackageManager().getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        Log.i("NscAppManger", "apkPath: " + apkPath);
        if (apkInfo != null) {
            return apkInfo.packageName;
        }
        return null;
    }
}
