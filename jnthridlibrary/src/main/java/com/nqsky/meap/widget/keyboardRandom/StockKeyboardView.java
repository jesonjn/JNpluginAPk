package com.nqsky.meap.widget.keyboardRandom;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

/**
 * 自定义keyboardView控件，重写onLongPress()实现长按删除按键功能
 * @author wang.wei 
 */
public class StockKeyboardView extends KeyboardView {

	public StockKeyboardView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public StockKeyboardView(Context context, AttributeSet attrs, int defStyle) {   
        super(context, attrs, defStyle);   
    }
	
	@Override
	protected boolean onLongPress(Key popupKey) {
		if (popupKey.codes[0] == Keyboard.KEYCODE_DELETE) {
			KeyboardActivity.mDefineKeyboardUtil.clearEditTextContent();
			//可使用OnKeyboardActionListener中的各种方法实现该功能
//			getOnKeyboardActionListener().onKey(Keyboard.KEYCODE_DELETE, null);  
			
		}
		return super.onLongPress(popupKey);
	}   
	

}
