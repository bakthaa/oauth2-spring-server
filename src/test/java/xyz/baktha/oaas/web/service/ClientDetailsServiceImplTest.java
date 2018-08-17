package xyz.baktha.oaas.web.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.test.context.junit4.SpringRunner;

import xyz.baktha.oaas.OaasApplication;

@RunWith(SpringRunner.class)
@SpringBootTest (classes = OaasApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Profile("test")
public class ClientDetailsServiceImplTest {
	
	@Autowired
	ClientDetailsServiceImpl clientDetailService;
	
	
	

//	@Test
	public final void a_testAddClientDetails() {
		
		
		BaseClientDetails bc = new BaseClientDetails();

		bc.setClientId("Simple_ID_2");
		bc.setClientSecret("JunitSec");
		bc.setAccessTokenValiditySeconds(100);
		bc.setAuthorizedGrantTypes(Arrays.asList("code", "password", "implicit"));
		bc.setRefreshTokenValiditySeconds(0);
		Set<String> uris = new HashSet<>();
		Collections.addAll(uris, "http://localhost:8080/logg");
		bc.setRegisteredRedirectUri(uris);
		bc.setResourceIds(Arrays.asList("Test_Resource"));
		bc.setScope(Arrays.asList("read", "write"));
		bc.setAuthorities(Arrays.asList(
				new SimpleGrantedAuthority("ROLE_USER"),
				new SimpleGrantedAuthority("ROLE_ADMIN")));
		clientDetailService.addClientDetails(bc);
	}
//
//	@Test
//	public final void testDeleteAll() {
//		fail("Not yet implemented"); // TODO
//	}
//
	@Test
	public final void b_testListClientDetails() {
		
		clientDetailService.listClientDetails().forEach(System.out::println);
	}

	@Test
	public final void c_testLoadClientByClientId() {
		
		ClientDetails res = clientDetailService.loadClientByClientId("Simple_ID_2");
		Assert.assertNotNull(res);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Assert.assertTrue(encoder.matches("JunitSec", res.getClientSecret()));
	}
//
//	@Test
//	public final void testRemoveClientDetails() {
//		fail("Not yet implemented"); // TODO
//	}

	@Test
	public final void d_testUpdateClientDetails() {
		
		BaseClientDetails bc = new BaseClientDetails();

		bc.setClientId("Simple_ID_2");
		bc.setClientSecret("JunitSec");
		bc.setAccessTokenValiditySeconds(100);
		bc.setAuthorizedGrantTypes(Arrays.asList("code", "password", "implicit"));
		bc.setRefreshTokenValiditySeconds(0);
		Set<String> uris = new HashSet<>();
		Collections.addAll(uris, "http://localhost:4200/login");
		bc.setRegisteredRedirectUri(uris);
		bc.setResourceIds(Arrays.asList("test_resource"));
		bc.setScope(Arrays.asList("read"));
		bc.setAuthorities(Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"), 
				new SimpleGrantedAuthority("ROLE_CLIENT")));
		
		clientDetailService.updateClientDetails(bc);
	}

	@Test
	public final void e_testUpdateClientSecret() {
		
		clientDetailService.updateClientSecret("Simple_ID_2", "JunitSec_2");
	}

}
