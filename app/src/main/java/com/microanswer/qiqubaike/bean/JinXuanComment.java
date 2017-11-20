package com.microanswer.qiqubaike.bean;

/**
 * Created by Microanswer on 2017/11/3.
 */

public class JinXuanComment {

    /**
     * hotScore : 1
     * content : 可能是来不及，小孩这样蛮正常的，小孩会忍不住的，有时候很急又找不到，尿在垃圾桶比随地尿好多了
     * id : 54439417
     * author : 奇友03042
     * parent_content : 这么大的商场没卫生间给你用？
     * author_avatar : http://image.uc.cn/s/uae/g/0q/product/anonymous1.png
     * time : 1510726275000
     * timeShow : 1510726275000
     * parent_author :
     * like : 13
     * parent_id : 54343030
     */

    private int hotScore;
    private String content;
    private String id;
    private String author;
    private String parent_content;
    private String author_avatar;
    private long time;
    private long timeShow;
    private int is_hot;
    private String parent_author;
    private int like;
    private String parent_id;

    public int getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(int is_hot) {
        this.is_hot = is_hot;
    }

    public int getHotScore() {
        return hotScore;
    }

    public void setHotScore(int hotScore) {
        this.hotScore = hotScore;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getParent_content() {
        return parent_content;
    }

    public void setParent_content(String parent_content) {
        this.parent_content = parent_content;
    }

    public String getAuthor_avatar() {
        return author_avatar;
    }

    public void setAuthor_avatar(String author_avatar) {
        this.author_avatar = author_avatar;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTimeShow() {
        return timeShow;
    }

    public void setTimeShow(long timeShow) {
        this.timeShow = timeShow;
    }

    public String getParent_author() {
        return parent_author;
    }

    public void setParent_author(String parent_author) {
        this.parent_author = parent_author;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }
}
