package com.bnuz.novelreader.service.impl;

import com.bnuz.novelreader.model.Book;
import com.bnuz.novelreader.service.WebCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service("WebCrawlerServiceImpl")
public class WebCrawlerServiceImpl implements WebCrawlerService {

    @Autowired
    private VodtwWebCrawlerServiceImpl vodtwWebCrawlerService;

    @Override
    public List<Book> getBook(String content) {
        List<Book> bookList = new LinkedList<Book>();

        bookList.addAll(vodtwWebCrawlerService.getBook(content));

        setBookListIndex(bookList);

        System.out.println("size:" + bookList.size());
        return bookList;
    }

    private void setBookListIndex(List<Book> bookList){
        for(int i = 0;i < bookList.size();i++){
            bookList.get(i).setIdx(i + 1);
        }
    }
}