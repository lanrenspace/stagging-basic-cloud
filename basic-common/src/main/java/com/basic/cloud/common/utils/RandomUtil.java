package com.basic.cloud.common.utils;

import java.util.Random;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public class RandomUtil {

    /**
     * 获取数字验证码
     *
     * @param len
     * @return
     */
    public static String getNumberRandom(int len) {
        Random r = new Random();
        StringBuilder rs = new StringBuilder();
        for (int i = 0; i < len; i++) {
            rs.append(r.nextInt(10));
        }
        return rs.toString();
    }


    /**
     * 获取数字英文混合验证码
     *
     * @param len
     * @return
     */
    public static String getStringRandom(int len) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            if ("char".equalsIgnoreCase(charOrNum)) {
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (random.nextInt(26) + temp));
            } else {
                val.append(random.nextInt(10));
            }
        }
        return val.toString();
    }
}
