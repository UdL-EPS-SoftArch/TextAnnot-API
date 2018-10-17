package cat.udl.eps.entsoftarch.textannot.repository;

import cat.udl.eps.entsoftarch.textannot.domain.Admin;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AdminRepository extends PagingAndSortingRepository<Admin, String> {

  /**
   * Find the list of admin users.
   * @param text String that refers to the name of the admin user.
   * @return a list of the admin users.
   */
  List<Admin> findByUsernameContaining(@Param("text") String text);
}
