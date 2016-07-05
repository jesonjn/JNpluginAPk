//package net.jeson.jnpluginlibrary.activity;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.content.res.AssetManager;
//import android.content.res.Resources;
//import android.os.Bundle;
//import android.util.Log;
//
//import net.jeson.jnpluginlibrary.JNPluginMode;
//import net.jeson.jnpluginlibrary.utils.LoaderUtil;
//
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Method;
//
//import dalvik.system.DexClassLoader;
//
///**
// * Created by jiangneng on 6/29/16.
// */
//public class JNPoxyActivity extends Activity
//{
//
//
//
////    @Override
////    protected void onStart() {
////        super.onStart();
////        if(localClass!=null) {
////            Method start;
////            try {
////                start = localClass.getMethod("onStart");
////                start.invoke(instance);
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////        }
////    }
////
////    @Override
////    protected void onResume() {
////        super.onResume();
////        if(localClass!=null) {
////            Method resume;
////            try {
////                resume = localClass.getMethod("onResume");
////                resume.invoke(instance);
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////        }
////    }
////
////    @Override
////    protected void onPause() {
////        super.onPause();
////        if(localClass!=null) {
////            Method pause;
////            try {
////                pause = localClass.getMethod("onPause");
////                pause.invoke(instance);
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////        }
////    }
////
////    @Override
////    protected void onStop() {
////        super.onStop();
////        if(localClass!=null) {
////            try {
////                Method stop = localClass.getMethod("onStop");
////                stop.invoke(instance);
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////        }
////    }
////
////    @Override
////    protected void onDestroy() {
////        super.onDestroy();
////        if(localClass!=null) {
////            try {
////                Method des = localClass.getMethod("onDestroy");
////                des.invoke(instance);
////            } catch (Exception e) {
////
////                e.printStackTrace();
////            }
////        }
////    }
////
////    @Override
////    public PackageManager getPackageManager() {
////        return super.getPackageManager();
////    }
//
//
//}
