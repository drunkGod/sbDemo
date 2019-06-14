package com.jvxb.demo.sbDemo.livable.configuration.interceptor;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jvxb.demo.sbDemo.livable.modules.api.enums.ApiRequestStatusEnum;
import com.jvxb.demo.sbDemo.livable.utils.CommonUtil;
import com.jvxb.demo.sbDemo.livable.utils.HttpUtil;

/**
 * API请求拦截器：拦截无效/恶意请求
 * 
 * @author 抓娃小兵
 */
public class ApiRequestInterceptor implements HandlerInterceptor {

	// 某ip（每分钟）允许访问的次数，
	private static final int PER_MIN_LIMIT_REUQEST_TIMES = 10;
	// 记录ip及其访问次数
	public static final ConcurrentHashMap<String, Integer> baseRequestRecordMap = new ConcurrentHashMap<>();
	public static final ConcurrentHashMap<String, Integer> thirdRequestRecordMap = new ConcurrentHashMap<>();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 获取请求的uri
		String uri = request.getRequestURI();
		// 获取容器路径
		String ctxPath = request.getContextPath();

		// 1.基本的对外提供数据的接口 /admin/api/base，仅验证访问次数是否超限
		if (uri.startsWith(ctxPath + "/admin/api/base")) {
			String status = isBaseRequestValid(request);
			if (!status.equals(ApiRequestStatusEnum.OK.getStatus())) {
				// 无效，则前往第三方请求异常接口，参数为异常码
				response.sendRedirect(ctxPath + "/admin/api/base/err?status=" + status);
				return false;
			} else {
				return true;
			}
		}
		
		// 2.异常请求接口/admin/api/(third|base)/err不拦截,
		if (uri.startsWith(ctxPath + "/admin/api/base/err") || uri.startsWith(ctxPath + "/admin/api/third/err")) {
			return true;
		}

		// 3.其他第三方的请求,需要验证请求是否有效。
		if (uri.startsWith(ctxPath + "/admin/api/third")) {
			String status = isThirdRequestValid(request);
			if (!status.equals(ApiRequestStatusEnum.OK.getStatus())) {
				// 无效，则前往第三方请求异常接口，参数为异常码
				response.sendRedirect(ctxPath + "/admin/api/third/err?status=" + status);
				return false;
			} else {
				
				return true;
			}
		}

		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}


	/**
	 * 检查基本请求是否有效
	 * 有效标准：
	 * 1.请求次数没有超额
	 * @param request
	 * @return
	 */
	private String isBaseRequestValid(HttpServletRequest request) {
		String code = ApiRequestStatusEnum.OK.getStatus();
		// 记录来访者ip，若该ip的访问次数超额，则返回REQUEST_MANY，防止恶意请求
		String ip = HttpUtil.getIpAddress(request);
		if (ip != null) {
			if (baseRequestRecordMap.containsKey(ip)) {
				Integer thisIpRequestTimes = baseRequestRecordMap.get(ip);
				if (thisIpRequestTimes > PER_MIN_LIMIT_REUQEST_TIMES) {
					code = ApiRequestStatusEnum.REQUEST_MANY.getStatus();
					return code;
				} else {
					baseRequestRecordMap.put(ip, thisIpRequestTimes + 1);
				}
			} else {
				baseRequestRecordMap.put(ip, 1);
			}
		}
		
		return code;
	}
	
	/**
	 * 检查第三方请求是否有效
	 * 有效标准：
	 * 1.thirdId不能为空
	 * 2.thirdId在提供范围内
	 * 3.thirdId为vip会员
	 * 4. 请求次数没有超额
	 * @param request
	 * @return
	 */
	private String isThirdRequestValid(HttpServletRequest request) {
		String code = ApiRequestStatusEnum.OK.getStatus();
		try {
			// 记录来访者ip，若该ip的访问次数超额，则返回REQUEST_MANY，防止恶意请求
			String ip = HttpUtil.getIpAddress(request);
			if (ip != null) {
				if (thirdRequestRecordMap.containsKey(ip)) {
					Integer thisIpRequestTimes = thirdRequestRecordMap.get(ip);
					if (thisIpRequestTimes > PER_MIN_LIMIT_REUQEST_TIMES) {
						code = ApiRequestStatusEnum.REQUEST_MANY.getStatus();
						return code;
					} else {
						thirdRequestRecordMap.put(ip, thisIpRequestTimes + 1);
					}
				} else {
					thirdRequestRecordMap.put(ip, 1);
				}
			}
			
			String thirdId = request.getParameter("thirdId");
			// 第三方Id为空，则返回NO_THIRD_ID
			if (thirdId == null || thirdId.trim().length() == 0) {
				code = ApiRequestStatusEnum.NO_THIRD_ID.getStatus();
			} else if (CommonUtil.notNum(thirdId) || Integer.parseInt(thirdId) < 0 || Integer.parseInt(thirdId) > 5) {
				// 第三方Id不在提供范围内，则返回UNKNOW_THIRD_ID。（此处理论上应该从数据库查，此处略）
				code = ApiRequestStatusEnum.UNKNOW_THIRD_ID.getStatus();
			}
			
			// 第三方是否为vip会员：略

		} catch (Exception e) {
			code = ApiRequestStatusEnum.UNKNOW_ERROR.getStatus();
		}

		return code;
	}
}
