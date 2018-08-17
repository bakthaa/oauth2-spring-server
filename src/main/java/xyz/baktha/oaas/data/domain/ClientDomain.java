package xyz.baktha.oaas.data.domain;

import java.util.Map;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Document(collection = "oaas_client")
@Data
@EqualsAndHashCode(callSuper = true)
public class ClientDomain extends Auditable<String> {

    @Id
    private String id;
    @Indexed(unique=true)
    private String clientId;
    private Set<String> resourceIds;
    private boolean secretRequired;
    @ToString.Exclude
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
}
