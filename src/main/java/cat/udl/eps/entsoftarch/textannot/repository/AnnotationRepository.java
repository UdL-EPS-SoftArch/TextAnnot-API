package cat.udl.eps.entsoftarch.textannot.repository;


import cat.udl.eps.entsoftarch.textannot.domain.Annotation;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AnnotationRepository extends PagingAndSortingRepository<Annotation, Integer> {
}
