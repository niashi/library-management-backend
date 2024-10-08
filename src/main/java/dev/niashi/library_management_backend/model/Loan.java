package dev.niashi.library_management_backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDate loanDate = LocalDate.now();
    private LocalDate returnDate;

    public Loan() {
    }

    public Loan(User user, Book book, LocalDate returnDate) {
        this.user = user;
        this.book = book;
        this.returnDate = returnDate;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Loan {" +
                "id =" + id +
                ", user =" + user +
                ", book =" + book +
                ", loanDate =" + loanDate +
                ", returnDate =" + returnDate +
                '}';
    }
}
