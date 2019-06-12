package com.jvxb.demo.sbDemo.livable.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

/**
 * 一般文件相关工具类
 * 
 * @author 抓娃小兵
 */
public class FileUtil {

	/**
	 * @Description : 上传文件
	 * @param file     : 上传的文件实体
	 * @param filePath : 上传文件的目标路径
	 * @param fileName : 文件名
	 * @throws Exception
	 */
	public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
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
	 * 文件下载
	 * 
	 * @param response
	 * @param filePath : 下载的文件地址
	 * @param fileName : 下载后显示的名字
	 * @throws Exception
	 */
	public static void fileDownload(final HttpServletResponse response, String filePath, String fileName)
			throws Exception {

		byte[] data = FileUtil.toByteArray2(filePath);
		fileName = URLEncoder.encode(fileName, "UTF-8");
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream;charset=UTF-8");
		OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
		outputStream.write(data);
		outputStream.flush();
		outputStream.close();
		response.flushBuffer();
	}

	/**
	 * 文件下载
	 * 
	 * @param response
	 * @param file     : 下载的文件对象
	 * @param fileName : 下载后显示的名字
	 * @throws Exception
	 */
	public static void fileDownload(final HttpServletResponse response, File file, String fileName) throws Exception {

		byte[] data = FileUtil.toByteArray2(file);
		fileName = URLEncoder.encode(fileName, "UTF-8");
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream;charset=UTF-8");
		OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
		outputStream.write(data);
		outputStream.flush();
		outputStream.close();
		response.flushBuffer();
	}

	/**
	 * 文件转为byte数组
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	private static byte[] toByteArray2(String filePath) throws Exception {
		File file = new File(filePath);
		if (!file.exists()) {
			throw new Exception(filePath);
		}
		return toByteArray2(file);
	}

	private static byte[] toByteArray2(File file) throws Exception {
		FileChannel channel = null;
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(file);
			channel = fs.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
			while ((channel.read(byteBuffer)) > 0) {
				// do nothing
				// System.out.println("reading");
			}
			return byteBuffer.array();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 覆盖内容写文件
	 * 
	 * @param fileName
	 * @param content
	 */
	public static void writeFile(String fileName, String content) {
		writeFile(fileName, content, false);
	}

	/**
	 * 追加内容写文件
	 * 
	 * @param fileName
	 * @param content
	 */
	public static void writeFile(String fileName, String content, boolean append) {
		File f = new File(fileName);
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			if (!f.exists()) {
				f.createNewFile();
			}

			if (append) {
				fw = new FileWriter(f.getAbsoluteFile(), true); // true表示可以追加新内容
			} else {
				fw = new FileWriter(f.getAbsoluteFile()); // 表示不追加，每次覆盖之前的内容
			}
			bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将文件内容读到List
	 * 
	 * @return
	 * @throws IOException
	 */
	public static List<String> readFileAsList(String filePath) {
		File file = new File(filePath);
		return readFileAsList(file);
	}
	
	/**
	 * 将文件内容读到String
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String readFileAsString(String filePath) throws IOException {
		File file = new File(filePath);
		return readFileAsString(file);
	}
	
	/**
	 * 将文件内容读到String
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String readFileAsString(File file) throws IOException {
		BufferedReader r = new BufferedReader(new FileReader(file));
		StringBuffer b = new StringBuffer();
		while (true) {
			int ch = r.read();
			if (ch == -1) {
				break;
			}
			b.append((char) ch);
		}
		r.close();
		return b.toString();
	}

	/**
	 * 将文件内容读到List
	 * 
	 * @return
	 */
	public static List<String> readFileAsList(File file) {
		BufferedReader buf = null;
		List<String> contentList = null;
		try {
			buf = new BufferedReader(new FileReader(file));
			contentList = new ArrayList<>();
			String str = null;
			while ((str = buf.readLine()) != null) {
				contentList.add(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				buf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return contentList;
	}

	/**
	 * 生成多个目录：
	 * 
	 * @param dirPath
	 * @throws Exception
	 */
	public static void genDirs(String dirPath) throws Exception {
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdirs();
			for (int i = 0; i < 10; i++) {
				Thread.sleep(500);
				if (dir.exists()) {
					break;
				}
			}
		}
	}

	/**
	 * 生成单个目录：
	 * 
	 * @param dirPath
	 * @throws Exception
	 */
	public static void genDir(String dirPath) throws Exception {
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdir();
			Thread.sleep(300);
			if (!dir.exists()) {
				for (int i = 0; i < 10; i++) {
					Thread.sleep(500);
					if (dir.exists()) {
						break;
					}
				}
			}
		}
	}

	// 删除当前目录下的所有子目录
	public static void delDirs(String dirPath) {
		try {
			delAllFile(dirPath); // 删除完里面所有内容
			System.out.println("删除" + dirPath + "下的所有内容成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
	public static boolean delAllFile(String path) {

		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + File.separator + tempList[i]);// 先删除文件夹里面的文件
				delDirs(path + File.separator + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 打开目标文件夹
	 * 
	 * @param dirPath
	 */
	public static void openDir(String dirPath) {
		File dir = new File(dirPath);
		if (dir != null && dir.isDirectory()) {
			try {
				java.awt.Desktop.getDesktop().open(new File(dirPath));
			} catch (Exception e) {
			}
		}
	}

}
