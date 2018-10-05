package sdcc2018.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sdcc2018.spring.domain.IntersectionGUI;
import sdcc2018.spring.domain.SemaphoreGUI;
import sdcc2018.spring.repo.IntersectionRepo;
import sdcc2018.spring.repo.SemaphoreRepo;
import sdcc2018.storm.entity.Intersection;

import java.util.ArrayList;

@Service
public class IntersectionService {


    @Autowired
    private IntersectionRepo intersectionRepo;

    @Autowired
    private SemaphoreRepo semaphoreRepo;


    @Transactional
    public IntersectionGUI createIntersection(IntersectionGUI intersection) {
       /* if(semaphoreRepo.saveAll(intersection.getSemaphoreList())==null)
            return null;*/
        return intersectionRepo.save(intersection);
    }

    public boolean deleteIntersection(String id) {
        IntersectionGUI intersectionToDelete = intersectionRepo.findByid(id);
        if ( intersectionToDelete == null)
            return false;

        /*for(int i=0;i!=4;i++)
            SemaphoreGUI semaphoresToDelete = semaphoreRepo.findByid(intersectionToDelete.getSemaphoreList().get(i).getId());*/
        intersectionRepo.delete(intersectionToDelete);
        return true;
    }

    public ArrayList<IntersectionGUI> findAll() {
        return intersectionRepo.findAll();
    }

    public IntersectionGUI findByIdIntersection(String id) {
        IntersectionGUI intersectionGUI =intersectionRepo.findByid(id);
        return intersectionGUI;
    }

    public IntersectionGUI updateIntersection(String id, IntersectionGUI intersection) {
        IntersectionGUI intersectionToUpdate = intersectionRepo.findByid(id);
        if (intersectionToUpdate == null )
            return null;

        intersectionToUpdate.updateIntersection(intersection);

        return intersectionRepo.save(intersectionToUpdate);
    }
}
