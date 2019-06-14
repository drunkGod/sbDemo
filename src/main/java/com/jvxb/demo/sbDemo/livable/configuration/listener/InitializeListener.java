package com.jvxb.demo.sbDemo.livable.configuration.listener;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.util.ResourceUtils;

import com.jvxb.demo.sbDemo.livable.configuration.constant.TitleConstant;
import com.jvxb.demo.sbDemo.livable.utils.FileUtil;

/**
 * 系统初始化监听器：服务启动时的操作 (如清空历史日志，预设特定值等)
 * 
 * @author 抓娃小兵
 */
@WebListener
public class InitializeListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		// 项目启动，清空历史日志
		//clearHistoryLog();
		
		// 设置系统title名称。从system_title.txt中读取
		setSystemTitleName(getSystemTitleFromTxt());
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
	}


	/**
	 * 删除日志
	 */
	@SuppressWarnings("unused")
	private void clearHistoryLog() {
		File file = new File(System.getProperty("user.dir") + "-Log");
		if (file.exists()) {
			FileUtil.delAllFile(file.getAbsolutePath());
		}
	}

	
	/**
	 * 从 resource/static/txt/title.txt中读取系统title名
	 * 
	 * @return
	 */
	private String getSystemTitleFromTxt() {
		String title = null;
		try {
			String srcPath = "static" + File.separator + "txt" + File.separator + "system_title.txt";
			File titleTxtFile = ResourceUtils.getFile("classpath:" + srcPath);
			title = FileUtil.readFileAsString(titleTxtFile);
		} catch (Exception e) {
		}
		return title;
	}
	
	/**
	 * 设置系统title名称
	 */
	private void setSystemTitleName(String titleName) {
		try {
			Field field = TitleConstant.class.getDeclaredField("TITLE_NAME");
			field.setAccessible(true);
			// 去除final修饰符
			Field modifiers_field = Field.class.getDeclaredField("modifiers");
			modifiers_field.setAccessible(true);
			modifiers_field.set(field, field.getModifiers() & ~Modifier.FINAL);
			field.set(this, titleName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
