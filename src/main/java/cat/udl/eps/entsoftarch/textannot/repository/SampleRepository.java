package cat.udl.eps.entsoftarch.textannot.repository;

import cat.udl.eps.entsoftarch.textannot.domain.Sample;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface SampleRepository extends PagingAndSortingRepository<Sample, Integer> {

    Sample findByTextContains (@Param("word") String word);

}