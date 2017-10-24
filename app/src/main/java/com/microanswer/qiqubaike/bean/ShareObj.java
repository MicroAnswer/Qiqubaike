package com.microanswer.qiqubaike.bean;

/**
 * Created by Microanswer on 2017/10/24.
 */

public class ShareObj {
    /**
     * title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
     */
    public String title = "title";
    /**
     * titleUrl是标题的网络链接，仅在人人网和QQ空间使用
     */
    public String titleUrl = "titleUrl";
    /**
     * text是分享文本，所有平台都需要这个字段
     */
    public String text = "text";
    /**
     * imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
     */
    public String imagePath = "imagePath";
    /**
     * url仅在微信（包括好友和朋友圈）中使用
     */
    public String url = "url";
    /**
     * comment是我对这条分享的评论，仅在人人网和QQ空间使用
     */
    public String comment = "comment";
    /**
     * site是分享此内容的网站名称，仅在QQ空间使用
     */
    public String site = "site";
    /**
     * siteUrl是分享此内容的网站地址，仅在QQ空间使用
     */
    public String siteUrl = "siteUrl";
}
