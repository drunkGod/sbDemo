package com.jvxb.demo.sbDemo.livable.modules.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jvxb.demo.sbDemo.base.entity.system.SysUser;
import com.jvxb.demo.sbDemo.livable.modules.system.mapper.SysUserMapper;
import com.jvxb.demo.sbDemo.livable.modules.system.service.ISysUserService;
import com.jvxb.demo.sbDemo.livable.utils.LayuiPageUtil;
import com.jvxb.demo.sbDemo.livable.utils.PageData;

@Service
public class SysUserServiceImpl implements ISysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;

	@Override
	public SysUser checkExist(String username, String password) {
		return sysUserMapper.checkExist(username, password);
	}

	@Override
	public JSONObject getTablePageData(Integer page, Integer limit, PageData pd) {
		PageHelper.startPage(page, limit);
		String name = pd.getString("name");
		String createTimeStart = pd.getString("createTimeStart");
		String createTimeEnd = pd.getString("createTimeEnd");
		List<PageData> list = sysUserMapper.getTablePageData(name, createTimeStart, createTimeEnd);
		PageInfo<PageData> pageInfo = new PageInfo<>(list);
		JSONObject pageJson = LayuiPageUtil.getLayuiPage(pageInfo);
		return pageJson;
	}

	@Override
	public void insertOrUpdate(PageData pd) {
		sysUserMapper.insertOrUpdate(pd);
	}

	@Override
	public void delete(PageData pd) {
		sysUserMapper.delete(pd);
	}

	@Override
	public PageData get(PageData pd) {
		return sysUserMapper.get(pd);
	}

	@Override
	public List<PageData> getAll(PageData pd) {
		return null;
	}

	@Override
	public void updateAll(PageData pd) {
		sysUserMapper.updateAll(pd);
	}

}
