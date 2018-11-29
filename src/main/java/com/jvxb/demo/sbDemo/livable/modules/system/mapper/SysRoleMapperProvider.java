package com.jvxb.demo.sbDemo.livable.modules.system.mapper;

import com.jvxb.demo.sbDemo.livable.utils.CommonUtil;
import com.jvxb.demo.sbDemo.livable.utils.PageData;

public class SysRoleMapperProvider {

	private String column = "role_id, role_name, role_desc, create_time";

	public String getSysRolePageData(String name) {
		String sql = "SELECT role_id as roleId, role_name as roleName, role_desc as roleDesc, create_time as createTime from sys_role where 1 = 1 ";
		if (name != null && name.length() > 0) {
			sql += " AND role_name like CONCAT ('%', #{name}, '%')  ";
		}
		sql += " ORDER BY create_time";
		return sql;
	}

	public String insertOrUpdate(PageData pd) {
        String sql = "";
        pd.turnEmptyValueToNull();
        if (pd.get("roleId") == null) {
            sql = insert(pd);
        } else {
            sql = update(pd);
        }
        return sql;
    }

    public String insert(PageData pd) {
        String sql = "";
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("insert into sys_role (");
        sqlBuilder.append(column);
        sqlBuilder.append(") values (");
        sqlBuilder.append(" #{roleId}, ");
        sqlBuilder.append(" #{roleName}, ");
        sqlBuilder.append(" #{roleDesc}, ");
        sqlBuilder.append(" #{createTime} ");
        sqlBuilder.append(")");
        sql = sqlBuilder.toString();
        return sql;
    }

    public String update(PageData pd) {
        String sql = "";
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("update sys_role set ");
        if(pd.get("roleId") != null) {
			sqlBuilder.append(" role_id = #{roleId},");
		}
        if(pd.get("roleName") != null) {
			sqlBuilder.append(" role_name = #{roleName},");
		}
        if(pd.get("roleDesc") != null) {
			sqlBuilder.append(" role_desc = #{roleDesc},");
		}
        if(pd.get("createTime") != null) {
			sqlBuilder.append(" create_time = #{createTime},");
		}
        sql = CommonUtil.trimLastDot(sqlBuilder.toString());
        sql = sql + " where role_id =  #{roleId}";
        return sql;
    }

    public String updateAll(PageData pd) {
        String sql = "";
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("update sys_role set ");
		sqlBuilder.append(" role_id = #{roleId},");
		sqlBuilder.append(" role_name = #{roleName},");
		sqlBuilder.append(" role_desc = #{roleDesc},");
		sqlBuilder.append(" create_time = #{createTime},");
        sql = CommonUtil.trimLastDot(sqlBuilder.toString());
        sql = sql + " where role_id = #{roleId}";
        return sql;
    }

}
