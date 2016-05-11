package bop.domain;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.*;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Property {
	private Property(){}
	@GraphId private Long id;

	@Indexed(unique = true)
	private String name;

	@Indexed
	private String type;

//	@RelatedTo(type = "RELATED",direction = Direction.BOTH)
//	public @Fetch
//	Set<Property> oneHopProperties;

//	public void oneHopTo(Property property){
//		if (oneHopProperties == null) {
//			oneHopProperties = new HashSet<>();
//		}
//		oneHopProperties.add(property);
//		//set can make sure no duplicated value being added
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
