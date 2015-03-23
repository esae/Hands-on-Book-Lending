/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.fhnw.esae.booklending.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.fhnw.esae.booklending.business.BookEJB;
import ch.fhnw.esae.booklending.business.BookLendingEJB;
import ch.fhnw.esae.booklending.business.CustomerEJB;
import ch.fhnw.esae.booklending.domain.Address;
import ch.fhnw.esae.booklending.domain.Book;
import ch.fhnw.esae.booklending.domain.BookLending;
import ch.fhnw.esae.booklending.domain.Customer;

/**
 *
 * @author andreas.martin
 */
@RunWith(Arquillian.class)
public class BookLendingEJBTest {
    
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
            .addPackage(BookEJB.class.getPackage())
            .addPackage(Book.class.getPackage())
            .addAsResource("META-INF/persistence.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    @EJB
    private CustomerEJB customerEJB;
    @EJB
    private BookEJB bookEJB;
    @EJB
    private BookLendingEJB bookLendingEJB;
    private Customer customer = null;
    private Book book = null;
    
    @BeforeClass
    public static void initContainer() throws Exception {
        //@BeforeClass annotated methods get executed outside of the container in Arquillian 1.0 (missing implementation)!!!!
    }

    @AfterClass
    public static void closeContainer() throws Exception {
        //@AfterClass annotated methods get executed outside of the container in Arquillian 1.0 (missing implementation)!!!!
    }

    @Before
    public void setUp() {
        Address address = new Address("Hauptstrasse", "Olten", "4600", "Switzerland", "Business");
        customer = new Customer();
        customer.setFirstName("Andreas");
        customer.setLastName("Martin");
        customer.setEmail("andreas.martin@fhnw.ch");
        customer.setDateOfBirth(new GregorianCalendar(1983, 04, 21).getTime());
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        customer.setAddress(addresses);
        book = new Book();
        book.setTitle("The Java Galaxy");
        book.setPrice(12.5F);
        book.setDescription("Science fiction comedy book");
        book.setIsbn("1-84023-742-2");
        book.setNbOfPage(354);
        book.setIllustrations(false);
        customerEJB.createCustomer(customer);
        bookEJB.createBook(book);
    }

    @After
    public void tearDown() {
        bookEJB.deleteBook(book);
        customerEJB.deleteCustomer(customer);
    }

    /**
     * Test of lendBook method, of class BookLendingEJB.
     */
    @Test
    public void testLendBook() throws Exception {
        BookLending bookLending = bookLendingEJB.lendBook(book, customer);
        assertNotNull("ID should not be null", bookLending.getId());
        assertNotNull("ID should not be null", bookLending.getCustomer().getAge());
        bookLendingEJB.deleteBookLending(bookLending);
    }

    /**
     * Test of returnBook method, of class BookLendingEJB.
     */
    @Test
    public void testReturnBook() throws Exception {
        BookLending bookLendingInit = bookLendingEJB.lendBook(book, customer);
        BookLending bookLending = bookLendingEJB.returnBook(book, customer);
        assertNotNull("ID should not be null", bookLending.getId());
        bookLendingEJB.deleteBookLending(bookLendingInit);
    }

    /**
     * Test of showAllLendings method, of class BookLendingEJB.
     */
    @Test
    public void testShowAllLendings() throws Exception {
        BookLending bookLendingInit = bookLendingEJB.lendBook(book, customer);
        List<BookLending> bookLendings = bookLendingEJB.showAllLendings(customer);
        assertNotSame(0, bookLendings.size());
        bookLendingEJB.deleteBookLending(bookLendingInit);
    }

    /**
     * Test of showAllLendings method, of class BookLendingEJB.
     */
    @Test
    public void testShowAllNotLendedBooks() throws Exception {
        List<Book> bookList = bookLendingEJB.showAllNotLendedBooks();
        assertNotSame(0, bookList.size());
    }
    
}
