package com.jvxb.demo.sbDemo.livable.configuration.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 系统上传路径配置类
 * 
 * @author 抓娃小兵
 */
@Component
@PropertySource(value="classpath:uploadSetting.properties")
public class UploadPathProperties {

	@Value("${windows.upload.path}") 
	private String windowsUploadPath;
	
	@Value("${linux.upload.path}") 
	private String linuxUploadPath;
	
	/**
	 * 根据当前系统，返回uploadSetting.properties中配置的上传路径
	 */
	public String getUploadPath() {
		if(System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0){
			return linuxUploadPath;
        }else if(System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0){
        	return windowsUploadPath;
        }
		return null;
	}

	@Override
	public String toString() {
		return "UploadPathConfig [windowsPath=" + windowsUploadPath + ", linuxPath=" + linuxUploadPath + "]";
	}
	
}
