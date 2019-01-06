package xyz.baktha.oaas.data.repo;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
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
import xyz.baktha.oaas.data.domain.ClientDomain;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@Import(MongoConfig.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientDetailRepoTest {

	
	
	@Autowired
	ClientDetailRepo clientDetailRepo;	
	
	ClientDomain cd;
	
	
	@Before
	public void setup() {
		
		cd = new ClientDomain();
		cd.setClient("Simple_ID_2");
		cd.setClientSecret("JunitSec");
		cd.setAccessTokenValiditySeconds(100);
		
		cd.setAuthorizedGrantTypes(toSet("code", "password", "implicit"));
		cd.setRefreshTokenValiditySeconds(0);
		
		Set<String> uris = new HashSet<>();
		Collections.addAll(uris, "http://localhost:8080/logg");
		cd.setRegisteredRedirectUri(uris);
		cd.setResourceIds(toSet("Test_Resource"));
		cd.setScope(toSet("read", "write"));
		cd.setAuthorities(toSet("ROLE_USER", "ROLE_ADMIN"));
	}
	
	@Test
	public void atestNotNull() {
		
		
//		clientDetailRepo.save(entity);
		assertNotNull(clientDetailRepo);
		assertNotNull(cd);
	}
	

	@Test
	public void btestSave() {
		
		clientDetailRepo.save(cd);
	}
	
	@Test
	public void ctestFindAll() {
		
		
		assertTrue(clientDetailRepo.findAll().size() > 0);		
	}
	
	@Test
	public void dtestDelete() {
		
		clientDetailRepo.deleteById(clientDetailRepo.findByClient(cd.getClient()).getId());
	}

	@SafeVarargs
	private static <E> Set<E> toSet(E... e) {
		
		return Arrays.asList(e).stream().collect(Collectors.toSet());
	}

}
