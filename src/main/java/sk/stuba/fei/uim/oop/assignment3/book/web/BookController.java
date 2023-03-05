package sk.stuba.fei.uim.oop.assignment3.book.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.oop.assignment3.book.logic.IBookService;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.*;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private IBookService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookResponse> getAllBooks() {
        return this.service.getAll().stream().map(BookResponse::new).collect(Collectors.toList());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponse> addBook(@RequestBody BookRequest body) throws NotFoundException {
        return new ResponseEntity<>(new BookResponse(this.service.create(body)), HttpStatus.CREATED);
    }

    @PostMapping(value = "/{id}/amount",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookAmountResponse> editAmount(@PathVariable("id") Long bookId, @RequestBody BookAmountRequest body) throws NotFoundException {
        return new ResponseEntity<>(new BookAmountResponse(this.service.editAmount(bookId,body)), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public BookResponse getBookById(@PathVariable("id") Long bookId) throws NotFoundException {
        return new BookResponse(this.service.getById(bookId));
    }

    @GetMapping(value = "/{id}/amount",produces = MediaType.APPLICATION_JSON_VALUE)
    public BookAmountResponse getAmountById(@PathVariable("id") Long bookId) throws NotFoundException {
        return new BookAmountResponse(this.service.getById(bookId));
    }

    @GetMapping(value = "/{id}/lendCount",produces = MediaType.APPLICATION_JSON_VALUE)
    public BookLendCountResponse getLendCountById(@PathVariable("id") Long bookId) throws NotFoundException {
        return new BookLendCountResponse(this.service.getById(bookId));
    }

    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public BookResponse updateAuthorById(@PathVariable("id") Long bookId,@RequestBody BookRequestEdit bookEdit) throws NotFoundException {
        return new BookResponse(this.service.editBook(bookId,bookEdit));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteBookById(@PathVariable("id") Long bookId) throws NotFoundException {
        this.service.delete(bookId);
    }
}
