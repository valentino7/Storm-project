package sdcc2018.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sdcc2018.spring.domain.Intersection;
import sdcc2018.spring.domain.Semaphore;
import sdcc2018.spring.repo.IntersectionRepo;
import sdcc2018.spring.repo.SemaphoreRepo;

import java.util.ArrayList;

@Service
public class IntersectionService {


    @Autowired
    private IntersectionRepo intersectionRepo;


    @Transactional
    public Intersection createIntersection(Intersection intersection) {
        return intersectionRepo.save(intersection);
    }

    public boolean deleteIntersection(int id) {
        Intersection intersectionToDelete = intersectionRepo.findByidIntersection(id);
        if ( intersectionToDelete == null)
            return false;
        intersectionRepo.delete(intersectionToDelete);
        return true;
    }

    public ArrayList<Intersection> findAll() {
        return intersectionRepo.findAll();
    }

    public Intersection findByIdIntersection(int id) {
        return intersectionRepo.findByidIntersection(id);
    }
}
