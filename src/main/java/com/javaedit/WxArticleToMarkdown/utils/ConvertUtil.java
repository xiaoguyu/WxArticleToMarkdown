package com.javaedit.WxArticleToMarkdown.utils;

import cn.hutool.core.util.StrUtil;
import com.javaedit.WxArticleToMarkdown.convert.CleanArticle;
import com.javaedit.WxArticleToMarkdown.convert.DownloadHtml;
import com.javaedit.WxArticleToMarkdown.convert.DownloadImg;
import com.javaedit.WxArticleToMarkdown.convert.HtmlToMarkdown;
import com.javaedit.WxArticleToMarkdown.dto.WxArticle;

/**
 * @author wjw
 * @description: 转换工具类
 * @title: ConvertUtil
 * @date 2022/9/2 16:57
 */
public class ConvertUtil {

    public static void convert(WxArticle article) {
        // 下载html
        String html = new DownloadHtml().handleArticle(article);

        // 清洗
        String cleanContent = new CleanArticle().handleArticle(article);
        if (StrUtil.isNotBlank(cleanContent)) {
            article.setCleanContent(cleanContent);
        }

        // 下载图片
        String downloadedContent = new DownloadImg().handleArticle(article);
        if (StrUtil.isNotBlank(downloadedContent)) {
            article.setCleanContent(downloadedContent);
        }

        // 转换成markdown
        new HtmlToMarkdown().handleArticle(article);
    }

}
