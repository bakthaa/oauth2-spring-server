
package xyz.baktha.oaas.data.repo;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import xyz.baktha.oaas.data.model.UserDetail;

public interface UserDetailRepo extends MongoRepository<UserDetail, Serializable> {

	@Query("{'phoneNo' : ?0}")
    public UserDetail findByPhone(String phone);	

}
