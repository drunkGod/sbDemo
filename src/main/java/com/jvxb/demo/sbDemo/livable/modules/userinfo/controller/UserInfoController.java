package com.jvxb.demo.sbDemo.livable.modules.userinfo.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.jvxb.demo.sbDemo.livable.configuration.annotation.LogAnnotation;
import com.jvxb.demo.sbDemo.livable.modules.base.controller.BaseController;
import com.jvxb.demo.sbDemo.livable.modules.userinfo.service.IUserInfoService;
import com.jvxb.demo.sbDemo.livable.utils.CommonUtil;
import com.jvxb.demo.sbDemo.livable.utils.DateUtil;
import com.jvxb.demo.sbDemo.livable.utils.FileUtil;
import com.jvxb.demo.sbDemo.livable.utils.ObjectExcelRead;
import com.jvxb.demo.sbDemo.livable.utils.PageData;
import com.jvxb.demo.sbDemo.livable.utils.UploadUtil;
import com.jvxb.demo.sbDemo.livable.utils.response.ResponseMessage;

/**
 * 注册用户控制器
 * 
 * @author 抓娃小兵
 */
@Controller
public class UserInfoController extends BaseController {

	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	UploadUtil uploadUtil;

	/**
	 * 前往列表页
	 */
	@RequestMapping("/admin/userInfo")
	public Object userInfo(ModelMap model) {
		return "userinfo/userInfo_list";
	}

	/**
	 * 获取列表页数据
	 */
	@RequestMapping("/admin/userInfo/pageData")
	@ResponseBody
	public Object userInfoPageData(Integer page, Integer limit) {
		PageData pd = this.getPageData();
		JSONObject pageData = userInfoService.getTablePageData(page, limit, pd);
		return pageData;
	}

	/**
	 * 前往表单页
	 */
	@RequestMapping("/admin/userInfo/form")
	public Object userInfoForm(ModelMap model) {
		PageData pd = this.getPageData();
		model.addAttribute("mode", pd.get("mode"));
		model.addAttribute("id", pd.get("id"));
		//获取图片信息
		if(CommonUtil.notNullOrEmpty(pd.get("id"))) {
			List<PageData> result = excuteSql("select head_url from live_user_info where id = " + pd.get("id"));
			if(result.get(0) != null) {
				String headUrl = result.get(0).getString("head_url");
				model.addAttribute("head_url", headUrl);
			}
			List<PageData> result2 = excuteSql("select photo_url from live_user_info where id = " + pd.get("id"));
			if(result2.get(0) != null) {
				String headUrl = result2.get(0).getString("photo_url");
				model.addAttribute("photo_url", headUrl);
			}
		}
		return "userinfo/userInfo_form";
	}

	/**
	 * 保存数据
	 */
	@RequestMapping("/admin/userInfo/save")
	@ResponseBody
	@LogAnnotation(operate = "注册用户新增/编辑")
	public Object userInfoSave() {
		PageData pd = this.getPageData();
		pd.put("password",
				pd.getString("phone") != null && pd.getString("phone").length() > 6 ? pd.getString("phone").substring(5)
						: "123456");
		// 个人爱好
		String hobby = "";
		String[] hobbyArr = { "hobby[man]", "hobby[girl]", "hobby[drink]", "hobby[eat]" };
		for (String hob : hobbyArr) {
			if (CommonUtil.notNullOrEmpty(pd.get(hob))) {
				hobby += hob + ",";
			}
		}
		pd.put("hobby", CommonUtil.trimLastDot(hobby));
		// 出生日期
		pd.put("birthday", pd.getNullOrNotEmpty("birthday"));
		if (CommonUtil.notNullOrEmpty(pd.get("birthday"))){
			pd.put("age", DateUtil.getYearSub(pd.getString("birthday"), DateUtil.getDay()));
		}
		//创建人、更新人
		Date now = new Date();
		if (CommonUtil.isNullOrEmpty(pd.get("id"))){
			pd.put("create_by", getSessionUser().getId());
			pd.put("create_time", now);
		} else {
			pd.put("update_by", getSessionUser().getId());
			pd.put("update_time", now);
		}
		userInfoService.insertOrUpdate(pd);
		return ResponseMessage.ok();
	}

	/**
	 * 查看数据
	 */
	@RequestMapping("/admin/userInfo/get/{id}")
	@ResponseBody
	public Object userInfoGet(@PathVariable Integer id) {
		if (CommonUtil.notNum(id)) {
			return ResponseMessage.error("无效数据");
		}
		// 获取相关数据
		PageData pd = new PageData();
		pd.put("id", id);
		PageData data = userInfoService.get(pd);
		//将hobby分段
		if (CommonUtil.notNullOrEmpty(data.get("hobby"))) {
			String[] hobbyArr = data.getString("hobby").split(",");
			for (String hobby : hobbyArr) {
				data.put(hobby, hobby);
			}
		}
		return ResponseMessage.ok(data);
	}

	/**
	 * 删除数据
	 */
	@RequestMapping("/admin/userInfo/delete/{id}")
	@ResponseBody
	@LogAnnotation(operate = "注册用户删除")
	public Object userInfoDelete(@PathVariable Integer id) {
		if (CommonUtil.notNum(id)) {
			return ResponseMessage.error("无效数据");
		}
		PageData pd = new PageData("id", id);
		userInfoService.delete(pd);
		return ResponseMessage.ok();
	}

	/**
	 * 打开excel导入用户界面
	 */
	@RequestMapping("/user/uploadUserPage")
	public Object uploadUser() {
		return "userinfo/userInfo_upload";
	}

	/**
	 * 通过excel导入用户
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/user/uploadUser")
	@ResponseBody
	@LogAnnotation(operate = "根据模板批量添加用户")
	public Object uploadUser(@RequestParam("excel") MultipartFile file, HttpServletRequest request) {
		String excelPath = null;
		if (file != null && !file.isEmpty()) {
			try {
				//上传excel，并返回excel最终路径
				excelPath = uploadUtil.uploadExcel(file);
				//根据excel路径读取excel: 读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
				@SuppressWarnings("rawtypes")
				List<PageData> listPd = (List)ObjectExcelRead.readExcel(excelPath, 2, 0, 0);
				if (listPd != null && listPd.size() > 0) {
					listPd.forEach((e)-> System.out.println(e.toString()));
					userInfoService.insertBatch(listPd);
				}
				
			} catch (Exception e) {
				return ResponseMessage.error("批量添加失败！");
			}
		}
		return ResponseMessage.ok();
	}

	/**
	 * 下载用户excel模板
	 */
	@RequestMapping("/user/downUser")
	@LogAnnotation(operate = "下载用户模板")
	public void downUser(HttpServletResponse response) {
		//目标模板文件的位置
		String targetPosition = uploadUtil.getUploadPath() + "excel" + File.separator + "_template" + File.separator + "userTemplate.xls";
		//下载后显示的名字
		String downLownName = "user.xls";
		try {
			FileUtil.fileDownload(response, targetPosition, downLownName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
