package com.jvxb.demo.sbDemo.livable.modules.base.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class ParameterMap<K, V> extends HashMap<K, V> {

	private static final long serialVersionUID = 1006135006298357486L;

	public ParameterMap() {
		super();
	}

	public ParameterMap(Map<? extends K, ? extends V> m) {
		super(m);
	}

	public static ParameterMap<String, Object> create(HttpServletRequest request) {
		Map<String, String[]> map = request.getParameterMap();
		HashMap<String, Object> newMap = new HashMap<>();
		for (String key : map.keySet()) {
			newMap.put(key, getObject(map, key));
		}
		return new ParameterMap<String, Object>(newMap);
	}

	public String getString(String key) {
		Object valueObj = super.get(key);
		String value = "";
		if (null == valueObj) {
			value = "";
		} else if (valueObj instanceof String[]) {
			String[] values = (String[]) valueObj;
			for (int i = 0; i < values.length; i++) {
				value += values[i].toString() + ",";
			}
			value = value.substring(0, value.length() - 1);
		} else {
			value = valueObj.toString();
		}
		return value;
	}

	public String getLastString(String key) {
		Object valueObj = super.get(key);
		String value = "";
		if (null == valueObj) {
			value = "";
		} else if (valueObj instanceof String[]) {
			String[] values = (String[]) valueObj;
			value += values[values.length - 1].toString();
		} else {
			value = valueObj.toString();
		}
		return value;
	}

	public String getFirstString(String key) {
		Object valueObj = super.get(key);
		String value = "";
		if (null == valueObj) {
			value = "";
		} else if (valueObj instanceof String[]) {
			String[] values = (String[]) valueObj;
			value += values[0].toString();
		} else {
			value = valueObj.toString();
		}
		return value;
	}

	public String[] getStrings(String key) {
		Object valueObj = super.get(key);
		String value = "";
		if (null == valueObj) {
			value = "";
		} else if (valueObj instanceof String[]) {
			String[] values = (String[]) valueObj;
			return values;
		} else {
			value = valueObj.toString();
		}
		return new String[] { value };
	}

	public static Object getObject(Map<String, ? extends Object> map, String key) {
		Object valueObj = map.get(key);
		if (valueObj instanceof String[]) {
			String[] values = (String[]) valueObj;
			if (values.length == 1) {
				return values[0].toString();
			}
		}
		return valueObj;
	}
}

