/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fhnw.esae.booklending.business;

import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ch.fhnw.esae.booklending.domain.Book;
import ch.fhnw.esae.booklending.domain.BookLending;
import ch.fhnw.esae.booklending.domain.Customer;




/**
 *
 * @author andreas.martin
 */
@Stateless
public class BookLendingEJB {
    
    @PersistenceContext(unitName = "primary")
    private EntityManager em;


    public BookLending lendBook(Book book, Customer customer) {
        BookLending bookLending = new BookLending();
        bookLending.setBook(book);
        bookLending.setCustomer(customer);
        bookLending.setLendingDate(new GregorianCalendar().getTime());
        bookLending.setBookIsLended(true);
        em.persist(bookLending);
        return bookLending;
    }


    public BookLending returnBook(Book book, Customer customer) {      
    	TypedQuery<BookLending> query = em.createNamedQuery("findLendingByCustomerAndBookId", BookLending.class);
        query.setParameter("book", book);
        query.setParameter("customer", customer);
        List<BookLending> bookLendings = query.getResultList();
        if(bookLendings.isEmpty())
            return null;
        BookLending bookLending = (BookLending) bookLendings.get(0);
        bookLending.setBookIsLended(false);
        bookLending.setReturnDate(new GregorianCalendar().getTime());
        em.persist(bookLending);
        return bookLending;
    }


    public List<BookLending> showAllLendings(Customer customer) {
    	TypedQuery<BookLending> query = em.createNamedQuery("findLendingByCustomer", BookLending.class);
        query.setParameter("customer", customer);
        List<BookLending> bookLendings = query.getResultList();
        if(bookLendings.isEmpty())
            return null;
        return bookLendings;
    }
    

    public List<Book> showAllNotLendedBooks()
    {
    	TypedQuery<Book> query = em.createNamedQuery("findNotLendedBooks", Book.class);
        List<Book> book = query.getResultList();
        if(book.isEmpty())
            return null;
        return book;
    }
    

    public void deleteBookLending(BookLending bookLending) {
        em.remove(em.merge(bookLending));
    }
    
}
