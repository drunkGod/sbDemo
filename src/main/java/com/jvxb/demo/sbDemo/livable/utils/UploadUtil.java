package com.jvxb.demo.sbDemo.livable.utils;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.jvxb.demo.sbDemo.livable.configuration.sysConf.UploadPathConfig;

/**
 * 上传工具类
 * @author 抓娃小兵
 *
 */
@Component
public class UploadUtil {

	@Autowired
	UploadPathConfig uploadPathConfig;
	
	/**
	 * 上传图片的具体方法
	 */
	public String uploadImg(MultipartFile file) {
		//目标地址 = 上传路径 + 文件名
		String targetPath = null;
		String uploadType = "img";
		if (file != null && !file.isEmpty()) {
			// 设置实际的上传路径: 基本路径/类型/日期/
			String basePath = uploadPathConfig.getUploadPath();
			String nowDate = DateUtil.getDays();
			String realPath = FileUtil.formatUploadPath(basePath, uploadType, nowDate);
			// 设置上传的文件名称: 格式化避免重复
			String fileName = file.getOriginalFilename();
			fileName = FileUtil.formatFileName(fileName);
			try {
				//开始上传
				FileUtil.uploadFile(file.getBytes(), realPath, fileName);
				// 上传成功后，返回最终的上传地址
				targetPath = getImgSrc(uploadType, nowDate, fileName);
			} catch (Exception e) {
				return null;
			}
		}
		return targetPath;
	}
	
	/**
	 * 上传图片的具体方法
	 */
	public String uploadExcel(MultipartFile file) {
		//目标地址 = 上传路径 + 文件名
		String targetPath = null;
		String uploadType = "excel";
		if (file != null && !file.isEmpty()) {
			// 设置实际的上传路径: 基本路径/类型/日期/
			String basePath = uploadPathConfig.getUploadPath();
			String nowDate = DateUtil.getDays();
			String realPath = FileUtil.formatUploadPath(basePath, uploadType, nowDate);
			// 设置上传的文件名称: 格式化避免重复
			String fileName = file.getOriginalFilename();
			fileName = FileUtil.formatFileName(fileName);
			try {
				//开始上传
				FileUtil.uploadFile(file.getBytes(), realPath, fileName);
				// 上传成功后，返回最终的上传地址
				targetPath = getExcelPath(uploadType, nowDate, fileName);
			} catch (Exception e) {
				return null;
			}
		}
		return targetPath;
	}
	
	/**
	 * 获取当前系统下的基本上传位置
	 * @return
	 */
	public String getUploadPath() {
		return uploadPathConfig.getUploadPath();
	}
	
	/**
	 * 获取前台图片标签中所需的src, uploadResource将会映射到WebMvcConfig中配置的目标文件夹，即uploadResource = uploadSetting.properties中配置的路径
	 */
	public String getImgSrc(String type, String nowDate, String fileName) {
		return File.separator + "uploadResource" + File.separator + type + File.separator + nowDate + File.separator + fileName;
	}

	/**
	 * 获取excel的位置, 即uploadSetting.properties中配置的路径 + excel + 日期 + excel名
	 */
	public String getExcelPath(String type, String nowDate, String fileName) {
		return getUploadPath() + type + File.separator + nowDate + File.separator + fileName;
	}
	
}
