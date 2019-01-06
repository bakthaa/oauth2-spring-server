package xyz.baktha.oaas.web.model;

import java.util.Set;

import javax.validation.constraints.Size;

import lombok.Data;
import lombok.ToString;

@Data
public class UserModel {

//	@Size(min = 8, max = 20, message = "Invalid Username")
	private String uname;

	@ToString.Exclude
//	@Size(min = 8, max = 20, message = "Invalid Password")
	private String pwd;

	private Set<String> grants;
}
