package net.jeson.jnpluginlibrary;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import net.jeson.jnpluginlibrary.activity.ContextImpl;
import net.jeson.jnpluginlibrary.utils.AppUtils;
import net.jeson.jnpluginlibrary.utils.LoaderUtil;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;

import dalvik.system.DexClassLoader;

/**
 * Created by jiangneng on 6/28/16.
 */
public class JNPluginManager {
    public static  JNPluginMode  initLoadApkWebView(Activity activity,File apkFile,View view)throws ClassNotFoundException,NoSuchMethodException,InstantiationException,IllegalAccessException,InvocationTargetException{
        DexClassLoader loader= LoaderUtil.loadApk(activity,apkFile.getAbsolutePath());
        String appKey=  AppUtils.getPackageNameFromApk(activity,apkFile.getAbsolutePath());
        ContextImpl context=  LoaderUtil.getUnInstalledResource(apkFile.getAbsolutePath(),activity,loader);
        try {
            Class   aClass=loader.loadClass(appKey+".main");
            final Object object = aClass.newInstance();
            JNPluginMode   mode=new JNPluginMode();
            mode.object=object;
            mode.appKey=appKey;
             Method initMethod= aClass.getMethod("init",Observer.class,String.class);
            mode.mInitMethod=initMethod;
            Method setActivitytMethod= aClass.getMethod("setActivity",Activity.class);
            setActivitytMethod.setAccessible(true);
            setActivitytMethod.invoke(object,activity);

            Method setResourcesMethod= aClass.getMethod("setResources",Resources.class);
            setResourcesMethod.setAccessible(true);
            setResourcesMethod.invoke(object,context.getmResources());

            Method setWebViewMethod= aClass.getMethod("setWebView",View.class);
            setWebViewMethod.setAccessible(true);
            setWebViewMethod.invoke(object,view);

            Method getPermissionMethod=aClass.getMethod("getPermission",null);
            getPermissionMethod.setAccessible(true);
              Object  permission=getPermissionMethod.invoke(object,null);
            if(permission!=null) {
                mode.permissions =( String[])permission;
            }
            Method getPluginNameMethod=aClass.getMethod("getPluginName",null);
            getPluginNameMethod.setAccessible(true);
             Object pluginName=getPluginNameMethod.invoke(object,null);
            if(pluginName!=null) {
                mode.pluginName = pluginName.toString();
            }

            mode.onDestroyMethod=aClass.getMethod("onDestroy",null);
            mode.onPauseMethod=aClass.getMethod("onPause",null);
            mode.onRestartMethod=aClass.getMethod("onRestart",null);
            mode.onRestoreInstanceStateMethod=aClass.getMethod("onRestoreInstanceState", Bundle.class);
            mode.onResumeMethod=aClass.getMethod("onResume",null);
            mode.onStartMethod=aClass.getMethod("onStart",null);
            mode.context=context;

             return mode;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw  e;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw  e;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw  e;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw  e;
        }
        return null;
    }
}
