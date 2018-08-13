package xyz.baktha.oaas.data.model;

import java.util.Map;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Document(collection = "oaas_client")
@Data
public class ClientDetail {

    @Id
    private String id;
    @Indexed
    private String clientId;
    private Set<String> resourceIds;
    private boolean secretRequired;
    @Indexed
    private String clientSecret;
    private boolean scoped;
    private Set<String> scope;
    private Set<String> authorizedGrantTypes;
    private Set<String> registeredRedirectUri;
    private Set<String> authorities;
    private Integer accessTokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;
    private boolean autoApprove;
    private Map<String, Object> additionalInformation;

    public ClientDetail() {
    }

}
