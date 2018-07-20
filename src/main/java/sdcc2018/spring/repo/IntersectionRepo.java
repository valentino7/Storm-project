package sdcc2018.spring.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sdcc2018.spring.domain.Intersection;

import java.util.ArrayList;

@Repository
public interface IntersectionRepo extends MongoRepository<Intersection, String> {

    Intersection findByidIntersection(int id);
    ArrayList<Intersection> findAll();

}
