package com.jvxb.demo.sbDemo.base.entity.system;

/**
 * 系统用户
 * 
 * @author 抓娃小兵
 */
public class SysUser {

	private Integer id;
	private String username;
	private String realName;
	private Integer roleId;
	private String roleName;

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

	private String password;
	private Integer valid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public SysUser(Integer id, String username, String realName, Integer roleId, String roleName, String password,
			Integer valid) {
		super();
		this.id = id;
		this.username = username;
		this.realName = realName;
		this.roleId = roleId;
		this.roleName = roleName;
		this.password = password;
		this.valid = valid;
	}

	public SysUser() {
		super();
	}

	@Override
	public String toString() {
		return "SysUser [id=" + id + ", username=" + username + ", realName=" + realName + ", roleId=" + roleId
				+ ", roleName=" + roleName + ", password=" + password + ", valid=" + valid + "]";
	}

}
