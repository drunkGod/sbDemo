package com.jvxb.demo.sbDemo.livable.modules.base.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jvxb.demo.sbDemo.base.entity.system.SysPermission;
import com.jvxb.demo.sbDemo.base.entity.system.SysUser;
import com.jvxb.demo.sbDemo.livable.configuration.annotation.LogAnnotation;
import com.jvxb.demo.sbDemo.livable.configuration.constant.TitleConstant;
import com.jvxb.demo.sbDemo.livable.modules.system.service.ISysPermissionService;
import com.jvxb.demo.sbDemo.livable.modules.system.service.ISysUserService;
import com.jvxb.demo.sbDemo.livable.utils.CommonUtil;

/**
 * 登录控制器：登录验证，登出，获取登录者权限。
 * 
 * @author 抓娃小兵
 */
@Controller
public class LoginController {

	@Resource
	ISysUserService sysUserService;
	@Resource
	ISysPermissionService permissionService;

	/**
	 * 前往登录页
	 */
	@RequestMapping("/admin/login")
	public Object login(String err, ModelMap model) {
		if (err != null && err.equals("2")) {
			model.put("err", "无效用户");
		}
		if (err != null && err.equals("3")) {
			model.put("err", "帐号密码错误");
		}
		//项目title名
		model.addAttribute("title", TitleConstant.TITLE_NAME);
		return "frame/login";
	}

	/**
	 * 检查登录
	 */
	@RequestMapping("/admin/checkLogin")
	public ModelAndView userList(String username, String password, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (username != null) {
			//判断用户是否存在
			SysUser user = sysUserService.checkExist(username, password);
			if (user != null) {
				//判断用户是否可用
				if (user.getValid() == 1) {
					request.getSession().setAttribute("user", user);
					return new ModelAndView("redirect:/admin/index");
				} else {
					return new ModelAndView("redirect:/admin/login?err=2");
				}
			}
			return new ModelAndView("redirect:/admin/login?err=3");
		} else {
			return new ModelAndView("redirect:/admin/login?err=2");
		}
	}

	/**
	 * 前往首页
	 */
	@RequestMapping("/admin/index")
	@LogAnnotation(operate = "登录系统")
	public Object index(ModelMap model, HttpServletRequest request) {
		
		// 根据当前用户，获取左侧权限列表
		SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
		String permTree = "<ul class='layui-nav layui-nav-tree'>" + getLeftPermission(sysUser, request.getContextPath())
				+ "</ul>";
		model.addAttribute("permTree", permTree);
		//项目title名
		model.addAttribute("title", TitleConstant.TITLE_NAME);
		return "frame/index";
	}

	/**
	 * 根据当前用户获取其权限列表
	 * 
	 * @return
	 */
	private String getLeftPermission(SysUser sysUser, String ctx) {
		if (sysUser == null) {
			return "";
		}
		// 获取当前用户角色对应的权限
		List<SysPermission> permList = permissionService.getPermissionBySysUserId(sysUser.getId());
		if (permList == null || permList.size() == 0) {
			return "";
		}
		// 将权限转为树结构，以此构建左侧的权限菜单
		permList = SysPermission.makeTree(permList);
		// 权限树按sort排序,sort小的排在前面。
		Collections.sort(permList, new permSort());

		// 根据权限树，返回权限树对应的html结构
		StringBuilder sb = new StringBuilder();
		for (SysPermission perm : permList) {
			List<SysPermission> childrens = perm.getChildren();
			// 权限树按sort排序,sort小的排在前面。
			Collections.sort(childrens, new permSort());

			// 如果仅存在一级列表,不存在二级列表,且连接不为空,则直接前往连接。
			if (CommonUtil.isNullOrEmpty(childrens) && CommonUtil.notNullOrEmpty(perm.getPermUrl())) {
				sb.append("<li class='layui-nav-item'><a href='javascript:;' data-id='");
				sb.append(perm.getId());
				sb.append("' data-title='");
				sb.append(perm.getPermName());
				sb.append("' data-url='");
				sb.append(perm.getPermUrl());
				sb.append("' class='site-demo-active' data-type='tabAdd'>");
				sb.append(perm.getPermName());
				sb.append("</a></li>");
				continue;
			}

			//普通的一级+二级结构
			sb.append("<li class='layui-nav-item'><a href='javascript:;'>");
			sb.append(perm.getPermName());
			sb.append("</a><dl class='layui-nav-child'>");
			if (CommonUtil.notNullOrEmpty(childrens)) {
				for (SysPermission children : childrens) {
					sb.append("<dd><a href='javascript:;' data-id='");
					sb.append(children.getId());
					sb.append("' data-title='");
					sb.append(children.getPermName());
					sb.append("' data-url='");
					sb.append(ctx + children.getPermUrl());
					sb.append("' class='site-demo-active' data-type='tabAdd'>&nbsp;&nbsp;&nbsp;");
					sb.append(children.getPermName());
					sb.append("</a></dd>");
				}
			}
			sb.append("</dl></li>");
		}
		return sb.toString();
	}

	/**
	 * 前往敬请期待页
	 */
	@RequestMapping("/admin/undo/page")
	public Object undo() {
		return "frame/undo";
	}

	/**
	 * 前往示例页
	 */
	@RequestMapping("/admin/demo/page")
	public Object demo() {
		return "frame/demo";
	}

	/**
	 * 前往主页
	 */
	@RequestMapping("/admin/home/page")
	public Object home() {
		return "frame/home";
	}

	/**
	 * 注销，前往首页
	 */
	@RequestMapping("/admin/logout")
	public Object logout(HttpServletRequest request) {
		request.getSession().removeAttribute("user");
		return "frame/login";
	}

	private class permSort implements Comparator<SysPermission> {
		@Override
		public int compare(SysPermission o1, SysPermission o2) {
			if (o1.getSort() != null && o2.getSort() != null && o1.getSort() < o2.getSort()) {
				return -1;
			} else if (o1.getSort() != null && o2.getSort() != null && o1.getSort() > o2.getSort()) {
				return 1;
			}
			return 0;
		}
	}

}
