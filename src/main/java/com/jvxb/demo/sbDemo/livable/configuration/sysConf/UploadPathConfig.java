package com.jvxb.demo.sbDemo.livable.configuration.sysConf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value="classpath:uploadSetting.properties")
public class UploadPathConfig {

	@Value("${upload.windows.path}") 
	private String windowsPath;
	
	@Value("${upload.linux.path}") 
	private String linuxPath;
	
	/**
	 * 根据当前系统，返回uploadSetting.properties中配置的上传路径
	 */
	public String getUploadPathConfig() {
		if(System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0){
			return linuxPath;
        }else if(System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0){
        	return windowsPath;
        }
		return null;
	}

	@Override
	public String toString() {
		return "UploadPathConfig [windowsPath=" + windowsPath + ", linuxPath=" + linuxPath + "]";
	}
	
}
