package sdcc2018.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sdcc2018.spring.domain.IntersectionGUI;
import sdcc2018.spring.repo.IntersectionRepo;
import sdcc2018.spring.repo.SemaphoreRepo;

import java.util.ArrayList;

@Service
public class IntersectionService {


    @Autowired
    private IntersectionRepo intersectionRepo;


    @Transactional
    public IntersectionGUI createIntersection(IntersectionGUI intersection) {
        return intersectionRepo.save(intersection);
    }

    public boolean deleteIntersection(int id) {
        IntersectionGUI intersectionToDelete = intersectionRepo.findByidIntersection(id);
        if ( intersectionToDelete == null)
            return false;
        intersectionRepo.delete(intersectionToDelete);
        return true;
    }

    public ArrayList<IntersectionGUI> findAll() {
        return intersectionRepo.findAll();
    }

    public IntersectionGUI findByIdIntersection(int id) {
        return intersectionRepo.findByidIntersection(id);
    }
}
