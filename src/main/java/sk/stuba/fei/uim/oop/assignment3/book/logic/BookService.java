package sk.stuba.fei.uim.oop.assignment3.book.logic;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.author.data.Author;
import sk.stuba.fei.uim.oop.assignment3.author.data.AuthorRepository;
import sk.stuba.fei.uim.oop.assignment3.author.logic.IAuthorService;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookAmountRequest;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookRequest;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookRequestEdit;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;
import sk.stuba.fei.uim.oop.assignment3.book.data.BookRepository;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

import java.util.List;

@Getter
@Setter
@Service
public class BookService implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private IAuthorService authorService;

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public List<Book> getAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Book create(BookRequest request) throws NotFoundException {
        Book b = new Book(request);
        Author a = this.authorRepository.findAuthorById(request.getAuthor());
        if (a == null) {
            throw new NotFoundException();
        }
        a.getBooks().add(b);
        return this.bookRepository.save(b);
    }

    @Override
    public Book getById(Long id) throws NotFoundException {
        Book b = this.bookRepository.findBookById(id);
        if (b == null) {
            throw new NotFoundException();
        }
        return b;
    }

    @Override
    public Book editBook(Long id, BookRequestEdit book) throws NotFoundException {
        Book b = this.bookRepository.findBookById(id);
        if (b == null) {
            throw new NotFoundException();
        }
        if (book.getName() != null) {
            b.setName(book.getName());
        }
        if (book.getDescription() != null) {
            b.setDescription(book.getDescription());
        }
        if (book.getAuthor() != 0) {
            b.setAuthor(this.authorRepository.findAuthorById(book.getAuthor()).getId());
        }
        if (book.getPages() != 0) {
            b.setPages(book.getPages());
        }
        return this.bookRepository.save(b);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        Book b = this.bookRepository.findBookById(id);
        if (b == null) {
            throw new NotFoundException();
        }
        this.authorService.deleteBook(b);
        this.bookRepository.delete(b);
    }

    @Override
    public Book editAmount(Long id, BookAmountRequest request) throws NotFoundException {
        Book b = this.bookRepository.findBookById(id);
        if (b == null) {
            throw new NotFoundException();
        }
        b.setAmount(b.getAmount() + request.getAmount());
        return this.bookRepository.save(b);
    }
}
