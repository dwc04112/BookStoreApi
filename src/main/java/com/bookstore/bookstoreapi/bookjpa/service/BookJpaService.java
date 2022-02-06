package com.bookstore.bookstoreapi.bookjpa.service;


import com.bookstore.bookstoreapi.bookjpa.dto.BookDTO;
import com.bookstore.bookstoreapi.bookjpa.model.Book;
import com.bookstore.bookstoreapi.bookjpa.model.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Slf4j
public class BookJpaService {

    final BookRepository bookRepository;

    //리스트 가져오기
    public List<Book> getBookList() {
        return bookRepository.findBookByIsDel("N");
    }

    //지정한 책만 가져오기
    public Book getBookId(long bid) {
        Optional<Book> bookData = bookRepository.findBookByBidAndIsDel(bid, "N");
        return bookData.orElseThrow(() -> new RuntimeException("no data"));
    }

    //책 등록
    public Book postBook(BookDTO bookDTO) {
        long newBookBidValue = this.getNewBookBidValue(bookRepository);
        Book postData = Book.builder()
                .bid(newBookBidValue)
                .mid(1)
                .author(bookDTO.getAuthor())
                .subject(bookDTO.getSubject())
                .page(bookDTO.getPage())
                .price(bookDTO.getPrice())
                .published_date(bookDTO.getPublished_date())
                .detail_tag(bookDTO.getDetail_tag())
                .tag(bookDTO.getTag())
                .keyword(bookDTO.getKeyword())
                .isDel("N")
                .build();
        return bookRepository.save(postData);
    }

    //책 id를 정하는 로직
    private long getNewBookBidValue(BookRepository bookRepository) {
        long result;
        Book boardOfMaxId = bookRepository.findTopByOrderByBidDesc();
        if (boardOfMaxId == null) {
            result = 1;
            log.debug("no board data, maxId is 1");
        } else {
            result = boardOfMaxId.getBid() + 1;
            log.debug("maxIdFromBoard=" + boardOfMaxId.getBid());
        }
        log.debug("newBoardIdValue=" + result);
        return result;
    }

    //책 삭제 ( isDel "N" -> "Y" )
    public Book updateIsDelBookById(BookDTO bookDTO) {
        Optional<Book> bookData = bookRepository.findBookByBidAndIsDel(bookDTO.getBid(), "N");
        Book data = bookData.orElseThrow(() -> new RuntimeException("no data"));
        //북 오너 정보와 매치 필요
        return null;
    }


}
