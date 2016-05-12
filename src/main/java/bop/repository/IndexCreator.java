package bop.repository;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class IndexCreator {
    @Autowired
    GraphDatabaseService graphDatabaseService;

    @PostConstruct
    public void createIndexes(){
        try {
            Transaction tx = graphDatabaseService.beginTx();

            graphDatabaseService.execute("CREATE INDEX ON :FId(name)");
            graphDatabaseService.execute("CREATE INDEX ON :AuId(name)");
            graphDatabaseService.execute("CREATE INDEX ON :AfId(name)");
            graphDatabaseService.execute("CREATE INDEX ON :JId(name)");
            graphDatabaseService.execute("CREATE INDEX ON :CId(name)");

            tx.success();

        } catch (Exception e){

        }
    }
}
