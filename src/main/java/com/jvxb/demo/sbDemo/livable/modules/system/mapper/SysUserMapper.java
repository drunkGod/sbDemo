package com.jvxb.demo.sbDemo.livable.modules.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import com.jvxb.demo.sbDemo.base.entity.system.SysUser;
import com.jvxb.demo.sbDemo.livable.modules.base.mapper.SqlMapper;
import com.jvxb.demo.sbDemo.livable.utils.PageData;

@Mapper
public interface SysUserMapper extends SqlMapper{

	@Select("select id, username, real_name as realName, password, valid, role_id roleId, role_name roleName from sys_user where username = #{username} and password = #{password}")
	SysUser checkExist(@Param("username") String username, @Param("password") String password);

	@SelectProvider(type = SysUserMapperProvider.class, method = "getTablePageData")
	List<PageData> getTablePageData(@Param("name") String name, @Param("createTimeStart") String createTimeStart,
			@Param("createTimeEnd") String createTimeEnd);

	@Select("select id, id as userId, username, real_name as realName, password, valid, role_id roleId, role_name roleName, phone from sys_user where id = #{id}")
	PageData get(PageData pd);

	@Select("select id, id as userId, username, real_name as realName, password, valid, role_id roleId, role_name roleName, phone from sys_user")
	List<PageData> getAll(PageData pd);
	
	@InsertProvider(type = SysUserMapperProvider.class, method = "insertOrUpdate")
	void insertOrUpdate(PageData pd);

	@DeleteProvider(type = SysUserMapperProvider.class, method = "delete")
	void delete(PageData pd);

	@UpdateProvider(type = SysUserMapperProvider.class, method = "updateAll")
	void updateAll(PageData pd);

	@Update("update sys_user set password = #{newPass} where id = #{id}")
	void updatePassword(@Param("id") Integer id, @Param("newPass") String newPass);

}
