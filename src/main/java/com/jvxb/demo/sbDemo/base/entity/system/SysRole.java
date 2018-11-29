package com.jvxb.demo.sbDemo.base.entity.system;

import java.util.Date;

/**
 * 系统角色
 * 
 * @author 抓娃小兵
 */
public class SysRole {

	private Integer roleId;
	private String roleName;
	private String roleDesc;
	private Date createTime;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "SysRole [roleId=" + roleId + ", roleName=" + roleName + ", roleDesc=" + roleDesc + ", createTime="
				+ createTime + "]";
	}

	public SysRole(Integer roleId, String roleName, String roleDesc, Date createTime) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleDesc = roleDesc;
		this.createTime = createTime;
	}

	public SysRole() {
		super();
		// TODO Auto-generated constructor stub
	}

}
