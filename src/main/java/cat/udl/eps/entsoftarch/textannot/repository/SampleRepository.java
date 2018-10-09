package cat.udl.eps.entsoftarch.textannot.repository;

import cat.udl.eps.entsoftarch.textannot.domain.Sample;
import cat.udl.eps.entsoftarch.textannot.domain.TagHierarchy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import org.springframework.data.repository.query.Param;

@RepositoryRestResource
public interface SampleRepository extends PagingAndSortingRepository<Sample, Integer> {
    List<Sample> findByTextContaining(@Param("text") String text);
    List<Sample> findByTextContains (@Param("word") String word);

    @Query("SELECT s " +
            "FROM Sample s, MetadataValue v, MetadataField f " +
            "WHERE v.forA.id = s.id AND  v.values.id = f.id AND v.value = ?2 AND f.name = ?1")
    List<Sample> findAllSamplesWithFieldNameAndValue(@Param("name") String name, @Param("value") String value);

    @Query("SELECT s " +
            "FROM Sample s, MetadataValue v, MetadataField f " +
            "WHERE v.forA.id = s.id AND  v.values.id = f.id AND f.name = ?1")
    List<Sample> findAllSamplesWithFieldName(@Param("name") String name);

    Sample findByText(@Param("text") String text);
    List<Sample> findByDescribedByName(@Param("text") String text);

    List<Sample> findByTaggedBy(@Param("taggedBy")TagHierarchy tagHierarchy);
}