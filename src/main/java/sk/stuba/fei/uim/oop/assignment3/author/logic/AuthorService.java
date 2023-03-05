package sk.stuba.fei.uim.oop.assignment3.author.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.author.data.Author;
import sk.stuba.fei.uim.oop.assignment3.author.data.AuthorRepository;
import sk.stuba.fei.uim.oop.assignment3.author.web.bodies.AuthorRequest;
import sk.stuba.fei.uim.oop.assignment3.author.web.bodies.AuthorUpdateRequest;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

import java.util.List;

@Service
public class AuthorService implements IAuthorService {
    @Autowired
    private AuthorRepository repository;

    @Override
    public Author create(AuthorRequest request) {
        return this.repository.save(new Author(request));
    }

    @Override
    public Author getById(Long id) throws NotFoundException {
        Author a = this.repository.findAuthorById(id);
        if (a == null) {
            throw new NotFoundException();
        }
        return a;
    }

    @Override
    public List<Author> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Author updateAuthor(Long id, AuthorUpdateRequest author) throws NotFoundException {
        Author a = this.repository.findAuthorById(id);
        if(a == null){
            throw new NotFoundException();
        }
        if(author.getName()!=null){
            a.setName(author.getName());
        }
        if(author.getSurname()!=null){
            a.setSurname(author.getSurname());
        }
        return this.repository.save(a);
    }
    @Override
    public void delete(Long id) throws NotFoundException {
        Author a = this.getById(id);
        if(a==null){
            throw new NotFoundException();
        }
        this.repository.delete(this.getById(id));
    }
    public void addBook(Book book) throws NotFoundException {
        Author a = this.repository.findAuthorById(book.getAuthor());
        if(a == null){
            throw new NotFoundException();
        }
        this.repository.findAuthorById(book.getAuthor()).getBooks().add(book);
    }
    public void deleteBook(Book book) throws NotFoundException {
        Author a = this.repository.findAuthorById(book.getAuthor());
        if(a == null){
            throw new NotFoundException();
        }
        a.getBooks().remove(book);
    }
}
