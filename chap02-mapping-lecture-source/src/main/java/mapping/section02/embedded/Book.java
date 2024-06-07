package mapping.section02.embedded;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookNo;

    @Column(name="book_title")
    private String bookTitle;

    @Column(name="author")
    private String author;

    @Column(name = "publisher")
    private String publisher;

    @Column(name="published_date")
    private LocalDate publishedDate;

    @Embedded
    private Price price;
    // 실제로는 Price 안의 모든 필드가 컬럼으로 사용된다 즉 하나의 컬럼이 아니라 세개의 컬럼이 들어 간것이다

    public Book(){}

    public Book(String bookTitle, String author, String publisher, LocalDate publishedDate, Price price) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.price = price;
    }
}
