package xyz.baktha.oaas.web.model;

import java.util.Set;

import lombok.Data;
import lombok.ToString;

@Data
public class ClientModel {

	private Set<String> authorities;
	private String clientId;
	@ToString.Exclude
	private String clientSec;
	private String validity;
	private String dirUrl;
	private Set<String> grants;
	private String resourceId;
	private Set<String> scopes;
}
