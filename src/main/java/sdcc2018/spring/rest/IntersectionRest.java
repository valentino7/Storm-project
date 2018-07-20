package sdcc2018.spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sdcc2018.spring.domain.Intersection;
import sdcc2018.spring.service.IntersectionService;
import sdcc2018.spring.service.SemaphoreService;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/intersection")
public class IntersectionRest {

    @Autowired
    private SemaphoreService semaphoreService;
    @Autowired
    private IntersectionService intersectionService;

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public ResponseEntity<Intersection> createIntersection(@RequestBody Intersection intersection) {
        return new ResponseEntity<>(intersectionService.createIntersection(intersection), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Intersection>> findAll() {
        return new ResponseEntity<>(intersectionService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(path = "findByID/{id}", method = RequestMethod.GET)
    public ResponseEntity<Intersection> findByIdIntersection(@PathVariable int id) {
        return new ResponseEntity<>(intersectionService.findByIdIntersection(id), HttpStatus.OK);
    }

    @RequestMapping(path = "deleteById/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteIntersection(@PathVariable int id) {
        boolean response = intersectionService.deleteIntersection(id);
        return new ResponseEntity<>(response, response ? HttpStatus.OK: HttpStatus.NOT_FOUND);
    }

}
