package com.aws.micro.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Order(1)
public class ServerSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	 
	 
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) 
      throws Exception {
    	//PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication()
        //.passwordEncoder(encoder)
          .withUser("admin").password(passwordEncoder().encode("admin")).roles("USER");
    }
 
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() 
      throws Exception {
        return super.authenticationManagerBean();
    }
 
    @Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();
		
		//http.authorizeRequests().antMatchers("/*").permitAll();

		/*
		 * http.authorizeRequests().antMatchers("/login").permitAll().anyRequest().
		 * authenticated().and().formLogin() .permitAll();
		 */

	}
}
