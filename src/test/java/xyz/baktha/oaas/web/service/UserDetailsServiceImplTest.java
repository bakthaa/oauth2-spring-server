package xyz.baktha.oaas.web.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import xyz.baktha.oaas.OaasApplication;

@RunWith(SpringRunner.class)
@SpringBootTest (classes = OaasApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Profile("test")
public class UserDetailsServiceImplTest {
	
	private static final String PHONE_NUM = "9876543210";
	@Autowired
	UserDetailsServiceImpl userDetailsService;;
	
	@Test
	public void a_addUser() {

		Set<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_MAKKAL"));
		authorities.add(new SimpleGrantedAuthority("ROLE_NIRVAGI"));

		User user = new User(PHONE_NUM, "test1234@", authorities);
		userDetailsService.addUser(user);
	}

	 @Test
	public void b_resetPwd() {

		userDetailsService.resetPwd(PHONE_NUM, "7777AAAAAAAAAABBBBBBB");
	}

	@Test
	public void c_resetPwdLess8() {

		System.out.println("user Reset");
		userDetailsService.resetPwd(PHONE_NUM, "645d");
	}

	@Test
	public void d_loadAllUser() {

		List<User> users = userDetailsService.getAll();
		Assert.assertNotNull(users);
		users.forEach((res) -> System.out.println(res.getUsername()));
	}
	
	@Test
	public void f_loadUserByUsername() {
		
		UserDetails user = userDetailsService.loadUserByUsername(PHONE_NUM);
		Assert.assertNotNull(user);
		Assert.assertEquals(PHONE_NUM, user.getUsername());
		
	}
	@Test(expected=UsernameNotFoundException.class)
	public void testLoadUserByUsername() {
		
		userDetailsService.loadUserByUsername("unknown");
		
	}
	
	@Test
	public void g_deleteUser() {
		
		long result = userDetailsService.deleteUser(PHONE_NUM);
		Assert.assertTrue(result > 0);
	}
	

}
