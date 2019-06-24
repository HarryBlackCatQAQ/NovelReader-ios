package com.bnuz.novelreader.service.impl;

import com.bnuz.novelreader.model.Book;
import com.bnuz.novelreader.model.Chapter;
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
        System.out.println("Vodtw size:" + bookList.size());

        setBookListIndex(bookList);

        return bookList;
    }

    private void setBookListIndex(List<Book> bookList){
        for(int i = 0;i < bookList.size();i++){
            bookList.get(i).setIdx(i + 1);
        }
    }

    public List<Chapter> getChapters(Book book){
        List<Chapter> chapterList = new LinkedList<Chapter>();
        chapterList.addAll(vodtwWebCrawlerService.getChapters(book));

        System.out.println("Chapters:" + chapterList.size());

        return chapterList;
    }

    @Override
    public Chapter getChapterContent(Chapter chapter) {
        return vodtwWebCrawlerService.getChapterContent(chapter);
    }
}