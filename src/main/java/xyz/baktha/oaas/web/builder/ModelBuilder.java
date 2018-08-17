package xyz.baktha.oaas.web.builder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.util.StringUtils;

import xyz.baktha.oaas.web.model.ClientModel;
import xyz.baktha.oaas.web.model.UserModel;

public final class ModelBuilder {

	public static final String ROLE_MAKKAL = "ROLE_MAKKAL";
	public static final String ROLE_CLIENT = "ROLE_CLIENT";
	public static final String ROLE_NIRVAGI = "ROLE_NIRVAGI";
	
	protected static final Function<Collection<GrantedAuthority>, Set<String>> MAPPER_FOR_AUTHORITIES_STRS = (
			authorities) -> Optional.ofNullable(authorities).map(auths -> auths.stream().filter(Objects::nonNull)
					.map(GrantedAuthority::getAuthority).collect(Collectors.toSet())).orElse(null);

	public static ClientModel toClientModel(final ClientDetails cd) {

		final ClientModel model = new ClientModel();
		model.setClientId(cd.getClientId());
		model.setResourceId(StringUtils.collectionToCommaDelimitedString(cd.getResourceIds()));
		model.setValidity("" + cd.getAccessTokenValiditySeconds());
		model.setGrants(cd.getAuthorizedGrantTypes());
		model.setDirUrl(StringUtils.collectionToCommaDelimitedString(cd.getRegisteredRedirectUri()));
		model.setScopes(cd.getScope());
		model.setAuthorities(MAPPER_FOR_AUTHORITIES_STRS.apply(cd.getAuthorities()));
		return model;
	}

	public static UserModel toUserModel(final User user) {

		final UserModel model = new UserModel();
		model.setUname(user.getUsername());
		// TODO: revisit
		model.setGrants(MAPPER_FOR_AUTHORITIES_STRS.apply(user.getAuthorities()));
		return model;

	}
	
	public static User toUser(final UserModel form) {
		
		Set<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority(ROLE_MAKKAL));

		return new User(form.getUname(), form.getPwd(), authorities);
		
	}

	public static final ClientDetails toClientDetail(final ClientModel clientModel) {

		BaseClientDetails bc = new BaseClientDetails();
		bc.setResourceIds(Arrays.asList(clientModel.getResourceId()));
		bc.setClientId(clientModel.getClientId());
		bc.setClientSecret(clientModel.getClientSec());
		bc.setAccessTokenValiditySeconds(new Integer(clientModel.getValidity()));
		bc.setAuthorizedGrantTypes(buildGrants(clientModel.getGrants()));
		bc.setRefreshTokenValiditySeconds(0);
		Set<String> uris = new HashSet<>();
		Collections.addAll(uris, clientModel.getDirUrl());
		bc.setRegisteredRedirectUri(uris);
		bc.setScope(buildScopes(clientModel.getScopes()));
		bc.setAuthorities(
				Arrays.asList(new SimpleGrantedAuthority(ROLE_CLIENT), new SimpleGrantedAuthority(ROLE_MAKKAL)));
		return bc;
	}

	public static Collection<String> buildGrants(final Set<String> arg) {

		// Arrays.asList("code", "password", "implicit");
		Set<String> grants = new HashSet<>();
		for (String grd : arg) {

			switch (grd) {

			case "1":
				grants.add("code");
				break;

			case "2":
				grants.add("implicit");
				break;

			case "3":
				grants.add("password");
				break;

			default:
				break;
			}
		}
		return grants;
	}

	public static Collection<String> buildScopes(final Set<String> arg) {

		Set<String> scopes = new HashSet<>();
		for (String scp : arg) {

			switch (scp) {

			case "1":
				scopes.add("read");
				break;

			case "2":
				scopes.add("write");
				break;

			default:
				break;
			}
		}
		return scopes;
	}
}
