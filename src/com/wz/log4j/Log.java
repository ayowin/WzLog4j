package com.wz.log4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class Log {
    /*
     * 预设日志等级
     */
    public static final byte DEBUG = 0;
    public static final byte WARNING = 1;
    public static final byte ERROR = 2;

    // 日志等级，高于等于此等级的日志，都将写入到日志文件中
    private static byte level = Log.ERROR;
    // 日志文件的路径，默认路径：类路径下的log文件夹
    private static String logPath = Log.class.getResource("/").getFile() + "log";

    /**
     * 设置日志等级
     * 
     * @param level 日志等级：Log.DEBUG，Log.WARNING，Log.ERROR
     */
    public static void setLevel(byte level) {
	Log.level = level;
    }

    /**
     * 设置日志文件的存储路径，请自行构造并传入绝对路径
     * 
     * @param logPath，默认路径为类的根路径
     */
    public static void setLogPath(String logPath) {
	Log.logPath = logPath;
    }

    /**
     * @param content 日志内容，自动调用对象的toString()
     */
    public static void d(Object content) {
	String tmp = Calendar.getInstance().getTime().toLocaleString() + " [DEBUG]: " + content.toString();
	System.out.println(tmp);
	if (Log.DEBUG >= Log.level)
	    toLogFile(tmp);
    }

    /**
     * @param content 日志内容，自动调用对象的toString()
     */
    public static void w(Object content) {
	String tmp = Calendar.getInstance().getTime().toLocaleString() + " [WARNING]: " + content.toString();
	System.out.println(tmp);
	if (Log.WARNING >= Log.level)
	    toLogFile(tmp);
    }

    /**
     * @param content 日志内容，自动调用对象的toString()
     */
    public static void e(Object content) {
	String tmp = Calendar.getInstance().getTime().toLocaleString() + " [ERROR]: " + content.toString();
	System.out.println(tmp);
	if (Log.ERROR >= Log.level)
	    toLogFile(tmp);
    }

    // 将日志输出到文件
    private static synchronized void toLogFile(String content) {
	
	// 如果日志路径为默认路径，则检查log文件夹是否存在，不存在则创建
	if(logPath.equals(Log.class.getResource("/").getFile() + "log")){
	    File file = new File(logPath);
	    if(!file.exists() || !file.isDirectory()) {
		if(!file.mkdir()) {
		    System.out.println("\"" + logPath + "log\" directory can not be created.");
		}
	    }
	}
	
	// 末尾忘记加“/”处理
	if (logPath.charAt(logPath.length() - 1) != '/') {
	    logPath += "/";
	}

	String realPath = logPath;
	realPath += Calendar.getInstance().get(Calendar.YEAR);
	realPath += "-";
	realPath += Calendar.getInstance().get(Calendar.MONTH) + 1;
	realPath += "-";
	realPath += Calendar.getInstance().get(Calendar.DATE);
	realPath += ".txt";

//	 System.out.println(realPath);

	File file = new File(realPath);
	try {
	    // 如果文件不存在则创建文件
	    if (!file.exists()) {
		file.createNewFile();
	    }
	    // 以追加方式创建文件输出流
	    FileOutputStream fileOutputStream = new FileOutputStream(file, true);
	    fileOutputStream.write(content.getBytes());
	    fileOutputStream.write('\n');
	    fileOutputStream.close();

	} catch (IOException e) {
	    System.out.println("The path \"" + logPath + "\" you specified may not exist,please checked!");
	    e.printStackTrace();
	}
    }
}
