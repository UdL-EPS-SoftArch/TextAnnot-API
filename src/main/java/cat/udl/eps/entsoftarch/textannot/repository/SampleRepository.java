package cat.udl.eps.entsoftarch.textannot.repository;

import cat.udl.eps.entsoftarch.textannot.domain.Sample;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SampleRepository extends PagingAndSortingRepository<Sample, Integer> { }