package cat.udl.eps.entsoftarch.textannot.config;

import cat.udl.eps.entsoftarch.textannot.domain.Admin;
import cat.udl.eps.entsoftarch.textannot.domain.Linguist;
import cat.udl.eps.entsoftarch.textannot.domain.User;
import cat.udl.eps.entsoftarch.textannot.repository.AdminRepository;
import cat.udl.eps.entsoftarch.textannot.repository.LinguistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

@Configuration
public class AuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

  @Autowired
  BasicUserDetailsService basicUserDetailsService;

  @Autowired
  AdminRepository adminRepository;

  @Autowired
  LinguistRepository linguistRepository;

  @Override
  public void init(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(basicUserDetailsService)
        .passwordEncoder(User.passwordEncoder);

    if (!adminRepository.exists("admin")) {
      Admin admin = new Admin();
      admin.setEmail("admin@textannot.org");
      admin.setUsername("admin");
      admin.setPassword("password");
      admin.encodePassword();
      adminRepository.save(admin);
    }
    if (!linguistRepository.exists("user")) {
      Linguist user = new Linguist();
      user.setEmail("user@textannot.org");
      user.setUsername("user");
      user.setPassword("password");
      user.encodePassword();
      linguistRepository.save(user);
    }
  }
}