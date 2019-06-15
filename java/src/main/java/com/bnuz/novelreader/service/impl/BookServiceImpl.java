package com.bnuz.novelreader.service.impl;

import com.bnuz.novelreader.model.Book;
import com.bnuz.novelreader.service.BookService;
import com.bnuz.novelreader.service.WebCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("BookServiceImpl")
public class BookServiceImpl implements BookService {

    @Autowired
    @Qualifier("WebCrawlerServiceImpl")
    private WebCrawlerService webCrawlerService;

    @Override
    public List<Book> getBook(String content) {
        if(content == null){
            return null;
        }
        else{
            return webCrawlerService.getBook(content);
        }

    }
}