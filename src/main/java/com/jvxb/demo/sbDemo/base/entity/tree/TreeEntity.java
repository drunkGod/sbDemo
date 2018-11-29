package com.jvxb.demo.sbDemo.base.entity.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 抽象树实体
 * 
 * @author 抓娃小兵
 */
public abstract class TreeEntity<T extends TreeEntity> {

	private Integer id;

	private Integer parentId;

	private List<T> children;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public List<T> getChildren() {
		return children;
	}

	public void setChildren(List<T> children) {
		this.children = children;
	}

	public static <T extends TreeEntity> List<T> makeTree(List<T> entityTreeList) {
		List<T> parentTreeList = newArrayList();
		// 遍历实体树，拿到每一个实体节点
		for (T currentEntity : entityTreeList) {
			// 如果当前节点为父节点,则加入父节点List.并允许其有子节点。
			if (currentEntity.getParentId() == null) {
				currentEntity.setChildren(newArrayList());
				parentTreeList.add(currentEntity);
			}
		}
		// 从实体树中把父节点树移除
		entityTreeList.removeAll(parentTreeList);
		// 把剩下的实体树节点，继续构建子树
		makeChildren(parentTreeList, entityTreeList);
		return parentTreeList;
	}

	private static <T extends TreeEntity> void makeChildren(List<T> parents, List<T> children) {
		if (children.isEmpty()) {
			return;
		}
		List<T> tmp = newArrayList();
		// 遍历父树、子树
		for (T parent : parents) {
			for (T child : children) {
				child.setChildren(newArrayList());
				// 如果父树节点的Id = 子树节点的 parentId
				if (Objects.equals(parent.getId(), child.getParentId())) {
					// 则将子树节点添加到当前父节点的
					parent.getChildren().add(child);
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

	private static <T> ArrayList<T> newArrayList() {
		return new ArrayList<T>();
	}

}
