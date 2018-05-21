package cat.udl.eps.entsoftarch.textannot.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TagHierarchy extends PagingAndSortingRepository<TagHierarchy,Integer> {
}
