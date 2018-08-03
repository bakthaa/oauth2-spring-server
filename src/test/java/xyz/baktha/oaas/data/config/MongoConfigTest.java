/**
 * 
 */
package xyz.baktha.oaas.data.config;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import xyz.baktha.oaas.web.app.OaasApplication;

/**
 * @author power-team
 *
 */
@RunWith(SpringRunner.class)
//@ContextConfiguration()
//@EnableMongoRepositories

//application.properties
@SpringBootTest (classes = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class}) //(classes = OaasApplication.class)
//@DataMongoTest
//MongoDataAutoConfiguration
public class MongoConfigTest {

	@Autowired
	MongoProperties mp;
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	ApplicationContext ac;
	
	@Before
	public void setUp() {
		

	}
	
	@Test
	public void test() {
		System.out.println("first test");
		System.out.println(mp.getDatabase());
		Arrays.asList(ac.getBeanDefinitionNames()).stream().sorted().forEach(System.out::println);
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, String> myMap = new HashMap<>();
			myMap.put("date", LocalDateTime.now() + "" );
			String val = objectMapper.writeValueAsString(myMap);
			
			System.out.println(val);
			mongoTemplate.insert(val , "test");
			System.out.println(mongoTemplate.collectionExists("test"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Class<String> entityClass = null;
//		System.out.println(mongoTemplate.findAll(entityClass, "test"));
	}

}
