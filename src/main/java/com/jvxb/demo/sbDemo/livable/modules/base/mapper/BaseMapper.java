package com.jvxb.demo.sbDemo.livable.modules.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.jvxb.demo.sbDemo.livable.utils.PageData;

@Mapper
public interface BaseMapper {

	/**
	 * 按照输入的参数原样执行sql，并返回多条数据
	 */
	@SelectProvider(type = BaseMapperProvider.class, method = "excuteSql")
	List<PageData> excuteSql(@Param("sql") String sql);

	/**
	 * 按照输入的表名、条件执行sql，并返回单条数据
	 */
	@SelectProvider(type = BaseMapperProvider.class, method = "selectOne")
	PageData selectOne(@Param("tableName") String tableName, @Param("condition") String condition);

	/**
	 * 按照输入的列名、表名、条件执行sql，并返回单条数据
	 */
	@SelectProvider(type = BaseMapperProvider.class, method = "selectOneInFixedColumn")
	PageData selectOneWithSomeColumn(@Param("columnName") String columnName, @Param("tableName") String tableName,
			@Param("condition") String condition);

	/**
	 * 按照输入的列名、表名、条件执行sql，并返回多条数据
	 */
	@SelectProvider(type = BaseMapperProvider.class, method = "selectAll")
	List<PageData> selectAll(@Param("tableName") String tableName, @Param("condition") String condition);

	/**
	 * 按照输入的列名、表名、条件执行sql，并返回多条数据
	 */
	@SelectProvider(type = BaseMapperProvider.class, method = "selectAllInFixedColumn")
	List<PageData> selectAllWithSomeColumn(@Param("columnName") String columnName, @Param("tableName") String tableName,
			@Param("condition") String condition);

}
