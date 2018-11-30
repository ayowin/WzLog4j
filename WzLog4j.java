package top.ouyangwei;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/*
 * WzLog4j
 * 作者：欧阳伟
 * 日期：2018-11-30
 * 描述：轻量级日志工具，以天为单位记录日志
 * 使用指南：
 * 		// 默认示例
 *		// 日志等级为error、日志文件路径为项目的bin目录
 * 		WzLog4j wzLog4j = WzLog4j.getSingleton();
 *		wzLog4j.d("debug");
 *		wzLog4j.w("warning");
 *		wzLog4j.e("error");
 *
 *		// 自定义示例
 *		// 日志等级设为WARNING、日志文件路径设为D:/
 *		WzLog4j wzLog4j = WzLog4j.getSingleton();
 *		wzLog4j.setLogLevel(WzLog4j.WARNING);
 *		wzLog4j.setLogPath("D:/");
 *		wzLog4j.d("debug");
 *		wzLog4j.w("warning");
 *		wzLog4j.e("error");
 */

public class WzLog4j
{
	private static WzLog4j wzLog4j = null;

	/**
	 * 日志应该为单例
	 * 
	 * @return WzLog4j
	 */
	public static WzLog4j getSingleton()
	{
		if (wzLog4j == null)
			wzLog4j = new WzLog4j();
		return wzLog4j;
	}

	public static final byte DEBUG = 0;
	public static final byte WARNING = 1;
	public static final byte ERROR = 2;

	/**
	 * 日志等级，高于等于此等级的日志，都将被保存到文件中
	 */
	private byte logLevel = WzLog4j.ERROR;

	/**
	 * 日志文件的存储路径，默认存储在程序的bin目录下
	 */
	private String logPath = WzLog4j.class.getResource("/").getFile();

	/**
	 * 设置日志等级
	 * 
	 * @param logLevel 日志等级： WzLog4j.DEBUG， WzLog4j.WARNING， WzLog4j.ERROR
	 */
	public void setLogLevel(byte logLevel)
	{
		this.logLevel = logLevel;
	}

	/**
	 * 设置日志文件的存储路径，请自行构造并传入绝对路径
	 * 
	 * @param logPath
	 */
	public void setLogPath(String logPath)
	{
		this.logPath = logPath;
	}

	/**
	 * [DEBUG] 输出
	 * 
	 * @param content 日志内容
	 */
	public void d(String content)
	{
		content = Calendar.getInstance().getTime().toLocaleString() + " [DEBUG]: " + content;
		System.out.println(content);
		if (WzLog4j.DEBUG >= this.logLevel)
			toLogFile(content);
	}

	/**
	 * [WARNING] 输出
	 * 
	 * @param content 日志内容
	 */
	public void w(String content)
	{
		content = Calendar.getInstance().getTime().toLocaleString() + " [WARNING]: " + content;
		System.out.println(content);
		if (WzLog4j.WARNING >= this.logLevel)
			toLogFile(content);
	}

	/**
	 * [ERROR] 输出
	 * 
	 * @param content 日志内容
	 */
	public void e(String content)
	{
		content = Calendar.getInstance().getTime().toLocaleString() + " [ERROR]: " + content;
		System.out.println(content);
		if (WzLog4j.ERROR >= this.logLevel)
			toLogFile(content);
	}

	/**
	 * 将日志写到文件中，以追加方式，文件不存在则创建 以日期为文件名，位置为当前类的位置
	 * 
	 * @param content 日志内容
	 */
	private synchronized void toLogFile(String content)
	{
		// 末尾忘记加“/”处理
		if(logPath.charAt(logPath.length() - 1) != '/')
			logPath += "/";
		
		String realPath = logPath;
		realPath += Calendar.getInstance().get(Calendar.YEAR);
		realPath += "-";
		realPath += Calendar.getInstance().get(Calendar.MONTH) + 1;
		realPath += "-";
		realPath += Calendar.getInstance().get(Calendar.DATE);
		realPath += ".txt";
		
//		System.out.println(realPath);
		
		File file = new File(realPath);
		try
		{
			// 如果文件不存在则创建文件
			if (!file.exists())
			{
				file.createNewFile();
			}
			// 以追加方式创建文件输出流
			FileOutputStream fileOutputStream = new FileOutputStream(file, true);
			fileOutputStream.write(content.getBytes());
			fileOutputStream.write('\n');
			fileOutputStream.close();

		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
