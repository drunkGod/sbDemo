package com.jvxb.demo.sbDemo.livable.modules.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jvxb.demo.sbDemo.livable.modules.base.controller.BaseController;
import com.jvxb.demo.sbDemo.livable.modules.system.service.ISysLogService;
import com.jvxb.demo.sbDemo.livable.utils.CommonUtil;
import com.jvxb.demo.sbDemo.livable.utils.PageData;
import com.jvxb.demo.sbDemo.livable.utils.response.ResponseMessage;

/**
 * 系统日志控制器
 * 
 * @author 抓娃小兵
 */
@Controller
public class SysLogController extends BaseController {

	@Autowired
	ISysLogService sysLogService;

	/**
	 * 前往列表页
	 */
	@RequestMapping("/admin/sysLog")
	public Object sysLog(ModelMap model) {
		return "system/sysLog_list";
	}

	/**
	 * 获取列表页数据
	 */
	@RequestMapping("/admin/sysLog/pageData")
	@ResponseBody
	public Object sysLogPageData(Integer page, Integer limit) {
		PageData pd = this.getPageData();
		JSONObject pageData = sysLogService.getTablePageData(page, limit, pd);
		return pageData;
	}

	/**
	 * 前往表单页
	 */
	@RequestMapping("/admin/sysLog/form")
	public Object sysLogForm(ModelMap model) {
		PageData pd = this.getPageData();
		model.addAttribute("mode", pd.get("mode"));
		model.addAttribute("id", pd.get("id"));
		return "system/sysLog_form";
	}

	/**
	 * 查看数据
	 */
	@RequestMapping("/admin/sysLog/get/{id}")
	@ResponseBody
	public Object sysLogGet(@PathVariable Integer id) {
		if (CommonUtil.notNum(id)) {
			return ResponseMessage.error("无效数据");
		}
		// 获取相关数据
		PageData pd = new PageData();
		pd.put("id", id);
		PageData data = sysLogService.get(pd);
		return ResponseMessage.ok(data);
	}

}
