
package xyz.baktha.oaas.data.repo;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import xyz.baktha.oaas.data.domain.UserDomain;

public interface UserDetailRepo extends MongoRepository<UserDomain, Serializable> {

	@Query("{'phoneNo' : ?0}")
    public UserDomain findByPhone(String phone);	
	
	@Query(value = "{'phoneNo' : ?0}", delete = true)
	public long deleteByPhone(String phone);	
}
