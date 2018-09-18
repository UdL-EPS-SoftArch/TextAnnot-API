package cat.udl.eps.entsoftarch.textannot.config;

import cat.udl.eps.entsoftarch.textannot.domain.Admin;
import cat.udl.eps.entsoftarch.textannot.domain.Linguist;
import cat.udl.eps.entsoftarch.textannot.domain.User;
import cat.udl.eps.entsoftarch.textannot.repository.AdminRepository;
import cat.udl.eps.entsoftarch.textannot.repository.LinguistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;

@Configuration
public class AuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {
  @Autowired Environment environment;
  @Autowired BasicUserDetailsService basicUserDetailsService;
  @Autowired AdminRepository adminRepository;
  @Autowired LinguistRepository linguistRepository;

  @Override
  public void init(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(basicUserDetailsService)
        .passwordEncoder(User.passwordEncoder);

    // Use encrypted secret password when deploying publicly in Heroku
    if(environment.acceptsProfiles("heroku")) {
      if (!adminRepository.existsById("admin")) {
        Admin admin = new Admin();
        admin.setEmail("admin@textannot.org");
        admin.setUsername("admin");
        admin.setPassword("$2a$10$B1dcscvS/lgiBnGdkhhupew8AhbjqUL7TjdA2ggvxQhs5jN7KVSMC");
        adminRepository.save(admin);
      }
      if (!linguistRepository.existsById("user")) {
        Linguist user = new Linguist();
        user.setEmail("user@textannot.org");
        user.setUsername("user");
        user.setPassword("$2a$10$B1dcscvS/lgiBnGdkhhupew8AhbjqUL7TjdA2ggvxQhs5jN7KVSMC");
        linguistRepository.save(user);
      }
    }
    else {
      if (!adminRepository.existsById("admin")) {
        Admin admin = new Admin();
        admin.setEmail("admin@textannot.org");
        admin.setUsername("admin");
        admin.setPassword("password");
        admin.encodePassword();
        adminRepository.save(admin);
      }
      if (!linguistRepository.existsById("user")) {
        Linguist user = new Linguist();
        user.setEmail("user@textannot.org");
        user.setUsername("user");
        user.setPassword("password");
        user.encodePassword();
        linguistRepository.save(user);
      }
    }
  }
}
