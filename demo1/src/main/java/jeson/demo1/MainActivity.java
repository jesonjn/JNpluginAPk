package jeson.demo1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import net.jeson.library.JNAbstartsWebView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("demo","这是在Demo中执行的了--------------"+getBaseContext());
        setContentView(R.layout.activity_main);
        findViewById(R.id.button22).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("setResult","点击事件-------------------");

                Bundle  bundle=new Bundle();
                bundle.putString("name","哈哈");
               JNAbstartsWebView.setResult( bundle,getBaseContext());//setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });

    }
}
