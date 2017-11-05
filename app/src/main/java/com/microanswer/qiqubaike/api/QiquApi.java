package com.microanswer.qiqubaike.api;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.microanswer.qiqubaike.bean.BannerItem;
import com.microanswer.qiqubaike.bean.JinXuanComment;
import com.microanswer.qiqubaike.bean.JinXuanItem;
import com.microanswer.qiqubaike.other.Fun;
import com.microanswer.qiqubaike.other.Promise;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 崎岖百科API接口地址
 * Created by Micro on 2017-10-2.
 */

public class QiquApi {

    private static String HOST = "https://qiqu-api.uc.cn";

    /**
     * 精选界面的banner内容接口
     */
    public static String JingXuanBanner = HOST + "/api/banner/jingxuan";

    /**
     * 精选界面条目内容接口
     */
    public static String JinXuanContent = HOST + "/qiqu/api/v1/channel/20070";

    /**
     * 精选条目内容详情借口
     */
    public static String JinXuanDetail = HOST + "/qiqu/api/v1/article/20070/{id}";

    /**
     * 获取内容评论接口
     */
    public static String JinXuanPinLun = HOST + "/interactive/v1/comment/article/{id}/byhot";

    /**
     * 获取精选分类中的banner数据
     *
     * @return
     */
    public static Promise getJinXuanBanner() {
        return new Promise(new Fun() {
            @Override
            public Object d0(Object obj) throws Throwable {
                // Log.i("HTTP - result - befor", "参数：" + obj);
                RequestParams requestParams = new RequestParams(obj.toString());
                requestParams.addQueryStringParameter("uc_param_str", "prpffrvesndsdnntnwossscp");
                String result = x.http().requestSync(HttpMethod.GET, requestParams, String.class);
                // Log.i("HTTP - result - back", result);

                // 解析服务器返回的数据
                JSONObject jsonObject = JSON.parseObject(result);
                boolean success = jsonObject.getBooleanValue("success");
                if (success) {
                    JSONArray data = jsonObject.getJSONArray("data");
                    return JSON.parseArray(data.toJSONString(), BannerItem.class);
                } else {
                    throw new Exception("服务器返回数据解析失败.");
                }
            }
        }).param(JingXuanBanner);
    }

    /**
     * 获取最新精选内容
     *
     * @return
     */
    public static Promise getJinXuanContent(final String recoid) {
        return new Promise(new Fun() {
            @Override
            public Object d0(Object obj) throws Throwable {
                RequestParams requestParams = new RequestParams(obj.toString());
                requestParams.addQueryStringParameter("uc_param_str", UUID.randomUUID().toString());
                requestParams.addQueryStringParameter("method", "new");
                requestParams.addQueryStringParameter("app", "ucqiquh5");
                requestParams.addQueryStringParameter("recoid", TextUtils.isEmpty(recoid) ? "" : recoid);
                String result = x.http().requestSync(HttpMethod.GET, requestParams, String.class);

                JSONObject jsonObject = JSON.parseObject(result);

                if ("ok".equals(jsonObject.getString("message"))) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    String recoid = data.getString("recoid");
                    JSONArray items = data.getJSONArray("items");
                    JSONObject articles = data.getJSONObject("articles");
                    List<JinXuanItem> articleBeans = new ArrayList<>();
                    for (int i = 0; i < items.size(); i++) {
                        JSONObject objectitems = items.getJSONObject(i);
                        JSONObject item = articles.getJSONObject(objectitems.getString("id"));
                        // Log.i("JSON", item.toJSONString());
                        JinXuanItem jinXuanItem = JSON.parseObject(item.toJSONString(), JinXuanItem.class);
                        if (!TextUtils.isEmpty(jinXuanItem.getSummary()) || !TextUtils.isEmpty(jinXuanItem.getTitle())) {
                            // 不知道为什么,有些条目明明是文案类型,可就是没有文案,过滤这种的

                            // 精选里面只能有图片(0)和文案(1)的内容,不能有其他的,比如视频(3)
                            if (item.getIntValue("item_type") < 2) {
                                articleBeans.add(jinXuanItem);
                            }
                        }
                    }

                    Map<String, Object> d = new HashMap<>();
                    d.put("recoid", recoid);
                    d.put("data", articleBeans);
                    // d.put("orign", jsonObject.toString());
                    return d;
                }
                return null;
            }
        }).param(JinXuanContent);
    }

    /**
     * 获取某条目的详细内容
     *
     * @param jinxuanId
     * @return
     */
    public static Promise getJinXuanDetail(String jinxuanId) {
        return new Promise(new Fun() {
            @Override
            public Object d0(Object obj) throws Throwable {
                String id = obj.toString();
                String finaurl = JinXuanDetail.replace("{id}", id);
                String ucParamStr = UUID.randomUUID().toString();

                RequestParams params = new RequestParams(finaurl);
                params.addQueryStringParameter("uc_param_str", ucParamStr);

                String s = x.http().requestSync(HttpMethod.GET, params, String.class);
                try {
                    // 解析数据
                    JSONObject jsonObject = JSON.parseObject(s);
                    if (TextUtils.equals(jsonObject.getString("message"), "ok")
                            && 0 == jsonObject.getIntValue("status")) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        return JSON.parseObject(data.toJSONString(), JinXuanItem.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).param(jinxuanId);
    }


    /**
     * 获取某条内容的评论
     *
     * @param jinxuanId
     * @return
     */
    public static Promise getJinXuanPinLun(String jinxuanId) {
        return new Promise(new Fun() {
            @Override
            public Object d0(Object obj) throws Throwable {
                String id = obj.toString();
                String finaurl = JinXuanPinLun.replace("{id}", id);
                String ucParamStr = UUID.randomUUID().toString();

                RequestParams params = new RequestParams(finaurl);
                params.addQueryStringParameter("uc_param_str", ucParamStr);
                params.addQueryStringParameter("pgn", "1");

                String s = x.http().requestSync(HttpMethod.GET, params, String.class);
                try {
                    // 解析数据
                    JSONObject jsonObject = JSON.parseObject(s);
                    if (TextUtils.equals(jsonObject.getString("message"), "ok")
                            && 0 == jsonObject.getIntValue("status")) {
                        JSONObject data = jsonObject.getJSONObject("data");

                        JSONArray ids = data.getJSONArray("comments");

                        if (ids != null && ids.size() > 0) {
                            Map<String, ArrayList<JinXuanComment>> d = new HashMap<String, ArrayList<JinXuanComment>>();
                            ArrayList<JinXuanComment> comments = new ArrayList<JinXuanComment>();
                            ArrayList<JinXuanComment> hotcomments = new ArrayList<JinXuanComment>();
                            JSONObject datas = data.getJSONObject("comments_map");

                            for (int index = 0; index < ids.size(); index++) {
                                id = ids.getString(index);
                                JinXuanComment jc = JSON.parseObject(datas.getJSONObject(id).toJSONString(), JinXuanComment.class);
                                if (jc.getIs_hot() == 1) {
                                    // 是神评论
                                    hotcomments.add(jc);
                                } else {
                                    // 不是神评论
                                    comments.add(jc);
                                }
                            }
                            d.put("hotcomments", hotcomments);
                            d.put("comments", comments);
                            return d;
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).param(jinxuanId);
    }
}
