package com.microanswer.qiqubaike.other;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Microanswer on 2017/10/18.
 */

public class Tool {
    private static Map<String, Integer> EMJ_MAP = new HashMap<>();

    static {
        EMJ_MAP.put("", 0X1F601); // 呲牙笑
        EMJ_MAP.put("", 0X1F602); // 笑哭
        EMJ_MAP.put("", 0X1F603); // 笑
        EMJ_MAP.put("", 0X1F604); // 大笑
        EMJ_MAP.put("", 0X1F605); // 笑汗
        EMJ_MAP.put("", 0X1F606); // 笑斗鸡眼
        EMJ_MAP.put("", 0X1F609); // 眨眼笑
        EMJ_MAP.put("", 0X1F60A); // 腮红笑
        EMJ_MAP.put("", 0X1F60B); // 吐舌头
        EMJ_MAP.put("", 0X1F60C); // 闭眼
        EMJ_MAP.put("", 0X1F60D); // 色
        EMJ_MAP.put("", 0X1F60F); // 斜嘴
        EMJ_MAP.put("", 0X1F612); // 斜眼
        EMJ_MAP.put("", 0X1F613); // 汗
        EMJ_MAP.put("", 0X1F614); // 委屈
        EMJ_MAP.put("", 0X1F616); // 超级委屈
        EMJ_MAP.put("", 0X1F618); // 飞吻
        EMJ_MAP.put("", 0X1F61A); // 亲亲
        EMJ_MAP.put("", 0X1F61C); // 眨眼吐舌头
        EMJ_MAP.put("", 0X1F61D); // 斗鸡眼吐舌头
        EMJ_MAP.put("", 0X1F61E); // 难过
    }

    /**
     * 更具文本内容中 [doge1] 这一类型的文本 获取其对应的表情图
     *
     * @param text
     * @return
     */
    private String getEmojString(String text) {
        Integer integer = EMJ_MAP.get(text);
        if (integer == null) {
            integer = 0;
        }
        return getEmojiStringByUnicode(integer);
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
}
