package cat.udl.eps.entsoftarch.textannot.repository;

import cat.udl.eps.entsoftarch.textannot.domain.Linguist;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LinguistRepository extends PagingAndSortingRepository<Linguist, String> {
  /**
   * Find the list of linguist users.
   * @param text String that refers to the name of the linguist user.
   * @return a list of the linguist users.
   */
  List<Linguist> findByUsernameContaining(@Param("text") String text);
}
