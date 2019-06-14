package com.jvxb.demo.sbDemo.base.entity.system;

import java.util.Date;

import com.jvxb.demo.sbDemo.base.entity.tree.TreeEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统权限
 * 
 * @author 抓娃小兵
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysPermission extends TreeEntity<SysPermission> {

	private Integer permId;
	private String permName;
	private String permDesc;
	private Integer permPid;
	private String permUrl;
	private Integer sort;
	private String isShow;
	private Date createTime;

	@Override
	public String toString() {
		return "SysPermission [permId=" + permId + ", permName=" + permName + ", permDesc=" + permDesc + ", permPid="
				+ permPid + ", isShow=" + isShow + ", permUrl=" + permUrl + ", sort=" + sort + ", createTime="
				+ createTime + ", getId()=" + getId() + ", getParentId()=" + getParentId() + ", getChildren()="
				+ getChildren() + "]";
	}

}
