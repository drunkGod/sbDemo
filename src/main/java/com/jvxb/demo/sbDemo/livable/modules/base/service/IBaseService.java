package com.jvxb.demo.sbDemo.livable.modules.base.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jvxb.demo.sbDemo.livable.utils.PageData;

public interface IBaseService {

	/**
	 * 获取数据列表数据
	 */
	JSONObject getTablePageData(Integer page, Integer limit, PageData pd);

	/**
	 * 插入/更新数据
	 */
	void insertOrUpdate(PageData pd);
	
	/**
	 * 更新所有数据
	 */
	void updateAll(PageData pd);

	/**
	 * 删除数据
	 */
	void delete(PageData pd);

	/**
	 * 获取单个数据
	 */
	PageData get(PageData pd);

	/**
	 * 获取所有数据
	 */
	List<PageData> getAll(PageData pd);
}
