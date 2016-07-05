package net.jeson.library;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;

import java.util.Observer;

/**
 * Created by jiangneng on 6/29/16.
 */
public abstract class JNAbstartsWebView {
    protected  static Activity mActivity;
    protected Resources mResources;
    protected View  mView;
    public void  setActivity(Activity activity){
        mActivity=activity;
    }

    public  void setResources(Resources  resources){
        mResources=resources;
    }
    public void setWebView(View  view){
        mView=view;
    }
    public String getString(String name)throws Resources.NotFoundException{
        try {

            int id = mResources.getIdentifier(name, "string", mActivity.getPackageName());
            return mResources.getString(id);
        }catch(Resources.NotFoundException e){
            e.printStackTrace();
            throw e;
        }
    }
     public int  getLayoutID(String name)throws Resources.NotFoundException{
         try {

             int id = mResources.getIdentifier(name, "layout", mActivity.getPackageName());
             return id;
         }catch(Resources.NotFoundException e){
             e.printStackTrace();
             throw e;
         }
    }

    public View getLayout(String name)throws Resources.NotFoundException {
        try {
            return LayoutInflater.from(mActivity).inflate(getLayoutID("pop_layout"),null);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }
    public int getDrawableID(String name)throws Resources.NotFoundException{
        try {
            int id = mResources.getIdentifier(name, "drawable", mActivity.getPackageName());
            return  id;
        }catch(Resources.NotFoundException e){
            e.printStackTrace();
            throw e;
        }
    }
    public int  getID(String name)throws Resources.NotFoundException{
        try {

            int id = mResources.getIdentifier(name, "id", mActivity.getPackageName());
            return id;
        }catch(Resources.NotFoundException e){
            e.printStackTrace();
            throw e;
        }
    }
    public int getColorID(String name)throws Resources.NotFoundException{
        try {
            int id = mResources.getIdentifier(name, "color", mActivity.getPackageName());
            return  id;
        }catch(Resources.NotFoundException e){
            e.printStackTrace();
            throw e;
        }
    }
    public int getStyleID(String name)throws Resources.NotFoundException{
        try {
            int id = mResources.getIdentifier(name, "style", mActivity.getPackageName());
            return  id;
        }catch(Resources.NotFoundException e){
            e.printStackTrace();
            throw e;
        }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Drawable getDrawable(String name)throws Resources.NotFoundException{
        try {
            int id = mResources.getIdentifier(name, "drawable", mActivity.getPackageName());
            return  mResources.getDrawable(id,mActivity.getTheme());
        }catch(Resources.NotFoundException e){
            e.printStackTrace();
            throw e;
        }
    }
    public abstract   String getPluginName();
    public abstract  void init(Observer observer,String data);
    public abstract String[]  getPermission();
    public abstract void  onDestroy();
    public abstract   void onRestart();
    public  abstract   void onResume();
    public  abstract   void onStart();
    public  abstract   void onPause();
    public abstract  void onRestoreInstanceState(Bundle savedInstanceState);
    public abstract  void onStop();

    public void  startActivity(Intent intent){
        mActivity.startActivity(intent);
    }
    public static void setResult(Bundle bundles,Context context){
        Intent intent=   new Intent("jn.plugin.action");
         intent.putExtra("plugin",bundles);
        intent.putExtra("appkey",context.getPackageName());
               context.sendBroadcast(intent);
    }
}
