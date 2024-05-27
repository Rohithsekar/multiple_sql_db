package com.example.multiple_datasource;

import com.example.multiple_datasource.model.location.Location;
import com.example.multiple_datasource.model.user.User;
import com.example.multiple_datasource.repositories.location.LocationRepository;
import com.example.multiple_datasource.repositories.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EnableTransactionManagement
public class JpaMultipleDBIntegrationTest {
 
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Test
    @Transactional("userTransactionManager")
    public void whenCreatingUser_thenCreated() {
        User user = new User();
        user.setName("John");
        user.setEmail("john@test.com");
        user.setAge(20);
        user = userRepository.save(user);

        assertNotNull(userRepository.findById(user.getId()));
    }

    @Test
    @Transactional("userTransactionManager")
    public void whenCreatingUsersWithSameEmail_thenRollback() {
        User user1 = new User();
        user1.setName("John");
        user1.setEmail("john@test.com");
        user1.setAge(20);
        user1 = userRepository.save(user1);
        assertNotNull(userRepository.findById(user1.getId()));

        User user2 = new User();
        user2.setName("Tom");
        user2.setEmail("john@test.com");
        user2.setAge(10);

        User savedUser = null;
        try {
            user2 = userRepository.save(user2);
            savedUser=userRepository.findById(user2.getId()).get();
        } catch (DataIntegrityViolationException e) {
        }catch (InvalidDataAccessApiUsageException e){

        }

        assertNull(savedUser);
    }

    @Test
    @Transactional("locationTransactionManager")
    public void whenCreatingLocation_thenCreated() {
        // Create a new location
        Location location = new Location();
        location.setName("Example Location");

        // Example of creating a point geometry
        // Note: You need to import the appropriate classes for Geometry and Point
        // from the JTS library
        Point point = new GeometryFactory().createPoint(new Coordinate(1.0, 1.0));
        location.setLocation(point);

        // Save the location
        location = locationRepository.save(location);

        // Check if the location is persisted
        assertNotNull(location.getId());
    }
}