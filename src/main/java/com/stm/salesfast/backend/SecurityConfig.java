package com.stm.salesfast.backend;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	RequestMatcher csrfRequestMatcher = new RequestMatcher() {

		private String allowedMethod = "GET";

		private AntPathRequestMatcher[] requestMatchers = {
				new AntPathRequestMatcher("/login"),
				new AntPathRequestMatcher("/home"),
				new AntPathRequestMatcher("/showalignments"),
				new AntPathRequestMatcher("/showappointments"),
				new AntPathRequestMatcher("/fixappointments"),
				new AntPathRequestMatcher("/addmeetingupdate"),
				new AntPathRequestMatcher("/addmeetingexperience"),
				new AntPathRequestMatcher("/yourappointment"),
				new AntPathRequestMatcher("/cancelappointment"),
				new AntPathRequestMatcher("/testpage"),
				new AntPathRequestMatcher("/datareport"),
				new AntPathRequestMatcher("/getdata"),
				new AntPathRequestMatcher("/shownotifications"),
				new AntPathRequestMatcher("/deletenotification"),
				new AntPathRequestMatcher("/virtuallearning"),
				new AntPathRequestMatcher("/updatefollowupappointment"),
				new AntPathRequestMatcher("/getvirtuallearningdata"),
				new AntPathRequestMatcher("/showappointmentphys"),
				new AntPathRequestMatcher("/addmeetingexpfromphy"),
				new AntPathRequestMatcher("/getedetailingdata"),
				new AntPathRequestMatcher("/addproductspage"),
				new AntPathRequestMatcher("/getmedicalfields"),
				new AntPathRequestMatcher("/uploadfiles"),
				new AntPathRequestMatcher("/addnewproduct"),
				new AntPathRequestMatcher("/livemeetingquestions"),
				new AntPathRequestMatcher("/getallqna"),
				new AntPathRequestMatcher("/submitquestion"),
				new AntPathRequestMatcher("/getsimilarqna"),
				new AntPathRequestMatcher("/unansweredques"),
				new AntPathRequestMatcher("/getunansweredques"),
				new AntPathRequestMatcher("/submitanswer"),
				new AntPathRequestMatcher("/submitsamplefeedback"),
				new AntPathRequestMatcher("/samplefeedbackreport"),
				new AntPathRequestMatcher("/getproductsforuser"),
				new AntPathRequestMatcher("/samplefeedbacksubmitted"),
				new AntPathRequestMatcher("/getsideeffectcomments"),
				new AntPathRequestMatcher("/getothercomments"),
				new AntPathRequestMatcher("/getnotificationcount"),
				new AntPathRequestMatcher("/cancelappointmentbysr"),
				new AntPathRequestMatcher("/updatefutureappointment"),
				new AntPathRequestMatcher("/allproducts"),
				new AntPathRequestMatcher("/getproductdocuments"),
				new AntPathRequestMatcher("/updateproductfile"),
				new AntPathRequestMatcher("/getallproducts"),
				new AntPathRequestMatcher("/uploadmeetingpitch"),
				new AntPathRequestMatcher("/allpitches"),
				new AntPathRequestMatcher("/getpitchforappointment"),
				new AntPathRequestMatcher("/updatemeetingpitch"),
				new AntPathRequestMatcher("/filterparameters"),
				new AntPathRequestMatcher("/getpastappointments"),
				new AntPathRequestMatcher("/getcurrentuser")
				};

		@Override
		public boolean matches(HttpServletRequest request) {
			for (AntPathRequestMatcher rm : requestMatchers) {
				if (rm.matches(request)) {
					return false;
				}
			}
			if (request.getMethod().equals(allowedMethod)) {
				return false;
			}
			return true;
		}

	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().requireCsrfProtectionMatcher(csrfRequestMatcher).and()
				.authorizeRequests().antMatchers("/resources/**", "/login/**")
				.permitAll().anyRequest().authenticated().and().formLogin()
				.loginPage("/login").failureUrl("/login")
				.usernameParameter("username").permitAll().and().logout()
				.logoutUrl("/logout").logoutSuccessUrl("/login").permitAll();
	}
	
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/yourappointment");
	    web.ignoring().antMatchers("/cancelappointment");
	    web.ignoring().antMatchers("/samplefeedback");
	    web.ignoring().antMatchers("/submitsamplefeedback");
	    web.ignoring().antMatchers("/samplefeedbacksubmitted");
	    
	}
}