package cat.udl.eps.entsoftarch.textannot.repository;

import cat.udl.eps.entsoftarch.textannot.domain.TagHierarchy;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface TagHierarchyRepository extends PagingAndSortingRepository<TagHierarchy, Integer> {

    /**
     * Returns the TagHierarchy found by a given name.
     * @param name The given name of TagHierarchy.
     * @return TagHierarchy object.
     */
    Optional<TagHierarchy> findByName(@Param("name") String name);
}