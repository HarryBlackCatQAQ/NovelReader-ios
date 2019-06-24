package com.bnuz.novelreader.controller;

import com.bnuz.novelreader.model.Book;
import com.bnuz.novelreader.model.Chapter;
import com.bnuz.novelreader.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @PostMapping(value = "/read")
    public List<Chapter> getChapterList(@RequestBody Book book){
        System.out.println("ok!");
        book.print();
        return chapterService.getChapters(book);
    }

    @PostMapping(value = "/readContent")
    public Chapter getChapterContent(@RequestBody Chapter chapter){
        System.out.println("Chapter ok!");
        return chapterService.getChapterContent(chapter);
    }
}