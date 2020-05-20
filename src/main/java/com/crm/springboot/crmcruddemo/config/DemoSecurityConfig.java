package com.crm.springboot.crmcruddemo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.util.HttpSessionMutexListener;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

	// add a reference to our security data source
	
	@Autowired
	@Qualifier("securityDataSource")
	private DataSource securityDataSource;
		
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// use jdbc authentication ... oh yeah!!!		
		auth.jdbcAuthentication().dataSource(securityDataSource);
		
	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/api/customers").hasRole("EMPLOYEE")
		.antMatchers(HttpMethod.GET, "/api/customers/**").hasRole("EMPLOYEE")
		.antMatchers(HttpMethod.POST, "/api/customers").hasAnyRole("MANAGER", "ADMIN")
		.antMatchers(HttpMethod.POST, "/api/customers/**").hasAnyRole("MANAGER", "ADMIN")
		.antMatchers(HttpMethod.PUT, "/api/customers").hasAnyRole("MANAGER", "ADMIN")
		.antMatchers(HttpMethod.PUT, "/api/customers/**").hasAnyRole("MANAGER", "ADMIN")
		.antMatchers(HttpMethod.DELETE, "/api/customers/**").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/api/searchCustomer/**").hasAnyRole("ADMIN", "MANAGER")
		.antMatchers(HttpMethod.GET,"/api/listPageable/**").hasAnyRole("ADMIN","MANAGER")
		.and()
		.httpBasic()
		.and()
		.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		
//		http.authorizeRequests()
//			.antMatchers("/api/customers/showForm*").hasAnyRole("MANAGER", "ADMIN")
//			.antMatchers("/api/customers/save*").hasAnyRole("MANAGER", "ADMIN")
//			.antMatchers("/api/customers/delete").hasRole("ADMIN")
//			.antMatchers("/api/customers/**").hasRole("EMPLOYEE")
//			.antMatchers("/resources/**").permitAll()
//			.and()
//			.httpBasic()
//			.and()
//			.csrf().disable()
//			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

/*
 * Below code is used when we are making the application orather WEB APPLICATION		
 */
//		http.authorizeRequests()
//		.antMatchers("/customers/showForm*").hasAnyRole("MANAGER", "ADMIN")
//		.antMatchers("/customers/save*").hasAnyRole("MANAGER", "ADMIN")
//		.antMatchers("/customers/delete").hasRole("ADMIN")
//		.antMatchers("/customers/**").hasRole("EMPLOYEE")
//		.antMatchers("/resources/**").permitAll()
//		.and()
//			.formLogin()
//				.loginPage("/showMyLoginPage")
//				.loginProcessingUrl("/authenticateTheUser")
//				.permitAll()
//			.and()
//			.logout().permitAll()
//			.and()
//			.exceptionHandling().accessDeniedPage("/access-denied");
		
		http.sessionManagement().maximumSessions(3);

		
	}
	
	//To ensure the Concurrent session suport
		@Bean
		public HttpSessionEventPublisher httpSessionEventPublisher() {
			return new HttpSessionEventPublisher();
		}
		
}






