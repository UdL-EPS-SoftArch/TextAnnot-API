package cat.udl.eps.entsoftarch.textannot.repository;

import cat.udl.eps.entsoftarch.textannot.domain.Tag;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends PagingAndSortingRepository<Tag, Integer> {
    List<Tag> findByNameContaining(@Param("name") String name);

}