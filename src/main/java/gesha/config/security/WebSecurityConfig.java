package gesha.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import gesha.authentication.PreAuthTokenFilter;

@Configuration
@Import(SpringSecurityConfiguration.class)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .csrf()
                .disable()

            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
                .exceptionHandling()
                    .authenticationEntryPoint(new RestApiAuthenticationEntryPoint())

            .and()
                .addFilterBefore(new PreAuthTokenFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)

            .authorizeRequests()
                .antMatchers("/hello-world").permitAll()
                .antMatchers("/authenticate").permitAll()
                .anyRequest().authenticated();
    }
}
