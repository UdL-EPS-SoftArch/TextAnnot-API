package cat.udl.eps.entsoftarch.textannot.config;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Value("${allowed-origins}")
  String[] allowedOrigins;

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

        .antMatchers(HttpMethod.GET, "/samples*/**").authenticated()
        .antMatchers(HttpMethod.GET, "/tags*/**").authenticated()

        .antMatchers(HttpMethod.GET, "/identity").authenticated()
        .antMatchers(HttpMethod.GET, "/metadataTemplates*/**").authenticated()

        .antMatchers(HttpMethod.POST, "/tags*/**").hasRole("ADMIN")
        .antMatchers(HttpMethod.DELETE, "/tags*/**").hasRole("ADMIN")

        .antMatchers(HttpMethod.POST, "/tagHierarchies*/**").hasRole("ADMIN")
        .antMatchers(HttpMethod.DELETE, "/tagHierarchies*/**").hasRole("ADMIN")

        .antMatchers(HttpMethod.POST, "/**/*").authenticated()
        .antMatchers(HttpMethod.PUT, "/**/*").authenticated()
        .antMatchers(HttpMethod.PATCH, "/**/*").authenticated()
        .antMatchers(HttpMethod.DELETE, "/**/*").authenticated()
        .anyRequest().permitAll()
        .and()
        .httpBasic().realmName("TextAnnot")
        .and()
        .cors()
        .and()
        .csrf().disable()
        .headers().frameOptions().sameOrigin();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedOrigins(Arrays.asList(allowedOrigins));
    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
    corsConfiguration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
    return source;
  }
}
