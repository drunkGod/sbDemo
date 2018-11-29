package com.jvxb.demo.sbDemo.livable.modules.system.controller;

import java.util.Arrays;
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
import com.jvxb.demo.sbDemo.livable.configuration.annotation.LogAnnotation;
import com.jvxb.demo.sbDemo.livable.modules.base.controller.BaseController;
import com.jvxb.demo.sbDemo.livable.modules.system.service.ISysRolePermService;
import com.jvxb.demo.sbDemo.livable.modules.system.service.ISysRoleService;
import com.jvxb.demo.sbDemo.livable.utils.CommonUtil;
import com.jvxb.demo.sbDemo.livable.utils.PageData;
import com.jvxb.demo.sbDemo.livable.utils.response.ResponseMessage;

/**
 * 系统角色控制器
 * 
 * @author 抓娃小兵
 */
@Controller
public class SysRoleContoller extends BaseController {

	@Autowired
	private ISysRoleService sysRoleService;
	@Autowired
	private ISysRolePermService sysRolePermService;

	/**
	 * 前往角色列表页
	 */
	@RequestMapping("/admin/sysRole")
	public Object sysRole() {
		return "system/sysRole_list";
	}

	/**
	 * 前往角色列表数据
	 */
	@RequestMapping("/admin/sysRole/pageData")
	@ResponseBody
	public Object sysRolePage(Integer page, Integer limit, String name) {
		JSONObject sysRoleList = sysRoleService.getSysRolePageData(page, limit, name);
		return sysRoleList;
	}

	/**
	 * 前往角色表单页
	 */
	@RequestMapping("/admin/sysRole/form")
	public Object sysRoleForm(ModelMap model) {
		PageData pd = this.getPageData();
		model.addAttribute("mode", pd.get("mode"));
		model.addAttribute("roleId", pd.get("roleId"));
		model.addAttribute("rolePerm", CommonUtil.isNullOrEmpty(pd.get("roleId")) ? null
				: sysRolePermService.getRolePermByRoleId(pd.getInteger("roleId")));
		return "system/sysRole_form";
	}

	/**
	 * 保存角色
	 */
	@RequestMapping("/admin/sysRole/save")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	@LogAnnotation(operate = "系统角色新增/编辑")
	public Object sysRoleSave() {
		PageData pd = this.getPageData();
		if (CommonUtil.isNullOrEmpty(pd.get("roleId"))) {
			pd.put("createTime", new Date());
		}
		sysRoleService.insertOrUpdate(pd);
		if (CommonUtil.notNullOrEmpty(pd.get("roleId"))) {
			// 判断角色权限与当前权限是否一致。一致则不需操作
			if (permIsChange(pd.getInteger("roleId"), pd.getString("rolePerm"))) {
				// 修改权限时，先将库中角色对应的权限删除
				sysRolePermService.deleteRolePermByRoleId(pd.getInteger("roleId"));
				//如果当前权限不为空，再插入当前权限。
				if (CommonUtil.notNullOrEmpty(pd.get("rolePerm"))) {
					sysRolePermService.insertOrUpdate(pd.getInteger("roleId"), pd.getString("rolePerm"));
				}
			}
		}
		return ResponseMessage.ok();
	}

	/**
	 * 判断角色权限是否需要修改
	 */
	private boolean permIsChange(Integer roleId, String curPerms) {
		// 获取角色在数据库中的权限
		String dbPerms = sysRolePermService.getRolePermByRoleId(roleId);

		// 数据库中无记录，当前权限也无记录。不需改变
		if (dbPerms == null && (curPerms == null || curPerms.equals(""))) {
			return false;
		}
		// 数据库中无记录，当前权限有记录。需要改变
		if (dbPerms == null && (curPerms != null && !curPerms.equals(""))) {
			return true;
		}
		// 数据库中有记录，当前权限无记录。需要改变
		if (dbPerms != null && (curPerms == null || curPerms.equals(""))) {
			return true;
		}
		// 数据库中有记录，当前权限也有记录。且当前权限与数据库权限长度都不相等，权限需要改变
		if (dbPerms.length() != curPerms.length()) {
			return true;
		}

		// 数据库中有记录，当前权限也有记录。且当前权限与数据库权限一直，权限不需要改变
		if (dbPerms.equals(curPerms)) {
			return false;
		}

		// 数据库中有记录，当前权限也有记录。如果权限长度相等，判断具体权限是否相等
		String[] dbPermsArr = dbPerms.split(",");
		String[] curPermsArr = curPerms.split(",");
		List<String> list = Arrays.asList(dbPermsArr);
		for (String curPerm : curPermsArr) {
			// 如果没有这个元素，则权限也已改变。
			if (!list.contains(curPerm)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 删除角色及角色-权限关系表数据
	 */
	@RequestMapping("/admin/sysRole/detele/{id}")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class)
	@LogAnnotation(operate = "系统角色删除")
	public Object sysRoleDelete(@PathVariable Integer id) {
		if (CommonUtil.notNum(id)) {
			return ResponseMessage.error("无效数据");
		}
		// 删除角色表
		sysRoleService.delete(id);
		// 删除角色-角色关联表对应信息
		sysRolePermService.deleteRolePermByRoleId(id);
		return ResponseMessage.ok();
	}

	/**
	 * 查看角色
	 */
	@RequestMapping("/admin/sysRole/get/{roleId}")
	@ResponseBody
	public Object getSysRole(@PathVariable Integer roleId) {
		if (CommonUtil.notNum(roleId)) {
			return ResponseMessage.error("无效数据");
		}
		// 获取角色数据相关
		PageData data = sysRoleService.get(roleId);
		return ResponseMessage.ok(data);
	}

	/**
	 * 获取所有角色
	 */
	@RequestMapping("/admin/sysRole/getAll")
	@ResponseBody
	public Object getSysRoles() {
		// 获取角色数据相关
		List<PageData> data = sysRoleService.getAll();
		return ResponseMessage.ok(data);
	}

}
