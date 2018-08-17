/**
 * 
 */
package xyz.baktha.oaas.data.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

/**
 * @author power-team
 *
 */

@Data
public abstract class Auditable<U> {
	
	@CreatedBy
	protected U createdBy;
	
    @CreatedDate
    protected LocalDateTime dateCreated;
    
    @LastModifiedBy
    protected U lastModifiedBy;
    
    @LastModifiedDate
    protected LocalDateTime lastModifiedDate;
}
