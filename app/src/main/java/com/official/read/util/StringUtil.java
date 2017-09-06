package com.official.read.util;

import java.text.DecimalFormat;

/**
 * com.official.read.util
 * Created by ZP on 2017/8/3.
 * description:
 * version: 1.0
 */

public class StringUtil {

    public static String moneyFor00orMoney(String money){
        if(money == null || "".equals(money) || "0".equals(money) || money.length() <= 0){
            return "--";
        }
        try {
            return "￥" + money + "万";
        } catch (Exception e) {
            return "--";
        }
    }

    /**
	 * 数字保留2位小数
	 */
    public static String decimal(Double number) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(number);
    }
}
