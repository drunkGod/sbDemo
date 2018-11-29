package com.jvxb.demo.sbDemo.livable.modules.system.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jvxb.demo.sbDemo.base.entity.system.SysPermission;
import com.jvxb.demo.sbDemo.livable.configuration.annotation.LogAnnotation;
import com.jvxb.demo.sbDemo.livable.modules.base.controller.BaseController;
import com.jvxb.demo.sbDemo.livable.modules.system.service.ISysPermissionService;
import com.jvxb.demo.sbDemo.livable.modules.system.service.ISysRolePermService;
import com.jvxb.demo.sbDemo.livable.utils.CommonUtil;
import com.jvxb.demo.sbDemo.livable.utils.PageData;
import com.jvxb.demo.sbDemo.livable.utils.response.ResponseMessage;

/**
 * 系统权限控制器
 * 
 * @author 抓娃小兵
 */
@Controller
public class SysPermissionContoller extends BaseController {

	@Autowired
	private ISysPermissionService sysPermissionService;
	@Autowired
	private ISysRolePermService sysRolePermService;

	/**
	 * 前往权限列表页
	 */
	@RequestMapping(value = { "/admin/sysPermission", "/admin/sysPermission/list" })
	public Object sysPermissionList() {
		return "system/sysPermission_list";
	}

	/**
	 * 获取权限列表页数据
	 */
	@RequestMapping("/admin/sysPermission/pageData")
	@ResponseBody
	public Object sysPermissionPage(Integer page, Integer limit, Integer id, Integer pid) {
		JSONObject sysRoleList = sysPermissionService.getSysPermissionPageData(page, limit, id, pid);
		return sysRoleList;
	}

	/**
	 * 前往权限表单页
	 */
	@RequestMapping("/admin/sysPermission/form")
	public Object sysPermissionForm(ModelMap model) {
		PageData pd = this.getPageData();
		model.addAttribute("mode", pd.get("mode"));
		model.addAttribute("permId", pd.get("permId"));
		model.addAttribute("permPid", pd.get("permPid"));
		model.addAttribute("permPname", CommonUtil.isNullOrEmpty(pd.get("permPname")) ? "根节点" : pd.get("permPname"));
		return "system/sysPermission_form";
	}

	/**
	 * 获取权限列表树数据
	 */
	@RequestMapping("/admin/sysPermissionTree")
	@ResponseBody
	public Object sysPermissionTree() {
		List<SysPermission> sysRoleList = sysPermissionService.getSysPermissionList();
		sysRoleList = SysPermission.makeTree(sysRoleList);
		return ResponseMessage.ok(sysRoleList);
	}

	/**
	 * 保存权限
	 */
	@RequestMapping("/admin/sysPermission/save")
	@ResponseBody
	@LogAnnotation(operate = "系统权限新增/编辑")
	public Object sysPermissionSave() {
		PageData pd = this.getPageData();
		if (CommonUtil.isNullOrEmpty(pd.get("permId"))) {
			pd.put("createTime", new Date());
		}
		sysPermissionService.insertOrUpdate(pd);
		return ResponseMessage.ok();
	}

	/**
	 * 删除权限
	 */
	@RequestMapping("/admin/sysPermission/detele/{id}")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	@LogAnnotation(operate = "系统权限删除")
	public Object sysPermissionDelete(@PathVariable Integer id) {
		if (CommonUtil.notNum(id)) {
			return ResponseMessage.error("无效数据");
		}
		// 删除权限表
		sysPermissionService.delete(id);
		// 删除角色-角色关联表对应信息
		sysRolePermService.deleteRolePermByPermId(id);
		return ResponseMessage.ok();
	}

	/**
	 * 批量删除权限
	 */
	@RequestMapping("/admin/sysPermission/deteles")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	@LogAnnotation(operate = "系统权限批量删除")
	public Object sysPermissionDeletes(String ids) {
		if (CommonUtil.isNullOrEmpty(ids)) {
			return ResponseMessage.error("无效数据");
		}
		// 删除权限表
		sysPermissionService.deletes(ids);
		// 删除角色-角色关联表对应信息
		sysRolePermService.deleteRolePermByPermIds(ids);
		return ResponseMessage.ok();
	}

	/**
	 * 查看权限
	 */
	@RequestMapping("/admin/sysPermission/get/{permId}")
	@ResponseBody
	public Object sysPermission(@PathVariable Integer permId) {
		if (permId == null) {
			return ResponseMessage.error("无效数据");
		}
		// 获取权限数据相关
		PageData data = sysPermissionService.get(permId);
		return ResponseMessage.ok(data);
	}

	/**
	 * 新增权限时，获取可用的父权限
	 */
	@RequestMapping("/admin/sysPerm/available")
	@ResponseBody
	public Object sysPermissionAvailable() {
		List<PageData> data = sysPermissionService.getAvailable();
		return ResponseMessage.ok(data);
	}

}
