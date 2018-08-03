package xyz.baktha.oaas.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author power-team
 *
 */
@SpringBootApplication
public class OaasApplication {

	public static void main(String[] args) {
		SpringApplication.run(OaasApplication.class, args);
		
	
		
		
	}
	
}

@RestController
class TestController {
	
	@GetMapping(value = "/first")
	public String first() {
		
		return "Hello World";
	}
	
}
