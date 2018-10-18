package cat.udl.eps.entsoftarch.textannot.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "TextAnnotUser") //Avoid collision with system table User in Postgres
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class User extends UriEntity<String> implements UserDetails {

  /**
   *
   * Creates a new instance of password encoder.
   */

  public static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  /**
   * Identifier of user needs to be unique, otherwise it will generate conflicts.
   */
  @Id
  private String username;


  /**
   * Email of user needs to be unique, otherwise it will generate conflicts and it can not be blank.
   */
  @NotBlank @Email
  private String email;

  /**
   * Password of user needs to be in range of 8 to 256 characters, and it can not be blank
   * Access to this Json property is in write only mode.
   */

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

  /**
   * Checks if Account has not expired.
   * @return true.
   */
  @Override
  public boolean isAccountNonExpired() { return true; }

  /**
   * Checks if Account is not locked.
   * @return true.
   */
  @Override
  public boolean isAccountNonLocked() { return true; }

  /**
   * Checks if credentials are not expired.
   * @return true.
   */
  @Override
  public boolean isCredentialsNonExpired() { return true; }

  /**
   * Checks if Account is enabled.
   * @return true.
   */
  @Override
  public boolean isEnabled() { return true; }
}
