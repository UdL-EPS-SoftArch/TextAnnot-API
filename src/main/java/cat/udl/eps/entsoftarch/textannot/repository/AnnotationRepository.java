package cat.udl.eps.entsoftarch.textannot.repository;


import cat.udl.eps.entsoftarch.textannot.domain.Annotation;
import cat.udl.eps.entsoftarch.textannot.domain.Sample;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AnnotationRepository extends PagingAndSortingRepository<Annotation, Integer> {
    Annotation findByid(@Param("id") Integer id);
    List<Annotation> findByAnnotated(@Param("value") Sample value);

}
