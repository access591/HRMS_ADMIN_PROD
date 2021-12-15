package com.hrms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.hrms.service.CustomUserDetailService;



@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomUserDetailService custumUserDetailService;
	
	
	 @Autowired CustomAuthenticationPasswordProvider authProvider;
	 
	 @Autowired
	    private CustomLogoutHandler logoutHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		System.out.println("role configure ");
		

		

		http.csrf().disable().authorizeRequests().antMatchers("/css/**", "/js/**", "/img/**", "/signin**", "/fonts/**","/admin","/doLogin","/forgetPassword/**","/forgotMsgPassword/**")
				.permitAll().antMatchers("/userProgramRights/**", "/deleteUserRights/**", "/saveUserRights")
				.hasAuthority("ROLE_USERPROGRAMRIGHTS")
				.antMatchers("/employeeMaster/**", "/saveEmployee", "**/updateEmployeemaster")
				.hasAuthority("ROLE_EMPLOYEEMASTER") // EMPLOYEEMASTER
				.antMatchers("/module/**", "/saveModule", "**/updateModule/**").hasAuthority("ROLE_MODULE") // MODULE
				.antMatchers("/program/**", "/saveProgram", "**/updateProgram/**").hasAuthority("ROLE_PROGRAM") // PROGRAMMASTER
				.antMatchers("/subModule/**", "/saveSubModule", "**/updateSubModule/**").hasAuthority("ROLE_SUBMODULE") /// SUBMODULE
				.antMatchers("/userMaster/**", "/saveUser/**", "**/upadteUser/**").hasAuthority("ROLE_USERMASTER") // USERMASTER
				.antMatchers("**/aboutDept/**","**/saveAboutdept/**" ,"**/updateAboutDept/**").hasAuthority("ROLE_ABOUTDEPT")
				.antMatchers("**/slider/**","**/saveSlider/**" ,"**/updateSlider/**").hasAnyAuthority("ROLE_SLIDER")
				.antMatchers("**/rule/**","**/saveRules/**" ,"**/updateRules/**").hasAuthority("ROLE_RULE")
				.antMatchers("**/policy/**","**/savePolicy/**" ,"**/updatePolicy/**").hasAuthority("ROLE_POLICY")
				.antMatchers("**/order/**","**/saveOrder/**" ,"**/updateOrder/**").hasAuthority("ROLE_ORDER")
				.antMatchers("**/notification/**","**/saveNotification/**" ,"**/updateNotification/**").hasAuthority("ROLE_NOTIFICATION")
				.antMatchers("**/latestUpdate/**","**/saveLatestUpdate/**" ,"**/updateLatestUpdate/**").hasAuthority("LATESTUPDATE")
				.antMatchers("**/acts/**","**/saveActs/**" ,"**/updateActs/**").hasAuthority("ROLE_ACTS")
				.antMatchers("**/contactUs/**","**/saveContactUs/**" ,"**/updateContactUs/**").hasAuthority("ROLE_CONTACTUS")
				.antMatchers("**/commission/**","**/saveCommission/**" ,"**/updateCommission/**").hasAuthority("ROLE_COMMISSION")
				.antMatchers("**/information/**","**/saveInformation/**" ,"**/updateInformation/**").hasAuthority("ROLE_INFO")
				.antMatchers("**/eservices/**","**/saveEservices/**" ,"**/updateEservices/**").hasAuthority("ROLE_ESERVICES")
				.antMatchers("**/imageGallery/**","**/saveImageGallery/**" ,"**/updateImageGallery/**").hasAuthority("ROLE_IMAGEGALLERY")
				.antMatchers("**/pathDirectory/**","**/savePathDirectory/**" ,"**/updatePathDirectory/**").hasAuthority("ROLE_PATHDIRECTORY")
																													
				.anyRequest().authenticated().and()
				
				.formLogin().loginPage("/signin").loginProcessingUrl("/doLogin")
				.defaultSuccessUrl("/home",true).failureUrl("/signin?error")
				.and()
			    .headers()
		        .frameOptions().disable()
		        .and()

	            .logout()
	            .logoutUrl("/logout")
	            .addLogoutHandler(logoutHandler)
	            .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
	            .permitAll()
				; // logout configuration

		
//		logout handler code 
//		.and()
//        .logout()
//            .logoutSuccessHandler(logoutSuccessHandler)
//            .permitAll()
		
//		 http.sessionManagement()
//        
//         .maximumSessions(1).maxSessionsPreventsLogin(true)
//         .expiredUrl("/signin?invalid-session=true");
		 
		 http.sessionManagement()
         .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
         .sessionFixation()
         .migrateSession()
         .maximumSessions(1).sessionRegistry(sessionRegistry()).expiredUrl( "/login?expire")
         .maxSessionsPreventsLogin(true);

		
		
//		http.sessionManagement().maximumSessions(1);
		
				http.exceptionHandling().accessDeniedPage("/UnauthorizePage")
				; // UnauthorizePage.html //logout
			
//				http.addFilterBefore(new CustomSecurityFilter(), 
//					      SecurityContextPersistenceFilter.class);
	}

	

	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		
		
			System.out.println("authentication manager builder ");
			//auth.userDetailsService(custumUserDetailService).passwordEncoder(passwordEncoder());
		
			auth.authenticationProvider(authProvider);
		
	}

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public SessionRegistry sessionRegistry() {
	    SessionRegistry sessionRegistry = new SessionRegistryImpl();
	    return sessionRegistry;
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
	    return new HttpSessionEventPublisher();
	}
	
	@Autowired UserDetailsService userDetailsService;
	  @Bean
	  public AuthenticationProvider daoAuthenticationProvider() {
	      DaoAuthenticationProvider impl = new DaoAuthenticationProvider();
	      impl.setUserDetailsService(userDetailsService());
	      impl.setHideUserNotFoundExceptions(false) ;
	      return impl ;
	  }
	
	
	
}
