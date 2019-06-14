package com.bnuz.novelreader.service;


import com.bnuz.novelreader.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("BookServiceImpl")
public class BookServiceImpl implements BookService{
    @Override
    public List<Book> getBook(String content) {
        return null;
    }
}