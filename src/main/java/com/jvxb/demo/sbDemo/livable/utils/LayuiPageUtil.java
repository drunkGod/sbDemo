package com.jvxb.demo.sbDemo.livable.utils;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

/**
 * Layui table分页工具类
 * 
 * @author 抓娃小兵
 */
public class LayuiPageUtil {

	@SuppressWarnings("rawtypes")
	public static JSONObject getLayuiPage(PageInfo pageInfo) {
		JSONObject obj = new JSONObject();
		obj.put("code", 0);
		obj.put("msg", "请求异常！");
		obj.put("count", pageInfo.getTotal());
		obj.put("data", pageInfo.getList());
		return obj;
	}

}
