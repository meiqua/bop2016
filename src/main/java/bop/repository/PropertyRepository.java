package bop.repository;

import bop.domain.AfId;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.List;


public interface PropertyRepository extends GraphRepository<AfId> {

    @Query("MATCH (au1:AuId {name:{0}})-[:HOP]-(afId:AfId)-[:HOP]-(au2:AuId {name:{1}})" +
            "RETURN afId.name")
    List<Long> findTwoHop(long name1,long name2);
}
