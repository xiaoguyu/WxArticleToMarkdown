package com.javaedit.WxArticleToMarkdown.convert;


import com.javaedit.WxArticleToMarkdown.dto.WxArticle;

/**
 * @author wjw
 * @description: 抽象文章处理器
 * @title: ArticleHandler
 * @date 2022/9/2 16:40
 */
public interface ArticleHandler {

    /**
     * @param article 文章对象
     * @return
     * @apiNote 处理文章，注意接口实现不允许对WxArticle做修改，调用者根据返回值自行修改WxArticle
     * @author wjw
     * @date 2022/9/2 16:41
     */
    String handleArticle(WxArticle article);

}
