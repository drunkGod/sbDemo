package com.jvxb.demo.sbDemo.livable.modules.system.service;

public interface ISysRolePermService {

	String getRolePermByRoleId(Integer roleId);

	void insertOrUpdate(Integer roleId, String rolePerm);

	void deleteRolePermByRoleId(Integer roleId);

	void deleteRolePermByPermId(Integer permId);

	void deleteRolePermByPermIds(String split);

}
