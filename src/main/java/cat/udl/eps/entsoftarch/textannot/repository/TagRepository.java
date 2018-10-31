package cat.udl.eps.entsoftarch.textannot.repository;

import cat.udl.eps.entsoftarch.textannot.domain.Tag;
import cat.udl.eps.entsoftarch.textannot.domain.TagHierarchy;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface TagRepository extends PagingAndSortingRepository<Tag, Integer> {

    /**
     * Finds all Tags that has text containing like the given one
     * @param name String name for finding in Tags
     * @return The list of Tags that contains text in given parameter
     */
    List<Tag> findByNameContaining(@Param("name") String name);

    /**
     Returns the Tags related to a tagHierarchy.
     * @param tagHierarchy The tagHierarchy that contains the Tags we want.
     * @return list of Tags.
     */
    List<Tag> findByTagHierarchy(@Param("tagHierarchy")TagHierarchy tagHierarchy);

    /**
     Returns the Tags related to a Tag parent.
     * @param parent The Tag parent that contains the Tag childs we want.
     * @return list of Tags.
     */

    List<Tag> findByParent(@Param("parent") Tag parent);
}
