package jeson.demo1;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.PopupWindow;
import android.widget.Toast;

import net.jeson.library.JNAbstartsWebView;

import java.util.Observer;

/**
 * Created by jiangneng on 6/27/16.
 */
public class main extends JNAbstartsWebView {

    public String str;
    public  String getPluginName(){
        return "testBug";
    }

    public void  init(Observer observer,String data){
            str="MResources不为空";
            try {
                Log.e("INIT","--------------"+mActivity.getBaseContext().getPackageName());
                str=getString("app_name");
            }catch(Resources.NotFoundException e){
                e.printStackTrace();

            }
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mActivity,"----------"+str,Toast.LENGTH_LONG).show();
                }
            });


           observer.update(null,"{'code:'1,'object':'只是为了测试' }");
//
        PopupWindow  popupWindow=new PopupWindow(mActivity);
        try {
           View view= LayoutInflater.from(mActivity).inflate(getLayoutID("pop_layout"),null);
            popupWindow.setContentView(view);
            view.findViewById(getID("suber_ppo")).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent  intent=new Intent(mActivity,jeson.demo1.MainActivity.class);
                    startActivity(intent);
                }
            });
        }catch (Resources.NotFoundException e){
            e.printStackTrace();
        }
        popupWindow.showAsDropDown(mView);



    }
   public String[]  getPermission(){
       return null;
   }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public void onStop() {

    }
}
