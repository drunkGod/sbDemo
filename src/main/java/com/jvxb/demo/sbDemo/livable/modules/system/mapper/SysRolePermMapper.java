package com.jvxb.demo.sbDemo.livable.modules.system.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.jvxb.demo.sbDemo.livable.modules.base.mapper.BaseMapper;

@Mapper
public interface SysRolePermMapper extends BaseMapper{

	@Select("SELECT group_concat(perm_id) FROM sys_role_perm where role_id = #{roleId}")
	String getRolePermByRoleId(@Param("roleId") Integer roleId);

	@SelectProvider(type = SysRolePermMapperProvider.class, method = "insertOrUpdate")
	void insertOrUpdate(@Param("roleId") Integer roleId, @Param("rolePerm") String rolePerm);

	@Delete("delete from sys_role_perm where role_id = #{roleId}")
	void deleteRolePermByRoleId(@Param("roleId") Integer roleId);

	@Delete("delete from sys_role_perm where perm_id = #{permId}")
	void deleteRolePermByPermId(@Param("permId") Integer permId);

	@SelectProvider(type = SysRolePermMapperProvider.class, method = "deleteRolePermByPermIds")
	void deleteRolePermByPermIds(@Param("perms") String permIds);

}
