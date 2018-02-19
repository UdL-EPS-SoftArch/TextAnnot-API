package cat.udl.eps.entsoftarch.textannot.domain;

import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.Transient;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

@Entity
@Data
public class Linguist extends User {

	@Override
	@Transient
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_LINGUIST");
	}
}
