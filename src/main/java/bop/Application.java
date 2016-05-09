package bop;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import org.neo4j.io.fs.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;

import java.io.File;

@SpringBootApplication
@EnableNeo4jRepositories
public class Application extends Neo4jConfiguration {

	public Application() {
		setBasePackage("bop");
	}

	@Bean(destroyMethod = "shutdown")
	public GraphDatabaseService graphDatabaseService() {
		return new GraphDatabaseFactory().newEmbeddedDatabase("target/bop.db");
	}

	public static void main(String[] args) throws Exception {

		FileUtils.deleteRecursively(new File("target/bop.db"));

		SpringApplication.run(Application.class, args);
	}
}
