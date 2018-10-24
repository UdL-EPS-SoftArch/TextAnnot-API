package cat.udl.eps.entsoftarch.textannot.repository;

import cat.udl.eps.entsoftarch.textannot.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface UserRepository extends PagingAndSortingRepository<User, String> {

  /**
   * Finds user by given email with given name as parameter
   * @param email String email for finding user
   * @return Returns user object.
   */
  User findByEmail(String email);


  /**
   * Finds user by given username, with given name as parameter
   * @param text String text for finding user
   * @return Returns user object.
   */
  User findByUsername(String text);
}
