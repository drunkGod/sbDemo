package com.jvxb.demo.sbDemo.livable.modules.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import com.jvxb.demo.sbDemo.livable.utils.LayuiPageUtil;
import com.jvxb.demo.sbDemo.livable.modules.system.mapper.SysLogMapper;
import com.jvxb.demo.sbDemo.livable.modules.system.service.ISysLogService;
import com.jvxb.demo.sbDemo.livable.utils.PageData;

@Service
public class SysLogServiceImpl implements ISysLogService {

	@Autowired
	private SysLogMapper sysLogMapper;

	@Override
	public JSONObject getTablePageData(Integer page, Integer limit, PageData pd) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, limit);
		String operator_name = pd.getString("operator_name");
		String content = pd.getString("content");
		List<PageData> list = sysLogMapper.getTablePageData(operator_name, content);
		PageInfo<PageData> pageInfo = new PageInfo<>(list);
		JSONObject pageJson = LayuiPageUtil.getLayuiPage(pageInfo);
		return pageJson;
	}

	@Override
	public void insertOrUpdate(PageData pd) {
		// TODO Auto-generated method stub
		sysLogMapper.insertOrUpdate(pd);
	}

	@Override
	public void delete(PageData pd) {
		// TODO Auto-generated method stub
		sysLogMapper.delete(pd);
	}

	@Override
	public PageData get(PageData pd) {
		return sysLogMapper.get(pd);
	}

	@Override
	public List<PageData> getAll(PageData pd) {
		// TODO Auto-generated method stub
		return sysLogMapper.getAll(pd);
	}

	@Override
	public void updateAll(PageData pd) {
		// TODO Auto-generated method stub
		
	}

}
