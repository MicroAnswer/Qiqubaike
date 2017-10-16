package com.microanswer.qiqubaike.bean;

import java.util.List;

/**
 * Created by Microanswer on 2017/10/16.
 */

public class JinXuanItem {

    private String summary;
    private String forum;
    private int cmt_cnt;
    private int share_cnt;
    private int vote_jian;
    private int vote_keng;
    private String from;
    private int vote_diao;
    private String id;
    private long grab_time;
    private String title;
    private int like_cnt;
    private int vote_ai;
    private String source_name;
    private int content_type;
    private int item_type;
    private String original_url;
    private String origin_src_name;
    private String editor_icon;
    private String editor_nickname;
    private int cid;
    private String content;
    private int dislike_cnt;
    private long publish_time;
    private String subtitle;
    private int view_cnt;
    private List<?> audios;
    private List<HotCmts> hot_cmts;
    private List<?> videos;
    private List<Thumbnails> thumbnails;
    private List<Images> images;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getForum() {
        return forum;
    }

    public void setForum(String forum) {
        this.forum = forum;
    }

    public int getCmt_cnt() {
        return cmt_cnt;
    }

    public void setCmt_cnt(int cmt_cnt) {
        this.cmt_cnt = cmt_cnt;
    }

    public int getShare_cnt() {
        return share_cnt;
    }

    public void setShare_cnt(int share_cnt) {
        this.share_cnt = share_cnt;
    }

    public int getVote_jian() {
        return vote_jian;
    }

    public void setVote_jian(int vote_jian) {
        this.vote_jian = vote_jian;
    }

    public int getVote_keng() {
        return vote_keng;
    }

    public void setVote_keng(int vote_keng) {
        this.vote_keng = vote_keng;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getVote_diao() {
        return vote_diao;
    }

    public void setVote_diao(int vote_diao) {
        this.vote_diao = vote_diao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getGrab_time() {
        return grab_time;
    }

    public void setGrab_time(long grab_time) {
        this.grab_time = grab_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLike_cnt() {
        return like_cnt;
    }

    public void setLike_cnt(int like_cnt) {
        this.like_cnt = like_cnt;
    }

    public int getVote_ai() {
        return vote_ai;
    }

    public void setVote_ai(int vote_ai) {
        this.vote_ai = vote_ai;
    }

    public String getSource_name() {
        return source_name;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }

    public int getContent_type() {
        return content_type;
    }

    public void setContent_type(int content_type) {
        this.content_type = content_type;
    }

    public int getItem_type() {
        return item_type;
    }

    public void setItem_type(int item_type) {
        this.item_type = item_type;
    }

    public String getOriginal_url() {
        return original_url;
    }

    public void setOriginal_url(String original_url) {
        this.original_url = original_url;
    }

    public String getOrigin_src_name() {
        return origin_src_name;
    }

    public void setOrigin_src_name(String origin_src_name) {
        this.origin_src_name = origin_src_name;
    }

    public String getEditor_icon() {
        return editor_icon;
    }

    public void setEditor_icon(String editor_icon) {
        this.editor_icon = editor_icon;
    }

    public String getEditor_nickname() {
        return editor_nickname;
    }

    public void setEditor_nickname(String editor_nickname) {
        this.editor_nickname = editor_nickname;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDislike_cnt() {
        return dislike_cnt;
    }

    public void setDislike_cnt(int dislike_cnt) {
        this.dislike_cnt = dislike_cnt;
    }

    public long getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(long publish_time) {
        this.publish_time = publish_time;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getView_cnt() {
        return view_cnt;
    }

    public void setView_cnt(int view_cnt) {
        this.view_cnt = view_cnt;
    }

    public List<?> getAudios() {
        return audios;
    }

    public void setAudios(List<?> audios) {
        this.audios = audios;
    }

    public List<HotCmts> getHot_cmts() {
        return hot_cmts;
    }

    public void setHot_cmts(List<HotCmts> hot_cmts) {
        this.hot_cmts = hot_cmts;
    }

    public List<?> getVideos() {
        return videos;
    }

    public void setVideos(List<?> videos) {
        this.videos = videos;
    }

    public List<Thumbnails> getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(List<Thumbnails> thumbnails) {
        this.thumbnails = thumbnails;
    }

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    public static class HotCmts {
        /**
         * content : 还好，上次我半条命都没了
         * nick_name : 魏钱钱钱
         * id : 53970827
         * upcnt : 127
         */

        private String content;
        private String nick_name;
        private String id;
        private int upcnt;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getUpcnt() {
            return upcnt;
        }

        public void setUpcnt(int upcnt) {
            this.upcnt = upcnt;
        }
    }

    public static class Thumbnails {
        /**
         * height : 888
         * width : 500
         * type : JPEG
         * url : http://image.uc.cn/s/nav/s/upload/1710150927a4e80e478793c1f9193aae94e56aa60ax500x888x59.jpeg
         */

        private int height;
        private int width;
        private String type;
        private String url;

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class Images {
        /**
         * title :
         * index : 0
         * height : 888
         * width : 500
         * focus : 50_50
         * type : JPEG
         * url : http://image.uc.cn/s/nav/g/qiqu/2015/95bfd2ff7a7663ce38d86d0eec533c56x500x888x59.jpeg
         */

        private String title;
        private int index;
        private int height;
        private int width;
        private String focus;
        private String type;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public String getFocus() {
            return focus;
        }

        public void setFocus(String focus) {
            this.focus = focus;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    @Override
    public String toString() {
        return "JinXuanItem{" +
                "summary='" + summary + '\'' +
                ", forum='" + forum + '\'' +
                ", cmt_cnt=" + cmt_cnt +
                ", share_cnt=" + share_cnt +
                ", vote_jian=" + vote_jian +
                ", vote_keng=" + vote_keng +
                ", from='" + from + '\'' +
                ", vote_diao=" + vote_diao +
                ", id='" + id + '\'' +
                ", grab_time=" + grab_time +
                ", title='" + title + '\'' +
                ", like_cnt=" + like_cnt +
                ", vote_ai=" + vote_ai +
                ", source_name='" + source_name + '\'' +
                ", content_type=" + content_type +
                ", item_type=" + item_type +
                ", original_url='" + original_url + '\'' +
                ", origin_src_name='" + origin_src_name + '\'' +
                ", editor_icon='" + editor_icon + '\'' +
                ", editor_nickname='" + editor_nickname + '\'' +
                ", cid=" + cid +
                ", content='" + content + '\'' +
                ", dislike_cnt=" + dislike_cnt +
                ", publish_time=" + publish_time +
                ", subtitle='" + subtitle + '\'' +
                ", view_cnt=" + view_cnt +
                ", audios=" + audios +
                ", hot_cmts=" + hot_cmts +
                ", videos=" + videos +
                ", thumbnails=" + thumbnails +
                ", images=" + images +
                '}';
    }
}
