package bop.repository;

import bop.domain.Property;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface PropertyRepository extends GraphRepository<Property> {

    @Query("MATCH (property:{1} {name:{0}})" +
            "RETURN property")
        Property findByNameAndType(long name,String type);

    @Query("MATCH (property:{1} {name:{0}})-[:HOP]-(oneHopProperties)" +
            "RETURN oneHopProperties")
        Iterable<Property> findOneHopProperties(long name,String type);

}
