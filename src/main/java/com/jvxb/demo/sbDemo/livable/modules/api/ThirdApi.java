package com.jvxb.demo.sbDemo.livable.modules.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jvxb.demo.sbDemo.livable.configuration.interceptor.ApiRequestInterceptor;
import com.jvxb.demo.sbDemo.livable.modules.api.enums.ApiRequestStatusEnum;
import com.jvxb.demo.sbDemo.livable.modules.base.controller.BaseController;
import com.jvxb.demo.sbDemo.livable.utils.PageData;
import com.jvxb.demo.sbDemo.livable.utils.response.ResponseMessage;

/**
 * 对第三方提供数据的接口，满足一定条件的用户才可以请求，条件设置：{@link ApiRequestInterceptor}
 * 
 * @author 抓娃小兵
 */

@RestController
@RequestMapping("/admin/api/third")
public class ThirdApi extends BaseController {

	@GetMapping({"", "err"})
	public Object err(HttpServletRequest request) {
		String status = request.getParameter("status");
		String errMsg = ApiRequestStatusEnum.getDescByStatus(status);
		return ResponseMessage.error("请求失败! 原因：" + "[" + errMsg + "]");
	}

	@GetMapping("test")
	public Object test() {
		ResponseMessage<Object> result = ResponseMessage.ok();
		String msg = "第三方请求数据成功！";
		String requestInfo = "";
		PageData requestDataMap = getPageData();
		if (!requestDataMap.isEmpty()) {
			requestInfo = requestDataMap.toString();
		}
		PageData userDataMap = getSqlMapper().selectOne("count(1) as count", "live_user_info", null);
		PageData returnData = new PageData("注册用户总数", userDataMap.get("count"));
		result.setData(returnData);
		result.setMessage(msg + "your requestInfo: [" + requestInfo + "]");
		return result;
	}

}
