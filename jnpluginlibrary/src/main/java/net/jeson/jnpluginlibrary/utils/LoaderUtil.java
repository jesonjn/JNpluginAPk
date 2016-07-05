package net.jeson.jnpluginlibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;


import net.jeson.jnpluginlibrary.activity.ContextImpl;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;

/**
 * @author jiangneng
 * @Description 动态加载工具类实现动态加载jar包
 */
public class LoaderUtil {


    protected  static Resources loadResources(Context context, String mDexPath, AssetManager mAssetManager, Resources resources, Resources.Theme mTheme) {

        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, mDexPath);
            mAssetManager = assetManager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Resources superRes = context.getResources();
        Resources mResources = new Resources(mAssetManager, superRes.getDisplayMetrics(),
                superRes.getConfiguration());
        mTheme = mResources.newTheme();
        mTheme.setTo(context.getTheme());
        return  mResources;
    }
    /**
     * 该方法用来获取未安装的apk的reosurces对象
     * @return
     */
    public static ContextImpl getUnInstalledResource(String  apkPath, Context context,DexClassLoader dexClassLoader) {
        // 反射出资源管理器
        try {
            Class<?> assetManager_clazz = Class
                    .forName("android.content.res.AssetManager");
            //生成assetManager对象
            Object assetObj = assetManager_clazz.newInstance();
            //因为addAssetPath是隐藏的，所以只能通过反射来获取
            Method addAssetMethod = assetManager_clazz.getDeclaredMethod("addAssetPath",String.class);
            addAssetMethod.invoke(assetObj,apkPath);
            Resources resources = context.getResources();
            Constructor<?>resources_constructor = Resources.class.getConstructor(assetManager_clazz,resources.getDisplayMetrics().getClass(),resources.getConfiguration().getClass());
            resources = (Resources) resources_constructor.newInstance(assetObj,resources.getDisplayMetrics(),resources.getConfiguration());
//            context.getTheme() = resources.newTheme();
//            context.getTheme().setTo(context.getTheme());
            return new ContextImpl(resources,(AssetManager) assetObj,dexClassLoader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * *
     *
     * @throws
     * @Title: loadJar
     * @Description: 项目工程中必须定义接口， 而被引入的第三方jar包实现这些接口，然后进行动态加载 。 *
     * 相当于第三方按照接口协议来开发， 使得第三方应用可以以插件的形式动态加载到应用平台中。 * @return void
     */
    public static DexClassLoader loadApk(Context context, String dexPath) {
        File dexOutputDir = context.getDir("dex", 0);
        DexClassLoader cl = new DexClassLoader(dexPath, dexOutputDir.getAbsolutePath(),
                null,  ClassLoader.getSystemClassLoader());

        return cl;
    }

    public static Class<?> newInstance(String className) throws ClassNotFoundException {
        return LoaderUtil.class.getClassLoader().loadClass(className);
    }

    public static Class<?> newInstance(BaseDexClassLoader loader, String className) throws ClassNotFoundException {
        return loader.loadClass(className);
    }

    /**
     * *
     *
     * @throws
     * @Title: loadUninstallApk
     * @Description: 动态加载未安装的apk * @return void
     */
    private void loadUninstallApk(Context context,String apk) {
        String path = Environment.getExternalStorageDirectory()
                + File.separator;
        String filename = apk; // 4.1以后不能够将optimizedDirectory设置到sd卡目录，
        // 否则抛出异常.
        File optimizedDirectoryFile = context.getDir("dex", 0);
        DexClassLoader classLoader = new DexClassLoader(path + filename,
                optimizedDirectoryFile.getAbsolutePath(), null,
                context.getClassLoader());
        try { // 通过反射机制调用， 包名为com.example.loaduninstallapkdemo,
            // 类名为UninstallApkActivity
            Class mLoadClass = classLoader
                    .loadClass("com.example.loadunstallapkdemo.UninstallApkActivity");
            Constructor constructor = mLoadClass.getConstructor(new Class[]{});
            Object testActivity = constructor.newInstance(new Object[]{});
            // 获取sayHello方法
//            Method helloMethod = mLoadClass.getMethod("sayHello", null);
//            helloMethod.setAccessible(true);
//            Object content = helloMethod.invoke(testActivity, null);
            // Toast.makeText(MainActivity.this, content.toString(),
            // Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * *
     *
     * @return void
     * @throws
     * @Title: loadInstalledApk
     * @Description: 动态加载已安装的apk
     */
    private void loadInstalledApk(Context con) {
        try {
            String pkgName = "com.example.loaduninstallapkdemo";
            Context context = con.createPackageContext(pkgName,
                    Context.CONTEXT_IGNORE_SECURITY
                            | Context.CONTEXT_INCLUDE_CODE);
            // 获取动态加载得到的资源
            Resources resources = context.getResources();
            // 过去该apk中的字符串资源"tips"， 并且toast出来，apk换肤的实现就是这种原理
            String toast = resources.getString(resources.getIdentifier("tips",
                    "string", pkgName));
            // Toast.makeText(MainActivity.this, toast,
            // Toast.LENGTH_SHORT).show() ;
            Class cls = context.getClassLoader().loadClass(
                    pkgName + ".UninstallApkActivity");
            // 跳转到该Activity
            con.startActivity(new Intent(context, cls));
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // Log.d("", e.toString()) ;
        }
    }
}
