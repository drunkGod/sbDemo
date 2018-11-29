package com.jvxb.demo.sbDemo.livable.modules.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jvxb.demo.sbDemo.base.entity.system.SysRole;
import com.jvxb.demo.sbDemo.livable.modules.system.mapper.SysRoleMapper;
import com.jvxb.demo.sbDemo.livable.modules.system.service.ISysRoleService;
import com.jvxb.demo.sbDemo.livable.utils.LayuiPageUtil;
import com.jvxb.demo.sbDemo.livable.utils.PageData;

@Service
public class SysRoleServiceImpl implements ISysRoleService {

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Override
	public JSONObject getSysRolePageData(Integer page, Integer rows, String name) {
		PageHelper.startPage(page, rows);
		List<SysRole> list = sysRoleMapper.getSysRolePageData(name);
		PageInfo<SysRole> pageInfo = new PageInfo<>(list);
		JSONObject pageJson = LayuiPageUtil.getLayuiPage(pageInfo);
		return pageJson;
	}

	@Override
	public void insertOrUpdate(PageData pd) {
		sysRoleMapper.insertOrUpdate(pd);
	}

	@Override
	public void delete(Integer roleId) {
		sysRoleMapper.delete(roleId);
	}

	@Override
	public PageData get(Integer roleId) {
		PageData data = sysRoleMapper.get(roleId);
		return data;
	}

	@Override
	public List<PageData> getAll() {
		List<PageData> data = sysRoleMapper.getAll();
		return data;
	}

}
