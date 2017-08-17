package sc.ejb.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The example see https://www.tutorialspoint.com/ejb/ejb_persistence.htm
 */
@Entity
@Table(name="book")
public class Book implements Serializable {
    private Book book;

    public Book(Book book) {
        this(2, "1");
        List<String> testsTRING = new ArrayList<String>();
        testsTRING.add("AA");
        testsTRING.add("BB");
        System.out.println("Book: testsTRING:" + testsTRING);
    }

    private int id;
    private String name;

    public Book(int id, String name) {
        this.id = id;
        this.name = name;
        List<String> testsTRING = new ArrayList<String>();
        testsTRING.add("AA");
        testsTRING.add("BB");
        System.out.println("Book: testsTRING:" + testsTRING);
    }

    public Book(){
        this(new Book(1, "1"));
        List<String> testsTRING = new ArrayList<String>();
        testsTRING.add("AA");
        testsTRING.add("BB");
        System.out.println("Book: testsTRING:" + testsTRING);
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
