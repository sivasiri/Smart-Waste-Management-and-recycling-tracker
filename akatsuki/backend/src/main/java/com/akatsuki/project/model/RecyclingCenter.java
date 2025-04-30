package com.akatsuki.project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;


@Document(collection = "recycling_centers")
public class RecyclingCenter {

	@Id
	private String id;

	private String name;
	private String address;
	private String type; // plastic, metal, organic, etc.
	@Field("location")
	@GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE) // ✅ Explicitly tell it to use 2dsphere

	private GeoJsonPoint location;

	// === Getters and Setters ===

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public GeoJsonPoint getLocation() {
		return location;
	}

	public void setLocation(GeoJsonPoint location) {
		this.location = location;
	}
}

