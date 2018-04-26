package cat.udl.eps.entsoftarch.textannot.repository;

import cat.udl.eps.entsoftarch.textannot.domain.MetadataValue;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface MetadataValueRepository extends PagingAndSortingRepository<MetadataValue, Integer> {
    MetadataValue findByValue(@Param("value") String value);
    List<MetadataValue> findByValueContaining(@Param("value") String value);
}
