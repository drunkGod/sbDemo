package com.jvxb.demo.sbDemo.livable.configuration.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 在线信息监听器：统计当前在线的系统用户，统计历史访问人数
 * 
 * @author 抓娃小兵
 */
@WebListener
public class OnlineInfoListener implements HttpSessionListener {

	public int count = 0;// 记录session的数量
	// 监听session的创建,synchronized 防并发bug

	public synchronized void sessionCreated(HttpSessionEvent arg0) {
		System.err.println("【HttpSessionListener监听器】count++  增加");
		count++;
		arg0.getSession().getServletContext().setAttribute("count", count);

	}

	@Override
	public synchronized void sessionDestroyed(HttpSessionEvent arg0) {// 监听session的撤销
		System.err.println("【HttpSessionListener监听器】count--  减少");
		count--;
		arg0.getSession().getServletContext().setAttribute("count", count);
	}
}
