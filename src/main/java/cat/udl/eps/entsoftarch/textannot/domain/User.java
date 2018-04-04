package cat.udl.eps.entsoftarch.textannot.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "TextAnnotUser") //Avoid collision with system table User in Postgres
@Data
public abstract class User extends UriEntity<String> implements UserDetails {

  public static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Id
  private String username;

  @NotBlank @Email
  private String email;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @NotBlank @Length(min=8, max=256)
  private String password;

  @Override
  public String getId() {
    return username;
  }

  @Override
  public String getUsername(){
    return username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  public void encodePassword() {
    this.password = passwordEncoder.encode(this.password);
  }

  @Override
  public boolean isAccountNonExpired() { return true; }

  @Override
  public boolean isAccountNonLocked() { return true; }

  @Override
  public boolean isCredentialsNonExpired() { return true; }

  @Override
  public boolean isEnabled() { return true; }
}
