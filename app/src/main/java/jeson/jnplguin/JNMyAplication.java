package jeson.jnplguin;

import android.content.Context;

import com.morgoo.droidplugin.PluginApplication;
import com.morgoo.droidplugin.PluginHelper;

/**
 * Created by jiangneng on 6/30/16.
 */
public class JNMyAplication extends PluginApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        PluginHelper.getInstance().applicationOnCreate(getBaseContext());
    }
    @Override
    protected void attachBaseContext(Context base) {
        PluginHelper.getInstance().applicationAttachBaseContext(base);
        super.attachBaseContext(base);

    }

}
