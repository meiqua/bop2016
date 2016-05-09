package bop.repository;

import java.util.List;
import java.util.Set;

import bop.domain.Property;
import org.springframework.data.neo4j.repository.GraphRepository;

//@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PropertyRepository extends GraphRepository<Property> {

    Property findByNameAndType(String name,String type);
    // auto completed by spring,through parsing the method name

    List<Property> findByOneHopPropertiesName(String name);
    // auto completed by spring,through parsing the method name

}
