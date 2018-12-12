package com.jvxb.demo.sbDemo.livable.modules.system.mapper;

import com.jvxb.demo.sbDemo.livable.utils.CommonUtil;
import com.jvxb.demo.sbDemo.livable.utils.PageData;

public class SysLogMapperProvider {

	private String column = "id, content, uri, parameter, operator_id, operator_name, create_time";

	public String getTablePageData(String operator_name, String content, String createTimeStart, String createTimeEnd) {
        String sql = "select " + column + " from sys_log where 1 = 1 ";
		if (CommonUtil.notNullOrEmpty(operator_name)) {
			sql += "and operator_name like concat('%', #{operator_name}, '%')  ";
		}
		if (CommonUtil.notNullOrEmpty(content)) {
			sql += "and content like concat('%', #{content}, '%')  ";
		}
		if (CommonUtil.notNullOrEmpty(createTimeStart)) {
			sql += "and create_time >= #{createTimeStart} ";
		}
		if (CommonUtil.notNullOrEmpty(createTimeEnd)) {
			sql += "and create_time <= #{createTimeEnd} ";
		}
		sql += " order by create_time desc";
		return sql;
	}

	public String insertOrUpdate(PageData pd) {
        String sql = "";
        pd.turnEmptyValueToNull();
        if (pd.get("id") == null) {
            sql = insert(pd);
        }
        return sql;
    }

    public String insert(PageData pd) {
        String sql = "";
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("insert into sys_log (");
        sqlBuilder.append(column);
        sqlBuilder.append(") values (");
        sqlBuilder.append(" #{id}, ");
        sqlBuilder.append(" #{content}, ");
        sqlBuilder.append(" #{uri}, ");
        sqlBuilder.append(" #{parameter}, ");
        sqlBuilder.append(" #{operator_id}, ");
        sqlBuilder.append(" #{operator_name}, ");
        sqlBuilder.append(" #{create_time} ");
        sqlBuilder.append(")");
        sql = sqlBuilder.toString();
        return sql;
    }

	public String delete(PageData pd) {
		String sql = "delete from sys_log where id = " + pd.get("id");
		return sql;
	}

	public String get(PageData pd) {
		String sql = "select * from sys_log where id = " + pd.get("id");
		return sql;
	}

	public String getAll(PageData pd) {
		String sql = "select * from sys_log";
		return sql;
	}

}
