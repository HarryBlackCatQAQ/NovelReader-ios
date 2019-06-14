package com.bnuz.novelreader.controller;

import com.bnuz.novelreader.model.Book;
import com.bnuz.novelreader.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping(value = "/searching")
    public List<Book> getBookList(String content){
        System.out.println("你传给我了:" + content);
        return null;
    }
}