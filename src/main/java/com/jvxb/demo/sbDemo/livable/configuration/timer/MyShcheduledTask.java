package com.jvxb.demo.sbDemo.livable.configuration.timer;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * 定时器
 * 
 * @author 抓娃小兵
 */
@Component
public class MyShcheduledTask {

	/**
	 * @Description : 执行周期设为3秒钟
	 * @throws Exception
	 */
//	@Scheduled(cron="*/3 * * * * *")
	public void autoShceduledTask() throws Exception {
		System.out.print("定时任务执行了");
		System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()));
	}

}
