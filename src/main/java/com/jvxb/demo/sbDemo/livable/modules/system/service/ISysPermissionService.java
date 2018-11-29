package com.jvxb.demo.sbDemo.livable.modules.system.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jvxb.demo.sbDemo.base.entity.system.SysPermission;
import com.jvxb.demo.sbDemo.livable.utils.PageData;

public interface ISysPermissionService {

	public JSONObject getSysPermissionPageData(Integer page, Integer limit, Integer id, Integer pid);

	List<SysPermission> getSysPermissionList();

	List<SysPermission> getPermissionBySysUserId(Integer id);

	public void insertOrUpdate(PageData pd);

	public void delete(Integer permId);

	public void deletes(String ids);

	public PageData get(Integer permId);

	public List<PageData> getAvailable();

}
