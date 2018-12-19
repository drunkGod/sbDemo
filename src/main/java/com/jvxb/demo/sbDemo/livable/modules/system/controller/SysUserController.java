package com.jvxb.demo.sbDemo.livable.modules.system.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jvxb.demo.sbDemo.base.entity.system.SysUser;
import com.jvxb.demo.sbDemo.livable.configuration.annotation.LogAnnotation;
import com.jvxb.demo.sbDemo.livable.modules.base.controller.BaseController;
import com.jvxb.demo.sbDemo.livable.modules.system.service.ISysUserService;
import com.jvxb.demo.sbDemo.livable.utils.CommonUtil;
import com.jvxb.demo.sbDemo.livable.utils.PageData;
import com.jvxb.demo.sbDemo.livable.utils.response.ResponseMessage;

/**
 * 系统用户控制器
 * 
 * @author 抓娃小兵
 */
@Controller
public class SysUserController extends BaseController {
	@Autowired
	ISysUserService sysUserService;

	/**
	 * 前往用户列表页
	 */
	@RequestMapping("/admin/sysUser")
	public Object sysUser() {
		return "system/sysUser_list";
	}

	/**
	 * 获取用户分页列表数据
	 */
	@RequestMapping("/admin/sysUser/pageData")
	@ResponseBody
	public Object sysUserPageData(Integer page, Integer limit) {
		PageData pd = this.getPageData();
		JSONObject sysUserList = sysUserService.getTablePageData(page, limit, pd);
		return sysUserList;
	}

	/**
	 * 前往用户表单页
	 */
	@RequestMapping("/admin/sysUser/form")
	public Object sysRoleForm(ModelMap model) {
		PageData pd = this.getPageData();
		model.addAttribute("mode", pd.get("mode"));
		model.addAttribute("id", pd.getString("id"));
		model.addAttribute("roleId", pd.getString("roleId"));
		return "system/sysUser_form";
	}

	/**
	 * 保存用户
	 */
	@RequestMapping("/admin/sysUser/save")
	@ResponseBody
	@LogAnnotation(operate = "系统用户新增/编辑")
	public Object sysUserSave() {
		PageData pd = this.getPageData();
		if(CommonUtil.isNullOrEmpty(pd.get("id"))) {
			pd.put("createTime", new Date());
		}
		sysUserService.insertOrUpdate(pd);
		return ResponseMessage.ok();
	}
	
	/**
	 * 修改密码
	 */
	@RequestMapping("/admin/modifyPassword")
	@ResponseBody
	public Object modifyPassword(String curPass, String newPass, String surePass) {
		SysUser user = getSessionUser();
		if(curPass != null && user.getPassword().equals(curPass)) {
			sysUserService.updatePassword(user.getId(), newPass);
		} else {
			return ResponseMessage.error("原始密码不正确，请重新输入！");
		}
		return ResponseMessage.ok();
	}
	
	/**
	 * 删除用户
	 */
	@RequestMapping("/admin/sysUser/delete/{id}")
	@ResponseBody
	@LogAnnotation(operate = "系统用户删除")
	public Object sysUserDel(@PathVariable Integer id) {
		if (CommonUtil.notNum(id)) {
			return ResponseMessage.error("无效数据");
		}
		PageData pd = new PageData("id", id);
		sysUserService.delete(pd);
		return ResponseMessage.ok();
	}

	/**
	 * 查看系统用户
	 */
	@RequestMapping("/admin/sysUser/get/{id}")
	@ResponseBody
	public Object sysUser(@PathVariable Integer id) {
		if (CommonUtil.notNum(id)) {
			return ResponseMessage.error("无效数据");
		}
		// 获取系统用户相关数据
		PageData pd = new PageData("id", id);
		PageData data = sysUserService.get(pd);
		return ResponseMessage.ok(data);
	}

}
