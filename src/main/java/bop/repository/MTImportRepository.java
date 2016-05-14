package bop.repository;

import bop.domain.AfId;
import bop.domain.AuId;
import org.neo4j.graphdb.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Component;

@Component
public class MTImportRepository implements ImportRepository {

    private final ManagedTransaction transaction;
    private final Neo4jTemplate neo4jTemplate;

    @Autowired
    public MTImportRepository(ManagedTransaction transaction, Neo4jTemplate neo4jTemplate) {
        this.transaction = transaction;
        this.neo4jTemplate = neo4jTemplate;
    }

    @Override
    public void save(AuId property) {
        if(neo4jTemplate.getPersistentState(property)!=null){
            transaction.prepareTransaction();
            neo4jTemplate.save(property);
        }
    }
    @Override
    public void save(AfId property) {
        if(neo4jTemplate.getPersistentState(property)!=null){
            transaction.prepareTransaction();
            neo4jTemplate.save(property);
        }
    }
    @Override
    public void bind(AuId property1, AfId property2) {

         if(!neo4jTemplate.getGraphDatabaseService()
                .execute("MATCH (p1:AuId" + " {name:" + property1.getName() + "})-[:HOP]-(p2:AfId"
                        + "{name:" + property2.getName() + "}"
                        + ") RETURN p1").hasNext()){
             Node p1Node = neo4jTemplate.getPersistentState(property1);
             Node p2Node = neo4jTemplate.getPersistentState(property2);
             transaction.prepareTransaction();
             neo4jTemplate.createRelationshipBetween(p1Node, p2Node, "HOP", null);
         }
    }

    @Override
    public void importFinish() {
        transaction.finishTransaction();
    }
}
