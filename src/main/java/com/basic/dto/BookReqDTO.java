package com.basic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookReqDTO {

    private Long id;

    @NotEmpty(message = "Title은 필수 입력항목입니다2.")
    private String title;

    @NotEmpty(message = "Author은 필수 입력항목입니다2.")
    private String author;

    @NotBlank(message = "Isbn은 필수 입력항목입니다2.")
    private String isbn;

    @NotEmpty(message = "Genre은 필수 입력항목입니다2.")
    private String genre;



}
