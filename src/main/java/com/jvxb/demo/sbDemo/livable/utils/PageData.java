package com.jvxb.demo.sbDemo.livable.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings({"rawtypes", "unchecked"})
public class PageData extends HashMap implements Map {

	private static final long serialVersionUID = 1L;

	Map map = null;
	HttpServletRequest request;

	public PageData(HttpServletRequest request) {
		this.request = request;
		Map properties = request.getParameterMap();
		Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();
		Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		map = returnMap;
	}

	/**
	 * 初始化PageData
	 */
	public PageData() {
		map = new HashMap();
	}

	/**
	 * 初始化PageData并根据参数赋值
	 */
	public PageData(Object... objs) {
		map = new HashMap<>();
		if (objs != null && objs.length > 0 && (objs.length % 2) == 0) {
			for (int i = 0; i < objs.length; i += 2) {
				// 此方式下，key只能为String。否则不保存。防止出错。
				if (objs[i] instanceof String) {
					map.put(objs[i], objs[i + 1]);
				}
			}
		}
	}

	/**
	 * 获取属性值
	 */
	@Override
	public Object get(Object key) {
		Object obj = null;
		if (map.get(key) instanceof Object[]) {
			Object[] arr = (Object[]) map.get(key);
			obj = request == null ? arr : (request.getParameter((String) key) == null ? arr : arr[0]);
		} else {
			obj = map.get(key);
		}
		return obj;
	}

	/**
	 * 获取属性值，并转为String形式
	 */
	public String getString(Object key) {
		return get(key) == null ? null : get(key).toString();
	}

	/**
	 * 获取属性值，null或空串时返回null，否则返回非空String
	 */
	public String getNullOrNotEmpty(Object key) {
		if (key == null || get(key) == null) {
			return null;
		}
		if (get(key).toString().trim().length() == 0) {
			return null;
		}
		return get(key).toString().trim();
	}

	/**
	 * 获取属性值，并转为Integer形式
	 */
	public Integer getInteger(Object key) {
		if (key == null || get(key) == null) {
			return null;
		}
		if (get(key).toString().trim().length() > 0) {
			return Integer.valueOf(get(key).toString().trim());
		}
		return null;
	}
	
	/**
	 * 将pd中的空字符串转为null
	 */
	public PageData turnEmptyValueToNull() {
		Set entrySet = this.entrySet();
		for (Object entry : entrySet) {
			Entry<Object, Object> tmpEntry = (Entry<Object, Object>)entry;
			if(tmpEntry.getValue() != null && tmpEntry.getValue().toString().trim().length() == 0) {
				this.put(tmpEntry.getKey(), null);
			}
		}
		return this;
	}

	@Override
	public Object put(Object key, Object value) {
		return map.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return map.remove(key);
	}

	public void clear() {
		map.clear();
	}

	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return map.containsValue(value);
	}

	public Set entrySet() {
		// TODO Auto-generated method stub
		return map.entrySet();
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return map.isEmpty();
	}

	public Set keySet() {
		// TODO Auto-generated method stub
		return map.keySet();
	}

	public void putAll(Map t) {
		// TODO Auto-generated method stub
		map.putAll(t);
	}

	public int size() {
		// TODO Auto-generated method stub
		return map.size();
	}

	public Collection values() {
		// TODO Auto-generated method stub
		return map.values();
	}

	public Object copyField(Map map, String key) {
		put(key, map.get(key));
		return map.get(key);
	}

}
