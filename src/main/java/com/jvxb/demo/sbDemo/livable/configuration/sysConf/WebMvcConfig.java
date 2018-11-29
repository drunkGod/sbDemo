package com.jvxb.demo.sbDemo.livable.configuration.sysConf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 系统配置
 * 
 * @author 抓娃小兵
 */
@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	UploadPathConfig uploadPathConfig;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//静态资源路径映射
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
		
		//上传文件路径映射: 将路径带有upload静态资源映射到uploadPath.
		if(System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0){
        	registry.addResourceHandler("/uploadResource/**").addResourceLocations("file:" + uploadPathConfig.getUploadPathConfig());
        } else if(System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0){
        	registry.addResourceHandler("/uploadResource/**").addResourceLocations("file://" + uploadPathConfig.getUploadPathConfig());
        }
	}

}
