package com.microanswer.qiqubaike.bean;

/**
 * Created by Microanswer on 2017/11/3.
 */

public class JinXuanComment {

    /**
     * hotScore : 1
     * content : 难道一言不合就开车的日子已逝？
     * id : 54263152
     * author : 麥那个誰
     * author_avatar : http://image.uc.cn/o/uop/1Ht08/;;0,uop/g/uop/avatar/1zwx9hgVh-Yx3788A.jpg;3,160
     * time : 1509642051000
     * timeShow : 1509642051000
     * is_hot : 1
     * like : 24
     */

    private int hotScore;
    private String content;
    private String id;
    private String author;
    private String author_avatar;
    private long time;
    private long timeShow;
    private int is_hot;
    private int like;

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

    public int getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(int is_hot) {
        this.is_hot = is_hot;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
}
