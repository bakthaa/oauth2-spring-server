/**
 * 
 */
package xyz.baktha.oaas.data.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author power-team
 *
 */
@Configuration
@EnableMongoRepositories(basePackages="xyz.baktha.oaas.data.repo")
@EnableMongoAuditing
public class MongoConfig {
}
