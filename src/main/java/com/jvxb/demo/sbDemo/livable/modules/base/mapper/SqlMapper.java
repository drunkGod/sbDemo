package com.jvxb.demo.sbDemo.livable.modules.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.jvxb.demo.sbDemo.livable.utils.PageData;

/**
  *  一个很不优雅，且比较粗陋，勉强还算实用的sql帮助类
 * 
 * @author 抓娃小兵
 */
@Mapper
public interface SqlMapper {

	/**
	 * 按照输入的参数原样执行sql，并返回数据，不推荐使用。
	 */
	@Deprecated
	@SelectProvider(type = SqlMapperProvider.class, method = "excuteSql")
	List<PageData> excuteSql(@Param("sql") String sql);

	/**
	 * 按照输入的列名、表名、条件执行sql，并返回单条数据
	 */
	@SelectProvider(type = SqlMapperProvider.class, method = "selectOne")
	PageData selectOne(@Param("columnName") String columnName, @Param("tableName") String tableName,
			@Param("condition") String condition);

	/**
	 * 按照输入的列名、表名、条件执行sql，并返回多条数据
	 */
	@SelectProvider(type = SqlMapperProvider.class, method = "selectAll")
	List<PageData> selectAll(@Param("columnName") String columnName, @Param("tableName") String tableName,
			@Param("condition") String condition);

}
