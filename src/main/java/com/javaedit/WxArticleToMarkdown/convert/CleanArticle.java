package com.javaedit.WxArticleToMarkdown.convert;

import cn.hutool.core.util.StrUtil;
import com.javaedit.WxArticleToMarkdown.dto.WxArticle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Safelist;

/**
 * @author wjw
 * @description: 清洗文章内容
 * @title: CleanArticle
 * @date 2022/9/2 16:42
 */
public class CleanArticle implements ArticleHandler {

    @Override
    public String handleArticle(WxArticle article) {
        // 是否有要处理
        String content = article.getContent();
        if (StrUtil.isEmpty(content)) {
            return null;
        }

        Safelist whitelist = Safelist.basicWithImages()
                .addTags("div",
                        "h1", "h2", "h3", "h4", "h5", "h6",
                        "table", "tbody", "td", "tfoot", "th", "thead", "tr",
                        "hr",
                        "span", "font")
                .addAttributes("th", "colspan", "align", "style")
                .addAttributes("td", "colspan", "align", "style")
                .addAttributes(":all", "title", "style")
                .addAttributes("img", "data-src", "data-type");
        Cleaner cleaner = new Cleaner(whitelist);

        Document doc = Jsoup.parse(content, "");
        doc = cleaner.clean(doc);

        System.out.println("CleanArticle-文档清洗成功");
        return doc.toString();
    }
}
