package com.llwy.llwystage.utils;


import com.llwy.llwystage.base.Constants;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.finalteam.toolsfinal.StringUtils;

/**
 * 获取参数签名类
 */
public class SignUtil {

    /**
     * String  MD5加密返回32位
     *
     * @param plainText
     * @return
     */
    public static String Md5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            return buf.toString();// 32位的加密

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取签名 不区分大小写
     *
     * @param map
     * @return
     */
    public static   String  GetSign(Map<String, String> map) {
        String ts = getTimeStamp();
        map.put("Key", Constants.KEY);
        map.put("Ts", ts);

        CollatorComparator comparator = new CollatorComparator();
        // 将map.entrySet()转换成list
        List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(map.entrySet());
        // 通过比较器来实现排序
        Collections.sort(list, comparator);

        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> mapping : list) {
            stringBuffer.append(mapping.getKey()).append("=").append(mapping.getValue()).append("&");
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);

        String Sign = Md5(stringBuffer.toString());

        map.remove("Key");

        return Sign;
    }

    /**
     * 获取签名  区分大小写
     *
     * @param params
     * @return
     */
    public static String GetSignNew(Map<String, String> params) {
        String ts = getTimeStamp();
        params.put("Key", Constants.KEY);
        params.put("Ts", ts);
        //**************************排序
        Map<String, String> sortMap = new TreeMap<String, String>();
        sortMap.putAll(params);
        // 以k1=v1&k2=v2...方式拼接参数
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> s : sortMap.entrySet()) {
            String k = s.getKey();
            String v = s.getValue();
            if (StringUtils.isBlank(v)) {// 过滤空值
                continue;
            }
            builder.append(k).append("=").append(v).append("&");
        }
        if (!sortMap.isEmpty()) {
            builder.deleteCharAt(builder.length() - 1);
        }
        ///***************  排序后返回 String

        String Sign = Md5(builder.toString());

        params.remove("Key");

        return Sign;
    }

    /**
     * 获取时间戳
     *
     * @return
     */
    public static String getTimeStamp() {
        Date date = new Date();
        String str = String.valueOf(date.getTime());
        String time = str.substring(0, 10);

        return time;
    }

}