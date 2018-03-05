package cat.udl.eps.entsoftarch.textannot.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, "/admins*/**").hasRole("ADMIN")
        .antMatchers(HttpMethod.POST, "/admins*/**").hasRole("ADMIN")
        .antMatchers(HttpMethod.PUT, "/admins*/**").hasRole("ADMIN")
        .antMatchers(HttpMethod.PATCH, "/admins*/*").hasRole("ADMIN")
        .antMatchers(HttpMethod.DELETE, "/admins*/**").hasRole("ADMIN")

        .antMatchers(HttpMethod.POST, "/linguists*/**").hasRole("ADMIN")
        .antMatchers(HttpMethod.DELETE, "/linguists*/**").hasRole("ADMIN")

        .antMatchers(HttpMethod.GET, "/identity").authenticated()
        .antMatchers(HttpMethod.GET, "/metadataTemplates*/**").authenticated()

        .antMatchers(HttpMethod.POST, "/**/*").authenticated()
        .antMatchers(HttpMethod.PUT, "/**/*").authenticated()
        .antMatchers(HttpMethod.PATCH, "/**/*").authenticated()
        .antMatchers(HttpMethod.DELETE, "/**/*").authenticated()
        .anyRequest().permitAll()
        .and()
        .httpBasic().realmName("TextAnnot")
        .and()
        .csrf().disable();
  }
}
