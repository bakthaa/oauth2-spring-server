package xyz.baktha.oaas.web.builder;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import xyz.baktha.oaas.web.service.UserDetailsServiceImpl;

public class DomainBuilderTest {
/*
	@Test
	public final void testToClientDetails() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testToClientDomainClientDetailsPasswordEncoder() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testToClientDomainClientDetailsPasswordEncoderClientDomain() {
		fail("Not yet implemented"); // TODO
	}


	@Test
	public final void testBuildUserDomain() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testBuildUser() {
		fail("Not yet implemented"); // TODO
	}*/

	@Test
	public final void testGetRightsSetOfString() {

		Set<String> rights = Arrays.asList("ddad",null, "null", "", " ", "adadadad", "ddad").stream().collect(Collectors.toSet());
		assertEquals(3, DomainBuilder.getRights(rights).size());
	}
	
	@Test
	public final void testGetRightsCollectionOfQextendsGrantedAuthority() {

		Set<SimpleGrantedAuthority> rights = Arrays.asList(
				new SimpleGrantedAuthority("ddad")
				,null, 
				new SimpleGrantedAuthority("null"), 
				new SimpleGrantedAuthority("adadadad")
			).stream().collect(Collectors.toSet());
		
		assertEquals(3, DomainBuilder.getRights(rights).size());
	}
	
	
}
