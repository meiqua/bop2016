package bop.domain;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;;

@NodeEntity
public class AfId {
    private AfId(){}

    public AfId(long name) {
        this.name = name;
    }

    @GraphId
    private Long graphId;

    @Indexed(unique=true)
    private long name;

    public long getName() {
        return name;
    }

    public void setName(long name) {
        this.name = name;
    }
}
