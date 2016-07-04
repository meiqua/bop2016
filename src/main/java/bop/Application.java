package bop;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import org.neo4j.io.fs.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.data.neo4j.support.DelegatingGraphDatabase;
import org.springframework.data.neo4j.support.Neo4jTemplate;

import java.io.File;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@EnableNeo4jRepositories
public class Application extends Neo4jConfiguration {

	public Application() {
		setBasePackage("bop");
	}

	@Bean(destroyMethod = "shutdown")
	public GraphDatabaseService graphDatabaseService() {
		return new GraphDatabaseFactory().newEmbeddedDatabase("bop.db");
	}

//	@Bean
//	public Neo4jTemplate neo4jTemplate(GraphDatabaseService graphDatabaseService){
//		GraphDatabase graphDatabase = new DelegatingGraphDatabase(graphDatabaseService);
//		return new Neo4jTemplate(graphDatabase);
//	}

//	@Bean
//	public int operationsThreshold() {
//		return 30000;
//		// 30k operations/transaction, will save much time compared to 1 operation/transaction
//	}

//	@Bean
//	public String csvFile() {
//		return "wanted.txt";
//		// CsvFile path added here
//	}


	public static void main(String[] args) throws Exception {

//		FileUtils.deleteRecursively(new File("target/bop.db"));

		SpringApplication.run(Application.class, args);
	}

}
