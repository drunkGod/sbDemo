package com.jvxb.demo.sbDemo.livable.configuration.constant;

import com.jvxb.demo.sbDemo.livable.configuration.listener.InitializeListener;

/**
 * 项目系统名称(title)设置
 * 1.为使用者提供便利，title在system_title.txt文件中设置
 * 2.具体设置见 {@link InitializeListener}
 * 
 * @author 抓娃小兵
 *
 */
public class TitleConstant {

	/** 默认为Java管理系统, 实际值为system_title.txt中的内容 */
	public static final String TITLE_NAME;
	static {
		TITLE_NAME = "Java管理系统";
	}

}
