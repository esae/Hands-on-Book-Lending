/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fhnw.esae.booklending.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author andreas.martin
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "findAllBooks", query = "SELECT b FROM Book b"),
    @NamedQuery(name = "findAllBooksById", query = "SELECT b FROM Book b WHERE b.id = :id")
})
@Table(name="BOOK_HANDS_ON_BOOK_LENDING")
public class Book implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4398413989452323780L;
	@Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String title;
    private Float price;
    @Column(length = 2000)
    private String description;
    private String isbn;
    private Integer nbOfPage;
    private Boolean illustrations;

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
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the price
     */
    public Float getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(Float price) {
        this.price = price;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @param isbn the isbn to set
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * @return the nbOfPage
     */
    public Integer getNbOfPage() {
        return nbOfPage;
    }

    /**
     * @param nbOfPage the nbOfPage to set
     */
    public void setNbOfPage(Integer nbOfPage) {
        this.nbOfPage = nbOfPage;
    }

    /**
     * @return the illustrations
     */
    public Boolean getIllustrations() {
        return illustrations;
    }

    /**
     * @param illustrations the illustrations to set
     */
    public void setIllustrations(Boolean illustrations) {
        this.illustrations = illustrations;
    }
}
