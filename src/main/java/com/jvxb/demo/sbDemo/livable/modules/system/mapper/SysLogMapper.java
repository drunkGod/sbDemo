package com.jvxb.demo.sbDemo.livable.modules.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.jvxb.demo.sbDemo.livable.modules.base.mapper.BaseMapper;
import com.jvxb.demo.sbDemo.livable.utils.PageData;

@Mapper
public interface SysLogMapper extends BaseMapper{

	@SelectProvider(type = SysLogMapperProvider.class, method = "getTablePageData")
	List<PageData> getTablePageData(@Param("operator_name") String operator_name, @Param("content") String content,
				@Param("createTimeStart") String createTimeStart, @Param("createTimeEnd") String createTimeEnd);

	@InsertProvider(type = SysLogMapperProvider.class, method = "insertOrUpdate")
	void insertOrUpdate(PageData pd);

	@DeleteProvider(type = SysLogMapperProvider.class, method = "delete")
	void delete(PageData pd);

	@SelectProvider(type = SysLogMapperProvider.class, method = "get")
	PageData get(PageData pd);

	@SelectProvider(type = SysLogMapperProvider.class, method = "getAll")
	List<PageData> getAll(PageData pd);

}
