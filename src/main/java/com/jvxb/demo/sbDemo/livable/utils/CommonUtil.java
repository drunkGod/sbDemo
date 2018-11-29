package com.jvxb.demo.sbDemo.livable.utils;

import java.io.File;
import java.util.Collection;
import java.util.Map;

/**
 * 一般工具类
 * 
 * @author 抓娃小兵
 */
public class CommonUtil {

	public static String getPath() {
		String filePath = System.getProperty("java.class.path");
		String pathSplit = System.getProperty("path.separator");// windows下是";",linux下是":"

		if (filePath.contains(pathSplit)) {
			filePath = filePath.substring(0, filePath.indexOf(pathSplit));
		} else if (filePath.endsWith(".jar")) {// 截取路径中的jar包名,可执行jar包运行的结果里包含".jar"
			filePath = filePath.substring(0, filePath.lastIndexOf(File.separator) + 1);
		}
		return filePath;
	}

	/**
	 * 除去字符串除空格外的最后一位
	 * 
	 * @return
	 */
	public static String trimLast(String str) {
		if (str == null) {
			return null;
		}
		if (str.trim().length() == 0) {
			return str;
		}
		return str.trim().substring(0, str.trim().length() - 1);
	}

	/**
	 * 字符串最后一位有效位是逗号则去除该逗号，否则不处理。
	 * 
	 * @return
	 */
	public static String trimLastDot(String str) {
		if (str == null) {
			return null;
		}
		if (str.trim().length() == 0) {
			return str;
		}
		if (str.trim().endsWith(",")) {
			return str.trim().substring(0, str.trim().length() - 1);
		}
		return str;
	}

	/**
	 * 判断对象为null,字符串为空串,集合/数组长度为0时返回true
	 * 
	 * @return
	 */
	public static boolean isNullOrEmpty(Object obj) {
		// 对象是否为null
		if (obj == null) {
			return true;
		}
		// 字符或字符串是否为''
		if (obj instanceof CharSequence) {
			return ((CharSequence) obj).length() == 0;
		}
		// 集合是否为empty
		if (obj instanceof Collection) {
			return ((Collection) obj).isEmpty();
		}
		if (obj instanceof Map) {
			return ((Map) obj).isEmpty();
		}
		// 数组长度是否为0
		if (obj instanceof Object[]) {
			Object[] object = (Object[]) obj;
			if (object.length == 0) {
				return true;
			}
			boolean empty = true;
			for (Object anObject : object) {
				if (!isNullOrEmpty(anObject)) {
					empty = false;
					break;
				}
			}
			return empty;
		}
		return false;
	}

	/**
	 * 判断对象不为null,字符串不为空串,集合/数组长度不为0时返回true
	 * 
	 * @return
	 */
	public static boolean notNullOrEmpty(Object object) {
		return !isNullOrEmpty(object);
	}

	/**
	 * 获取插入数据库时所需的varchar形式
	 * 
	 * @param str
	 */
	public static String getSqlVarchar(Object str) {
		if (str == null) {
			return null;
		}

		if (str.toString().length() == 0) {
			return "''";
		}

		return "'" + str + "'";
	}

	/**
	 * 获取插入数据库时所需的int形式
	 * 
	 * @param str
	 */
	public static Integer getSqlInt(Object str) {
		if (str == null) {
			return null;
		}

		if (str.toString().trim().length() > 0) {
			try {
				return Integer.valueOf(str.toString());
			} catch (Exception e) {
			}
		}

		return null;
	}
	
	/**
	 * 获取插入数据库时所需的小数形式
	 * 
	 * @param str
	 */
	public static Double getSqlDecimal(Object str) {
		if (str == null) {
			return null;
		}
		
		if (str.toString().trim().length() > 0) {
			try {
				return Double.valueOf(str.toString());
			} catch (Exception e) {
			}
		}
		
		return null;
	}

	/**
	 * 将sql查询的结果由下划线形式转化为驼峰形式。 如 "id, user_name, sp.is_show" 转化为 "id, user_name as
	 * userName, sp.is_show as isShow"形式
	 */
	public static String turnUnderlineVar2Camel(String str) {
		if (str == null || str.trim().length() == 0) {
			return null;
		}

		StringBuilder builder = new StringBuilder();
		for (String string : str.split(",")) {
			string = string.trim();
			// 空内容，直接过滤
			if (string.length() == 0) {
				continue;
			}
			// 如果是id，或d.id形式，直接使用
			if (!string.contains("_")) {
				builder.append(string).append(", ");
				continue;
			}

			// 如果是user_name，或 d.user_name形式
			String camelStr = "";
			String[] tmpArr = string.split("_");
			// 取_前的值
			if (tmpArr[0].matches("\\w+\\.(\\w+)")) {
				camelStr = tmpArr[0].replaceFirst("\\w+\\.(\\w+)", "$1");
			} else {
				camelStr = tmpArr[0];
			}
			// 取_后的值
			if (tmpArr.length > 1) {
				for (int i = 1; i < tmpArr.length; i++) {
					// 将第一位转为大写
					camelStr += tmpArr[i].substring(0, 1).toUpperCase() + tmpArr[i].substring(1);
				}
				;
			}
			// d.user_name -> d.user_name AS userName
			builder.append(string).append(" AS ").append(camelStr).append(", ");
		}

		return trimLastDot(builder.toString()) + " ";
	}

	/**
	 * 将驼峰形式转化为下划线形式。 如 "userName -> user_name"形式
	 */
	public static String turnCamel2UnderlineVar(String str) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			if (Character.isUpperCase(str.charAt(i))) {
				// 如果是大写，则将大写转为_小写的形式
				builder.append("_" + Character.toLowerCase(str.charAt(i)));
			} else {
				builder.append(str.charAt(i));
			}
		}
		return builder.toString();
	}

	/**
	 * 非数字返回true
	 */
	public static boolean notNum(Object id) {
		if (id == null) {
			return true;
		} else if (!id.toString().matches("^\\d+$")) {
			return true;
		}
		return false;
	}

	/**
	 * 数字返回true
	 * 
	 * @param id
	 * @return
	 */
	public static boolean isNum(Object id) {
		return !notNum(id);
	}

	public static String lowerCaseFirstChar(String str) {
		if (str == null) {
			return null;
		}
		if (str.length() == 0) {
			return str;
		}
		return str.substring(0, 1).toLowerCase() + str.substring(1);

	}

}
