package com.jvxb.demo.sbDemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jvxb.demo.sbDemo.livable.modules.base.mapper.SqlMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SbDemoApplicationTests {
	
	@Autowired
	SqlMapper baseMapper;
	
	@Test
	public void contextLoads() {
		
		System.err.println("-----------------------------------------------");
//		List<PageData> result = baseMapper.excuteSql("show create table live_mydb_test");
//		System.err.println(PageData);
		System.err.println("-----------------------------------------------");
	}


}
