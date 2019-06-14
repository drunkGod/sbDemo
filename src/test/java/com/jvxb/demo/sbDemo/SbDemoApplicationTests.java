package com.jvxb.demo.sbDemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SbDemoApplicationTests {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void contextLoads() {
		
		System.err.println("-----------------------------------------------");
//		List<PageData> result = baseMapper.excuteSql("show create table live_mydb_test");
//		System.err.println(PageData);
		logger.info("it is a info");
		logger.debug("it is a info");
		logger.warn("it is a info");
		logger.error("it is a info");
		System.err.println("-----------------------------------------------");
	}


}
