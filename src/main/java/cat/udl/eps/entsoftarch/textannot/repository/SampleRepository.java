package cat.udl.eps.entsoftarch.textannot.repository;

import cat.udl.eps.entsoftarch.textannot.domain.Sample;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import org.springframework.data.repository.query.Param;

@RepositoryRestResource
public interface SampleRepository extends PagingAndSortingRepository<Sample, Integer> {
    List<Sample> findByTextContaining(@Param("text") String text);
    List<Sample> findByTextContains (@Param("word") String word);

    List<Sample> findByHasValuedNameAndHasValue(@Param("name") String name, @Param("value") String value);
    List<Sample> findByHasValuedName(@Param("name") String name);
    Sample findByText(@Param("text") String text);
    List<Sample> findByDescribedByName(@Param("text") String text);
}
