package sk.stuba.fei.uim.oop.assignment3.lendingList.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.lendingList.logic.ILendingListService;
import sk.stuba.fei.uim.oop.assignment3.lendingList.web.bodies.LendingListRequest;
import sk.stuba.fei.uim.oop.assignment3.lendingList.web.bodies.LendingListResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/list")
public class LendingListController {

    @Autowired
    private ILendingListService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LendingListResponse> getAllLendingLists() {
        return this.service.getAll().stream().map(LendingListResponse::new).collect(Collectors.toList());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LendingListResponse> createList(){
        return new ResponseEntity<>(new LendingListResponse(this.service.create()), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LendingListResponse> getListById(@PathVariable("id") Long id) throws NotFoundException {
        return new ResponseEntity<>(new LendingListResponse(this.service.getById(id)),HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id) throws NotFoundException {
        this.service.delete(id);
    }

    @PostMapping(value = ("/{id}/add"),consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LendingListResponse> addBookToList(@PathVariable("id") Long id, @RequestBody LendingListRequest request) throws NotFoundException, IllegalOperationException {
        return new ResponseEntity<>(new LendingListResponse(this.service.add(id,request)),HttpStatus.OK);
    }

    @DeleteMapping(value = ("/{id}/remove"), consumes = MediaType.APPLICATION_JSON_VALUE)
    public void removeBookFromList(@PathVariable("id") Long id, @RequestBody LendingListRequest request) throws NotFoundException {
        this.service.deleteBook(id,request);
    }

    @GetMapping(value = "/{id}/lend")
    public void lendList(@PathVariable("id") Long id) throws NotFoundException, IllegalOperationException {
        this.service.lend(id);
    }
}
