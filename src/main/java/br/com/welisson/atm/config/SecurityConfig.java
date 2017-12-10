package br.com.welisson.atm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.Filter;
import java.util.Arrays;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${endpoints.cors.allow-credentials}")
	private String allowCredentials;

	@Value("${endpoints.cors.allowed-origins}")
	private String allowedOrigins;

	@Value("${endpoints.cors.allowed-methods}")
	private String allowedMethods;

	@Value("${endpoints.cors.allowed-headers}")
	private String allowedHeaders;

	@Value("${endpoints.cors.exposed-headers}")
	private String exposedHeaders;

	@Value("${endpoints.cors.max-age}")
	private String maxAge;


	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.csrf().disable();

		http.addFilterBefore(corsFilter(), AbstractPreAuthenticatedProcessingFilter.class);

		http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll();
		http.authorizeRequests().anyRequest().permitAll();
	}

	@Bean
	public Filter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(Boolean.valueOf(allowCredentials));
		config.setMaxAge(Long.valueOf(maxAge));
		Arrays.stream(allowedOrigins.split(",")).forEach(config::addAllowedOrigin);
		Arrays.stream(allowedHeaders.split(",")).forEach(config::addAllowedHeader);
		Arrays.stream(allowedMethods.split(",")).forEach(config::addAllowedMethod);
		Arrays.stream(exposedHeaders.split(",")).forEach(config::addExposedHeader);
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
	
}