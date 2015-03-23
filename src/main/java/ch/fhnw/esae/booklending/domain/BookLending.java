/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fhnw.esae.booklending.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.*;



/**
 *
 * @author andreas.martin
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "findLendingByCustomer", query = "SELECT l FROM BookLending l, Customer c WHERE l.customer = c AND l.customer = :customer ORDER BY l.lendingDate"),
    @NamedQuery(name = "findLendingByCustomerAndBookId", query = "SELECT l FROM BookLending l, Book b, Customer c WHERE l.book = b AND l.customer = c AND l.book = :book AND l.customer = :customer ORDER BY l.lendingDate"),
    @NamedQuery(name = "findNotLendedBooks", query = "select b from Book b WHERE b.id NOT IN (select bl.book.id from BookLending bl WHERE bl.bookIsLended = true)")
})
@Table(name="BOOKLENDING_HANDS_ON_BOOK_LENDING")
public class BookLending implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    @JoinColumn(name = "book_fk")
    private Book book;
    @OneToOne
    @JoinColumn(name = "customer_fk")
    private Customer customer;
    @Temporal(TemporalType.DATE)
    private Date lendingDate;
    @Temporal(TemporalType.DATE)
    private Date returnDate;
    private Boolean bookIsLended;
    @Transient
    private Integer nbOfDaysToReturn;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the book
     */
    public Book getBook() {
        return book;
    }

    /**
     * @param book the book to set
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the lendingDate
     */
    public Date getLendingDate() {
        return lendingDate;
    }

    /**
     * @param lendingDate the lendingDate to set
     */
    public void setLendingDate(Date lendingDate) {
        this.lendingDate = lendingDate;
    }

    /**
     * @return the returnDate
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * @param returnDate the returnDate to set
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * @return the nbOfDaysToReturn
     */
    public Integer getNbOfDaysToReturn() {
        return nbOfDaysToReturn;
    }

    /**
     * @return the bookIsLended
     */
    public Boolean getBookIsLended() {
        return bookIsLended;
    }

    /**
     * @param bookIsLended the bookIsLended to set
     */
    public void setBookIsLended(Boolean bookIsLended) {
        this.bookIsLended = bookIsLended;
    }
    
    @PostLoad
    @PostPersist
    @PostUpdate
    public void calculateNbOfDaysToReturn() {
        Calendar lendingD = new GregorianCalendar();
        lendingD.setTime(lendingDate);
        Calendar now = new GregorianCalendar();
        now.setTime(new Date());
        nbOfDaysToReturn = 30 - (now.get(Calendar.DAY_OF_YEAR) - lendingD.get(Calendar.DAY_OF_YEAR)); //30 days as default
    }
    
}
