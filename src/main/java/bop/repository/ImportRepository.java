package bop.repository;

import bop.domain.AfId;
import bop.domain.AuId;

//@RepositoryRestResource(collectionResourceRel = "property", path = "property")
public interface ImportRepository {

    void save(AfId property);

    void save(AuId property);

    void bind(AuId property1, AfId property2);

    void importFinish();

}
