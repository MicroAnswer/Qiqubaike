package com.microanswer.qiqubaike.bean;

/**
 * Created by Microanswer on 2017/10/16.
 */

public class BannerItem {

    /**
     *  标题
     */
    private String title;
    /**
     * 图片地址
     */
    private String imgUrl;

    /**
     * 不知道，，是空的
     */
    private String thumbnailUrl;

    /**
     * 跳转链接
     */
    private String redirectUrl;

    /**
     * -1
     */
    private int timeRangeType;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public int getTimeRangeType() {
        return timeRangeType;
    }

    public void setTimeRangeType(int timeRangeType) {
        this.timeRangeType = timeRangeType;
    }
}
