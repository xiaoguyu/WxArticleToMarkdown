package com.javaedit.WxArticleToMarkdown.convert;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import com.javaedit.WxArticleToMarkdown.dto.WxArticle;
import com.javaedit.WxArticleToMarkdown.utils.ConvertConfig;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;

/**
 * @author wjw
 * @description: 下载文章中的图片保存到本地，并将图片url转成本地链接
 * @title: DownloadImg
 * @date 2022/9/2 16:53
 */
public class DownloadImg implements ArticleHandler {

    @Override
    public String handleArticle(WxArticle article) {
        String content = article.getCleanContent();
        if (StrUtil.isEmpty(content)) {
            return null;
        }

        // 根据详情页url计算文件夹名字
        String dirName = SecureUtil.md5(article.getContentUrl());
        FileUtil.mkdir(ConvertConfig.IMG_DIR + File.separator + dirName);

        Document doc = Jsoup.parse(content, "");

        // img标签中没有src
        Elements imgList = doc.getElementsByTag("img");
        for (Element imgEle : imgList) {
            String srcStr = imgEle.attr("src");
            if (StrUtil.isBlank(srcStr)) {
                srcStr = imgEle.attr("data-src");
            }
            if (StrUtil.isBlank(srcStr)) {
                continue;
            }
            String fileType = imgEle.attr("data-type");
            if (StrUtil.isBlank(fileType)) {
                fileType = "jpeg";
            }
            String imgName = SecureUtil.md5(srcStr) + "." + fileType;
            // 文件相对路径
            String relativeFilePath = dirName + File.separator + imgName;
            // 文件绝对路径
            File imgFile = new File(ConvertConfig.IMG_DIR, relativeFilePath);
            // 下载图片
            if (!FileUtil.exist(imgFile)) {
                HttpUtil.downloadFile(srcStr, imgFile);
            }

            // 替换路径
            imgEle.attr("src", relativeFilePath);
        }

        System.out.println("DownloadImg-图片下载成功");
        return doc.toString();
    }
}
