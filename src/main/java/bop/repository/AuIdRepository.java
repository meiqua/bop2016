package bop.repository;

import bop.domain.AfId;
import bop.domain.AuId;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Component;

@Component
public interface AuIdRepository extends GraphRepository<AuId> {
    AuId findByName(long name);
}
