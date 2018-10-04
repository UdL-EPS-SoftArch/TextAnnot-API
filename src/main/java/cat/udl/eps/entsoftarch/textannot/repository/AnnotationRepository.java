package cat.udl.eps.entsoftarch.textannot.repository;


import cat.udl.eps.entsoftarch.textannot.domain.Annotation;
import cat.udl.eps.entsoftarch.textannot.domain.Sample;
import cat.udl.eps.entsoftarch.textannot.domain.Tag;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface AnnotationRepository extends PagingAndSortingRepository<Annotation, Integer> {
    Optional<List<Annotation>> findBySample(@Param("sample") Sample sample);
    Optional<List<Annotation>> findByTag(@Param("tag") Tag tag);

}

