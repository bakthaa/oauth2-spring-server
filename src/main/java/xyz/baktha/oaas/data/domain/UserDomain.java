
package xyz.baktha.oaas.data.domain;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Document(collection = "oaas_user")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDomain extends Auditable<String> {

    @Id
    private String id;
    @Indexed(unique=true)
    private String phoneNo;
    @ToString.Exclude
    private String password;
    private Set<String> rights;
    
	/*@Override
	public String toString() {
		return "User [id=" + id + ", phoneNo=" + phoneNo + ", password=[]" + ", rights=" + rights
				+ ", dateCreated=" + dateCreated + "]";
	}*/

}
