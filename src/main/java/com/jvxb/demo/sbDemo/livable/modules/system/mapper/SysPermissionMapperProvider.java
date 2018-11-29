package com.jvxb.demo.sbDemo.livable.modules.system.mapper;

import com.jvxb.demo.sbDemo.livable.utils.CommonUtil;
import com.jvxb.demo.sbDemo.livable.utils.PageData;

public class SysPermissionMapperProvider {
	
    private String column = "perm_id, perm_name, perm_desc, perm_pid, perm_url, sort, is_show, create_time";

	public String getSysPermissionPageData(Integer id, Integer pid) {
		String sql = "select "
				+ "sp.perm_id as id, sp.perm_pid as parentId, " 
				+ CommonUtil.turnUnderlineVar2Camel(
				"  sp.perm_id, sp.perm_pid, sp.perm_name, sp.perm_desc, sp.perm_url, sp.is_show, sp.sort, sp.create_time")
				+ " from sys_permission sp "
				+ " where 1 = 1 ";
		if (id == null && pid == null) {
			sql += " and sp.perm_pid is null  ";
		} else if (id != null) {
			sql += " and sp.perm_pid = #{id}  ";
		}
		sql += " order by sp.sort, sp.create_time desc";
		return sql;
	}

	public String getPermissionBySysUserId(Integer id) {
		String sql = "select"
				+ "  sp.perm_id as id, sp.perm_id as permId, sp.perm_pid as permPid,"
				+ "	 sp.perm_pid as parentId, sp.perm_name as permName, sp.perm_name as permDesc,"
				+ "	 sp.perm_url as permUrl, sp.sort, sp.create_time as createTime " 
				+ " from sys_role_perm srp "
				+ " left join sys_permission sp on srp.perm_id = sp.perm_id "
				+ " left join sys_user su on srp.role_id = su.role_id " 
				+ " where su.id = #{id} and sp.is_show = 1 ";
		return sql;
	}

	public String insertOrUpdate(PageData pd) {
        String sql = "";
        pd.turnEmptyValueToNull();
        if (pd.get("permId") == null) {
            sql = insert(pd);
        } else {
            sql = update(pd);
        }
        return sql;
    }

    public String insert(PageData pd) {
        String sql = "";
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("insert into sys_permission (");
        sqlBuilder.append(column);
        sqlBuilder.append(") values (");
        sqlBuilder.append(" #{permId}, ");
        sqlBuilder.append(" #{permName}, ");
        sqlBuilder.append(" #{permDesc}, ");
        sqlBuilder.append(" #{permPid}, ");
        sqlBuilder.append(" #{permUrl}, ");
        sqlBuilder.append(" #{sort}, ");
        sqlBuilder.append(" #{isShow}, ");
        sqlBuilder.append(" #{createTime} ");
        sqlBuilder.append(")");
        sql = sqlBuilder.toString();
        return sql;
    }

    public String update(PageData pd) {
        String sql = "";
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("update sys_permission set ");
        if(pd.get("permId") != null) {
			sqlBuilder.append(" perm_id = #{permId},");
		}
        if(pd.get("permName") != null) {
			sqlBuilder.append(" perm_name = #{permName},");
		}
        if(pd.get("permDesc") != null) {
			sqlBuilder.append(" perm_desc = #{permDesc},");
		}
        if(pd.get("permPid") != null) {
			sqlBuilder.append(" perm_pid = #{permPid},");
		}
        if(pd.get("permUrl") != null) {
			sqlBuilder.append(" perm_url = #{permUrl},");
		}
        if(pd.get("sort") != null) {
			sqlBuilder.append(" sort = #{sort},");
		}
        if(pd.get("isShow") != null) {
			sqlBuilder.append(" is_show = #{isShow},");
		}
        if(pd.get("createTime") != null) {
			sqlBuilder.append(" create_time = #{createTime},");
		}
        sql = CommonUtil.trimLastDot(sqlBuilder.toString());
        sql = sql + " where perm_id =  #{permId}";
        return sql;
    }

    public String updateAll(PageData pd) {
        String sql = "";
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("update sys_permission set ");
		sqlBuilder.append(" perm_id = #{permId},");
		sqlBuilder.append(" perm_name = #{permName},");
		sqlBuilder.append(" perm_desc = #{permDesc},");
		sqlBuilder.append(" perm_pid = #{permPid},");
		sqlBuilder.append(" perm_url = #{permUrl},");
		sqlBuilder.append(" sort = #{sort},");
		sqlBuilder.append(" is_show = #{isShow},");
		sqlBuilder.append(" create_time = #{createTime},");
        sql = CommonUtil.trimLastDot(sqlBuilder.toString());
        sql = sql + " where perm_id = #{permId}";
        return sql;
    }

	public String deletes(String ids) {
		StringBuilder sql = new StringBuilder("delete from sys_permission where perm_id in (");
		String delId = "";
		for (String id : ids.split(",")) {
			delId += id + ",";
		}
		delId = CommonUtil.trimLast(delId);
		sql.append(delId).append(") ");
		sql.append(" or perm_pid in (");
		sql.append(delId).append(") ");
		return sql.toString();
	}
}
