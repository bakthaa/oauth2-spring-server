package xyz.baktha.oaas.web.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;

import xyz.baktha.oaas.data.domain.ClientDomain;
import xyz.baktha.oaas.data.exception.InvalidClientException;
import xyz.baktha.oaas.data.repo.ClientDetailRepo;
import xyz.baktha.oaas.web.builder.DomainBuilder;

@Service
public class ClientDetailsServiceImpl implements ClientDetailsService, ClientRegistrationService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ClientDetailRepo clientDetailsRepo;

	// @Secured("ROLE_ADMIN")
	@Override
	public void addClientDetails(ClientDetails cd) throws ClientAlreadyExistsException {

		final ClientDomain clientDomain = Optional.ofNullable(cd)
				.map(val -> DomainBuilder.toClientDomain(val, passwordEncoder))
				.orElseThrow(() -> new InvalidClientException(""));

		clientDetailsRepo.save(clientDomain);
	}

	// not used
	public void deleteAll() {
		// clientDetailsRepo.deleteAll();
	}

	@Override
	public List<ClientDetails> listClientDetails() {

		final List<ClientDomain> mdbcds = clientDetailsRepo.findAll();
		return Optional.ofNullable(mdbcds).map(clientDomains -> clientDomains.stream().filter(Objects::nonNull)
				.map(DomainBuilder::toClientDetails).collect(Collectors.toList())).orElse(null);
	}

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

		final ClientDomain clientDomain = clientDetailsRepo.findByClient(clientId);
		return Optional.ofNullable(clientDomain).map(DomainBuilder::toClientDetails)
				.orElseThrow(() -> new ClientRegistrationException("Client not found with id '" + clientId + "'"));
	}

	@Override
	// @Secured("ROLE_ADMIN")
	// Not used
	public void removeClientDetails(String clientId) throws NoSuchClientException {
		ClientDomain clientDetails = clientDetailsRepo.findByClient(clientId);
		if (null == clientDetails) {
			throw new NoSuchClientException("Client not found with ID '" + clientId + "'");
		}
		// clientDetailsRepo.delete(clientDetails);
	}

	@Override
	// @Secured("ROLE_ADMIN")
	public void updateClientDetails(ClientDetails cd) throws NoSuchClientException {

		final ClientDomain clientDomain = clientDetailsRepo.findByClient(cd.getClientId());

		if (null == clientDomain) {
			throw new NoSuchClientException("Client not found with ID '" + cd.getClientId() + "'");
		}
		DomainBuilder.toClientDomain(cd, passwordEncoder, clientDomain);
		clientDetailsRepo.save(clientDomain);
	}

	@Override
	// @Secured("ROLE_ADMIN")
	public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {

		final ClientDomain clientDetailModels = clientDetailsRepo.findByClient(clientId);
		if (null == clientDetailModels) {
			throw new NoSuchClientException("Client not found with ID '" + clientId + "'");
		}
		clientDetailModels.setClientSecret(passwordEncoder.encode(secret));
		clientDetailsRepo.save(clientDetailModels);
	}

}
