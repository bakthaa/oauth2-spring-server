package xyz.baktha.oaas.web.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Service;

import xyz.baktha.oaas.data.exception.InvalidClientException;
import xyz.baktha.oaas.data.exception.InvalidUserException;
import xyz.baktha.oaas.web.builder.ModelBuilder;
import xyz.baktha.oaas.web.model.ClientModel;
import xyz.baktha.oaas.web.model.UserModel;

@Service
public class AdminService {

	private static final Log LOG = LogFactory.getLog(AdminService.class);

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	ClientDetailsServiceImpl clientDetailsService;

	public List<UserModel> getAllUser() {

		List<User> dbusers = userDetailsService.getAll();

		return Optional.ofNullable(dbusers).map(coll -> coll.stream().filter(Objects::nonNull)
				.map(ModelBuilder::toUserModel).collect(Collectors.toList())).orElse(null);
	}

	public void addUser(final UserModel userModel) {

		
		final User user = Optional.ofNullable(userModel).map(ModelBuilder::toUser)
				.orElseThrow(() -> new InvalidUserException("userModel val null"));

		userDetailsService.addUser(user);
		LOG.info("New user added [" + user.getUsername() + ", " + user.getAuthorities() + "]");
	}

	public List<ClientModel> getClients() {

		List<ClientDetails> clientDetails = clientDetailsService.listClientDetails();

		return Optional.ofNullable(clientDetails).map(coll -> coll.stream().filter(Objects::nonNull)
				.map(ModelBuilder::toClientModel).collect(Collectors.toList())).orElse(null);
	}

	public void addClient(final ClientModel clientModel) {

		final ClientDetails bc = Optional.ofNullable(clientModel).map(ModelBuilder::toClientDetail)
				.orElseThrow(() -> new InvalidClientException("clientModel val null"));

		clientDetailsService.addClientDetails(bc);
		LOG.info("New client added [" + bc.getClientId() + ", " + bc.getAuthorities() + "]");
	}

}
