package sdcc2018.spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sdcc2018.spring.domain.Semaphore;
import sdcc2018.spring.service.SemaphoreService;

@RestController
@RequestMapping(path = "/semaphore")
public class SemaphoreRest {

    @Autowired
    private SemaphoreService semaphoreService;


    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Semaphore> saveSemaphore(@RequestBody Semaphore semaphore) {
        return new ResponseEntity<>(semaphoreService.createSemaphore(semaphore), HttpStatus.CREATED);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<Semaphore> find(@RequestBody Semaphore semaphore) {
        return new ResponseEntity<>(semaphoreService.createSemaphore(semaphore), HttpStatus.CREATED);
    }


}
