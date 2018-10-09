package cat.udl.eps.entsoftarch.textannot.repository;


import cat.udl.eps.entsoftarch.textannot.domain.MetadataField;
import cat.udl.eps.entsoftarch.textannot.domain.MetadataTemplate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface MetadataFieldRepository extends PagingAndSortingRepository<MetadataField, Integer> {
    MetadataField findByName (@Param("name") String name);
    MetadataField findByCategoryAndName (@Param("category") String category, @Param("name") String name);
    List<MetadataField> findByDefinedAt(@Param("metadataTemplate")MetadataTemplate metadataTemplate);

    @Query("SELECT t " +
            "FROM MetadataTemplate t, MetadataField f " +
            "WHERE f.definedAt.id = t.id AND f.type = ?1")
    List<MetadataTemplate> findAllMetadataTemplatesByType(@Param("type") String type);

    @Query("SELECT t " +
            "FROM MetadataTemplate t, MetadataField f " +
            "WHERE f.definedAt.id = t.id AND f.name = ?1")
    List<MetadataTemplate> findAllMetadataTemplatesByName(@Param("name") String name);

    @Query("SELECT t " +
            "FROM MetadataTemplate t, MetadataField f " +
            "WHERE f.definedAt.id = t.id AND f.name = ?1 AND f.type = ?2")
    List<MetadataTemplate> findAllMetadataTemplatesByNameAndType(@Param("name") String name, @Param("type") String type);
}
