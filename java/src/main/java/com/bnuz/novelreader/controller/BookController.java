package com.bnuz.novelreader.controller;

import com.bnuz.novelreader.model.Book;
import com.bnuz.novelreader.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping(value = "/test")
    public List<Book> test(String content){
        System.out.println("你传给我了:" + content);
        List<Book> list = new LinkedList<Book>();
        for(int i = 0;i < 10;i++){
            Book book = new Book();
            book.setName("" + i);
            book.setImageUrl("" + i);
            book.setAuthor("" + i);
            list.add(book);
        }
        return list;
    }

    @GetMapping(value = "/getTest")
    public String getTest(String content){
        System.out.println("你传给我了:" + content);
        return "你成功调用了get方法";
    }
}