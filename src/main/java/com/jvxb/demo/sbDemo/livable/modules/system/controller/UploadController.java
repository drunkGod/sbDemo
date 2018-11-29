package com.jvxb.demo.sbDemo.livable.modules.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jvxb.demo.sbDemo.livable.configuration.sysConf.UploadPathConfig;
import com.jvxb.demo.sbDemo.livable.utils.CommonFileUtil;
import com.jvxb.demo.sbDemo.livable.utils.DateUtil;
import com.jvxb.demo.sbDemo.livable.utils.response.ResponseMessage;

/**
 * 上传控制器
 * 
 * @author 抓娃小兵
 */
@Controller
@RequestMapping("/admin/uploadController")
public class UploadController {
	
	@Autowired
	UploadPathConfig uploadPathConfig;

	// 处理文件上传
	@RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
	@ResponseBody
	public Object uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		String imgSrc = null;
		String uploadType = "img";
		if (file != null && !file.isEmpty()) {
			// 设置文件名称
			String fileName = file.getOriginalFilename();
			fileName = CommonFileUtil.formatImgName(fileName);
			// 设置实际上传路径: 基本路径/类型/日期/
			String basePath = uploadPathConfig.getUploadPathConfig();
			String nowDate = DateUtil.getDays();
			String realPath = CommonFileUtil.formatUploadPath(basePath, uploadType, nowDate);
			try {
				//开始上传
				CommonFileUtil.uploadFile(file.getBytes(), realPath, fileName);
				// 上传成功后，返回前台展示需要的图片地址
				imgSrc = CommonFileUtil.getResourceSrc(uploadType, nowDate, fileName);
			} catch (Exception e) {
				return ResponseMessage.error(e.getMessage());
			}
		}
		return ResponseMessage.ok(imgSrc);
	}

	// 处理文件上传
	@RequestMapping(value = "/uploadExcel", method = RequestMethod.POST)
	@ResponseBody
	public Object uploadExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		String excelSrc = null;
		String uploadType = "excel";
		if (file != null && !file.isEmpty()) {
			// 设置文件名称
			String fileName = file.getOriginalFilename();
			fileName = CommonFileUtil.formatImgName(fileName);
			// 设置实际上传路径: 基本路径/类型/日期/
			String basePath = uploadPathConfig.getUploadPathConfig();
			String nowDate = DateUtil.getDays();
			String realPath = CommonFileUtil.formatUploadPath(basePath, uploadType, nowDate);
			try {
				//开始上传
				CommonFileUtil.uploadFile(file.getBytes(), realPath, fileName);
				// 上传成功后，返回前台展示需要的图片地址
				excelSrc = CommonFileUtil.getResourceSrc(uploadType, nowDate, fileName);
			} catch (Exception e) {
				return ResponseMessage.error(e.getMessage());
			}
		}
		return ResponseMessage.ok(excelSrc);
	}

}
