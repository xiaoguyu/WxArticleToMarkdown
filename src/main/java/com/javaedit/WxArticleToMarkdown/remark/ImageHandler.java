package com.javaedit.WxArticleToMarkdown.remark;

import com.overzealous.remark.convert.AbstractNodeHandler;
import com.overzealous.remark.convert.DocumentConverter;
import com.overzealous.remark.convert.NodeHandler;
import com.overzealous.remark.util.BlockWriter;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;

/**
 * @author wjw
 * @description: 图片处理器
 * @title: ImageHandler
 * @date 2022/9/3 10:08
 */
public class ImageHandler extends AbstractNodeHandler {

    /**
     * Creates a link reference to an image, and renders the correct output.
     *
     * @param parent    The previous node walker, in case we just want to remove an element.
     * @param node      Node to handle
     * @param converter Parent converter for this object.
     */
    public void handleNode(NodeHandler parent, Element node, DocumentConverter converter) {
        String url = converter.getCleaner().cleanUrl(node.attr("src"));
        if (StringUtils.isBlank(url)) {
            url = converter.getCleaner().cleanUrl(node.attr("data-src"));
        }
        String alt = node.attr("alt");
        if (alt == null || alt.trim().length() == 0) {
            alt = node.attr("title");
            if (alt == null) {
                alt = "";
            }
        }
        alt = converter.getCleaner().clean(alt.trim());
        if (converter.getOptions().inlineLinks) {
            if (alt.length() == 0) {
                alt = "Image";
            }
            converter.getOutput().printf("![%s](%s)", alt, url);
        } else {
            String linkId = converter.addLink(url, alt, true);
            // give a usable description based on filename whenever possible
            if (alt.length() == 0) {
                alt = linkId;
            }
            BlockWriter out = converter.getOutput();
            if (alt.equals(linkId)) {
                out.printf("![%s][]", linkId);
            } else {
                out.printf("![%s][%s]", alt, linkId);
            }
        }
    }
}