package bop.domain;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.annotation.*;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Property {
	private Property(){}
	@GraphId private Long graphId;

	private long name;
	//Use lower API to create labels

	@Transient
	private String type;
	//I want to create labels, but doubting @labels doesn't work well
	//So I use lower API to create labels

	public long getName() {
		return name;
	}

	public void setName(long name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
