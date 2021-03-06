package com.kevinppaulo.issueTracker.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kevinppaulo.issueTracker.security.auth.ApplicationUserDetailsService;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfigurer extends WebSecurityConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;
	private final ApplicationUserDetailsService userDetailsService;

	@Autowired
	public ApplicationSecurityConfigurer(PasswordEncoder passwordEncoder,
			ApplicationUserDetailsService userDetailsService) {
		this.passwordEncoder = passwordEncoder;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET,"/signup", "/signup/**", "/css/**", "/js/**", "/img/**", "/h2-console/**", "/error").permitAll()
			.antMatchers(HttpMethod.POST,"/signup", "/signup/**", "/h2-console/**").permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.loginPage("/login").permitAll()
			.defaultSuccessUrl("/app", true)
			.and()
			.logout()
				.logoutUrl("/logout").permitAll()
				.clearAuthentication(true)
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.logoutSuccessUrl("/login");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
		daoAuthProvider.setPasswordEncoder(passwordEncoder);
		daoAuthProvider.setUserDetailsService(userDetailsService);
		return daoAuthProvider;
	}

}
