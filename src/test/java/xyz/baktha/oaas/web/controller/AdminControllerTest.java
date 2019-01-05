package xyz.baktha.oaas.web.controller;

import org.assertj.core.util.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import xyz.baktha.oaas.web.model.ClientModel;
import xyz.baktha.oaas.web.model.UserModel;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminControllerTest {

	
	@Autowired
	AdminController controller;
	
	/*
	 * @Value("${m2.home}") String abc;
	 */
	
	@Test
	public final void testGetUsers() {
		
//		System.out.println(abc);
		
		System.out.println(controller.getUsers());
		
	}

	
	@Test
	public final void testGetClients() {
		
		System.out.println(controller.getClients());
	}
	
	@Test
	public final void testAdduser() {
		
		UserModel form = new UserModel();
		form.setUname("ooooo1234");
		form.setPwd("www234zzzz");		
		form.setGrants(Sets.newTreeSet("1", "2"));
		controller.adduser(form);
	}

	@Test
	public final void testAddClient() {
		
		ClientModel form = new ClientModel();
		form.setClientId("abc");
		form.setClientSec("12345");
		form.setReClientSec("12345");
		form.setValidity("3600");
		form.setDirUrl("http://localhost:8080");
		form.setResourceId("abc");
		form.setAuthorities(Sets.newTreeSet("ROLE_USER", "ROLE_ADMIN"));
		form.setGrants(Sets.newTreeSet("1", "2"));
		form.setScopes(Sets.newTreeSet("1", "2"));
		controller.addClient(form );
		
	}
//
//	@Test
//	public final void testIsValidUser() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public final void testIsValidClient() {
//		fail("Not yet implemented"); // TODO
//	}

}
