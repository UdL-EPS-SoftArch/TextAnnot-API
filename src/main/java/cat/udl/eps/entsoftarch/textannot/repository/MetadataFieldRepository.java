package cat.udl.eps.entsoftarch.textannot.repository;


import cat.udl.eps.entsoftarch.textannot.domain.MetadataField;
import cat.udl.eps.entsoftarch.textannot.domain.MetadataTemplate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface MetadataFieldRepository extends PagingAndSortingRepository<MetadataField, Integer> {
    /**
     * Returns the metadataField found by a given name.
     * @param name The given name of metadataField.
     * @return metadataField object.
     */
    MetadataField findByName (@Param("name") String name);

    /**
     * Returns the metadataField found by a given name and a given category.
     * @param category The given category of metadataField.
     * @param name The given name of metadataField.
     * @return metadataField object.
     */
    MetadataField findByCategoryAndName (@Param("category") String category, @Param("name") String name);

    /**
     * Returns the metadataField list related to a metadataTemplate.
     * @param metadataTemplate The given metadataTemplate that contains metadataFields that we want.
     * @return a list of metadataFields.
     */
    List<MetadataField> findByDefinedAt(@Param("metadataTemplate")MetadataTemplate metadataTemplate);

    /**
     * Query that gives us a list of metadataTemplate that accomplish that his id is equal to metadatafield id
        and his type is equal to the first parameter(String type).
     * @param type The given type of MetadataTemplate
     * @return a list of metadataTemplate that accomplishes the result of the query.
     */
    @Query("SELECT t " +
            "FROM MetadataTemplate t, MetadataField f " +
            "WHERE f.definedAt.id = t.id AND f.type = ?1")
    List<MetadataTemplate> findAllMetadataTemplatesByType(@Param("type") String type);

    /**
     * Query that gives us a list of metadataTemplate that accomplish that his id is equal to metadatafield id
     and his name is equal to the first parameter(String name).
     * @param name The given name of MetadataTemplate
     * @return a list of metadataTemplate that accomplishes the result of the query.
     */
    @Query("SELECT t " +
            "FROM MetadataTemplate t, MetadataField f " +
            "WHERE f.definedAt.id = t.id AND f.name = ?1")
    List<MetadataTemplate> findAllMetadataTemplatesByName(@Param("name") String name);

    /**
     * Query that gives us a list of metadataTemplate that accomplish that his id is equal to metadatafield id
        and his name is equal to the first parameter(String name)
        and his type is equal to the second parameter(String type)
     * @param name The given name of MetadataTemplate
     * @param type The given type of MetadataTemplate
     * @return a list of metadataTemplate that accomplishes the result of the query.
     */
    @Query("SELECT t " +
            "FROM MetadataTemplate t, MetadataField f " +
            "WHERE f.definedAt.id = t.id AND f.name = ?1 AND f.type = ?2")
    List<MetadataTemplate> findAllMetadataTemplatesByNameAndType(@Param("name") String name, @Param("type") String type);
}
