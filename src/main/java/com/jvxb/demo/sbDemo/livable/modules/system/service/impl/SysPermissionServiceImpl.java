package com.jvxb.demo.sbDemo.livable.modules.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jvxb.demo.sbDemo.base.entity.system.SysPermission;
import com.jvxb.demo.sbDemo.livable.modules.system.mapper.SysPermissionMapper;
import com.jvxb.demo.sbDemo.livable.modules.system.service.ISysPermissionService;
import com.jvxb.demo.sbDemo.livable.utils.LayuiPageUtil;
import com.jvxb.demo.sbDemo.livable.utils.PageData;

@Service
public class SysPermissionServiceImpl implements ISysPermissionService {

	@Autowired
	private SysPermissionMapper sysPermissionMapper;

	@Override
	public JSONObject getSysPermissionPageData(Integer page, Integer rows, Integer id, Integer pid) {
		PageHelper.startPage(page, rows);
		List<SysPermission> list = sysPermissionMapper.getSysPermissionPageData(id, pid);
		PageInfo<SysPermission> pageInfo = new PageInfo<>(list);
		JSONObject pageJson = LayuiPageUtil.getLayuiPage(pageInfo);
		return pageJson;
	}

	@Override
	public List<SysPermission> getSysPermissionList() {
		return sysPermissionMapper.getSysPermissionList();
	}

	@Override
	public void insertOrUpdate(PageData pd) {
		sysPermissionMapper.insertOrUpdate(pd);
	}

	@Override
	public void delete(Integer permId) {
		sysPermissionMapper.delete(permId);
	}

	@Override
	public PageData get(Integer permId) {
		PageData data = sysPermissionMapper.get(permId);
		return data;
	}

	@Override
	public void deletes(String ids) {
		sysPermissionMapper.deletes(ids);
	}

	@Override
	public List<SysPermission> getPermissionBySysUserId(Integer id) {
		return sysPermissionMapper.getPermissionBySysUserId(id);
	}

	@Override
	public List<PageData> getAvailable() {
		return sysPermissionMapper.getAvailable();
	}

}
