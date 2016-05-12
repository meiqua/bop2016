package bop.repository;

import java.util.List;
import java.util.Set;

import bop.domain.Property;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//@RepositoryRestResource(collectionResourceRel = "property", path = "property")
public interface ImportRepository {

    void save(Property property);
    void bind(Property property1,Property property2);

    void importFinish();

}