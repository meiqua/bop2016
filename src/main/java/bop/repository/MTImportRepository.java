package bop.repository;

import bop.domain.Property;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Component;
import org.neo4j.graphdb.DynamicLabel;

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
    public void save(Property property) {
        if(neo4jTemplate.getPersistentState(property)!=null){
            transaction.prepareTransaction();
            Label label = DynamicLabel.label(property.getType());
            neo4jTemplate.save(property);
            Node pNode = neo4jTemplate.getPersistentState(property);
            pNode.addLabel(label);
        }
    }

    @Override
    public void bind(Property property1, Property property2) {

         if(!neo4jTemplate.getGraphDatabaseService()
                .execute("MATCH (p1:"+property1.getType()+" {name:"+property1.getName()+"})-[:HOP]-(p2:"+
                        property2.getType()+"{name:"+property2.getName()+"}"
                        +") RETURN p1").hasNext()){
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
