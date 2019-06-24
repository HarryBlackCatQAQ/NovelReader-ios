package com.bnuz.novelreader.service;

import com.bnuz.novelreader.model.Book;
import com.bnuz.novelreader.model.Chapter;

import java.util.List;

public interface ChapterService {
    public List<Chapter> getChapters(Book book);

    public Chapter getChapterContent(Chapter chapter);
}
