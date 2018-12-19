package com.jvxb.demo.sbDemo.livable.modules.base.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.jvxb.demo.sbDemo.base.entity.system.SysUser;
import com.jvxb.demo.sbDemo.livable.modules.base.mapper.BaseMapper;
import com.jvxb.demo.sbDemo.livable.utils.PageData;

/**
 * 基本父类控制器
 * 
 * @author 抓娃小兵
 */
public abstract class BaseController {
	
	@Autowired
	BaseMapper baseMapper;

	/**
	 * 得到PageData
	 */
	public PageData getPageData() {
		return new PageData(this.getRequest());
	}

	public ParameterMap<String, Object> getParameterMap() {
		return ParameterMap.create(getRequest());
	}

	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView() {
		return new ModelAndView();
	}

	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request;
	}

	/**
	 * 得到当前登录的用户
	 */
	public SysUser getSessionUser() {
		SysUser user = (SysUser) getRequest().getSession().getAttribute("user");
		return user;
	}

	/**
	 * 得到项目路径
	 */
	public String getContextPath() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request.getContextPath();
	}
	
	/**
	 * 慎用！！ 可能引起sql注入！
	 * @param sql
	 * @return
	 */
	public List<PageData> excuteSql(String sql){
		List<PageData> result = baseMapper.excuteSql(sql);
		return result;
	}

}
