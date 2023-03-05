package sk.stuba.fei.uim.oop.assignment3.book.logic;

import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookAmountRequest;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookRequest;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookRequestEdit;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

import java.util.List;

public interface IBookService {
    List<Book> getAll();
    Book create(BookRequest request) throws NotFoundException;
    Book getById(Long id) throws NotFoundException;
    Book editBook(Long id, BookRequestEdit book) throws NotFoundException;
    void delete(Long id) throws NotFoundException;
    Book editAmount(Long id, BookAmountRequest request) throws NotFoundException;
}
