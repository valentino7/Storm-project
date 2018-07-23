package sdcc2018.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sdcc2018.spring.domain.SemaphoreGUI;
import sdcc2018.spring.repo.SemaphoreRepo;

@Service
public class SemaphoreService {

    @Autowired
    private SemaphoreRepo semaphoreRepo;

    @Transactional
    public SemaphoreGUI createSemaphore(SemaphoreGUI semaphore) {
        return semaphoreRepo.save(semaphore);
    }


}
