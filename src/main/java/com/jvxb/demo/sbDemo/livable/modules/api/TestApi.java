package com.jvxb.demo.sbDemo.livable.modules.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jvxb.demo.sbDemo.livable.modules.base.controller.BaseController;
import com.jvxb.demo.sbDemo.livable.utils.PageData;
import com.jvxb.demo.sbDemo.livable.utils.ZTreeUtil;
import com.jvxb.demo.sbDemo.livable.utils.response.ResponseMessage;

/**
 * 对外提供数据的接口
 * 
 * @author 抓娃小兵
 */

@RestController
@RequestMapping("/admin/demo/api")
public class TestApi extends BaseController {

	@GetMapping("test")
	public Object test() {
		ResponseMessage<Object> result = ResponseMessage.ok();
		String msg = "call success!";
		String requestInfo = "";
		PageData requestDataMap = getPageData();
		if (!requestDataMap.isEmpty()) {
			requestInfo = requestDataMap.toString();
		}
		result.setMessage(msg + "your requestInfo：【" + requestInfo + "】");
		return result;
	}

	@GetMapping("treeTest")
	public Object treeTest() {
		List<PageData> pageDataList = new ArrayList<>();
		PageData pd1 = new PageData("id", 1, "name", "节点1", "parentId", null);
		PageData pd2 = new PageData("id", 2, "name", "节点2", "parentId", null);
		PageData pd11 = new PageData("id", 3, "name", "节点1.1", "parentId", 1);
		PageData pd12 = new PageData("id", 4, "name", "节点1.2", "parentId", 1);
		PageData pd111 = new PageData("id", 5, "name", "节点1.1.1", "parentId", 3);
		PageData pd112 = new PageData("id", 6, "name", "节点1.1.2", "parentId", 3);
		PageData pd1111 = new PageData("id", 7, "name", "节点1.1.1.1", "parentId", 5);
		pageDataList.add(pd1);
		pageDataList.add(pd2);
		pageDataList.add(pd11);
		pageDataList.add(pd12);
		pageDataList.add(pd111);
		pageDataList.add(pd112);
		pageDataList.add(pd1111);
		pageDataList = ZTreeUtil.makeTree(pageDataList);
		return ResponseMessage.ok(pageDataList);
	}

	@GetMapping("executeSql")
	public Object executeSql(@RequestParam String sql) {
		List<PageData> pageDataList = excuteSql(sql);
		return ResponseMessage.ok(pageDataList);

	}

}
