package cat.udl.eps.entsoftarch.textannot.repository;

import cat.udl.eps.entsoftarch.textannot.domain.Admin;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AdminRepository extends PagingAndSortingRepository<Admin, String> {
  List<Admin> findByUsernameContaining(@Param("text") String text);
}
