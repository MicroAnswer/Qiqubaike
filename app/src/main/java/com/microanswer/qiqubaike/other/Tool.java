package com.microanswer.qiqubaike.other;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;

import com.microanswer.qiqubaike.R;
import com.microanswer.qiqubaike.bean.ShareObj;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Microanswer on 2017/10/18.
 */

public class Tool {
    private static Map<String, String> EMJ_MAP = null;
    private static Pattern pattern = Pattern.compile("\\[(([-+]?[1])|(\\w*))\\]");

    static {
        if (EMJ_MAP == null) {
            EMJ_MAP = new HashMap<>();
        }

        if (EMJ_MAP.size() < 1) {
            EMJ_MAP.put("[grin]", "01");
            EMJ_MAP.put("[scream]", "02");
            EMJ_MAP.put("[triumph]", "03");
            EMJ_MAP.put("[kissing_face]", "04");
            EMJ_MAP.put("[smirk]", "05");
            EMJ_MAP.put("[satisfied]", "06");
            EMJ_MAP.put("[sunglasses]", "07");
            EMJ_MAP.put("[sleepy]", "08");
            EMJ_MAP.put("[praise]", "09");
            EMJ_MAP.put("[trample]", "10");
            EMJ_MAP.put("[doge1]", "11");
            EMJ_MAP.put("[doge2]", "12");
            EMJ_MAP.put("[heart_eyes]", "13");
            EMJ_MAP.put("[big_eyes]", "14");
            EMJ_MAP.put("[thiking]", "15");
            EMJ_MAP.put("[slap]", "16");
            EMJ_MAP.put("[blush]", "17");
            EMJ_MAP.put("[smile]", "18");
            EMJ_MAP.put("[byebye]", "19");
            EMJ_MAP.put("[throwup]", "20");
            EMJ_MAP.put("[begging]", "21");
            EMJ_MAP.put("[sob]", "22");
            EMJ_MAP.put("[sleeping]", "23");
            EMJ_MAP.put("[awkward]", "24");
            EMJ_MAP.put("[screaming]", "25");
            EMJ_MAP.put("[tittering]", "26");
            EMJ_MAP.put("[despise]", "27");
            EMJ_MAP.put("[nose]", "28");
            EMJ_MAP.put("[candle]", "29");
            EMJ_MAP.put("[plane]", "30");
            EMJ_MAP.put("[dlam]", "31");
            EMJ_MAP.put("[xjj_mengbi]", "33");
            EMJ_MAP.put("[ward]", "34");
        }
    }

    /**
     * 格式化神评论或则文案内容, 因为这些内容可能有图片表情
     *
     * @param text
     * @return
     */
    public static SpannableString formatUcText(Context context, String text) {
        Matcher m = pattern.matcher(text);

        // Log.i("Pattern", text);
        SpannableString spannableString = new SpannableString(text);
        int indexStart = 0;
        while (m.find()) {
            // 匹配到
            String match = m.group();
            // Log.i("Pattern", match);
            // 获取对应路径
            String path = EMJ_MAP.get(match);
            if (TextUtils.isEmpty(path)) {
                path = "01";
            }
            path = "ucemoj/w_" + path + ".png";

            try {
                AssetManager assets = context.getAssets();
                ImageSpan imageSpan = new ImageSpan(context, BitmapFactory.decodeStream(assets.open(path)));

                indexStart = text.indexOf(match, indexStart);
                int indexEnd = indexStart + match.length();
                spannableString.setSpan(imageSpan, indexStart, indexEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                indexStart++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Log.i("Pattern", "-----------------");
        return spannableString;
    }

    /**
     * 根据 unicode 获取一个字符串,这个字符串设置到TextView里面会自动显示成一个emoj表情.
     * 当然你了这个unicode 值本身必须就是代表了某个表情
     * <p>
     * U+1F602 --> 0x1F602(使用这个)
     *
     * @param unicode 例如: 0x1F602[笑哭]
     * @return
     */
    private String getEmojiStringByUnicode(int unicode) {
        if (unicode == 0) {
            return "";
        }
        return new String(Character.toChars(unicode));
    }

    /**
     * 分享
     */
    public void showShare(Context context, ShareObj shareObj) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(shareObj.title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(shareObj.titleUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(shareObj.text);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(shareObj.imagePath);//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(shareObj.url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(shareObj.comment);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(shareObj.site);
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(shareObj.siteUrl);

        // 启动分享GUI
        oks.show(context);
    }
}
