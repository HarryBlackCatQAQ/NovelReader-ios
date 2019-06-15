package com.bnuz.novelreader.service.impl;

import com.bnuz.novelreader.crawler.HtmlUnit;
import com.bnuz.novelreader.model.Book;
import com.bnuz.novelreader.service.WebCrawlerService;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("VodtwWebCrawlerServiceImpl")
public class VodtwWebCrawlerServiceImpl implements WebCrawlerService {

    @Autowired
    private HtmlUnit htmlUnit;

    private String vodtwUrl = "https://www.vodtw.com";

    @Override
    public List<Book> getBook(String content) {
        List<Book> BookList = null;
        try {

            HtmlPage htmlPage = htmlUnit.getHtmlPage(vodtwUrl);

            // 获取搜索输入框
            HtmlInput input = (HtmlInput) htmlPage.getByXPath("//*[@id=\"searchwarpper\"]/form/ul/li[2]/input").get(0);

            input.setValueAttribute(content);

            // 获取搜索按钮
            HtmlInput btn = (HtmlInput) htmlPage.getByXPath("//*[@id=\"Image12\"]").get(0);
            htmlPage.cleanUp();

            // “点击” 搜索
            HtmlPage page2 = btn.click();

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

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

        for(int i = 0;i < urls.length;i++){
            final int idx = i;

//            HtmlUnit htmlUnit1 = new HtmlUnit();

            HtmlPage page1 = htmlUnit.getHtmlPage(urls[i]);
//                System.out.println(urls[i]);

            fixedThreadPool.execute(new Thread(()->{
                Document document = Jsoup.parse(page1.asXml());
                page1.cleanUp();

                Element element2 = document.select("body > div.wrapper_list > div.bookinfo.clearfix > div.bookpic > img").get(0);
                Element element1 = document.select("body > div.wrapper_list > div.bookinfo.clearfix > div.bookdetail > ul > li:nth-child(1) > h2").get(0);
//                System.out.println(element2.attr("src"));
//                System.out.println(element2.attr("alt"));
//                System.out.println(element1.text());

                Book book = new Book();
                book.setAuthor(element1.text());
                book.setImageUrl(element2.attr("src"));
                book.setName(element2.attr("alt"));
                book.setUrl(urls[idx]);
                book.setWebType("vodtw");
                book.setIdx(idx);

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
}