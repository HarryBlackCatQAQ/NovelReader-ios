package com.bnuz.novelreader.service.impl;

import com.bnuz.novelreader.model.Book;
import com.bnuz.novelreader.model.Chapter;
import com.bnuz.novelreader.service.ChapterService;
import com.bnuz.novelreader.service.WebCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ChapterServiceImpl")
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    @Qualifier("WebCrawlerServiceImpl")
    private WebCrawlerService webCrawlerService;

    @Override
    public List<Chapter> getChapters(Book book) {
        return webCrawlerService.getChapters(book);
    }

    @Override
    public Chapter getChapterContent(Chapter chapter) {
        return webCrawlerService.getChapterContent(chapter);
    }


}