package com.jvxb.demo.sbDemo.livable.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


/**
 * ztree工具类
 * 使用要求： 类HashMap结构中拥有 "id","name","parentId"三个属性, 使用makeTree方法可使该list成为ztree结构。
 * @author 抓娃小兵
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class ZTreeUtil<T extends HashMap> {

	public static <T extends HashMap> List<T> makeTree(List<T> HashMapList) {
		List<T> parentTreeList = newArrayList();
		// 遍历HashMap树，拿到每一个HashMap节点
		for (T currentHashMap : HashMapList) {
			// 如果当前节点为父节点,则加入父节点List.并允许其有子节点。
			if (currentHashMap.get("parentId") == null) {
				currentHashMap.put("children", newArrayList());
				parentTreeList.add(currentHashMap);
			}
		}
		// 从HashMap树中把父节点树移除
		HashMapList.removeAll(parentTreeList);
		// 把剩下的HashMap树节点，继续构建子树
		makeChildren(parentTreeList, HashMapList);
		return parentTreeList;
	}

	private static <T extends HashMap> void makeChildren(List<T> parents, List<T> children) {
		if (children.isEmpty()) {
			return;
		}
		List<T> tmp = newArrayList();
		// 遍历父树、子树
		for (T parent : parents) {
			for (T child : children) {
				child.put("children", newArrayList());
				// 如果父树节点的Id = 子树节点的 parentId
				if (Objects.equals(parent.get("id"), child.get("parentId"))) {
					// 则将子树节点添加到当前父节点的
					((List<T>) parent.get("children")).add(child);
					tmp.add(child);
				}
			}
		}
		// 移除已经找到父类的子树节点
		children.removeAll(tmp);
		// 如果还存在子树节点未找到父类，继续构建树。
		if (children.size() > 0 && tmp.size() > 0) {
			makeChildren(tmp, children);
		}
	}

	private static <T extends HashMap> ArrayList<T> newArrayList() {
		return new ArrayList<T>();
	}

}
