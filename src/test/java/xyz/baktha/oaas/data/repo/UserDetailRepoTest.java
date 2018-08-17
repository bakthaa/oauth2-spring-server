package xyz.baktha.oaas.data.repo;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import xyz.baktha.oaas.data.config.MongoConfig;
import xyz.baktha.oaas.data.domain.UserDomain;

@RunWith(SpringRunner.class)
@SpringBootTest (classes = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@Import(MongoConfig.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDetailRepoTest {

	@Autowired
	UserDetailRepo userRepo;
	
	private static String USER_PHONE ="123456789";
	
	@Test
	public void atestInsertS() {
		
		System.out.println(userRepo);
		UserDomain user = new UserDomain();
		user.setPassword("Sdfsdfsjdhfsjdhgf");
		user.setPhoneNo(USER_PHONE);
		user.setRights(new HashSet<String>(Arrays.asList("fgdfG")));
		userRepo.save(user);
		System.out.println("User Added");
	}

	@Test
	public void btestFindAll() {
		assertTrue(userRepo.findAll().size() > 0);
	}

	@Test
	public void ctestDelete() {
		
//		userRepo.findAll().stream().forEach(user -> System.out.println(user.getId()));
		userRepo.delete(userRepo.findByPhone(USER_PHONE));
		System.out.println("User Deleted");
	}

}
