package com.basic.controller;

import com.basic.entity.Book;
import com.basic.exception.BusinessException;
import com.basic.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookBasicRestController {

    @Autowired
    private BookRepository bookRepository;
    // 레파지토리 인젝션받기

    @PostMapping
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @GetMapping
    public List<Book> getBooks() {
        System.out.println("Test");
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        Book book = optionalBook.orElseThrow(() -> new BusinessException("Book NOT FOUND",
                HttpStatus.NOT_FOUND));
            return book;
    }

    @GetMapping("/isbn/{isbn}")
    public Book getBookByEmail(@PathVariable String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException("요청하신 isbn에 해당하는 유저가 없습니다",
                        HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book bookDetail) {
        Book customer = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));
        customer.setTitle(bookDetail.getTitle());
        Book updatedCustomer = bookRepository.save(customer);
        return updatedCustomer;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));

        bookRepository.delete(book);

        return ResponseEntity.ok(id + " Book가 삭제 되었습니다.");
    }

}
