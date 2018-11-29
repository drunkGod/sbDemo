package com.jvxb.demo.sbDemo.livable.modules.base.mapper;

public class BaseMapperProvider {

	public String excuteSql(String sql) {
		if (sql == null || sql.length() == 0) {
			return "select -1";
		}
		return sql;
	}

	public String selectOne(String tableName, String addition) {
		String sql = "select * from " + tableName  + " ";;
		//如果包含 = 号，则添加where在前面。否则直接拼上order或者limit等
		if (addition != null) {
			sql += addition;
		}
		return sql;
	}
	
	public String selectOneInFixedColumn(String columnName, String tableName, String addition) {
		String selectColumn = columnName == null ? "*" : columnName;
		String sql = "select " + selectColumn + " from " + " ";
		if (addition != null) {
			sql += addition;
		}
		return sql;
	}

	public String selectAll(String tableName, String addition) {
		String sql = "select * from " + tableName + " ";
		if (addition != null) {
			//如果包含 = 号，则添加where在前面。否则直接拼上order或者limit等
			if (addition != null) {
				sql += addition;
			}
		}
		return sql;
	}
	
	public String selectAllInFixedColumn(String columnName, String tableName, String addition) {
		String selectColumn = columnName == null ? "*" : columnName;
		String sql = "select " + selectColumn + " from " + tableName + " ";
		if (addition != null) {
			//如果包含 = 号，则添加where在前面。否则直接拼上order或者limit等
			if (addition != null) {
				sql += addition;
			}
		}
		return sql;
	}

}
