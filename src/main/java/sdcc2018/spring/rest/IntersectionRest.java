package sdcc2018.spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sdcc2018.spring.domain.IntersectionGUI;
import sdcc2018.spring.service.IntersectionService;
import sdcc2018.spring.service.SemaphoreService;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/intersection")
@CrossOrigin
public class IntersectionRest {


    @Autowired
    private IntersectionService intersectionService;


    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public ResponseEntity<IntersectionGUI> createIntersection(@RequestBody IntersectionGUI intersection) {
        return new ResponseEntity<>(intersectionService.createIntersection(intersection), HttpStatus.OK);
    }

    @RequestMapping(path = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<IntersectionGUI> updateIntersection(@PathVariable String id,@RequestBody IntersectionGUI intersection) {
        return new ResponseEntity<>(intersectionService.updateIntersection(id,intersection), HttpStatus.OK);
    }

    @RequestMapping(path = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<IntersectionGUI>> findAll() {
        return new ResponseEntity<>(intersectionService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(path = "findByID/{id}", method = RequestMethod.GET)
    public ResponseEntity<IntersectionGUI> findByIdIntersection(@PathVariable String id) {
        IntersectionGUI response = intersectionService.findByIdIntersection(id);
        return new ResponseEntity<>(response,response!= null ? HttpStatus.OK:  HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "deleteById/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteIntersection(@PathVariable String id) {
        boolean response = intersectionService.deleteIntersection(id);
        return new ResponseEntity<>(response, response ? HttpStatus.OK: HttpStatus.NOT_FOUND);
    }

}
