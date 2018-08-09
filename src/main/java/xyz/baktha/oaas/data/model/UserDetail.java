
package xyz.baktha.oaas.data.model;

import java.util.Date;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Document(collection = "oaas_user")
@Data
public class UserDetail {

    @Id
    private String id;
    @Indexed(unique=true)
    private String phoneNo;
    private String password;
    private Set<String> rights;
    @CreatedDate
    private Date dateCreated;

	/*@Override
	public String toString() {
		return "User [id=" + id + ", phoneNo=" + phoneNo + ", password=[]" + ", rights=" + rights
				+ ", dateCreated=" + dateCreated + "]";
	}*/

}
