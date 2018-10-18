package cat.udl.eps.entsoftarch.textannot.repository;

import cat.udl.eps.entsoftarch.textannot.domain.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface MetadataValueRepository extends PagingAndSortingRepository<MetadataValue, Integer> {
    /**
     * Returns the metadataField found by a given value.
     * @param value The given value of metadataField.
     * @return metadataValue object.
     */
    MetadataValue findByValue(@Param("value") String value);

    /**
     * Returns a list of metadataValue that were found by a given value.
     * @param value The given value of a group of metadataValue.
     * @return list of metadataValues.
     */
    List<MetadataValue> findByValueContaining(@Param("value") String value);

    /**
     * Returns a list of metadataValue that were found by a given metadataField.
     * @param metadataField The given metadataField of a group of metadataValue.
     * @return list of metadataValues.
     */
    List<MetadataValue> findByValues(@Param("metadataField")MetadataField metadataField);

    /**
     * Query that gives us a list of metadataTemplate that accomplish that his id is equal to metadatafield id
     and his value is equal to the first parameter(String value).
     * @param value The given value of MetadataTemplate
     * @return a list of metadataTemplate that accomplishes the result of the query.
     */
    @Query("SELECT t " +
            "FROM MetadataTemplate t, MetadataField f, MetadataValue v " +
            "WHERE v.values.id = f.id AND f.definedAt.id = t.id AND v.value = ?1")
    List<MetadataTemplate> findAllMetadataTemplatesByValue(@Param("value")String value);

    /**
     * Returns a list of metadataValue that were found by a given sample.
     * @param sample The given sample of a group of metadataValues
     * @return list of metadataValues.
     */
    List<MetadataValue> findByForA(@Param("sample") Sample sample);
}
