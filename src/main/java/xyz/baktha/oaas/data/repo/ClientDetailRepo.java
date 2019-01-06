package xyz.baktha.oaas.data.repo;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;

import xyz.baktha.oaas.data.domain.ClientDomain;


public interface ClientDetailRepo extends MongoRepository<ClientDomain, Serializable> {

    public ClientDomain findByClient(String clientId);

}
