package cat.udl.eps.entsoftarch.textannot.repository;

import cat.udl.eps.entsoftarch.textannot.domain.MetadataTemplate;
import cat.udl.eps.entsoftarch.textannot.domain.Sample;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import org.springframework.data.repository.query.Param;

@RepositoryRestResource
public interface SampleRepository extends PagingAndSortingRepository<Sample, Integer> {
    List<Sample> findByTextContaining(String text);
    List<Sample> findByTextContains (@Param("word") String word);

    List<Sample> findByHasValuedNameAndHasValue(String fieldName, String value);
    Sample findByText(@Param("text") String text);
    List<Sample> findByMetadataTemplate(MetadataTemplate mdtemplate);
}