package sdcc2018.spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sdcc2018.spring.domain.SemaphoreGUI;
import sdcc2018.spring.service.SemaphoreService;

@RestController
@RequestMapping(path = "/semaphore")
public class SemaphoreRest {

    @Autowired
    private SemaphoreService semaphoreService;


    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<SemaphoreGUI> saveSemaphore(@RequestBody SemaphoreGUI semaphore) {
        return new ResponseEntity<>(semaphoreService.createSemaphore(semaphore), HttpStatus.CREATED);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<SemaphoreGUI> find(@RequestBody SemaphoreGUI semaphore) {
        return new ResponseEntity<>(semaphoreService.createSemaphore(semaphore), HttpStatus.CREATED);
    }


}
