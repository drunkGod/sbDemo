package com.jvxb.demo.sbDemo.livable.configuration.timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jvxb.demo.sbDemo.livable.configuration.interceptor.ApiRequestInterceptor;

/**
 * 定时器: 在一定时间内限制Api请求的次数，达一定时间后重置请求的次数。
 * 如某接口每天只允许某ip访问N次，或者某接口每n分钟只允许访问N次，到达时间后重置接口的访问次数。
 * 
 * @author 抓娃小兵
 */
@Component
public class ApiRequestShcheduledTask {

	/**
	 * @Description : 执行周期设为每整分钟
	 * @throws Exception
	 */
	//执行周期: 每n分钟(cron="0 * */n * * ?") || 每整n小时(cron="0 0 0/n * * ?")
	@Scheduled(cron="0 * */3 * * ?")
	public void autoShceduledTask() throws Exception {
		ApiRequestInterceptor.baseRequestRecordMap.clear();
		ApiRequestInterceptor.thirdRequestRecordMap.clear();
//		for(String ip : ApiRequestInterceptor.requestRecordMap.keySet()) {
//			System.out.println(ip + " -已访问-  " + ApiRequestInterceptor.requestRecordMap.get(ip));
//		}
	}

}
