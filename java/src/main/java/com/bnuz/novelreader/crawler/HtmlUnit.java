package com.bnuz.novelreader.crawler;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HtmlUnit {
    private WebClient webClient;

    public HtmlUnit(){
        webClient = new WebClient(BrowserVersion.CHROME);

        // 取消 JS 支持
        webClient.getOptions().setJavaScriptEnabled(false);
        // 取消 CSS 支持
        webClient.getOptions().setCssEnabled(false);
        //设置超时
        webClient.getOptions().setTimeout(Integer.MAX_VALUE);
    }

    public HtmlPage getHtmlPage(String url){

        // 获取指定网页实体
        HtmlPage page = null;
        try {
            page = (HtmlPage) webClient.getPage(url);
            webClient.waitForBackgroundJavaScript(10000);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return page;
    }

    public void closeWebClient(){
        this.webClient.close();
    }

    public WebClient getWebClient() {
        return webClient;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
}