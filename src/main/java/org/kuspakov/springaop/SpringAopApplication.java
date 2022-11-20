package org.kuspakov.springaop;

import org.kuspakov.springaop.entity.Book;
import org.kuspakov.springaop.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class SpringAopApplication implements CommandLineRunner {

    @Resource
    private BookRepository bookRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringAopApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Book book1 = new Book("Война иа Мир", "Л.Н. Толстой");
        Book book2 = new Book("Капитанская дочка", "А.С. Пушкин");
        bookRepository.save(book1);
        bookRepository.save(book2);
    }
}
