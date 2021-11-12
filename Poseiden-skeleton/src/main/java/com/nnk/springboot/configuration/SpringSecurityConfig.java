package com.nnk.springboot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.nnk.springboot.services.PoseidonUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PoseidonUserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests()
				.antMatchers("/bidList/**", "/admin/*", "/user/**", "/trade/**", "/ruleName/**", "/rating/**",
						"/curvePoint/**")
				.hasAnyAuthority("ADMIN", "USER").antMatchers("/user/**").permitAll().and().formLogin()
				.defaultSuccessUrl("/bidList/list").and().logout().logoutUrl("/app-logout").logoutSuccessUrl("/").and()
				.exceptionHandling().accessDeniedPage("/app/error");
		httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).maximumSessions(1);
	}

	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

}
