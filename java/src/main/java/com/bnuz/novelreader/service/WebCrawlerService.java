package com.bnuz.novelreader.service;

import com.bnuz.novelreader.model.Book;

import java.util.List;

public interface WebCrawlerService {
    public List<Book> getBook(String content);
}
