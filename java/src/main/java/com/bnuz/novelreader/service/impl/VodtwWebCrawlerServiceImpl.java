package com.bnuz.novelreader.service.impl;

import com.bnuz.novelreader.crawler.HtmlUnit;
import com.bnuz.novelreader.model.Book;
import com.bnuz.novelreader.model.Chapter;
import com.bnuz.novelreader.service.WebCrawlerService;
import com.bnuz.novelreader.util.ContentStringUtil;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("VodtwWebCrawlerServiceImpl")
public class VodtwWebCrawlerServiceImpl implements WebCrawlerService {
    private HtmlUnit htmlUnit;

    private String vodtwUrl = "https://www.vodtw.com";

    @Override
    public List<Book> getBook(String content) {
        List<Book> BookList = null;
        try {

            htmlUnit = new HtmlUnit();

            System.out.println("here!1");

            HtmlPage htmlPage = htmlUnit.getHtmlPage(vodtwUrl);

            System.out.println("here!2");

            // 获取搜索输入框
            HtmlInput input = (HtmlInput) htmlPage.getByXPath("//*[@id=\"searchwarpper\"]/form/ul/li[2]/input").get(0);
            input.setValueAttribute(content);

            // 获取搜索按钮
            HtmlInput btn = (HtmlInput) htmlPage.getByXPath("//*[@id=\"Image12\"]").get(0);
            htmlPage.cleanUp();

            // “点击” 搜索
            HtmlPage page2 = btn.click();

            System.out.println("here!3");

            BookList = AnalysisOfTheBook(page2);
            page2.cleanUp();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return BookList;
    }


    private List<Book> AnalysisOfTheBook(HtmlPage page){
        List<Book> BookList = new LinkedList<Book>();

        String urls[] = getBookUrl(page.asXml());

        System.out.println("here!4");

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

        System.out.println("here!5");

        for(int i = 0;i < urls.length;i++){
            final int idx = i;

//            HtmlUnit htmlUnit1 = new HtmlUnit();

            HtmlPage page1 = htmlUnit.getHtmlPage(urls[i]);
//                System.out.println(urls[i]);

            fixedThreadPool.execute(new Thread(()->{
                Document document = Jsoup.parse(page1.asXml());

                Element element2 = document.select("body > div.wrapper_list > div.bookinfo.clearfix > div.bookpic > img").get(0);
                Element element1 = document.select("body > div.wrapper_list > div.bookinfo.clearfix > div.bookdetail > ul > li:nth-child(1) > h2").get(0);
//                System.out.println(element2.attr("src"));
//                System.out.println(element2.attr("alt"));
//                System.out.println(element1.text());

                Book book = new Book();
                String tp = element1.text();
                tp = tp.replaceAll("作 者：","");
                book.setAuthor(tp);
                book.setImageUrl(element2.attr("src"));
                book.setName(element2.attr("alt"));
                book.setUrl(urls[idx]);
                book.setHtmlContent(page1.asXml());
                book.setWebType("vodtw");
                book.setIdx(idx);
                page1.cleanUp();

//                System.out.println(book);



                BookList.add(book);
            }));

        }

        fixedThreadPool.shutdown();
        htmlUnit.closeWebClient();
        return BookList;
    }

    private String[] getBookUrl(String html){
        Document document = Jsoup.parse(html);
        Elements elements = document.select("#CListTitle > a:nth-child(1)");

        String urls[] = new String[elements.size()];

        for(int i = 0;i < elements.size();i++){
            urls[i] = vodtwUrl + elements.get(i).attr("href");
        }

        return urls;
    }


    public List<Chapter> getChapters(Book book){
        List<Chapter> ChapterList = new LinkedList<Chapter>();

        Document document = Jsoup.parse(book.getHtmlContent());
        Elements elements = document.select("body > div.wrapper_list > div.insert_list > dl > dd > ul > li > a");

        int counter = 1;
        for(Element element : elements){
//            System.out.println("第" + (counter) + "章");
            int idx = book.getUrl().lastIndexOf('/');
            Chapter chapter = new Chapter();
            chapter.setUrl(book.getUrl().substring(0,idx) + "/" + element.attr("href"));
            chapter.setIdx(counter);
            chapter.setTitle(element.text());
            chapter.setDownload(false);
            chapter.setWebType(book.getWebType());

            ChapterList.add(chapter);

            counter++;

        }

        return ChapterList;
    }

    @Override
    public Chapter getChapterContent(Chapter chapter) {
        Document document = Jsoup.parse(getPage(chapter));

        Element element1 = document.select("#BookText").get(0);

        chapter.setContent(ContentStringUtil.contentReplace(element1.text()));
        chapter.setDownload(true);
//        System.out.println(ContentStringUtil.contentReplace(element1.text()));
        return chapter;
    }

    private String getPage(Chapter chapter){
        HtmlUnit htmlUnit = new HtmlUnit();
        HtmlPage page = htmlUnit.getHtmlPage(chapter.getUrl());

        htmlUnit.closeWebClient();

        return page.asXml();
    }
}