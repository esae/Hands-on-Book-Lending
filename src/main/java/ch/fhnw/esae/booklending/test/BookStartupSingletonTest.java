/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fhnw.esae.booklending.test;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import ch.fhnw.esae.booklending.business.BookEJB;
import ch.fhnw.esae.booklending.domain.Book;
import static org.junit.Assert.assertNotNull;

/**
 *
 * @author andreas.martin
 */
@Singleton
@Startup
public class BookStartupSingletonTest {
    
    @EJB
    private BookEJB bookEJB;

    @PostConstruct
    void init() {
        try {
            shouldCreateABook();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void shouldCreateABook() throws Exception {
        Book book = new Book();
        book.setTitle("HelloNew");
        book.setPrice(12.5F);
        book.setDescription("Science fiction comedy book");
        book.setIsbn("1-84023-742-2");
        book.setNbOfPage(354);
        book.setIllustrations(false);
        book = bookEJB.createBook(book);
        assertNotNull("ID should not be null", book.getId());
        System.out.println("ID should not be null: " + book.getId());
        List<Book> books = bookEJB.findBooks();
        assertNotNull(books);
    }
}
