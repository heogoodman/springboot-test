package com.basic.controller;

import com.basic.dto.BookReqDTO;
import com.basic.dto.BookResDTO;
import com.basic.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/bookspage")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/index")
    public ModelAndView index() {
        List<BookResDTO> bookResDTOList = bookService.getBooks();
        return new ModelAndView("index","books",bookResDTOList); // 타임리프의인덱스라는html파일로보낸다
    }

    @GetMapping("/info/{id}")
    public String info(@PathVariable Long id,Model model){
        BookResDTO bookResDTO = bookService.getBookById(id);
        model.addAttribute("book",bookResDTO);
        return "info-book";
    }

    @GetMapping("/signup")
    public String showSignUpForm(BookReqDTO book) {
        return "add-book";
    }

    @PostMapping("/addbook")
    public String addBook(@Valid BookReqDTO book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-book";
        }
        bookService.saveBook(book);
        return "redirect:/bookspage/index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model){
        BookResDTO bookResDTO = bookService.getBookById(id);
        model.addAttribute("book",bookResDTO);
        return "update-book";
    }

    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable("id") long id, @Valid BookReqDTO book,
                                 BindingResult result, Model model) { //id를써야해서 폼을씀
        if (result.hasErrors()) {
            System.out.println(">>> hasErros book "  + book);
            model.addAttribute("book",book);

            return "update-book";

        }
//        bookService.updateCustomerForm(book);
        bookService.updateBook(id,book);

        return "redirect:/bookspage/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") long id) {
        bookService.deleteBook(id);
        return "redirect:/bookspage/index";
    }

}
