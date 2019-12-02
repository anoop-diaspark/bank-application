package com.diaspark.INB.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	/*@Override
	public void configure(HttpSecurity http) throws Exception {

		// @formatter: off
		http.csrf().disable().authorizeRequests().antMatchers("/**").authenticated().and()
				.addFilterBefore(new JWTAuthFilter(jwtTokenProvider), BasicAuthenticationFilter.class)
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
				.authenticationEntryPoint(new InbAppAuthenticationEntryPoint());
		// @formatter: on
	}
**/
	@Override
    public void configure(HttpSecurity http) throws Exception {

        // @formatter: off
        http.csrf()
                .disable();
        // @formatter: on
    }
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
		//web.ignoring().antMatchers("/user/login", "/user/registration");
		web.ignoring().antMatchers("/**/*");
		//System.out.println("======config=====");
		//web.securityInterceptor(securityInterceptor)
	}
	/**/

	//@Override
//	protected void configure1(WebSecurity http) throws Exception {
//        System.out.println("===================configure =====");
//		CorsConfigurationSource source = corsConfigurationSource();
//
//		http.addFilterBefore(new CorsFilter(source), ChannelProcessingFilter.class);
//
//		http.headers().httpStrictTransportSecurity().includeSubDomains(true).maxAgeInSeconds(31536000);
//
//		http.headers().xssProtection().disable();
//
//		String[] excludePaths = { "/user/login", "/user/registration" };
//
//		http.csrf().disable().authorizeRequests().antMatchers(excludePaths).permitAll()
//
//		 .antMatchers(HttpMethod.OPTIONS).permitAll().antMatchers("/**/*").permitAll();
//
//	}
//
//	/**
//	 * 
//	 * This method is use for cors Configuration.
//	 *
//	 * 
//	 * 
//	 * @return source
//	 * 
//	 */
//
//	private CorsConfigurationSource corsConfigurationSource() {
//
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//
//		CorsConfiguration config = new CorsConfiguration();
//
//		config.addAllowedOrigin("*");
//		// config.addAllowedHeader("Au");
//		 config.addAllowedMethod("POST,GET,PUT,OPTION");
//		// more config
//
//		source.registerCorsConfiguration("/**", config);
//
//		return source;
//
//	}

}