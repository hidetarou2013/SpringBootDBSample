package com.example.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootDbSampleApplication.class)
@WebAppConfiguration
public class SpringBootDbSampleApplicationTests {

	@Test
	public void contextLoads() {
	}

}
