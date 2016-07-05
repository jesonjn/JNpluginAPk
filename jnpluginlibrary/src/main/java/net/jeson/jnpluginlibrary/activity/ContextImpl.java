package net.jeson.jnpluginlibrary.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;


import dalvik.system.DexClassLoader;

/**
 * Created by jiangneng on 6/29/16.
 */
public class ContextImpl {
    private Resources mResources;
    private AssetManager mAssetManager;
    private DexClassLoader  mDexClassLoader;

    public ContextImpl(Resources resources,AssetManager assetManager,DexClassLoader dexClassLoader) {
        mResources=resources;
        mAssetManager=assetManager;
        mDexClassLoader=dexClassLoader;
    }

    public  Resources  getmResources(){
        return mResources;
    }

    public AssetManager getmAssetManager() {
        return mAssetManager;
    }

    public DexClassLoader getmDexClassLoader() {
        return mDexClassLoader;
    }
}
