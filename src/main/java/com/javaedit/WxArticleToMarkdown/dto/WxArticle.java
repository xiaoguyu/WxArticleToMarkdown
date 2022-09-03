package com.javaedit.WxArticleToMarkdown.dto;

/**
 * @author wjw
 * @description: 微信文章
 * @title: WxArticle
 * @date 2022/9/2 15:54
 */
public class WxArticle {

    /**
     * 标题
     */
    private String title;
    /**
     * html源码内容
     */
    private String content;
    /**
     * 详情url
     */
    private String contentUrl;
    /**
     * 清洗过的html内容
     */
    private String cleanContent;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getCleanContent() {
        return cleanContent;
    }

    public void setCleanContent(String cleanContent) {
        this.cleanContent = cleanContent;
    }
}
