package com.jvxb.demo.sbDemo.livable.configuration.annotation;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jvxb.demo.sbDemo.base.entity.system.SysUser;
import com.jvxb.demo.sbDemo.livable.modules.system.service.ISysLogService;
import com.jvxb.demo.sbDemo.livable.utils.PageData;

/**
 * 系统日志切面
 * 
 * @author 抓娃小兵
 */
@Component
@Aspect
public class LogAspect {

	@Autowired
	ISysLogService sysLogService;

	@Pointcut("@annotation(com.jvxb.demo.sbDemo.livable.configuration.annotation.LogAnnotation)")
	public void logAspect() {
	}

	@After("logAspect()")
	public void doBeforeMyAspect(JoinPoint joinPoint) {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		SysUser currentUser = (SysUser) session.getAttribute("user");
		Map<String, String[]> paraMap = request.getParameterMap();
		JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(paraMap));
		if (paraMap != null) {
			// 密码就不要记录啦
			if (jsonObject != null && jsonObject.keySet().contains("password")) {
				jsonObject.remove("password");
			}
		}
		// 根据joinPoint获取方法上的注入值
		String operate = getControllerOperate(joinPoint);
		boolean isSave = getControllerIsSave(joinPoint);
		// 根据request获取请求中的参数值
		String name = currentUser != null ? currentUser.getRealName() : "佚名";
		if (isSave) {
			PageData pd = new PageData();
			pd.put("content", operate);
			pd.put("uri", request.getRequestURI());
			pd.put("parameter", jsonObject.isEmpty() ? null : jsonObject.toString());
			pd.put("operator_id", currentUser.getId());
			pd.put("operator_name", name);
			pd.put("create_time", new Date());
			sysLogService.insertOrUpdate(pd);
		}
		// 根据isSave决定是否存入数据库。
	}

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 *
	 * @param joinPoint 切点
	 * @return 方法描述
	 * @throws Exception
	 */
	public static String getControllerOperate(JoinPoint joinPoint) {
		String operate = "";
		try {
			String targetName = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			Object[] arguments = joinPoint.getArgs();
			Class<?> targetClass = Class.forName(targetName);
			Method[] methods = targetClass.getMethods();
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					Class<?>[] clazzs = method.getParameterTypes();
					if (clazzs.length == arguments.length) {
						operate = method.getAnnotation(LogAnnotation.class).operate();
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return operate;
	}

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 *
	 * @param joinPoint 切点
	 * @return 方法描述
	 * @throws Exception
	 */
	public static boolean getControllerIsSave(JoinPoint joinPoint) {
		boolean isSave = false;
		try {
			String targetName = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			Object[] arguments = joinPoint.getArgs();
			Class<?> targetClass = Class.forName(targetName);
			Method[] methods = targetClass.getMethods();
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					Class<?>[] clazzs = method.getParameterTypes();
					if (clazzs.length == arguments.length) {
						isSave = method.getAnnotation(LogAnnotation.class).isSave();
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isSave;
	}
}
