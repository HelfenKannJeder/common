package de.helfenkannjeder.common.identityprovider.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.helfenkannjeder.common.identityprovider.rest.logging.RestLogFilter;
import de.helfenkannjeder.oauth.provider.api.UserApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "de.helfenkannjeder.common.identityprovider.domain")
@ComponentScan(basePackages = "de.helfenkannjeder.common.identityprovider")
@EnableJpaRepositories("de.helfenkannjeder.common.identityprovider.domain.repository")
@SpringBootApplication
public class IdentityProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdentityProviderApplication.class, args);
	}

	@Bean
	public ObjectMapper jacksonObjectMapper() {
		return ObjectMapperFactory.objectMapperForRestEndpoint();
	}

	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new RestLogFilter());
		filterRegistrationBean.setOrder(1);
		return filterRegistrationBean;
	}

	@Bean
	public UserApi helfenKannJederOAuthProviderApi(@Value("${helfenKannJederOAuthProvider.endpoint}") String endpoint, ObjectMapper objectMapper) {
		return UserApi.createUserApi(endpoint, objectMapper);
	}
}
