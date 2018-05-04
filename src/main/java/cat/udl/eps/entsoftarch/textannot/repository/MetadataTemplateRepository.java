package cat.udl.eps.entsoftarch.textannot.repository;

import cat.udl.eps.entsoftarch.textannot.domain.MetadataTemplate;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface MetadataTemplateRepository extends PagingAndSortingRepository<MetadataTemplate, String> {
    public List<MetadataTemplate> findByDefinesNameAndDefinesType(@Param("name") String name, @Param("type") String type);
    public List<MetadataTemplate> findByDefinesName(@Param("name") String name);
    public List<MetadataTemplate> findByDefinesType(@Param("type") String type);
    public List<MetadataTemplate> findByDefinesValuesValue(@Param("value") String value);
    MetadataTemplate findByName (@Param("name") String name);

}
