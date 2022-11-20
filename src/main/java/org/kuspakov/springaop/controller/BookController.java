package org.kuspakov.springaop.controller;

import org.kuspakov.springaop.dto.BookDto;
import org.kuspakov.springaop.entity.Book;
import org.kuspakov.springaop.service.BookService;
import org.kuspakov.springaop.util.CustomResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public CustomResponse<Book> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/book/{title}")
    public CustomResponse<Book> getBookByTitle(@PathVariable("title") String title) {
        return bookService.getBookByTitle(title);
    }

    @PostMapping("/books")
    public CustomResponse<Book> addBook(@RequestBody BookDto book) {
        Book newBook = new Book();
        newBook.setAuthor(book.getAuthor());
        newBook.setTitle(book.getTitle());
        return bookService.addBook(newBook);
    }
}
