package cat.udl.eps.entsoftarch.textannot.repository;


import cat.udl.eps.entsoftarch.textannot.domain.Annotation;
import cat.udl.eps.entsoftarch.textannot.domain.Sample;
import cat.udl.eps.entsoftarch.textannot.domain.Tag;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AnnotationRepository extends PagingAndSortingRepository<Annotation, Integer> {
    /**
     * Returns the annotations related to a sample.
     * @param sample The sample that contains the annotations we want.
     * @return list of annotations.
     */
    List<Annotation> findBySample(@Param("sample") Sample sample);

    /**
     Returns the annotations related to a tag.
     * @param tag The tags that contains the annotations we want.
     * @return list of annotations.
     */
    List<Annotation> findByTag(@Param("tag") Tag tag);

}

