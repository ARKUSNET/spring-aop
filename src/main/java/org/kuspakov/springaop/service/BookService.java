package org.kuspakov.springaop.service;

import org.kuspakov.springaop.entity.Book;
import org.kuspakov.springaop.repository.BookRepository;
import org.kuspakov.springaop.util.CustomResponse;
import org.kuspakov.springaop.util.CustomStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public CustomResponse<Book> getAll() {
        List<Book> books = bookRepository.findAll();
        return new CustomResponse<>(books, CustomStatus.SUCCESS);
    }

    public CustomResponse<Book> getBookByTitle(String title) {
        Book book = bookRepository.findBookByTitle(title).orElseThrow(NoSuchElementException::new);
        return new CustomResponse<>(Collections.singletonList(book), CustomStatus.SUCCESS);
    }


    public CustomResponse<Book> addBook(Book book) {
        Book newBook = bookRepository.save(book);
        return new CustomResponse<>(Collections.singletonList(newBook), CustomStatus.SUCCESS);
    }
}