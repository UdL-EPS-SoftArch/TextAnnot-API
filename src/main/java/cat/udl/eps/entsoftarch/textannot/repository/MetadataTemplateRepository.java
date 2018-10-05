package cat.udl.eps.entsoftarch.textannot.repository;

import cat.udl.eps.entsoftarch.textannot.domain.MetadataTemplate;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface MetadataTemplateRepository extends PagingAndSortingRepository<MetadataTemplate, String> {
    Optional<MetadataTemplate> findByName(@Param("name") String name);
}