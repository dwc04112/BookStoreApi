package com.bookstore.bookstoreapi.bookjpa.service;


import com.bookstore.bookstoreapi.bookjpa.dto.BookDTO;
import com.bookstore.bookstoreapi.bookjpa.model.Book;
import com.bookstore.bookstoreapi.bookjpa.model.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@Slf4j
public class BookJpaService {

    final BookRepository boardRepository;

    //책 등록
    public Book postBook(BookDTO bookDTO) {
        long newBookBidValue = this.getNewBookBidValue(boardRepository);
        Book postData = Book.builder()
                .bid(newBookBidValue)
                .author(bookDTO.getAuthor())
                .subject(bookDTO.getSubject())
                .price(bookDTO.getPrice())
                .published_date(bookDTO.getPublished_date())
                .detail_tag(bookDTO.getDetail_tag())
                .tag(bookDTO.getTag())
                .keyword(bookDTO.getKeyword())
                .isDel("N")
                .build();
        return boardRepository.save(postData);
    }
    //책 id를 정하는 로직
    private long getNewBookBidValue(BookRepository bookRepository) {
        long result;
        Book boardOfMaxId = bookRepository.findTopByOrderByBidDesc();
        if(boardOfMaxId == null) {
            result = 1;
            log.debug("no board data, maxId is 1");
        } else {
            result = boardOfMaxId.getBid() + 1;
            log.debug("maxIdFromBoard="+boardOfMaxId.getBid());
        }
        log.debug("newBoardIdValue="+result);
        return result;
    }
}
