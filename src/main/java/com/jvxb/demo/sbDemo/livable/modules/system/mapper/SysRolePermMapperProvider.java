package com.jvxb.demo.sbDemo.livable.modules.system.mapper;

import com.jvxb.demo.sbDemo.livable.utils.CommonUtil;
import com.jvxb.demo.sbDemo.livable.utils.DateUtil;

public class SysRolePermMapperProvider {
	
	public String insertOrUpdate(Integer roleId, String rolePerm) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO sys_role_perm ");
		sql.append("  (role_id, perm_id, create_time) ");
		sql.append(" VALUES ");
		for(String perm : rolePerm.split(",")) {
			sql.append("("+ roleId +", "+ Integer.valueOf(perm) +", '" + DateUtil.getTime() + "'), ");
		}
		return CommonUtil.trimLast(sql.toString().trim());
	}
	
	
	public String deleteRolePermByPermIds(String ids) {
		StringBuilder sql = new StringBuilder("DELETE FROM sys_role_perm WHERE perm_id IN (");
		String delId = "";
		for (String id : ids.split(",")) {
			delId += id + ",";
		}
		delId = CommonUtil.trimLast(delId);
		sql.append(delId).append(") ");
		return sql.toString();
	}

}
