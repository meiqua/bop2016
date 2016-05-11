package bop.repository;

import bop.domain.Property;
import org.aspectj.asm.internal.Relationship;
import org.neo4j.cypher.internal.compiler.v1_9.symbols.RelationshipType;
import org.neo4j.graphdb.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Component;

@Component
public class MTPropertyRepository implements PropertyRepository {

    private final ManagedTransaction transaction;
    private final Neo4jTemplate neo4jTemplate;

    @Autowired
    public MTPropertyRepository(ManagedTransaction transaction, Neo4jTemplate neo4jTemplate) {
        this.transaction = transaction;
        this.neo4jTemplate = neo4jTemplate;
    }

    @Override
    public void save(Property property) {
        transaction.prepareTransaction();
        neo4jTemplate.save(property);
    }

    @Override
    public void bind(Property property1, Property property2) {
        Node p1Node = neo4jTemplate.getPersistentState(property1);
        Node p2Node = neo4jTemplate.getPersistentState(property2);

        transaction.prepareTransaction();
        neo4jTemplate.createRelationshipBetween(p1Node,p2Node,"hop",null);
    }
}
