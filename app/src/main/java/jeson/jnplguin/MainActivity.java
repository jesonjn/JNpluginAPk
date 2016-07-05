package jeson.jnplguin;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.morgoo.droidplugin.pm.PluginManager;

import net.jeson.jnpluginlibrary.JNMethodNotFindException;
import net.jeson.jnpluginlibrary.JNObjectInitViadException;
import net.jeson.jnpluginlibrary.JNPluginManager;
import net.jeson.jnpluginlibrary.JNPluginMode;
import net.jeson.jnpluginlibrary.utils.FilePathUtil;
import net.jeson.jnpluginlibrary.utils.FileUtils;


import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;


public class MainActivity extends Activity implements ServiceConnection {

//    protected Object instance;
    protected Resources mResources;
    protected AssetManager mAssetManager;
    protected JNPluginMode pluginMode;
    protected  JNPluginBroadcastReceiver  mReceiver=new JNPluginBroadcastReceiver();


    @Override
    public Resources getResources() {
        return mResources==null?super.getResources():mResources;
    }

    @Override
    public AssetManager getAssets() {
        return mAssetManager==null?super.getAssets():mAssetManager;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
         IntentFilter  filter=new IntentFilter();
         filter.addAction("jn.plugin.action");
          registerReceiver(mReceiver,filter);

        File file2 = new File(FilePathUtil.getDefaultConfigPath(this) + "demo1-debug.apk");
        if (!file2.exists()) {
            try {
                FileUtils.copyFile(getResources().getAssets().open("demo1-debug.apk"), file2);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else
        {
            file2.deleteOnExit();
            try {
                FileUtils.copyFile(getResources().getAssets().open("demo1-debug.apk"), file2);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if (PluginManager.getInstance().isConnected()) {
            startLoad();
        } else {
            PluginManager.getInstance().addServiceConnection(this);
        }

        try {
           final JNPluginMode mode= JNPluginManager.initLoadApkWebView(this,file2,findViewById(R.id.button));
//            attachBaseContext(mode.getBaseContext());

            Toast.makeText( MainActivity.this,"----插件名称:------"+mode.pluginName,Toast.LENGTH_LONG).show();

            findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        mResources=mode.getContext().getmResources();
                        mAssetManager=mode.getContext().getmAssetManager();
//                        instance=mode.getInstance();
                        pluginMode=mode;

                     mode.init(new Observer() {
                        @Override
                        public void update(Observable observable, final Object data) {
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText( MainActivity.this,"----------"+data,Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    },"");
                    } catch (JNMethodNotFindException e) {
                        e.printStackTrace();
                    } catch (JNObjectInitViadException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            });


        }  catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        startLoad();
    }
       Handler  handler=new Handler();
    private void startLoad() {
        new Thread("ApkScanner") {
            @Override
            public void run() {
                try {
                    File file2 = new File(FilePathUtil.getDefaultConfigPath(MainActivity.this) + "demo1-debug.apk");
                    final int re = PluginManager.getInstance().installPackage(file2.getAbsolutePath(), 0);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, re == PluginManager.INSTALL_FAILED_NO_REQUESTEDPERMISSION ? "安装失败，文件请求的权限太多" : "安装完成", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    @Override
    public PackageManager getPackageManager() {
        if(pluginMode!=null){

        }
        return super.getPackageManager();
    }

    @Override
    public String getPackageName() {
        if(pluginMode!=null){
            return  pluginMode.getAppKey();
        }
        return super.getPackageName();
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
    }

    @Override
    public void startActivity(Intent intent) {
        try {
            boolean bo = PluginManager.getInstance().isPluginPackage(pluginMode.getAppKey());
        }catch (RemoteException e){
            e.printStackTrace();
        }
        ComponentName name=new ComponentName(pluginMode.getAppKey(),intent.getComponent().getClassName());
        intent.setComponent(name);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        super.startActivity(intent);

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if(pluginMode!=null&&pluginMode.onRestartMethod!=null){

            pluginMode.onRestartMethod.setAccessible(true);
            try {
                pluginMode.onRestartMethod.invoke(pluginMode.getInstance(),null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        PluginManager.getInstance().removeServiceConnection(this);
        if(pluginMode!=null&&pluginMode.onDestroyMethod!=null){

            pluginMode.onDestroyMethod.setAccessible(true);
            try {
                pluginMode.onDestroyMethod.invoke(pluginMode.getInstance(),null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(pluginMode!=null&&pluginMode.onResumeMethod!=null){

            pluginMode.onResumeMethod.setAccessible(true);
            try {
                pluginMode.onResumeMethod.invoke(pluginMode.getInstance(),null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(pluginMode!=null&&pluginMode.onStartMethod!=null){

            pluginMode.onStartMethod.setAccessible(true);
            try {
                pluginMode.onStartMethod.invoke(pluginMode.getInstance(),null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(pluginMode!=null&&pluginMode.onRestoreInstanceStateMethod!=null){

            pluginMode.onRestoreInstanceStateMethod.setAccessible(true);
            try {
                pluginMode.onRestoreInstanceStateMethod.invoke(pluginMode.getInstance(),savedInstanceState);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(pluginMode!=null&&pluginMode.onPauseMethod!=null){

            pluginMode.onPauseMethod.setAccessible(true);
            try {
                pluginMode.onPauseMethod.invoke(pluginMode.getInstance(),null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        Log.e("user",requestCode+"-------------"+data.getStringExtra("name"));
    }
    public class JNPluginBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("onReceive",intent+"-------ssss------"+intent.getBundleExtra("plugin").getString("name"));
            String appkey=intent.getStringExtra("appkey");
            if(pluginMode.getAppKey().equals(appkey)){
                Toast.makeText(MainActivity.this,"收到包 "+appkey,Toast.LENGTH_SHORT).show();
            }

        }
    }

}
