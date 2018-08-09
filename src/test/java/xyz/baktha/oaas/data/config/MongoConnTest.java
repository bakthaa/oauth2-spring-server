/**
 * 
 */
package xyz.baktha.oaas.data.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author power-team
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest (classes = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class MongoConnTest {

	@Autowired
	MongoProperties mp;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	ApplicationContext ac;
	
	@Before
	public void setUp() throws JsonProcessingException {
		
		Map<String, String> myMap = new HashMap<>();
		myMap.put("date", LocalDateTime.now() + "" );
		String val = new ObjectMapper().writeValueAsString(myMap);
		mongoTemplate.insert(val , "test");
	}
	
	@Test
	 public void test() {
		
		System.out.println("first test");
		assertEquals("oaas", mp.getDatabase());
		assertTrue(mongoTemplate.collectionExists("test"));
		mongoTemplate.findAll(Map.class, "test").forEach(System.out::println);
		assertTrue(mongoTemplate.findAll(Map.class, "test").size() > 0);
//		Arrays.asList(ac.getBeanDefinitionNames()).stream().sorted().forEach(System.out::println);
	}

}
