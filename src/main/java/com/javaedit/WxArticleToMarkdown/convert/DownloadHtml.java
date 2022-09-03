package com.javaedit.WxArticleToMarkdown.convert;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.javaedit.WxArticleToMarkdown.dto.WxArticle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author wjw
 * @description: 下载html
 * @title: DownloadHtml
 * @date 2022/9/3 16:42
 */
public class DownloadHtml implements ArticleHandler {

    @Override
    public String handleArticle(WxArticle article) {
        if (StrUtil.isBlank(article.getContentUrl())) {
            throw new RuntimeException("请输入文章url");
        }

        String html = HttpUtil.get(article.getContentUrl());
        String title = "无标题";

        Document doc = Jsoup.parse(html);
        Elements h1Eles = doc.getElementsByTag("h1");
        if (!h1Eles.isEmpty()) {
            title = h1Eles.get(0).text();
        }

        article.setTitle(title);
        article.setContent(html);

        System.out.println("DownloadHtml-html源码下载成功");
        return html;
    }
}
