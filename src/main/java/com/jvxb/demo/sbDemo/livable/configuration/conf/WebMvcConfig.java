package com.jvxb.demo.sbDemo.livable.configuration.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jvxb.demo.sbDemo.livable.configuration.interceptor.ApiRequestInterceptor;
import com.jvxb.demo.sbDemo.livable.utils.UploadUtil;

/**
 * 系统Mvc配置类
 * 
 * @author 抓娃小兵
 */
@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	UploadPathProperties uploadPathConfig;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 静态资源路径映射
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");

		// 上传文件路径映射: 将路径带有uploadResource的静态资源映射到uploadPath.
		if (System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0) {
			registry.addResourceHandler("/" + UploadUtil.UPLOAD_RESOURCE + "/**")
					.addResourceLocations("file:" + uploadPathConfig.getUploadPath());
		} else if (System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0) {
			registry.addResourceHandler("/" + UploadUtil.UPLOAD_RESOURCE + "/**")
					.addResourceLocations("file://" + uploadPathConfig.getUploadPath());
		}
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 拦截器
		registry.addInterceptor(new ApiRequestInterceptor()).addPathPatterns("/admin/api/**");
	}

}
