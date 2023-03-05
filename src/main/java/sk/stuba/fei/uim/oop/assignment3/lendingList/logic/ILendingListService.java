package sk.stuba.fei.uim.oop.assignment3.lendingList.logic;

import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.lendingList.web.bodies.LendingListRequest;
import sk.stuba.fei.uim.oop.assignment3.lendingList.data.LendingList;

import java.util.List;

public interface ILendingListService {
    LendingList create();
    LendingList getById(Long id) throws NotFoundException;
    List<LendingList> getAll();
    void delete(Long id) throws NotFoundException;
    LendingList add(Long id, LendingListRequest request) throws NotFoundException, IllegalOperationException;
    void deleteBook(Long id, LendingListRequest request) throws NotFoundException;
    void lend(Long id) throws NotFoundException, IllegalOperationException;
}
