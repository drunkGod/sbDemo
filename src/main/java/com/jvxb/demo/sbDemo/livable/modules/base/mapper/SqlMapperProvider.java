package com.jvxb.demo.sbDemo.livable.modules.base.mapper;

public class SqlMapperProvider {

	public String excuteSql(String sql) {
		if (sql == null || sql.length() == 0) {
			return "select null";
		}
		return sql;
	}

	public String selectOne(String columnName, String tableName, String addition) {
		String sql = "select " + columnName + " from " + tableName + " ";
		if (addition != null) {
			sql += addition;
		}
		return sql;
	}

	public String selectAll(String columnName, String tableName, String addition) {
		String sql = "select " + columnName + " from " + tableName + " ";
		if (addition != null) {
			sql += addition;
		}
		return sql;
	}

}
