package sk.stuba.fei.uim.oop.assignment3.lendingList.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Data
@Entity
public class LendingList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany
    private List<Book> books;

    private boolean lended;

    public LendingList() {
        this.books = new ArrayList<>();
        this.lended = false;
    }
}
