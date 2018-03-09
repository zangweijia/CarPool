package com.llwy.llwystage.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GetSign {
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

    public static List<String> getSign(Map<String, String> map) {
        List<String> lists = new ArrayList<>();

        Map<String, String> mReturnMap = new HashMap<>();


        String Ts = getTimeStamp();
        map.put("Key", "a3a665be98dc60e212365ee77979cdsh");
        map.put("Ts", Ts);

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

        mReturnMap.put("sign", Sign);
        mReturnMap.put("ts", Ts);

        return lists;
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