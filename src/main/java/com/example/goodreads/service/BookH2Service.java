package com.example.goodreads.service;

import com.example.goodreads.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.*;
import com.example.goodreads.model.*;

@Service
public class BookH2Service implements BookRepository {
    @Autowired
    public JdbcTemplate db;

    @Override
    public ArrayList<Book> getBooks() {
        List<Book> storedBooks = db.query("select * from book", new BookRowMapper());
        ArrayList<Book> books = new ArrayList<>(storedBooks);
        return books;

    }

    @Override
    public Book getBookById(int bookId) {
        Book book = (Book) db.queryForObject("select * from book where id=?", new BookRowMapper(), bookId);
        return book;

    }

    @Override
    public Book addBook(Book book) {
        db.update("insert into book(name,imageUrl) values(?,?)", book.getName(), book.getImageUrl());
        Book savedBook = (Book) db.queryForObject("select * from book where name=? and imageUrl=?",
                new BookRowMapper(), book.getName(),
                book.getImageUrl());
        return savedBook;

    }

    @Override

    public Book updateBook(int bookId, Book book) {
        if (book.getName() != null) {
            db.update("UPDATE book SET name=? where id=?", book.getName(), bookId);
        }
        if (book.getImageUrl() != null) {
            db.update("UPDATE book SET imageUrl=? where id=?", book.getImageUrl(), bookId);
        }
        return getBookById(bookId);
    }

    @Override

    public void deleteBook(int bookId) {

    }

}