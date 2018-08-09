package xyz.baktha.oaas.data.repo;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import xyz.baktha.oaas.data.config.MongoConfig;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MongoAutoConfiguration.class, MongoAutoConfiguration.class})
@Import(MongoConfig.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientDetailRepoTest {

	
	@Autowired
	ClientDetailRepo clientDetail;
	
	@Test
	public void testNotNull() {
		
		System.out.println(clientDetail);
		assertNotNull(clientDetail);
	}
	
//	@Test
	public void testFindByClientId() {
		fail("Not yet implemented");
	}

//	@Test
	public void testSave() {
		fail("Not yet implemented");
	}

}
