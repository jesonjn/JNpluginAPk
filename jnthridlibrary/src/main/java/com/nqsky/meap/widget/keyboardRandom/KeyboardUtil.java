package com.nqsky.meap.widget.keyboardRandom;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;


import java.util.List;

import jeson.jnthridlibrary.R;

/**
 * 自定义对话框工具类，用于控制键盘
 * @author wang.wei 
 */
public class KeyboardUtil {
	
	private Context mContext;
	
	private EditText mEditText;
	
	/**
	 * 全键盘对象
	 */
	private Keyboard keyboard_all;
	
	/**
	 * 数字键盘对象
	 */
	private Keyboard keyboard_number;
	
	/**
	 * 呈现虚拟键盘的视图。它处理渲染键和检测按键和触摸动作
	 */
	private StockKeyboardView mKeyboardView;

	/**
	 * 是否数据键盘
	 */
	public boolean isNum = false;
	
	/**
	 * 是否大写
	 */
	public boolean isUpper = false;
	
	public KeyboardUtil(Context mContext, EditText mEditText, StockKeyboardView mKeyboardView) {
		this.mContext = mContext;
		this.mEditText = mEditText;
		this.mKeyboardView = mKeyboardView;
		
		this.keyboard_all = new Keyboard(mContext, R.xml.keyboard_qwerty);
		this.keyboard_number = new Keyboard(mContext, R.xml.keyboard_symbols);
		this.mKeyboardView.setKeyboard(keyboard_all);
		this.mKeyboardView.setEnabled(true);
		this.mKeyboardView.setPreviewEnabled(true);
		this.mKeyboardView.setOnKeyboardActionListener(listener);
		/**启用或禁用键的反馈弹出*/
		this.mKeyboardView.setPreviewEnabled(true); // true启用反弹键盘，false禁用反弹键盘
		
	}
	
	private OnKeyboardActionListener listener = new OnKeyboardActionListener() {
		
		@Override
		public void swipeUp() {
			
		}
		
		@Override
		public void swipeRight() {
			
		}
		
		@Override
		public void swipeLeft() {
			
		}
		
		@Override
		public void swipeDown() {
			
		}
		
		@Override
		public void onText(CharSequence text) {
			
		}
		
		@Override
		public void onRelease(int primaryCode) { // 按键释放时触发方法
//			if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 完成
//				hideKeyboard();
//			}
		}

		//按键点击时触发方法
		@Override
		public void onPress(int primaryCode) {
		}
		
		@Override
		public void onKey(int primaryCode, int[] keyCodes) {
			Editable editable = mEditText.getText();
			int start = mEditText.getSelectionStart();
			switch (primaryCode) { //按键codes编码
			case Keyboard.KEYCODE_CANCEL:// 完成（如果不监听，则输入框中出现乱码字符）
				hideKeyboard();
				break;

			case Keyboard.KEYCODE_DELETE:// 删除
				if (editable != null && editable.length() > 0) {
					if (start > 0) {
						editable.delete(start - 1, start);// 开始，结束位置
					}
				}
				break;
				
			case Keyboard.KEYCODE_SHIFT: //大小写切换
				changeKey();
				mKeyboardView.setKeyboard(keyboard_all);
				break;
				
			case Keyboard.KEYCODE_MODE_CHANGE:
				changeKeyTONum();
				break;
				
			default:
				editable.insert(start, Character.toString((char) primaryCode));
				break;
			}
		}
	};

	/**
	 * 清空输入框所有内容
	 */
	public void clearEditTextContent() {
		if (mEditText != null) {
			Editable editable = mEditText.getText();
			int start = mEditText.getSelectionStart();
			if (start > 0) {
				editable.clear();
			}
		}

	}
	
	/**
	 * 显示键盘
	 */
	public void showKeyboard() {
		int visibility = mKeyboardView.getVisibility();
		if (visibility == View.GONE || visibility == View.INVISIBLE) {
			mKeyboardView.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * 隐藏键盘
	 */
	public void hideKeyboard() {
		int visibility = mKeyboardView.getVisibility();
		if (visibility == View.VISIBLE) {
			mKeyboardView.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 判断当前键盘是否可见
	 * 
	 * @return true为键盘可见，false为键盘不可见
	 */
	public boolean isShowKeyboard() {
		int visibility = mKeyboardView.getVisibility();
		if (visibility == View.VISIBLE) {
			return true;
		}
		return false;
	}
	
	/**
	 * 切换键盘大小写字母
	 * 按照ascii码表可知，小写字母 = 大写字母+32;
	 */
	private void changeKey() {
		List<Key> keyList = keyboard_all.getKeys();
		if (isUpper) {// 如果为真表示当前为大写，需切换为小写
			isUpper = false;
			for (Key key : keyList) {
				if (key.label != null && isWord(key.label.toString())) {
					key.label = key.label.toString().toLowerCase();
					key.codes[0] = key.codes[0] + 32;
				}
			}
		} else {// 如果为假表示当前为小写，需切换为大写
			isUpper = true;
			for (Key key : keyList) {
				if (key.label != null && isWord(key.label.toString())) {
					key.label = key.label.toString().toUpperCase();
					key.codes[0] = key.codes[0] - 32;
				}
			}
		}
	}
	
	/**
	 * 随机数字键盘,随机键盘LABEL中不能存在图片，否则在随机换位过程中会报错
	 */
	private void randomNumKey() {
		List<Key> keyList = keyboard_number.getKeys();
		int size = keyList.size();
		for (int i = 0; i < size; i++) {
			int random_a = (int) (Math.random() * (size));
			int random_b = (int) (Math.random() * (size));

			int code = keyList.get(random_a).codes[0];
			CharSequence label = keyList.get(random_a).label;

			keyList.get(random_a).codes[0] = keyList.get(random_b).codes[0];
			keyList.get(random_a).label = keyList.get(random_b).label;

			keyList.get(random_b).codes[0] = code;
			keyList.get(random_b).label = label;
		}
	}
	
	/**
	 * 数字键盘切换
	 */
	private void changeKeyTONum() {
		if (isNum) { // 当前为数字键盘,切换为全字母键盘
			isNum = false;
			mKeyboardView.setKeyboard(keyboard_all);
		} else { // 当前为全字母键盘，切换为数字键盘
			isNum = true;
			randomNumKey();
			mKeyboardView.setKeyboard(keyboard_number);
		}
	}
	
	/**判断是否为字母
	 * 
	 * @param str 需判断的字符串
	 */ 
	private boolean isWord(String str) {
		String wordStr = "abcdefghijklmnopqrstuvwxyz";
		if (wordStr.indexOf(str.toLowerCase()) > -1) {
			return true;
		}
		return false;
	}
	
}
