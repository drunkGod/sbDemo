package com.jvxb.demo.sbDemo.base.entity.system;

import lombok.Data;

/**
 * 系统用户
 * 
 * @author 抓娃小兵
 */
@Data
public class SysUser {

	private Integer id;
	private String username;
	private String realName;
	private Integer roleId;
	private String roleName;
	private String password;
	private Integer valid;

}
