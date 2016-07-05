package net.jeson.jnpluginlibrary.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

/**
 * 默认的路径获得工具<br>
 * <p>
 * 规则：<br>
 * <P>
 * 在sdcard目录下,以当前App的PackageName建立主文件夹<br>
 * <p>
 * 分类存储:<br>
 * 图片<br>
 * 小文件 (.josn/.text/.xml)<br>
 * 断点下载的大文件等<br>
 * 
 * 修改
 * 
 */
public class FilePathUtil {

	/**
	 * /mnt/sdcard
	 */
	public static String SDCARD_PATH;
	
	/**
	 * 内存
	 */
	public static String MEMORY_STORAGE_DIRECTORY;
	
	/**
	 * 默认存储路径
	 */
	public static String DEFAULT_STORAGE_DIRECTORY;
	

	/**
	 * 默认公司路径
	 * 
	 */
	public static String DEFAULT_COMPANY_DIRECTORY;

	static {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
			DEFAULT_STORAGE_DIRECTORY = SDCARD_PATH;
		} else {
			// 存储方式 0-内存、1-外接SD卡
			MEMORY_STORAGE_DIRECTORY = Environment.getDataDirectory().getAbsolutePath();
			DEFAULT_STORAGE_DIRECTORY = MEMORY_STORAGE_DIRECTORY;
		}

	}
	
	/**
	 * 获得 sdcard路径
	 * 
	 * @return
	 */
	public static String getSdcardPath() {
		return DEFAULT_STORAGE_DIRECTORY;
	}

	/**
	 * 获得 sdcard下以app's packageName命名的文件夹路径
	 * 
	 * @param context
	 * @return /mnt/sdcard/packageName
	 */
	public static String getAppPath(Context context) {
		if (context != null && DEFAULT_STORAGE_DIRECTORY != null) {
			File file = new File(DEFAULT_STORAGE_DIRECTORY + File.separator + context.getPackageName());
			if (!file.exists()) {
				file.mkdirs();
			}
			return DEFAULT_STORAGE_DIRECTORY + File.separator + context.getPackageName();
		}
		return null;
	}
	
	/**
	 * 设置 某个公司的默认存储路径
	 * 
	 * @param context
	 * @param serverIP
	 *            服务器IP
	 * @param companyName
	 *            公司名
	 * @return 服务器下公司对应的存储地址
	 */
	public static void setCompanyPath(Context context, String companyName)
	{

		if (context != null && null != companyName)
		{
			File file = new File(getAppPath(context) + File.separator + companyName);
			if (!file.exists())
			{
				file.mkdirs();
			}
			DEFAULT_COMPANY_DIRECTORY = file.getAbsolutePath();
		}
	}

	/**
	 * 获取公司路径
	 * 
	 * @param context
	 * @return 公司默认存储路径
	 */
	public static String getCompanyPath(Context context)
	{
		if (!TextUtils.isEmpty(DEFAULT_COMPANY_DIRECTORY))
		{
			return DEFAULT_COMPANY_DIRECTORY;
		}
		return null;
	}

	/**
	 * 获得当前应用默认的小文件保存路径<br>
	 * 
	 * @param context
	 * @return
	 */
	public static String getDefaultStrPath(Context context) {
		if (context != null && DEFAULT_STORAGE_DIRECTORY != null) {
			File file = new File(DEFAULT_STORAGE_DIRECTORY + File.separator + context.getPackageName() + "/str");
			if (!file.exists()) {
				file.mkdirs();
			}
			return DEFAULT_STORAGE_DIRECTORY + File.separator + context.getPackageName() + "/str";
		}
		return null;
	}

	/**
	 * 获得当前应用默认的小文件保存路径<br>
	 * 
	 * @param context
	 * @return
	 */
	public static String getDefaultConfigPath(Context context) {
		if (context != null && DEFAULT_STORAGE_DIRECTORY != null) {
			File file = new File(DEFAULT_STORAGE_DIRECTORY + File.separator + context.getPackageName() + "/config"+ File.separator);
			if (!file.exists()) {
				file.mkdirs();
			}
			return DEFAULT_STORAGE_DIRECTORY + File.separator + context.getPackageName() + "/config"+ File.separator;
		}
		return null;
	}

	/**
	 * 获得当前应用默认的图片保存路径
	 * 
	 * @param context
	 * @return /sdcard/packageName/image
	 */
	public static String getDefaultImagePath(Context context) {
		if (context != null && DEFAULT_STORAGE_DIRECTORY != null) {
			File file = new File(DEFAULT_STORAGE_DIRECTORY + File.separator + context.getPackageName() + "/image" + File.separator);
			if (!file.exists()) {
				file.mkdirs();
			}
			return DEFAULT_STORAGE_DIRECTORY + File.separator + context.getPackageName() + "/image" + File.separator;
		}
		return null;
	}
	/**
	 * 获得当前应用默认的图片保存路径
	 * 
	 * @param context
	 * @return /sdcard/packageName/image
	 */
	public static String getDefaultPaintPath(Context context) {
		if (context != null && DEFAULT_STORAGE_DIRECTORY != null) {
			File file = new File(DEFAULT_STORAGE_DIRECTORY + File.separator + context.getPackageName() + "/myPaint" + File.separator);
			if (!file.exists()) {
				file.mkdirs();
			}
			return DEFAULT_STORAGE_DIRECTORY + File.separator + context.getPackageName() + "/myPaint" + File.separator;
		}
		return null;
	}

	/**
	 * 获得当前应用默认的下载保存路径
	 * 
	 * @param context
	 * @return
	 */
	public static String getDefaultDownLoadPath(Context context) {
		if (context != null && DEFAULT_STORAGE_DIRECTORY != null) {
			File file = new File(DEFAULT_STORAGE_DIRECTORY + File.separator + context.getPackageName() + "/download");
			if (!file.exists()) {
				file.mkdirs();
			}
			return DEFAULT_STORAGE_DIRECTORY + File.separator + context.getPackageName() + "/download";
		}
		return null;
	}
	
	/**
	 * 获得当前应用默认的log路径
	 * 
	 * @param context
	 * @return
	 */
	public static String getDefaultLogPath(Context context) {
		if (context != null && DEFAULT_STORAGE_DIRECTORY != null) {
			File file = new File(DEFAULT_STORAGE_DIRECTORY + File.separator + context.getPackageName() + "/log");
			if (!file.exists()) {
				file.mkdirs();
			}
			return DEFAULT_STORAGE_DIRECTORY + File.separator + context.getPackageName() + "/log";
		}
		return null;
	}

	/**
	 * 获得当前应用默认的解压路径
	 * 
	 * @param context
	 * @return
	 */
	public static String getDefaultUnzipPath(Context context) {
		if (context != null && DEFAULT_STORAGE_DIRECTORY != null) {
			File file = new File(DEFAULT_STORAGE_DIRECTORY + File.separator + context.getPackageName() + "/unZip");
			if (!file.exists()) {
				file.mkdirs();
			}
			return DEFAULT_STORAGE_DIRECTORY + File.separator + context.getPackageName() + "/unZip";
		}
		return null;
	}

	/**
	 * 获得当前应用默认的数据库文件保存路径
	 * 
	 * @param context
	 * @return
	 */
	public static String getDefaultDataBasePath(Context context) {
		if (context != null && DEFAULT_STORAGE_DIRECTORY != null) {
			File file = new File(DEFAULT_STORAGE_DIRECTORY + File.separator + context.getPackageName() + "/database");
			if (!file.exists()) {
				file.mkdirs();
			}
			return DEFAULT_STORAGE_DIRECTORY + File.separator + context.getPackageName() + "/database";
		}
		return null;
	}
	
	/**
	 * 获得当前应用默认的数据库文件保存路径 带用户id
	 * 
	 * @param context
	 * @return
	 */
	public static String getDefaultDataBasePath(Context context, String userId) {
		if (context != null && DEFAULT_COMPANY_DIRECTORY != null) {
			File file = new File(DEFAULT_COMPANY_DIRECTORY + File.separator + userId + File.separator + "database");
			if (!file.exists()) {
				file.mkdirs();
			}
			return file.getAbsolutePath();
		}
		return null;
	}

	/**
	 * 获得当前应用默认的异常文件保存路径
	 * 
	 * @param context
	 * @return
	 */
	public static String getDefaultCrashPath(Context context) {
		if (context != null && DEFAULT_STORAGE_DIRECTORY != null) {
			File file = new File(DEFAULT_STORAGE_DIRECTORY + File.separator + context.getPackageName() + "/crash");
			if (!file.exists()) {
				file.mkdirs();
			}
			return DEFAULT_STORAGE_DIRECTORY + File.separator + context.getPackageName() + "/crash";
		}
		return null;
	}

	/**
	 * 获得当前应用默认的加密文件保存路径<br>
	 * 
	 * @param context
	 * @return
	 */
	public static String getDefaultEncryptPath(Context context) {
		if (context != null && DEFAULT_STORAGE_DIRECTORY != null) {
			File file = new File(DEFAULT_STORAGE_DIRECTORY + File.separator + context.getPackageName() + "/encrypt" + File.separator);
			if (!file.exists()) {
				file.mkdirs();
			}
			return DEFAULT_STORAGE_DIRECTORY + File.separator + context.getPackageName() + "/encrypt" + File.separator;
		}
		return null;
	}
}
