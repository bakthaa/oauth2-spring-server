/**
 * 
 */
package xyz.baktha.oaas.web.builder;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.util.StringUtils;

import xyz.baktha.oaas.data.domain.ClientDomain;
import xyz.baktha.oaas.data.domain.UserDomain;

/**
 * @author power-team
 *
 */
public final class DomainBuilder {

	private static final Function<Set<String>, Collection<? extends GrantedAuthority>> MAPPER_STRINGS_TO_AUTHS = coll -> coll
			.stream().filter(str -> !StringUtils.isEmpty(str)).map(role -> new SimpleGrantedAuthority(role))
			.collect(Collectors.toList());

	private static final Function<Collection<? extends GrantedAuthority>, Set<String>> MAPPER_AUTHS_TO_STRINGS = coll -> coll
			.stream().filter(Objects::nonNull).map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

	public static ClientDetails toClientDetails(final ClientDomain clientDomain) {

		BaseClientDetails bcd = new BaseClientDetails();
		bcd.setAccessTokenValiditySeconds(clientDomain.getAccessTokenValiditySeconds());
		bcd.setAuthorizedGrantTypes(clientDomain.getAuthorizedGrantTypes());
		bcd.setClientId(clientDomain.getClient());
		bcd.setClientSecret(clientDomain.getClientSecret());
		bcd.setRefreshTokenValiditySeconds(clientDomain.getRefreshTokenValiditySeconds());
		bcd.setRegisteredRedirectUri(clientDomain.getRegisteredRedirectUri());
		bcd.setResourceIds(clientDomain.getResourceIds());
		bcd.setScope(clientDomain.getScope());
		bcd.setAutoApproveScopes(clientDomain.getScope());
		Collection<? extends GrantedAuthority> authorities = Optional.ofNullable(clientDomain.getAuthorities())
				.map(MAPPER_STRINGS_TO_AUTHS).orElse(null);
		bcd.setAuthorities(authorities);
		return bcd;
	};

	public static ClientDomain toClientDomain(final ClientDetails cd, final PasswordEncoder encoder) {

		return toClientDomain(cd, encoder, new ClientDomain());
	}

	public static ClientDomain toClientDomain(final ClientDetails cd, final PasswordEncoder encoder,
			final ClientDomain clientDomain) {

		clientDomain.setAccessTokenValiditySeconds(cd.getAccessTokenValiditySeconds());
		clientDomain.setAdditionalInformation(cd.getAdditionalInformation());
		clientDomain.setAuthorizedGrantTypes(cd.getAuthorizedGrantTypes());
		clientDomain.setClient(cd.getClientId());
//		clientDomain.setId(cd.getClientId());
		clientDomain.setClientSecret(encoder.encode(cd.getClientSecret()));
		clientDomain.setRefreshTokenValiditySeconds(cd.getRefreshTokenValiditySeconds());
		clientDomain.setRegisteredRedirectUri(cd.getRegisteredRedirectUri());
		clientDomain.setResourceIds(cd.getResourceIds());
		clientDomain.setScope(cd.getScope());
		clientDomain.setScoped(cd.isScoped());
		clientDomain.setSecretRequired(cd.isSecretRequired());
		clientDomain.setAutoApprove(true);

		Set<String> autho = Optional.ofNullable(cd.getAuthorities()).map(MAPPER_AUTHS_TO_STRINGS).orElse(null);
		clientDomain.setAuthorities(autho);
		return clientDomain;
	};

	protected static Set<String> getRights(Collection<? extends GrantedAuthority> authorities) {

		return Optional.ofNullable(authorities).map(coll -> coll.stream().filter(val -> !Objects.isNull(val))
				.map(GrantedAuthority::getAuthority).collect(Collectors.toSet())).orElse(null);
	}

	protected static Set<? extends GrantedAuthority> getRights(Set<String> rights) {

		return Optional.ofNullable(rights).map(coll -> coll.stream().filter(StringUtils::hasText)
				.map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toSet())).orElse(null);

	}

	public static UserDomain buildUserDomain(final User user, final PasswordEncoder encoder) {

		if (null == user)
			return null;

		UserDomain userModel = new UserDomain();
		userModel.setPhoneNo(user.getUsername());
		userModel.setPassword(encoder.encode(user.getPassword()));
//		userModel.setDateCreated(new Date());
		userModel.setRights(getRights(user.getAuthorities()));

		return userModel;
	}
	
	public static User buildUser(UserDomain userDomain) {

		if (null == userDomain)
			return null;
		
		return new User(userDomain.getPhoneNo(), userDomain.getPassword(), getRights(userDomain.getRights()));
	}

}
