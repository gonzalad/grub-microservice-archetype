package ${packageName}.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.talend.iam.security.Configurer;
import org.talend.iam.security.FilterProperties;
import org.talend.iam.security.JWTAuthenticationProvider;

//@Configuration
//@EnableWebSecurity
//@ComponentScan("org.talend.iam")
//@EnableOAuth2Client
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private FilterProperties filterProperties;

    @Autowired
    private Configurer configurer;

    @Autowired 
    private JWTAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        if (filterProperties.isEnabled()) {
            auth.authenticationProvider(jwtAuthenticationProvider);
        }
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        if (filterProperties.isEnabled()) {
            // Configurer prepare OAuth2 rest template and add authentication
            // filter
            configurer.configure(http, super.authenticationManagerBean());
            http.anonymous().disable();
            http.antMatcher("/**").authorizeRequests().anyRequest().authenticated();
        } else {
            http.antMatcher("/**").authorizeRequests().anyRequest().permitAll();
        }
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
