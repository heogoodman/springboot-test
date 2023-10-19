package com.basic.service;

import com.basic.dto.BookReqDTO;
import com.basic.dto.BookResDTO;
import com.basic.entity.Book;
import com.basic.exception.BusinessException;
import com.basic.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public BookResDTO saveBook(BookReqDTO bookReqDTO) {
        Book book = modelMapper.map(bookReqDTO,Book.class); // entity 타입으로 변환 db와직접적연결
        Book savedBook = bookRepository.save(book); // 그이후 레파지토리에서 엔티티객체를 넣어준다.
        return modelMapper.map(savedBook,BookResDTO.class); //레파지토리.save도 스프링에서 제공해줌
    }

    @Transactional(readOnly = true)
    public List<BookResDTO> getBooks() {
        List<Book> bookList = bookRepository.findAll();
        List<BookResDTO> bookResDTOList = bookList.stream()
                .map(book -> modelMapper.map(book,BookResDTO.class))
                .collect(toList());    //Entity를 Res로 그리고 다시 리스트로
        return bookResDTOList;
    }

    @Transactional(readOnly = true)
    public BookResDTO getBookById(Long id) {
        Book bookEntity = bookRepository.findById(id) // findById메소드자체리턴값이 엔티티임
                .orElseThrow(() -> new BusinessException(id + " Book Not Found", HttpStatus.NOT_FOUND));
        BookResDTO bookResDto = modelMapper.map(bookEntity,BookResDTO.class);
        return bookResDto;                  //Entity를 Res로
    }

    @Transactional(readOnly = true)
    public BookResDTO getBookByIsbn(String isbn) {
        Book bookEntity = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException(isbn + " Book Not Found", HttpStatus.NOT_FOUND));
        BookResDTO bookResDto = modelMapper.map(bookEntity,BookResDTO.class);
        return bookResDto;
    }

    public BookResDTO updateBook(Long id, BookReqDTO bookReqDTO) {
        Book existBook = bookRepository.findById(id)
                .orElseThrow(() ->
                        new BusinessException(id + " Book Not Found",HttpStatus.NOT_FOUND));
        //Dirty Checking 변경감지를 하므로 setter method만 호출해도 update query가 실행이 된다.
        existBook.setTitle(bookReqDTO.getTitle());
        existBook.setGenre(bookReqDTO.getGenre());
        existBook.setIsbn(bookReqDTO.getIsbn());
        return modelMapper.map(existBook, BookResDTO.class);
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new BusinessException(id + " Book Not Found",HttpStatus.NOT_FOUND));
        bookRepository.delete(book);
    }

}
