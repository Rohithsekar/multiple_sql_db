package com.example.multiple_datasource.model.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Geometry;

import javax.persistence.*;

@Entity
@Table(name = "locations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    // Example of using PostGIS Geometry data type
//    @Type(type = "org.hibernate.spatial.JTSGeometryType")
    @Column(name = "location", columnDefinition = "Geometry")
    private Geometry location;


    public Location(String name, Geometry location) {
        this.name = name;
        this.location = location;
    }


}