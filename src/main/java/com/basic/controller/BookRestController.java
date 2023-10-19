package com.basic.controller;

import com.basic.dto.BookReqDTO;
import com.basic.dto.BookResDTO;
import com.basic.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    @PostMapping
    public BookResDTO saveBook(@RequestBody BookReqDTO bookReqDTO){
        return bookService.saveBook(bookReqDTO); //객체로날라오기때문에 @PathVariable불가임
    }


    @GetMapping
    public List<BookResDTO> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/{id}")
    public BookResDTO getBookById(@PathVariable Long id) { //여기의메소드이름과 Service의메소드이름은 우리가지어준것
        return bookService.getBookById(id);   //서비스안의 findById(id) 는 부트에서 제공하는 메소드
    }

    @GetMapping("/isbn/{isbn}")  //위에 id와 구분못함 타입이달라도 안됨
    public BookResDTO getBookByIsbn(@PathVariable String isbn) {
        return bookService.getBookByIsbn(isbn);
    }

    @PutMapping("/{id}")
    public BookResDTO updateBook(@PathVariable Long id, @RequestBody BookReqDTO bookReqDTO){
        return bookService.updateBook(id,bookReqDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(id + " Book이 삭제처리 되었습니다.");
    }
    
}
