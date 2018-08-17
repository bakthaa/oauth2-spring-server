package xyz.baktha.oaas.web.service;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.util.StringUtils;

public class AdminServiceTest {

	@Test
	public final void testGetAllUser() {
		
		List<String> str = Arrays.asList("aaaa","bbbbbb", "ccccc");
		
		Set<Integer> ans = str.stream().filter(val -> {
			System.out.println(val);
			return StringUtils.hasText(val);
					
		}).map(role -> role.length()).collect(Collectors.toSet());
		ans.forEach(System.out::println);
	}
//	@Test
//	public final void testGetAllUser() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public final void testAddUser() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public final void testGetClients() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public final void testAddClient() {
//		fail("Not yet implemented"); // TODO
//	}

}
