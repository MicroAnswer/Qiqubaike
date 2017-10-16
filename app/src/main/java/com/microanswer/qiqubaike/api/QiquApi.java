package com.microanswer.qiqubaike.api;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.microanswer.qiqubaike.bean.BannerItem;
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
                requestParams.addQueryStringParameter("uc_param_str", "dnnivebichfrmintcpgieiwidsudsv");
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
                        JSONObject item = items.getJSONObject(i);
                        articleBeans.add(JSON.parseObject(articles.getJSONObject(item.getString("id")).toJSONString(), JinXuanItem.class));
                    }

                    Map<String, Object> d = new HashMap<>();
                    d.put("recoid", recoid);
                    d.put("data", articleBeans);
                    return d;
                }
                return null;
            }
        }).param(JinXuanContent);
    }
}
