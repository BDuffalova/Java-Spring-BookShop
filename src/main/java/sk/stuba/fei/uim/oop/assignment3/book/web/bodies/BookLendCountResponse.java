package sk.stuba.fei.uim.oop.assignment3.book.web.bodies;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;

@Getter
@Setter
public class BookLendCountResponse {

    private int amount;

    public BookLendCountResponse(Book b) {
        this.amount = b.getLendCount();
    }
}
