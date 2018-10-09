package cat.udl.eps.entsoftarch.textannot.repository;

import cat.udl.eps.entsoftarch.textannot.domain.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface MetadataValueRepository extends PagingAndSortingRepository<MetadataValue, Integer> {
    MetadataValue findByValue(@Param("value") String value);
    List<MetadataValue> findByValueContaining(@Param("value") String value);
    List<MetadataValue> findByValues(@Param("metadataField")MetadataField metadataField);

    @Query("SELECT t " +
            "FROM MetadataTemplate t, MetadataField f, MetadataValue v " +
            "WHERE v.values.id = f.id AND f.definedAt.id = t.id AND v.value = ?1")
    List<MetadataTemplate> findAllMetadataTemplatesByValue(@Param("value")String value);

    List<MetadataValue> findByForA(@Param("sample") Sample sample);
}
