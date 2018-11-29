package com.jvxb.demo.sbDemo.livable.modules.system.mapper;

import com.jvxb.demo.sbDemo.livable.utils.CommonUtil;
import com.jvxb.demo.sbDemo.livable.utils.PageData;

public class SysUserMapperProvider {
	
    private String column = "id, role_id, role_name, username, real_name, password, phone, valid, create_time";

	public String getTablePageData(String name, String createTimeStart, String createTimeEnd) {
		String sql = "SELECT id, username, real_name as realName, valid, role_id as roleId, role_name as roleName, phone, create_time as createTime "
				+ " FROM sys_user  where 1 = 1 ";
		if (CommonUtil.notNullOrEmpty(name)) {
			sql += "AND username like CONCAT ('%', #{name}, '%') ";
		}
		if (CommonUtil.notNullOrEmpty(createTimeStart)) {
			sql += "AND create_time >= #{createTimeStart} ";
		}
		if (CommonUtil.notNullOrEmpty(createTimeEnd)) {
			sql += "AND create_time <= #{createTimeEnd} ";
		}
		return sql;
	}
	
	public String insertOrUpdate(PageData pd) {
        String sql = "";
        pd.turnEmptyValueToNull();
        if (pd.get("id") == null) {
            sql = insert(pd);
        } else {
            sql = update(pd);
        }
        return sql;
    }

    public String insert(PageData pd) {
        String sql = "";
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("insert into sys_user (");
        sqlBuilder.append(column);
        sqlBuilder.append(") values (");
        sqlBuilder.append(" #{id}, ");
        sqlBuilder.append(" #{roleId}, ");
        sqlBuilder.append(" #{roleName}, ");
        sqlBuilder.append(" #{username}, ");
        sqlBuilder.append(" #{realName}, ");
        sqlBuilder.append(" #{password}, ");
        sqlBuilder.append(" #{phone}, ");
        sqlBuilder.append(" #{valid}, ");
        sqlBuilder.append(" #{createTime} ");
        sqlBuilder.append(")");
        sql = sqlBuilder.toString();
        return sql;
    }

    public String update(PageData pd) {
        String sql = "";
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("update sys_user set ");
        if(pd.get("id") != null) {
			sqlBuilder.append(" id = #{id},");
		}
        if(pd.get("roleId") != null) {
			sqlBuilder.append(" role_id = #{roleId},");
		}
        if(pd.get("roleName") != null) {
			sqlBuilder.append(" role_name = #{roleName},");
		}
        if(pd.get("username") != null) {
			sqlBuilder.append(" username = #{username},");
		}
        if(pd.get("realName") != null) {
			sqlBuilder.append(" real_name = #{realName},");
		}
        if(pd.get("password") != null) {
			sqlBuilder.append(" password = #{password},");
		}
//        if(pd.get("phone") != null) {
			sqlBuilder.append(" phone = #{phone},");
//		}
        if(pd.get("valid") != null) {
			sqlBuilder.append(" valid = #{valid},");
		}
        if(pd.get("createTime") != null) {
			sqlBuilder.append(" create_time = #{createTime},");
		}
        sql = CommonUtil.trimLastDot(sqlBuilder.toString());
        sql = sql + " where id =  #{id}";
        return sql;
    }
	
	public String updateAll(PageData pd) {
		pd.turnEmptyValueToNull();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("update sys_user set ");
		sqlBuilder.append(" role_id = #{roleId},");
		sqlBuilder.append(" role_name = #{roleName},");
		sqlBuilder.append(" username = #{username},");
		sqlBuilder.append(" real_name = #{realName},");
		sqlBuilder.append(" password = #{password},");
		sqlBuilder.append(" phone = #{phone},");
		sqlBuilder.append(" valid = #{valid},");
		sqlBuilder.append(" create_time = #{createTime} ");
		sqlBuilder.append("where id = " + pd.get("userId"));
		return sqlBuilder.toString();
	}
	
	public String delete(PageData pd) {
		String sql  = "delete from sys_user where id = " + pd.get("id");
		return sql;
	}
}
