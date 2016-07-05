package jeson.jnplguin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class JNPluginReceiver extends BroadcastReceiver {
    public JNPluginReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("onReceive",intent+"-------------"+intent.getBundleExtra("plugin").getString("name"));
        Intent intent2=new Intent("mode.jnplugin.action");
        intent2.putExtra("plugin",intent.getExtras());
        intent2.putExtra("appkey",intent.getStringExtra("appkey"));
        context.sendBroadcast(intent2);

    }
}
