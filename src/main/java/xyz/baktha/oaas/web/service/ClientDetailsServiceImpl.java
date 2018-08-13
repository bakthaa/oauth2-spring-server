package xyz.baktha.oaas.web.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import xyz.baktha.oaas.data.model.ClientDetail;
import xyz.baktha.oaas.data.repo.ClientDetailRepo;

@Service
public class ClientDetailsServiceImpl implements ClientDetailsService, ClientRegistrationService {

    private static BaseClientDetails getClientFromMongoDBClientDetails(ClientDetail clientDetails) {
        BaseClientDetails bc = new BaseClientDetails();
        bc.setAccessTokenValiditySeconds(clientDetails.getAccessTokenValiditySeconds());
        bc.setAuthorizedGrantTypes(clientDetails.getAuthorizedGrantTypes());
        bc.setClientId(clientDetails.getClientId());
        bc.setClientSecret(clientDetails.getClientSecret());
        bc.setRefreshTokenValiditySeconds(clientDetails.getRefreshTokenValiditySeconds());
        bc.setRegisteredRedirectUri(clientDetails.getRegisteredRedirectUri());
        bc.setResourceIds(clientDetails.getResourceIds());
        bc.setScope(clientDetails.getScope());
        bc.setAutoApproveScopes(clientDetails.getScope());
        if(null != clientDetails.getAuthorities()){
        	
        	Collection<GrantedAuthority> authorities = new HashSet<>();
        	for (String client : clientDetails.getAuthorities()) {
        		authorities.add(new SimpleGrantedAuthority(client));
			}
        	bc.setAuthorities(authorities);
        }
        return bc;
    }
    
    private static List<ClientDetails> getClientsFromMongoDBClientDetails(List<ClientDetail> clientDetails) {
        List<ClientDetails> bcds = new LinkedList<>();
        if (clientDetails != null && !clientDetails.isEmpty()) {
        	
        	for (ClientDetail clientDetail : clientDetails) {
				
        		bcds.add(getClientFromMongoDBClientDetails(clientDetail));
			}
        }
        return bcds;
    }

    private ClientDetail getMongoDBClientDetailsFromClient(ClientDetails cd) {
    	ClientDetail clientDetailModel = new ClientDetail();
        clientDetailModel.setAccessTokenValiditySeconds(cd.getAccessTokenValiditySeconds());
        clientDetailModel.setAdditionalInformation(cd.getAdditionalInformation());
        clientDetailModel.setAuthorizedGrantTypes(cd.getAuthorizedGrantTypes());
        clientDetailModel.setClientId(cd.getClientId());
        clientDetailModel.setClientSecret(passwordEncoder.encode(cd.getClientSecret()));
        clientDetailModel.setRefreshTokenValiditySeconds(cd.getRefreshTokenValiditySeconds());
        clientDetailModel.setRegisteredRedirectUri(cd.getRegisteredRedirectUri());
        clientDetailModel.setResourceIds(cd.getResourceIds());
        clientDetailModel.setScope(cd.getScope());
        clientDetailModel.setScoped(cd.isScoped());
        clientDetailModel.setSecretRequired(cd.isSecretRequired());
        clientDetailModel.setId(cd.getClientId());
        clientDetailModel.setAutoApprove(true);
        
        if(null != cd.getAuthorities()){
        	
        	Collection<String> autho = new HashSet<>();       	
        	for (GrantedAuthority a : cd.getAuthorities()) {
        		
        		autho.add(a.getAuthority());    	
        	}
        	clientDetailModel.setAuthorities(autho);
        }
        return clientDetailModel;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientDetailRepo clientDetailsRepo;

    //@Secured("ROLE_ADMIN")
    @Override
    public void addClientDetails(ClientDetails cd) throws ClientAlreadyExistsException {
    	ClientDetail clientDetails = getMongoDBClientDetailsFromClient(cd);
        clientDetailsRepo.save(clientDetails);
    }

    public void deleteAll() {
        //clientDetailsRepo.deleteAll();
    }

    @Override
    public List<ClientDetails> listClientDetails() {
        List<ClientDetail> mdbcds = clientDetailsRepo.findAll();
        return getClientsFromMongoDBClientDetails(mdbcds);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientDetail clientDetails = clientDetailsRepo.findByClientId(clientId);
        if (null == clientDetails) {
            throw new ClientRegistrationException("Client not found with id '" + clientId + "'");
        }
        return getClientFromMongoDBClientDetails(clientDetails);
    }

    @Override
    //@Secured("ROLE_ADMIN")
    public void removeClientDetails(String clientId) throws NoSuchClientException {
    	ClientDetail clientDetails = clientDetailsRepo.findByClientId(clientId);
        if (null == clientDetails) {
            throw new NoSuchClientException("Client not found with ID '" + clientId + "'");
        }
        //clientDetailsRepo.delete(clientDetails);
    }

    /*public ClientDetail save(ClientDetail authClient) {
        return clientDetailsRepo.save(authClient);
    }*/

    @Override
    //@Secured("ROLE_ADMIN")
    public void updateClientDetails(ClientDetails cd) throws NoSuchClientException {
    	ClientDetail clientDetails = clientDetailsRepo.findByClientId(cd.getClientId());
        if (null == clientDetails) {
            throw new NoSuchClientException("Client not found with ID '" + cd.getClientId() + "'");
        }
        clientDetails = getMongoDBClientDetailsFromClient(cd);
        clientDetailsRepo.save(clientDetails);
    }

    @Override
    //@Secured("ROLE_ADMIN")
    public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
    	ClientDetail clientDetailModels = clientDetailsRepo.findByClientId(clientId);
        if (null == clientDetailModels) {
            throw new NoSuchClientException("Client not found with ID '" + clientId + "'");
        }
        clientDetailModels.setClientSecret(passwordEncoder.encode(secret));
        clientDetailsRepo.save(clientDetailModels);
    }

}
