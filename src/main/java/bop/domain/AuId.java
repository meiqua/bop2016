package bop.domain;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.*;
;import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class AuId {
    private AuId(){}

    public AuId(long name) {
        this.name = name;
    }

    @GraphId
    private Long graphId;

    @Indexed(unique=true)
    private long name;

    @RelatedTo(type = "HOP" ,elementClass = AfId.class,direction = Direction.INCOMING)
    public @Fetch
    Set<AfId> afIds;

    public void hopTo(AfId afId) {
        if (afIds == null) {
            afIds = new HashSet<>();
        }
        afIds.add(afId);
    }


    public long getName() {
        return name;
    }

    public void setName(long name) {
        this.name = name;
    }
}
