package com.wipro.ovcds.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.dao.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.wipro.ovcds.service.UserDetailsServiceImpl;
 
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;

	@Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
     
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
     
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
    		
    	    .csrf().disable()
    		.authorizeRequests()
    		
    		.antMatchers("/shop/**","/admin/**","/home/**","/cart/**","/api/v1/**").permitAll()
    		.antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/productImages").permitAll()
        	.antMatchers("/register", "/h2-console/**").permitAll()
            .antMatchers("/").hasAnyAuthority("USER", "ADMIN")
            .antMatchers("/cart/**").hasAnyAuthority("USER", "ADMIN")
            .antMatchers("/admin/**").hasAuthority("ADMIN")
            
//            .antMatchers("/h2-console/**").hasAuthority("ADMIN")
            .anyRequest().authenticated()
            .and()
            
            .formLogin().loginPage("/login")
            
            .usernameParameter("username")
            .successHandler(loginSuccessHandler)
            
            .permitAll()
            .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login")
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
			.and()
			.exceptionHandling().accessDeniedPage("/403")
			;
			http.headers().frameOptions().disable();
			 
    }
    
}
