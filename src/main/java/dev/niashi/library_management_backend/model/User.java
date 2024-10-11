package dev.niashi.library_management_backend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_user")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Document is required.")
    private String document;

    private String email;

    @NotBlank(message = "Phone is required.")
    private String phone;

    private String address;
    private LocalDate registrationDate = LocalDate.now();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Loan> loans = new ArrayList<>();

    public User() {
    }

    public User(
            String name,
            String document,
            String email,
            String phone,
            String address
    ) {
        this.name = name;
        this.document = document;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
        loan.setUser(this);
    }

    @Override
    public String toString() {
        return "User {" +
                "id =" + id +
                ", name ='" + name + '\'' +
                ", document =" + document +
                ", email ='" + email + '\'' +
                ", phone ='" + phone + '\'' +
                ", registrationDate =" + registrationDate +
                ", address ='" + address + '\'' +
                ", loans =" + loans +
                '}';
    }
}
