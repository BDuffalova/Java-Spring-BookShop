package sk.stuba.fei.uim.oop.assignment3.author.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.oop.assignment3.author.logic.IAuthorService;
import sk.stuba.fei.uim.oop.assignment3.author.web.bodies.AuthorRequest;
import sk.stuba.fei.uim.oop.assignment3.author.web.bodies.AuthorResponse;
import sk.stuba.fei.uim.oop.assignment3.author.web.bodies.AuthorUpdateRequest;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private IAuthorService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AuthorResponse> getAllAuthors() {
        return this.service.getAll().stream().map(AuthorResponse::new).collect(Collectors.toList());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorResponse> addAuthor(@RequestBody AuthorRequest body) {
        return new ResponseEntity<>(new AuthorResponse(this.service.create(body)), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthorResponse getAuthorById(@PathVariable("id") Long authorId) throws NotFoundException {
        return new AuthorResponse(this.service.getById(authorId));
    }

    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthorResponse updateAuthorById(@PathVariable("id") Long authorId,@RequestBody AuthorUpdateRequest authorUpdate) throws NotFoundException {
        return new AuthorResponse(this.service.updateAuthor(authorId,authorUpdate));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteAuthorById(@PathVariable("id") Long authorId) throws NotFoundException {
        this.service.delete(authorId);
    }
}
