package net.jeson.jnpluginlibrary;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;
import android.webkit.WebView;


import net.jeson.jnpluginlibrary.activity.ContextImpl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by jiangneng on 6/28/16.
 */
public class JNPluginMode implements Serializable{
    public String pluginName;
    public String[] permissions;
    protected Method    mInitMethod;
    protected  Object  object;
   protected ContextImpl  context;
    protected  String appKey;
    public  Method  onDestroyMethod;
    public  Method  onResumeMethod;
    public  Method  onStartMethod;
    public  Method  onRestoreInstanceStateMethod;
    public  Method  onPauseMethod;
    public  Method  onRestartMethod;


    public void  init( Observer observer,String data)throws JNMethodNotFindException,JNObjectInitViadException,IllegalAccessException,InvocationTargetException {
        if(mInitMethod==null){
            throw new JNMethodNotFindException();
        }
        if(object==null){
          throw new JNObjectInitViadException();
        }
        try {
            mInitMethod.invoke(object, observer,data);
        } catch (IllegalAccessException e) {
           throw  e;
        } catch (InvocationTargetException e) {
            throw e;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
   public ContextImpl getContext(){
       return context;
   }
   public Object getInstance(){
       return  object;
   }
    public boolean vaild(){
        if(TextUtils.isEmpty(pluginName)){
            return false;
        }
        if(object==null){
            return false;
        }
        if(mInitMethod==null){
            return false;
        }
        return true;
    }
  public String getAppKey(){
      return   appKey;
  }

}
