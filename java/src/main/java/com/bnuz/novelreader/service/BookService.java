package com.bnuz.novelreader.service;

import com.bnuz.novelreader.model.Book;

import java.util.List;

public interface BookService {
    public List<Book> getBook(String content);
}
