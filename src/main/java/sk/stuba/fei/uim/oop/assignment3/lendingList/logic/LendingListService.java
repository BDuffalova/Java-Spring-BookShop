package sk.stuba.fei.uim.oop.assignment3.lendingList.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;
import sk.stuba.fei.uim.oop.assignment3.book.data.BookRepository;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.lendingList.web.bodies.LendingListRequest;
import sk.stuba.fei.uim.oop.assignment3.lendingList.data.LendingList;
import sk.stuba.fei.uim.oop.assignment3.lendingList.data.LendingListRepository;

import java.util.List;
import java.util.Objects;

@Service
public class LendingListService implements ILendingListService {

    @Autowired
    private LendingListRepository repository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public LendingList create() {
        return this.repository.save(new LendingList());
    }

    @Override
    public LendingList getById(Long id) throws NotFoundException {
        LendingList l = this.repository.findLendingListById(id);
        if (l == null) {
            throw new NotFoundException();
        }
        return l;
    }

    @Override
    public List<LendingList> getAll() {
        return this.repository.findAll();
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        LendingList l = this.repository.findLendingListById(id);
        if (l == null) {
            throw new NotFoundException();
        }
        for (Book b : l.getBooks()) {
            b.setLendCount(b.getLendCount() - 1);
        }
        this.repository.delete(l);
    }

    @Override
    public LendingList add(Long id, LendingListRequest request) throws NotFoundException, IllegalOperationException {
        LendingList l = this.repository.findLendingListById(id);
        Book b = this.bookRepository.findBookById(request.getId());
        if (l == null) {
            throw new NotFoundException();
        }

        for (Book book : l.getBooks()) {
            if (Objects.equals(book.getId(), request.getId())) {
                throw new IllegalOperationException();
            }
        }

        if (l.isLended()) {
            throw new IllegalOperationException();
        }
        if (b == null) {
            throw new NotFoundException();
        }
        l.getBooks().add(b);
        return this.repository.save(l);
    }

    @Override
    public void deleteBook(Long id, LendingListRequest request) throws NotFoundException {
        LendingList l = this.repository.findLendingListById(id);
        Book b = this.bookRepository.findBookById(request.getId());
        if (l == null) {
            throw new NotFoundException();
        }
        if (b == null) {
            throw new NotFoundException();
        }
        l.getBooks().remove(b);
        this.repository.save(l);
    }

    @Override
    public void lend(Long id) throws NotFoundException, IllegalOperationException {
        LendingList l = this.repository.findLendingListById(id);
        if (l == null) {
            throw new NotFoundException();
        }
        if (l.isLended()) {
            throw new IllegalOperationException();
        }
        l.setLended(true);
        for (Book b : l.getBooks()) {
            b.setLendCount(b.getLendCount() + 1);
        }
        this.repository.save(l);
    }
}
