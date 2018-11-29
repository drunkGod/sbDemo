package com.jvxb.demo.sbDemo.livable.modules.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.jvxb.demo.sbDemo.base.entity.system.SysRole;
import com.jvxb.demo.sbDemo.livable.modules.base.mapper.BaseMapper;
import com.jvxb.demo.sbDemo.livable.utils.PageData;

@Mapper
public interface SysRoleMapper extends BaseMapper{

	@SelectProvider(type = SysRoleMapperProvider.class, method = "getSysRolePageData")
	List<SysRole> getSysRolePageData(String name);

	@InsertProvider(type = SysRoleMapperProvider.class, method = "insertOrUpdate")
	@Options(useGeneratedKeys = true, keyProperty = "roleId")
	void insertOrUpdate(PageData pd);

	@Delete("delete from sys_role where role_id = #{roleId}")
	void delete(Integer roleId);

	@Select("select role_id roleId, role_name roleName, role_desc roleDesc, create_time createTime from sys_role where role_id = #{roleId}")
	PageData get(Integer roleId);

	@Select("select role_id roleId, role_name roleName, role_desc roleDesc, create_time createTime from sys_role")
	List<PageData> getAll();

}
