package com.jvxb.demo.sbDemo.base.entity.system;

import java.util.Date;

import lombok.Data;

/**
 * 系统角色
 * 
 * @author 抓娃小兵
 */
@Data
public class SysRole {

	private Integer roleId;
	private String roleName;
	private String roleDesc;
	private Date createTime;

}
