package com.javaedit.WxArticleToMarkdown.convert;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.javaedit.WxArticleToMarkdown.dto.WxArticle;
import com.javaedit.WxArticleToMarkdown.remark.ImageHandler;
import com.javaedit.WxArticleToMarkdown.utils.ConvertConfig;
import com.overzealous.remark.IgnoredHtmlElement;
import com.overzealous.remark.Options;
import com.overzealous.remark.Remark;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;

/**
 * @author wjw
 * @description: 将html内容转换成markdown
 * @title: HtmlToMarkdown
 * @date 2022/9/2 16:54
 */
public class HtmlToMarkdown implements ArticleHandler {

    @Override
    public String handleArticle(WxArticle article) {
        String content = article.getCleanContent();
        if (StrUtil.isEmpty(content)) {
            return null;
        }

        // 创建文章目录
        String cleanArticleName = ReUtil.delAll("[\\\\/:*?\"<>|\\.\\s]", article.getTitle());// 清除标题中的无效字符
        // 防止文章标题过长
        if (cleanArticleName.length() > 20) {
            cleanArticleName = cleanArticleName.substring(0, 20);
        }
        File dirFile = new File(ConvertConfig.CONVERT_OUT_DIR + File.separator + cleanArticleName);
        FileUtil.mkdir(dirFile);
        // 复制图片文件夹
        String imageDirName = SecureUtil.md5(article.getContentUrl());// 原始图片目录
        File srcImgDir = new File(ConvertConfig.IMG_DIR + File.separator + imageDirName);
        if (FileUtil.exist(srcImgDir)) {
            FileUtil.copyFilesFromDir(srcImgDir, new File(dirFile, imageDirName), false);
        }
        // 转换成markdown
        File articleFile = new File(dirFile, cleanArticleName + ".md");
        // 将img标签的src赋值给data-src
        Document doc = Jsoup.parse(content, "");
        Elements imgList = doc.getElementsByTag("img");
        imgList.forEach(imgEle -> {
            String srcStr = imgEle.attr("src");
            if (StrUtil.isNotBlank(srcStr)) {
                imgEle.attr("data-src", srcStr);
            }
        });

        Options markdown = Options.markdown();
        // 不清除img的data-src属性
        markdown.ignoredHtmlElements.add(IgnoredHtmlElement.create("img", "data-src"));
        Remark remark = new Remark(markdown);
        remark.getConverter().addInlineNode(new ImageHandler(), "img");

        String markdownStr = remark.convert(doc);

        // 美化下格式，如果两行都是空行，这去除一行
        StringBuilder sb = removeBlankLine(markdownStr);
        sb.append("\r\n").append("文章链接：").append(article.getContentUrl());

        FileUtil.writeUtf8String(sb.toString(), articleFile);

        System.out.println("HtmlToMarkdown-markdown转换成功");
        return null;
    }

    private StringBuilder removeBlankLine(String content) {
        StringBuilder sb = new StringBuilder();
        boolean lastBlank = false;
        for (String line : content.split("\n")) {
            if (StrUtil.isBlank(line)) {
                if (!lastBlank) {
                    sb.append(line).append("\n");
                }
                lastBlank = true;
            } else {
                sb.append(line).append("\n");
                ;
                lastBlank = false;
            }
        }
        return sb;
    }
}
