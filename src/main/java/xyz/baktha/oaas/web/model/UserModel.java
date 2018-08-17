package xyz.baktha.oaas.web.model;

import java.util.Set;

import lombok.Data;
import lombok.ToString;

@Data
public class UserModel {

	private String uname;
	@ToString.Exclude
	private String pwd;
	@ToString.Exclude
	private String rePwd;
	private Set<String> grants;
}
