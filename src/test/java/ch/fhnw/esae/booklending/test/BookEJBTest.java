/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.fhnw.esae.booklending.test;

import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.fhnw.esae.booklending.business.BookEJB;
import ch.fhnw.esae.booklending.domain.Book;
import static org.junit.Assert.*;

/**
 *
 * @author andreas.martin
 */
@RunWith(Arquillian.class)
public class BookEJBTest {
    
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
            .addPackage(BookEJB.class.getPackage())
            .addPackage(Book.class.getPackage())
            .addAsResource("META-INF/persistence.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    @EJB
    BookEJB bookEJB;
    
    @Test
    public void shouldCreateABook() throws Exception {
        Book book = new Book();
        book.setTitle("The Hitchhiker's Guide to the Galaxy");
        book.setPrice(12.5F);
        book.setDescription("Science fiction comedy book");
        book.setIsbn("1-84023-742-2");
        book.setNbOfPage(354);
        book.setIllustrations(false);
        book = bookEJB.createBook(book);
        assertNotNull("ID should not be null", book.getId());
        List<Book> books = bookEJB.findBooks();
        assertNotNull(books);
    }
    
}
