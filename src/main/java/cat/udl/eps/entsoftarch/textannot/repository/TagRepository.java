package cat.udl.eps.entsoftarch.textannot.repository;

import cat.udl.eps.entsoftarch.textannot.domain.Tag;
import cat.udl.eps.entsoftarch.textannot.domain.TagHierarchy;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface TagRepository extends PagingAndSortingRepository<Tag, Integer> {
    List<Tag> findByNameContaining(@Param("name") String name);
    List<Tag> findByTagHierarchy(@Param("tagHierarchy")TagHierarchy tagHierarchy);
    List<Tag> findByParent(@Param("parent") Tag parent);
}
