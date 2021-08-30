package com.ahirajustice.app.security;

import com.ahirajustice.app.constants.SecurityConstants;
import com.ahirajustice.app.filters.AuthenticationFilter;
import com.ahirajustice.app.filters.AuthorizationFilter;
import com.ahirajustice.app.services.auth.IAuthService;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final IAuthService authService;
    private final BCryptPasswordEncoder passwordEncoder;

    public WebSecurity(IAuthService authService, BCryptPasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable()
                .authorizeRequests().antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
                .anyRequest().authenticated().and().addFilter(getAuthenticationFilter())
                .addFilter(getAuthorizationFilter());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(passwordEncoder);
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
        filter.setFilterProcessesUrl(SecurityConstants.LOGIN_URL);
        return filter;
    }

    private AuthorizationFilter getAuthorizationFilter() throws Exception {
        final AuthorizationFilter filter = new AuthorizationFilter(authenticationManager());
        return filter;
    }
}
