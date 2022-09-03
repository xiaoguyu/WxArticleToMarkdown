package com.javaedit.WxArticleToMarkdown;

import com.javaedit.WxArticleToMarkdown.dto.WxArticle;
import com.javaedit.WxArticleToMarkdown.utils.ConvertUtil;

/**
 * @author wjw
 * @description:
 * @title: WxArticleToMarkDown
 * @date 2022/9/3 16:29
 */
public class WxArticleToMarkDown {

    public static void main(String[] args) throws Exception {
        // 需要转换的微信文章链接
        String url = "";
        // 图片和文章保存在D:\attach目录中，需要请自行修改ConvertConfig
        //ConvertConfig.IMG_DIR = "D:\\attach\\article_img"

        WxArticle article = new WxArticle();
        article.setContentUrl(url);

        ConvertUtil.convert(article);
    }
}
