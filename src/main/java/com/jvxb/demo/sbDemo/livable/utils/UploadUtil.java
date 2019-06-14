package com.jvxb.demo.sbDemo.livable.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.jvxb.demo.sbDemo.livable.configuration.conf.UploadPathProperties;

/**
 * 上传工具类
 * @author 抓娃小兵
 *
 */
@Component
public class UploadUtil {

	private static Random random = new Random();
	public static final String UPLOAD_RESOURCE = "uploadResource";


	@Autowired
	UploadPathProperties uploadPathConfig;
	
	/**
	   *   上传图片
	 * @param file 图片对象
	 * @return 上传Excel的具体方法，返回图片<img >标签中所需的src
	 */
	public String uploadImg(MultipartFile file) {
		//目标地址 = 上传路径 + 文件名
		String targetPath = null;
		String uploadType = "img";
		if (file != null && !file.isEmpty()) {
			// 设置实际的上传路径: 基本路径/类型/日期/
			String basePath = uploadPathConfig.getUploadPath();
			String nowDate = DateUtil.getDays();
			String realPath = formatUploadPath(basePath, uploadType, nowDate);
			// 设置上传的文件名称: 格式化以避免文件名
			String fileName = file.getOriginalFilename();
			fileName = formatFileName(fileName);
			try {
				//开始上传
				uploadFile(file.getBytes(), realPath, fileName);
				// 上传成功后，返回最终的上传地址
				targetPath = getImgSrc(uploadType, nowDate, fileName);
			} catch (Exception e) {
				return null;
			}
		}
		return targetPath;
	}
	
	/**
	   *   上传Excel文件
	 * @param file excel文件对象
	 * @return 上传Excel的具体方法，返回excel的最终路径
	 */
	public String uploadExcel(MultipartFile file) {
		//目标地址 = 上传路径 + 文件名
		String targetPath = null;
		String uploadType = "excel";
		if (file != null && !file.isEmpty()) {
			// 设置实际的上传路径: 基本路径/类型/日期/
			String basePath = uploadPathConfig.getUploadPath();
			String nowDate = DateUtil.getDays();
			String realPath = formatUploadPath(basePath, uploadType, nowDate);
			// 设置上传的文件名称: 格式化以避免文件名重复
			String fileName = file.getOriginalFilename();
			fileName = formatFileName(fileName);
			try {
				//开始上传
				uploadFile(file.getBytes(), realPath, fileName);
				// 上传成功后，返回最终的上传地址
				targetPath = getExcelPath(uploadType, nowDate, fileName);
			} catch (Exception e) {
				return null;
			}
		}
		return targetPath;
	}
	
	
	/**
	 * @Description : 上传文件
	 * @param file     : 上传的文件实体
	 * @param filePath : 上传文件的目标路径
	 * @param fileName : 文件名
	 * @throws Exception
	 */
	public void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
		File targetFile = new File(filePath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(filePath + fileName);
		out.write(file);
		out.flush();
		out.close();
	}
	
	/**
	 * 获取当前系统下配置的基本上传路径
	 * @return
	 */
	public String getUploadPath() {
		return uploadPathConfig.getUploadPath();
	}
	
	/**
	 * 格式化文件名称, 避免文件名重复
	 */
	public static String formatFileName(String fileName) {
		String prefix = fileName.getBytes().toString().substring(3);
		String midfix = new Date().getTime() + "";
		String random1 = random.nextInt(10) + "";
		String random2 = random.nextInt(10) + "";
		String suffix = fileName.replaceAll(".+(\\.\\w+)", "$1");
		String realName = prefix + midfix + random1 + random2 + suffix;
		return realName;
	}
	
	/**
	 * 设置上传路径：根据上传时间和上传类型设置
	 */
	public static String formatUploadPath(String basePath, String type, String date) {
		String uploadPath = basePath + type + File.separator + date + File.separator;
		return uploadPath;
	}
	
	/**
	 * 获取前台图片标签中所需的src, uploadResource将会映射到WebMvcConfig中配置的目标文件夹，即uploadResource = uploadSetting.properties中配置的路径
	 */
	public String getImgSrc(String type, String nowDate, String fileName) {
		return UPLOAD_RESOURCE + File.separator + type + File.separator + nowDate + File.separator + fileName;
	}

	/**
	 * 获取excel的位置, 即uploadSetting.properties中配置的路径 + excel + 日期 + excel名
	 */
	public String getExcelPath(String type, String nowDate, String fileName) {
		return getUploadPath() + type + File.separator + nowDate + File.separator + fileName;
	}
	
	
}
