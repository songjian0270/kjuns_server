package com.kjuns.task;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kjuns.util.SysConf;

public class DeleteLogFileTask {

	private static Logger logger = LoggerFactory.getLogger(DeleteLogFileTask.class);

	// 日志文件目录
	private static final String logPath = SysConf.LOG_PATH;

	// 保留日志文件数
	private static final Integer logFileCnt = SysConf.LOG_FILE_CNT;

	public void execute() {
		logger.info("DeleteLogFileTask execute..." + System.currentTimeMillis());
		if (logPath == null || logFileCnt == null) {
			logger.error("日志目录参数或保存日志文件数未配置！");
			return;
		}
		File logFolder = new File(logPath);
		// 目录不存在或是文件直接结束
		if (!logFolder.exists() || logFolder.isFile()) {
			logger.error("目录{}不存在", logPath);
			return;
		}
		File[] filelist = logFolder.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if (pathname.isDirectory())
					return false;
				return true;
			}
		});
		// 如果文件少保留日志文件数个直接结束
		if (filelist == null || filelist.length <= logFileCnt) {
			logger.error("日志文件数小于{}个,无需要删除!", logFileCnt);
			return;
		}
		LogFile[] ls = new LogFile[filelist.length];
		LogFile logFile = null;
		for (int i = 0; i < filelist.length; i++) {
			logFile = new LogFile();
			logFile.setName(filelist[i].getName());
			logFile.setFile(filelist[i]);
			logFile.setLastModified(filelist[i].lastModified());
			ls[i] = logFile;
		}
		// 排序
		Arrays.sort(ls, new LogFileComparator());
		// 调试
		/*
		 * for(LogFile lf:ls){
		 * System.out.println(lf.getName()+"___"+lf.getLastModified()+"___"+
		 * CommonConstants.DATETIME_FULL_STR.format(new
		 * Date(lf.getLastModified()))); }
		 */
		boolean delFlag = false;
		for (int i = logFileCnt; i < ls.length; i++) {
			delFlag = ls[i].getFile().delete();
			if (!delFlag) {
				// 删除文件失败
				logger.error("文件{}删除失败！", ls[i].getName());
			} else {
				// 删除文件成功
				logger.info("文件{}删除成功！", ls[i].getName());
			}
		}
	}

	public static void main(String args[]) {
		new DeleteLogFileTask().execute();
	}
}

class LogFileComparator implements Comparator<LogFile> {
	@Override
	public int compare(LogFile o1, LogFile o2) {
		return o1.getLastModified() > o2.getLastModified() ? -1 : 1;
	}

}

class LogFile {

	private String name;

	private File file;

	private long lastModified;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
