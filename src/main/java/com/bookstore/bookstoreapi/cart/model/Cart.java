package com.bookstore.bookstoreapi.cart.model;

import com.bookstore.bookstoreapi.bookjpa.model.Book;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Getter
@Table(name = "cart")
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Cart implements Serializable {


    @Id
    @Column(name = "cartId")
    private long cartId;

    @Column(unique = true)
    private long mid;

    @ManyToOne(fetch=FetchType.LAZY) // (1)
    @JoinColumn(name="bid", insertable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Book book;

    @Column(unique = true)
    private int bookCount;
    private String isDel;

    public void updateCount(int bookCount){
        this.bookCount = bookCount;
    }

    public Cart(Long cartId,
                Long mid,
                Book book,
                int bookCount,
                String isDel
                ){
        this.cartId = cartId;
        this.mid = mid;
        this.book = book;
        this.bookCount = bookCount;
        this.isDel = isDel;
    }
}
