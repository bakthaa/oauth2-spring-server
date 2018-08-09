package xyz.baktha.oaas.data.repo;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;

import xyz.baktha.oaas.data.model.ClientDetail;


public interface ClientDetailRepo extends MongoRepository<ClientDetail, Serializable> {

    public ClientDetail findByClientId(String clientId);

}
