package net.cmderobertis.bookbroker.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message="Title required")
    private String title;
    @NotEmpty(message="Author required")
    private String author;
    @NotEmpty(message="Thoughts required")
    private String thoughts;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrower_id")
    private User borrower;
    public Book() {}
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getAuthor() {return author;}
    public void setAuthor(String author) {this.author = author;}
    public String getThoughts() {return thoughts;}
    public void setThoughts(String thoughts) {this.thoughts = thoughts;}
    public User getOwner() {return owner;}
    public void setOwner(User owner) {this.owner = owner;}
    public User getBorrower() {return borrower;}
    public void setBorrower(User borrower) {this.borrower = borrower;}
}
